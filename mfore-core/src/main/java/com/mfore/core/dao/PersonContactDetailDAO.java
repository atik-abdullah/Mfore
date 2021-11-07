package com.mfore.core.dao;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.GetGeneratedKeys;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;

import com.mfore.core.mappers.PersonContactDetailMapper;
import com.mfore.core.representations.PersonContactDetail;

public interface PersonContactDetailDAO {

	@Mapper(PersonContactDetailMapper.class)
	@SqlQuery("select * from `person_contact_detail` where person_contact_detail_id= :id")
	PersonContactDetail getPersonContactDetailById(@Bind("id") int id);
	
	@GetGeneratedKeys
	@SqlUpdate("insert into `person_contact_detail` "
			+ "(email, phone, street_address, postal_code, city, country) "
			+ "values (:email, :phone, :street_address, :postal_code, :city, :country)")
	int createPersonContactDetail(
			@Bind("email") String email, 
			@Bind("phone") String phone,
			@Bind("street_address") String streetAddress,
			@Bind("postal_code") String postalCode,
			@Bind("city") String city,
			@Bind("country") String country);
	
	@SqlUpdate("update `person_contact_detail` set email = :email, phone = :phone,  "
			+ "street_address = :street_address, postal_code = :postal_code, city = :city, country = :country where person_contact_detail_id = :person_contact_detail_id")
	int updatePersonContactDetail(
			@Bind("person_contact_detail_id") int person_contact_detail_id,
			@Bind("email") String email,
			@Bind("phone") String phone, 
			@Bind("street_address") String street_address,
			@Bind("postal_code") String postal_code,
			@Bind("city") String city,
			@Bind("country") String country);
}
