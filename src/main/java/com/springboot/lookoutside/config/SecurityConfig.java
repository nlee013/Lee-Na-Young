package com.springboot.lookoutside.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.springboot.lookoutside.config.auth.PrincipalDetailService;

@Configuration // 빈 등록 (Ioc)
@EnableWebSecurity // 필터 
@EnableGlobalMethodSecurity(prePostEnabled = true) // 특정 주소로 접근 시 권한 및 인증을 미리 체크
public class SecurityConfig {

	@Autowired
	private PrincipalDetailService principalDetailService;
	
	@Bean // Ioc
	public BCryptPasswordEncoder endodePw() {
		return new BCryptPasswordEncoder();
	}
	
	//시큐리티가 대신 로그인해주는데 pw가 해쉬되어 있는 상태로 비교
	
	AuthenticationManager authenticationManager(AuthenticationManagerBuilder auth) throws Exception {
		return auth.userDetailsService(principalDetailService).passwordEncoder(endodePw()).and().build(); // 해쉬된 비밀번호 비교
	}
	
	@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
	        .httpBasic().disable()
	        .cors().configurationSource(corsConfigurationSource())
	        .and()
        	.csrf().disable() // csrf 토큰 비활성화 (테스트시 비활성화)
	        .authorizeRequests()
				.antMatchers("/","/user/**","/manager/**").permitAll()// user로 들어오는 경로 모두 허용
				//.antMatchers("/manager/**").hasRole("ADMIN") // Admin만 가능
				.anyRequest().authenticated() // 다른 요청은 인증이 되어야한다.
        	.and()
        		.formLogin()
        		.successHandler(new CustomAuthenticationSuccessHandler())
        		 // .loginPage("/login") //로그인페이지 주소
        		.loginProcessingUrl("/user/sign-in"); // 스프링 시큐리티가 해당 주소로 요청오는 로그인을 가로채서 대신 로그인 해준다.
//        		.defaultSuccessUrl("/"); //성공시 데려갈 주소
        		
        
        		
        return http.build();
    }
	
	@Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.addAllowedOrigin("*");
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
