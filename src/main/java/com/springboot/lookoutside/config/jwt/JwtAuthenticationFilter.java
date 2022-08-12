package com.springboot.lookoutside.config.jwt;

import java.io.IOException;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.lookoutside.config.auth.PrincipalDetails;
import com.springboot.lookoutside.domain.User;

import lombok.RequiredArgsConstructor;

//스프링 시큐리티에서 UsernmaePasswordAuthencationFilter가 있음
//login 요청해서 username, password 전송하면 (post)
//UsernamePasswordAuthenticationFilter 동작을 한다.

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {

        try {
            ObjectMapper om = new ObjectMapper();
            User user = om.readValue(request.getInputStream(), User.class);

            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(user.getUseId(), user.getUsePw());

            Authentication authentication =
            		authenticationManager.authenticate(authenticationToken);
            
            PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
            System.out.println(principalDetails.getUser().getUseId());
            
            return authentication;

        } catch (IOException e) {
             e.printStackTrace();
        }
        
        return null;
    }

	// JWT Token 생성해서 response에 담아주기
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		
		PrincipalDetails principalDetailis = (PrincipalDetails) authResult.getPrincipal();
		
		String jwtToken = JWT.create()
				.withSubject(principalDetailis.getUsername())
				.withExpiresAt(new Date(System.currentTimeMillis()+JwtProperties.EXPIRATION_TIME))
				.withClaim("useId", principalDetailis.getUser().getUseId())
				.withClaim("useNick", principalDetailis.getUser().getUseNick())
				.sign(Algorithm.HMAC512(JwtProperties.SECRET));
		
		response.addHeader(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX+jwtToken);
		response.getWriter().write("{" +
					                "\"useId\":" + '"' +principalDetailis.getUser().getUseId() +
					                ",\"useNick\":" + '"' +principalDetailis.getUser().getUseNick() + '"' +
					                ",\"jwtToken\":"  + '"' +jwtToken + '"' +
					                '}');
		
	}
	
}