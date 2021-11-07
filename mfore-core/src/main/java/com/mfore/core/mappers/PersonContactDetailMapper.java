package com.mfore.core.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import com.mfore.core.representations.PersonContactDetail;

public class PersonContactDetailMapper implements ResultSetMapper<PersonContactDetail>{
	public PersonContactDetail map(int index, ResultSet r,StatementContext ctx)throws SQLException {
		
		return new PersonContactDetail(r.getInt("person_contact_detail_id"), 
				r.getString("email"),
				r.getString("phone"),
				r.getString("street_address"),
				r.getString("postal_code"),
				r.getString("city"),
				r.getString("country")
				);
	}
}
