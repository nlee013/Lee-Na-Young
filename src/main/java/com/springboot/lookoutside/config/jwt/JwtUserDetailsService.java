package com.springboot.lookoutside.config.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.lookoutside.domain.User;
import com.springboot.lookoutside.repository.UserRepository;


@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String useId) throws UsernameNotFoundException {

    	User user = userRepository.findByUseId(useId)
				.orElseThrow(()-> {
					return new UsernameNotFoundException("해당 사용자를 찾을 수 없습니다." + useId);
				});

    	return new JwtUserDetails(user);
    }
}
