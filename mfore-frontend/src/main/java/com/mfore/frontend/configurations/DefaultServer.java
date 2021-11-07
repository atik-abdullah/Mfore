package com.mfore.frontend.configurations;

import com.fasterxml.jackson.annotation.JsonProperty;


public class DefaultServer {

    private String type;

    private int maxThreads;

    @JsonProperty
    public String getType() {
        return type;
    }
    
    @JsonProperty
    public int getMaxThreads() {
        return maxThreads;
    }

}
