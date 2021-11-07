package com.mfore.core.representations;

import java.sql.Date;

public class MnyrsSpecificDetail {

	private final int mnyrs_specific_detail_id;
	private final int fk_person_id;
	private final Date birth_date;
	private final String gender;
	private final int height;
	private final int weight;
	private final String blood_group;
	
	public MnyrsSpecificDetail() {
		this.mnyrs_specific_detail_id= 0;
		this.fk_person_id= 0;
		this.birth_date= null;
		this.gender= null;
		this.height= 0;
		this.weight= 0;
		this.blood_group = null;
	}
	
	public MnyrsSpecificDetail(			
			int mnyrs_specific_detail_id,
			int fk_person_id,
			Date birth_date,
			String gender,
			int height,
			int weight,
			String blood_group) {
		
		this.mnyrs_specific_detail_id= mnyrs_specific_detail_id;
		this.fk_person_id= fk_person_id;
		this.birth_date= birth_date;
		this.gender= gender;
		this.height= height;
		this.weight= weight;
		this.blood_group = blood_group;
	}

	public int getMnyrs_specific_detail_id() {
		return mnyrs_specific_detail_id;
	}

	public int getFk_person_id() {
		return fk_person_id;
	}

	public Date getBirth_date() {
		return birth_date;
	}

	public String getGender() {
		return gender;
	}

	public int getHeight() {
		return height;
	}

	public int getWeight() {
		return weight;
	}

	public String getBlood_group() {
		return blood_group;
	}
	
	
}
