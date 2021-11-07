package com.mfore.core.dao;

import java.sql.Time;
import java.util.List;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.GetGeneratedKeys;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;

import com.mfore.core.mappers.ReadingProfileServiceMapper;
import com.mfore.core.representations.ReadingProfileService;

public interface ReadingProfileServiceDAO {

	@Mapper(ReadingProfileServiceMapper.class)
	@SqlQuery("select * from `reading_profile_service` where fk_person_id= :id")
	List <ReadingProfileService> getReadingProfileServiceByPersonId(@Bind("id") int id);
	
	@GetGeneratedKeys
	@SqlUpdate("insert into `reading_profile_service` "
			+ "(reading_days, reading_type, fk_person_id) "
			+ "values (:reading_days, :reading_type, :fk_person_id)")
	int createReadingProfileService(
			@Bind("reading_days") String reading_days, 
			@Bind("reading_type") int reading_type,
			@Bind("fk_person_id") int fk_person_id);

	@SqlUpdate("update `reading_profile_service` set reading_days = :reading_days,  "
			+ " reading_type = :reading_type, fk_person_id = :fk_person_id where fk_person_id = :fk_person_id")
	int updateReadingProfileService(
			@Bind("fk_person_id") int fk_person_id,			
			@Bind("reading_days") String reading_days,
			@Bind("reading_type") String reading_type);
	
	@GetGeneratedKeys
	@SqlUpdate("delete from `reading_profile_service` where fk_person_id= :fk_person_id")
	int deleteReadingProfileService(@Bind("fk_person_id") int fk_person_id);
	
}
