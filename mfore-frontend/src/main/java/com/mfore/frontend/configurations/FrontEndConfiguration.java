package com.mfore.frontend.configurations;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;

public class FrontEndConfiguration extends Configuration{


	@JsonProperty
	private String message;

	@JsonProperty
	private int messageRepetitions;
	
	@JsonProperty
	private String type;

	@JsonProperty
	private int maxThreads;

	//private DefaultServer ds = new DefaultServer();
	
	public String getType() {
		return type;
	}

	public int getMaxThreads() {
		return maxThreads;
	}

	public String getMessage() {
		return message;
	}

	public int getMessageRepetitions() {
		return messageRepetitions;
	}

	@JsonProperty
	private DataSourceFactory database = new DataSourceFactory();

	public DataSourceFactory getDataSourceFactory() {
		return database;
	}
	
/*    @JsonProperty("server")
    public DefaultServer getDefaultServer() {
        return ds;
    }*/

    @Valid
    @NotNull
    @JsonProperty("server")
    private ServerConfiguration httpConfiguration = new ServerConfiguration();

    public ServerConfiguration getHttpConfiguration() {
        return httpConfiguration;
    }
    
}
