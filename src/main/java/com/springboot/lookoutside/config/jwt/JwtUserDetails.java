package com.springboot.lookoutside.config.jwt;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.springboot.lookoutside.domain.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class JwtUserDetails implements UserDetails, OAuth2User {

    private User user;
    
    private Map<String, Object> attributes;

    public JwtUserDetails(User user) {
        super();
        this.user = user;
    }
    
    public JwtUserDetails(User user, Map<String, Object> attributes) {
        this.user = user;
        this.attributes = attributes;
    }

    @Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		Collection<GrantedAuthority> collectors = new ArrayList<GrantedAuthority>();
		/*
		collectors.add(new GrantedAuthority() {
			
			@Override
			public String getAuthority() {
				
				return "ROLE_" + user.getUseRole(); // ROLE_ 꼭 붙여야한다. //ROLE_USER
			}
		});
		*/
		//위 아래가 같은 함수 
		
		collectors.add(() -> {
			return "ROLE_" + user.getUseRole();
		});
		
		return collectors;
	}

    @Override
    public String getPassword() {
        return user.getUsePw();
    }

    @Override
    public String getUsername() {
        return user.getUseId();
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


	@Override
	public Map<String, Object> getAttributes() {
		
		return attributes;
	}


	@Override
	public String getName() {
		
		return null;
	}


}