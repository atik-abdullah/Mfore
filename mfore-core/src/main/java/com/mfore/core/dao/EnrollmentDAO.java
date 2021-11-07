package com.mfore.core.dao;

import java.util.Date;
import java.util.List;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.GetGeneratedKeys;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;

import com.mfore.core.mappers.EnrollmentMapper;
import com.mfore.core.representations.Enrollment;

public interface EnrollmentDAO {

	@Mapper(EnrollmentMapper.class)
	@SqlQuery("select * from `enrollment` where fk_person_id= :id")
	List<Enrollment> getEnrollmentById(@Bind("id") int id);

	@GetGeneratedKeys
	@SqlUpdate("insert into `enrollment` "
			+ "(enrollment_date, fk_person_id, fk_service_id) "
			+ "values (:enrollment_date, :fk_person_id, :fk_service_id)")
	int createEnrollment(
			@Bind("enrollment_date") Date enrollmentDate, 
			@Bind("fk_person_id") int fkPersonId,
			@Bind("fk_service_id") int fkServiceId);
	
	@GetGeneratedKeys
	@SqlUpdate("delete from `enrollment` where fk_person_id= :fk_person_id")
	int deleteEnrollment(@Bind("fk_person_id") int fk_person_id);
	
}
