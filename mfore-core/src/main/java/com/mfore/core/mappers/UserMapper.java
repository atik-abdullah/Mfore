package com.mfore.core.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import com.mfore.core.representations.User;

public class UserMapper implements ResultSetMapper<User>{
	public User map(int index, ResultSet r,StatementContext ctx)throws SQLException {
		
		return new User(r.getInt("user_id"), 
				r.getString("user_name"),
				r.getString("password_hash"),
				r.getString("password_salt"),
				r.getDate("registered_on")
				);
	}
}