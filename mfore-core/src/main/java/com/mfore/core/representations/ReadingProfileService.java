package com.mfore.core.representations;

public class ReadingProfileService {
	private final int reading_profile_service_id;
	private final String reading_days;
	private final int reading_type;
	private final int fk_person_id;
	
	
	public ReadingProfileService() {
		this.reading_profile_service_id= 0;
		this.reading_days= null;
		this.reading_type= 0;
		this.fk_person_id = 0;
	}
	
	
	public ReadingProfileService(
			int reading_profile_service_id,
			String reading_days,
			int reading_type,
			int fk_person_id) {
		
		this.reading_profile_service_id= reading_profile_service_id;
		this.reading_days = reading_days;
		this.reading_type = reading_type;
		this.fk_person_id = fk_person_id;
	}


	public int getReading_profile_service_id() {
		return reading_profile_service_id;
	}


	public String getReading_days() {
		return reading_days;
	}


	public int getReading_type() {
		return reading_type;
	}


	public int getFk_person_id() {
		return fk_person_id;
	}

}
