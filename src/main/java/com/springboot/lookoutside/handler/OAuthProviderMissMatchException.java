package com.springboot.lookoutside.handler;

public class OAuthProviderMissMatchException extends RuntimeException{

    public OAuthProviderMissMatchException(String message) {
        super(message);
    }

    public OAuthProviderMissMatchException(String message, Throwable cause) {
        super(message, cause);
    }

    public OAuthProviderMissMatchException(Throwable cause) {
        super(cause);
    }
    
}
