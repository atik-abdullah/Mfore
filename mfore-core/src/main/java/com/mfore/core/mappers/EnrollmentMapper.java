package com.mfore.core.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import com.mfore.core.representations.Enrollment;

public class EnrollmentMapper implements ResultSetMapper<Enrollment>{
	public Enrollment map(int index, ResultSet r,StatementContext ctx)throws SQLException {
		
		return new Enrollment(r.getDate("enrollment_date"), 
				r.getInt("fk_person_id"),
				r.getInt("fk_service_id")
				);
	}
}
