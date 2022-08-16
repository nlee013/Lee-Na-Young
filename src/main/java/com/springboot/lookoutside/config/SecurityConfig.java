package com.springboot.lookoutside.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.springboot.lookoutside.config.jwt.JwtAuthenticationFilter;
import com.springboot.lookoutside.config.jwt.JwtAuthorizationFilter;
import com.springboot.lookoutside.config.oauth.PrincipalOauth2UserService;
import com.springboot.lookoutside.repository.UserRepository;

@Configuration // 빈 등록 (Ioc)
@EnableWebSecurity // 필터 
public class SecurityConfig {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	AuthenticationConfiguration authenticationConfiguration;
	
	@Autowired
	private PrincipalOauth2UserService principalOauth2UserService;
	
	@Bean // Ioc
	public BCryptPasswordEncoder endodePw() {
		return new BCryptPasswordEncoder();
	}
	
	//시큐리티가 대신 로그인해주는데 pw가 해쉬되어 있는 상태로 비교
	/*
	AuthenticationManager authenticationManager(AuthenticationManagerBuilder auth) throws Exception {
		return auth.userDetailsService(principalDetailService).passwordEncoder(endodePw()).and().build(); // 해쉬된 비밀번호 비교
	}
	*/
	
	@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
        	//.addFilterBefore(new MyFilter1(), UsernamePasswordAuthenticationFilter.class) // 시큐리티 필터 전이나 후로만 걸 수 있다.
        	.cors().configurationSource(corsConfigurationSource()) //cors 설정
        .and()
	        .csrf().disable() // csrf 토큰 비활성화 (테스트시 비활성화)
	        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) //세션을 사용하지 않겠다.
	    .and()
	        .httpBasic().disable()
	        .formLogin().disable()
	        .addFilter(jwtAuthorizationFilter())
	        .addFilter(new JwtAuthenticationFilter(authenticationManager()))
	        .addFilter(new JwtAuthorizationFilter(authenticationManager(), userRepository))
	        .authorizeRequests()
	        .antMatchers("/","/user/**","/manager/**","/region/**","/article/**").permitAll()// user로 들어오는 경로 모두 허용
			.antMatchers("/admin/**").hasRole("ADMIN") // Admin만 가능
			.anyRequest().authenticated()
		.and()
    		.oauth2Login().loginPage("/")
    		.userInfoEndpoint()
    		.userService(principalOauth2UserService);// 구글 로그인이 완료된 뒤 후처리; // 다른 요청은 인증이 되어야한다.

        return http.build(); 
    }
	
	@Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
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
	
	//login 경로 변경 필터
	public JwtAuthenticationFilter jwtAuthorizationFilter() throws Exception {
	    JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager());
	    jwtAuthenticationFilter.setFilterProcessesUrl("/user/sign-in");
	    return jwtAuthenticationFilter;
	}
	
}
