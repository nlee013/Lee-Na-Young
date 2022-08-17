package com.springboot.lookoutside.config.jwt;

import java.io.Serializable;

public class JwtRequest implements Serializable {

    private static final long serialVersionUID = 5926468583005150707L;

    private String useId;
    private String usePw;

    //need default constructor for JSON Parsing
    public JwtRequest() {   }

    public JwtRequest(String useId, String usePw) {
        this.setUseId(useId);
        this.setUsePw(usePw);
    }

    public String getUseId() {
        return this.useId;
    }

    public void setUseId(String useId) {
        this.useId = useId;
    }

    public String getUsePw() {
        return this.usePw;
    }

    public void setUsePw(String usePw) {
        this.usePw = usePw;
    }
}