package com.mfore.frontend.configurations;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ServerConfiguration{

    @JsonProperty("port")
    private int port;

    @JsonProperty("type")
    private String type;
    
    public int getPort() {
        return port;
    }

	public String getType() {
		return type;
	}
}
