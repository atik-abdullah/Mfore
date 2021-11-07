package com.mfore.core.representations;

import java.util.Date;

public class User {
	private final int user_id;
	private final String user_name;
	private final String password_hash;
	private final String password_salt;	
	private final Date registered_on;


	public User() {
		this.user_id= 0;
		this.user_name= null;
		this.password_hash= null;
		this.password_salt= null;
		this.registered_on= null;
	}
	
	public User(
			int user_id,
			String user_name,
			String password_hash,
			String password_salt,
			Date registered_on) {
		
		this.user_id= user_id;
		this.user_name = user_name;
		this.password_hash = password_hash;
		this.password_salt = password_salt;
		this.registered_on = registered_on;
	}

	public int getUser_id() {
		return user_id;
	}

	public String getUser_name() {
		return user_name;
	}

	public String getPassword_hash() {
		return password_hash;
	}

	public String getPassword_salt() {
		return password_salt;
	}

	public Date getRegistered_on() {
		return registered_on;
	}

}
