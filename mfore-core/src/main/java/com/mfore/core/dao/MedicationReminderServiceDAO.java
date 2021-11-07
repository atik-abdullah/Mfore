package com.mfore.core.dao;

import java.sql.Time;
import java.util.List;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.GetGeneratedKeys;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;

import com.mfore.core.mappers.MedicationReminderServiceMapper;
import com.mfore.core.mappers.PersonMapper;
import com.mfore.core.representations.MedicationReminderService;
import com.mfore.core.representations.Person;

public interface MedicationReminderServiceDAO {

	@Mapper(MedicationReminderServiceMapper.class)
	@SqlQuery("select * from `medication_reminder_service` where fk_person_id= :id")
	List<MedicationReminderService> getMedicationReminderServiceByPersonId(@Bind("id") int id);
	
	@GetGeneratedKeys
	@SqlUpdate("insert into `medication_reminder_service` "
			+ "(medicine_desc, reminding_time, fk_person_id) "
			+ "values (:medicine_desc, :reminding_time, :fk_person_id)")
	int createMedicationReminderService(
			@Bind("medicine_desc") String medicine_desc, 
			@Bind("reminding_time") String reminding_time,
			@Bind("fk_person_id") int fk_person_id);

	@SqlUpdate("update `medication_reminder_service` set medicine_desc = :medicine_desc,  "
			+ " reminding_time = :reminding_time, fk_person_id = :fk_person_id where fk_person_id = :fk_person_id")
	int updateMedicationReminderService(
			@Bind("fk_person_id") int fk_person_id,			
			@Bind("medicine_desc") String medicine_desc,
			@Bind("reminding_time") String reminding_time);
	
	@GetGeneratedKeys
	@SqlUpdate("delete from `medication_reminder_service` where fk_person_id= :fk_person_id")
	int deleteMedicationReminderService(@Bind("fk_person_id") int fk_person_id);
	
}
