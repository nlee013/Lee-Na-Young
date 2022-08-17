package com.springboot.lookoutside.config.oauth;

import java.util.Map;

//provider에 따라 각각 정보가 다르게 들어온다.
public abstract class OAuth2UserInfo {
	
    protected Map<String, Object> attributes;

    public OAuth2UserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public abstract String getId();

    public abstract String getName();

    public abstract String getEmail();

    public abstract String getImageUrl();

    public abstract String getFirstName();

    public abstract String getLastName();
}
