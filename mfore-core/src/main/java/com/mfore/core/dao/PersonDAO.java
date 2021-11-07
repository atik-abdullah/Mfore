package com.mfore.core.dao;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.GetGeneratedKeys;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;

import com.mfore.core.mappers.PersonMapper;
import com.mfore.core.representations.Person;

public interface PersonDAO {

	@Mapper(PersonMapper.class)
	@SqlQuery("select * from `person` where person_id= :id")
	Person getPersonById(@Bind("id") int id);

	@Mapper(PersonMapper.class)
	@SqlQuery("select * from `person` where fk_user_id= :id")
	Person getPersonByUserId(@Bind("id") int id);

	@GetGeneratedKeys
	@SqlUpdate("insert into `person` "
			+ "(fk_user_id, fk_identification_type_id, fk_person_contact_detail_id, first_name, last_name, identification_number) "
			+ "values (:fk_user_id, :fk_identification_type_id, :fk_person_contact_detail_id, :first_name, :last_name, :identification_number)")
	int createPerson(
			@Bind("fk_user_id") int fkUserId, 
			@Bind("fk_identification_type_id") int fkIdentificationTypeId,
			@Bind("fk_person_contact_detail_id") int fkContactPersonDetailId,
			@Bind("first_name") String firstName,
			@Bind("last_name") String LastName,
			@Bind("identification_number") String identificationNumber);
	
	@SqlUpdate("update `person` set fk_identification_type_id = :fk_identification_type_id,  "
			+ " first_name = :first_name, last_name = :last_name, identification_number = :identification_number where person_id = :person_id")
	int updatePerson(
			@Bind("person_id") int person_id,
			@Bind("fk_identification_type_id") int fkIdentificationId, 
			@Bind("first_name") String firstName,
			@Bind("last_name") String lastName,
			@Bind("identification_number") String identificationNumber);
}
