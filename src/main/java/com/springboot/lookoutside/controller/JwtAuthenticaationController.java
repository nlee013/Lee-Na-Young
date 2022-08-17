package com.springboot.lookoutside.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.lookoutside.config.jwt.JwtRequest;
import com.springboot.lookoutside.config.jwt.JwtTokenUtil;
import com.springboot.lookoutside.config.jwt.JwtUserDetailsService;

@RestController
public class JwtAuthenticaationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @PostMapping("/authenticate")
    public String createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception{
        authenticate(authenticationRequest.getUseId(), authenticationRequest.getUsePw());

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUseId());

//        final String token = jwtTokenUtil.generateToken(userDetails);

        return jwtTokenUtil.generateToken(authenticationRequest.getUseId());

    }

    private void authenticate(String useId, String usePw) throws Exception {
        try {
            Authentication authentication=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(useId, usePw));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            System.out.println(authentication.getAuthorities().toString());

        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }


}
