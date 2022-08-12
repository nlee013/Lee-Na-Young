package com.springboot.lookoutside.config.auth;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.springboot.lookoutside.domain.User;

import lombok.Data;

// 스프링 시큐리티가 로그인 요청을 가로채서 로그인을 진행하고 완료가 되면 UserDetails 타입의 오브젝트를
// 스프링 시큐리티의 고유한 세션저장소에 저장을 해준다. 그 때 저장되는곳이 PricipalDetail
@Data
public class PrincipalDetails implements UserDetails{
	private User user;
	
	public PrincipalDetails(User user) {
		this.user = user;
	}

	@Override
	public String getPassword() {
		
		return user.getUsePw();
	}

	@Override
	public String getUsername() {
		
		return user.getUseId();
	}
	
	public int getUseNo() {
		
		return user.getUseNo();
	}
	
	public String getUserNick() {
		
		return user.getUseNick();
	}

	//계정이 만료되지 않았는지 리턴. (true : 만료안됨)
	@Override
	public boolean isAccountNonExpired() {
		
		return true;
	}

	//계정이 잠겨있는지 리턴 ( true : 잠기지 않음)
	@Override
	public boolean isAccountNonLocked() {
		
		return true;
	}

	// 비밀번호가 만료되지 않았는지 리턴 (true : 만료안됨)
	@Override
	public boolean isCredentialsNonExpired() {
		
		return true;
	}

	//계정이 활성화(사용가능) 리턴 (true : 활성화)
	@Override
	public boolean isEnabled() {
		
		return true;
	}
	
	//계정의 권한 리턴
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

	
	
}
