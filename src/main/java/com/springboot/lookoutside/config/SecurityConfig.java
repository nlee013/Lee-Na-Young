package com.springboot.lookoutside.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.springboot.lookoutside.config.jwt.JwtAuthenticationEntryPoint;
import com.springboot.lookoutside.config.jwt.JwtAuthenticationFilter;
import com.springboot.lookoutside.config.jwt.JwtRequestFilter;
//import com.springboot.lookoutside.config.jwt.JwtRequestFilter;
import com.springboot.lookoutside.config.jwt.JwtUserDetailsService;
import com.springboot.lookoutside.handler.OAuth2AuthenticationFailureHandler;
import com.springboot.lookoutside.handler.OAuth2SuccessHandler;
import com.springboot.lookoutside.repository.HttpCookieOAuth2AuthorizationRequestRepository;
import com.springboot.lookoutside.service.CustomOauth2UserService;

import lombok.AllArgsConstructor;

@Configuration // 빈 등록 (Ioc)
@AllArgsConstructor //없어서 오류났었음 휴;;
@EnableWebSecurity // 필터 
public class SecurityConfig {
	
	@Autowired
	AuthenticationConfiguration authenticationConfiguration;
	
	@Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;
    
    @Autowired
    private CustomOauth2UserService customOauth2UserService;

    @Autowired
    private OAuth2SuccessHandler oAuth2SuccessHandler;

    @Autowired
    private OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;

    @Autowired
    private HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;

    @Bean
    public HttpCookieOAuth2AuthorizationRequestRepository cookieAuthorizationRequestRepository() {
        return new HttpCookieOAuth2AuthorizationRequestRepository();
    }
	
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
	        .authorizeRequests()
	        .antMatchers("/**","/user/**","/manager/**","/region/**","/article/**").permitAll()// user로 들어오는 경로 모두 허용
			.antMatchers("/admin/**").hasRole("ADMIN") // Admin만 가능
			.anyRequest().authenticated()
		.and()
			.exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
		.and()
    		.oauth2Login()
    		.authorizationEndpoint()
            .authorizationRequestRepository(cookieAuthorizationRequestRepository())
            .baseUri("/oauth2/authorization")
        .and()
            .redirectionEndpoint()
            .baseUri("/oauth2/callback/*")
        .and()
            .userInfoEndpoint()
            .userService(customOauth2UserService)
        .and()
            .successHandler(oAuth2SuccessHandler)
            .failureHandler((AuthenticationFailureHandler) oAuth2AuthenticationFailureHandler);
        
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        
        return http.build(); 
    }
	
	@Bean // Ioc
	public BCryptPasswordEncoder endodePw() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
	
	@Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(jwtUserDetailsService);
        authProvider.setPasswordEncoder(endodePw());

        return authProvider;
    }

	//CORS config
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
	   // jwtAuthenticationFilter.setFilterProcessesUrl("/user/sign-in");
	    return jwtAuthenticationFilter;
	}
	
}
