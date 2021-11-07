package com.mfore.core.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import com.mfore.core.representations.Person;

public class PersonMapper  implements ResultSetMapper<Person>{
	public Person map(int index, ResultSet r,StatementContext ctx)throws SQLException {
		
		return new Person(r.getInt("person_id"), 
				r.getInt("fk_user_id"),
				r.getInt("fk_identification_type_id"),
				r.getInt("fk_person_contact_detail_id"),
				r.getString("first_name"),
				r.getString("last_name"),
				r.getString("identification_number")
				);
	}
}
