package com.mfore.core.representations;

public class PersonContactDetail {
	private final int person_contact_detail_id;
	private final String email;
	private final String phone;
	private final String street_address;
	private final String postal_code;
	private final String city;
	private final String country;
	
	public PersonContactDetail() {
		this.person_contact_detail_id= 0;
		this.email= null;
		this.phone= null;
		this.street_address= null;
		this.postal_code = null;
		this.city = null;
		this.country = null;

	}
	
	public PersonContactDetail(
			int person_contact_detail_id,
			String email,
			String phone,
			String street_address,
			String postal_code, 
			String city,
			String country) {
		
		this.person_contact_detail_id= person_contact_detail_id;
		this.email = email;
		this.phone = phone;
		this.street_address = street_address;
		this.postal_code = postal_code;
		this.city = city;
		this.country = country;
	}

	public int getPerson_contact_detail_id() {
		return person_contact_detail_id;
	}

	public String getEmail() {
		return email;
	}

	public String getPhone() {
		return phone;
	}

	public String getStreet_address() {
		return street_address;
	}

	public String getPostal_code() {
		return postal_code;
	}

	public String getCity() {
		return city;
	}

	public String getCountry() {
		return country;
	}
	
	
}
