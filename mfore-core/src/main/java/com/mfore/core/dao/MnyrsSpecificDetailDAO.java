package com.mfore.core.dao;

import java.util.Date;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.GetGeneratedKeys;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;

import com.mfore.core.mappers.MnyrsSpecificDetailMapper;
import com.mfore.core.representations.MnyrsSpecificDetail;

public interface MnyrsSpecificDetailDAO {

	@Mapper(MnyrsSpecificDetailMapper.class)
	@SqlQuery("select * from `mnyrs_specific_detail` where fk_person_id= :id")
	MnyrsSpecificDetail getMnyrsSpecificDetailByPersonId(@Bind("id") int id);
	
	@GetGeneratedKeys
	@SqlUpdate("insert into `mnyrs_specific_detail` "
			+ "(fk_person_id, birth_date, gender, height, weight, blood_group) "
			+ "values (:fk_person_id, :birth_date, :gender, :height, :weight, :blood_group)")
	int createMnyrsSpecificDetail(
			@Bind("fk_person_id") int fk_person_id, 
			@Bind("birth_date") Date birth_date,
			@Bind("gender") String gender,
			@Bind("height") int height,
			@Bind("weight") int weight,
			@Bind("blood_group") String blood_group);

	
	@SqlUpdate("update `mnyrs_specific_detail` set birth_date = :birth_date, gender = :gender,  "
			+ "height = :height, weight = :weight, blood_group = :blood_group where fk_person_id = :fk_person_id")
	int updateMnyrsSpecificDetail(
			@Bind("fk_person_id") int fk_person_id,
			@Bind("birth_date") Date birth_date,
			@Bind("gender") String gender, 
			@Bind("height") int height,
			@Bind("weight") int weight,
			@Bind("blood_group") String blood_group);
}
