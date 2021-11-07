package com.mfore.core.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import com.mfore.core.representations.MedicationReminderService;

public class MedicationReminderServiceMapper  implements ResultSetMapper<MedicationReminderService>{
	public MedicationReminderService map(int index, ResultSet r,StatementContext ctx)throws SQLException {
		
		return new MedicationReminderService(
				r.getInt("medication_reminder_service_id"), 
				r.getString("medicine_desc"),
				r.getString("reminding_time"),
				r.getInt("fk_person_id")
				);
	}
}
