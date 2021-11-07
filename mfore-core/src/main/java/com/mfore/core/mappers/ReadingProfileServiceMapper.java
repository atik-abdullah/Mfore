package com.mfore.core.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import com.mfore.core.representations.MedicationReminderService;
import com.mfore.core.representations.ReadingProfileService;

public class ReadingProfileServiceMapper implements ResultSetMapper<ReadingProfileService>{
	public ReadingProfileService map(int index, ResultSet r,StatementContext ctx)throws SQLException {
		
		return new ReadingProfileService(
				r.getInt("reading_profile_service_id"), 
				r.getString("reading_days"),
				r.getInt("reading_type"),
				r.getInt("fk_person_id")
				);
	}
}
