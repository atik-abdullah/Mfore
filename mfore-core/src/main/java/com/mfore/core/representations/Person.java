package com.mfore.core.representations;

public class Person {
	private final int person_id;
	private final int fk_user_id;
	private final int fk_identification_type_id;
	private final int fk_person_contact_detail_id;
	private final String first_name;
	private final String last_name;
	private final String identification_number;

	public Person() {
		this.person_id= 0;
		this.fk_user_id= 0;
		this.fk_identification_type_id= 0;
		this.fk_person_contact_detail_id= 0;
		this.first_name = null;
		this.last_name = null;
		this.identification_number = null;

	}
	
	public Person(
			int person_id,
			int fk_user_id,
			int fk_identification_type_id,
			int fk_person_contact_detail_id,
			String first_name, 
			String last_name,
			String identification_number) {
		
		this.person_id= person_id;
		this.fk_user_id = fk_user_id;
		this.fk_identification_type_id = fk_identification_type_id;
		this.fk_person_contact_detail_id = fk_person_contact_detail_id;
		this.first_name = first_name;
		this.last_name = last_name;
		this.identification_number = identification_number;
	}

	public int getPerson_id() {
		return person_id;
	}

	public int getFk_user_id() {
		return fk_user_id;
	}

	public int getFk_identification_type_id() {
		return fk_identification_type_id;
	}

	public int getFk_person_contact_detail_id() {
		return fk_person_contact_detail_id;
	}

	public String getFirst_name() {
		return first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public String getIdentification_number() {
		return identification_number;
	}


	
}
