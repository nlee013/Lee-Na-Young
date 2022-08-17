package com.springboot.lookoutside.service;

import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.springboot.lookoutside.config.jwt.JwtTokenUtil;
import com.springboot.lookoutside.config.jwt.JwtUserDetails;
import com.springboot.lookoutside.config.oauth.OAuth2UserInfo;
import com.springboot.lookoutside.config.oauth.OAuth2UserInfoFactory;
import com.springboot.lookoutside.domain.Provider;
import com.springboot.lookoutside.domain.User;
import com.springboot.lookoutside.handler.OAuthProviderMissMatchException;
import com.springboot.lookoutside.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomOauth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    private final JwtTokenUtil jwtTokenUtil;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        System.out.println("attributes" + super.loadUser(userRequest).getAttributes());
        OAuth2User user = super.loadUser(userRequest);
		//여기서 attriutes를 찍어보면 모두 각기 다른 이름으로 데이터가 들어오는 것을 확인할 수 있다. 
        try{
            return process(userRequest, user);
        } catch (AuthenticationException ex){
            throw new OAuth2AuthenticationException(ex.getMessage());
        } catch (Exception ex){
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }
    }

	//인증을 요청하는 사용자에 따라서 없는 회원이면 회원가입, 이미 존재하는 회원이면 업데이트를 실행한다. 
    private OAuth2User process(OAuth2UserRequest userRequest, OAuth2User user) {
        Provider provider = Provider.valueOf(userRequest.getClientRegistration().getRegistrationId().toUpperCase());
		//provider타입에 따라서 각각 다르게 userInfo가져온다. (가져온 필요한 정보는 OAuth2UserInfo로 동일하다)
        OAuth2UserInfo userInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(provider, user.getAttributes());

        User savedUser = userRepository.findByUseEmail2(userInfo.getEmail());

        if (savedUser != null) {
            if (provider != savedUser.getProvider()) {
                throw new OAuthProviderMissMatchException (
                        "Looks like you're signed up with " + provider +
                                " account. Please use your " + savedUser.getProvider() + " account to login."
                );
            }
            updateUser(savedUser, userInfo);
        } else {
            savedUser = createUser(userInfo, provider);
        }
        System.out.println(jwtTokenUtil.generateToken(userInfo.getEmail()));

        return new JwtUserDetails(savedUser, user.getAttributes());
    }

	//넘어온 사용자 정보를 통해서 회원가입을 실행한다. 
    private User createUser(OAuth2UserInfo userInfo, Provider provider) {
        User user = new User();
        user.setUseName(userInfo.getFirstName() + userInfo.getLastName());
        user.setUsePw("");
        user.setUseEmail(userInfo.getEmail());
        user.setProvider(provider);

        return userRepository.save(user);
    }

	//사용자정보에 변경이 있다면 사용자 정보를 업데이트 해준다. 
    private User updateUser(User user, OAuth2UserInfo userInfo) {
        if ((userInfo.getFirstName() + userInfo.getLastName()) != null && !user.getUseName().equals(userInfo.getName())) {
            user.setUseName(userInfo.getFirstName() + userInfo.getLastName());
        }

        return user;
    }
}
