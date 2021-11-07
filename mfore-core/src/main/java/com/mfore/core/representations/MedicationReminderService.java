package com.mfore.core.representations;

import java.sql.Time;

public class MedicationReminderService {
	private final int medication_reminder_service_id;
	String medicine_desc;
	String reminding_time;


	private final int fk_person_id;
	
	
	public MedicationReminderService() {
		this.medication_reminder_service_id= 0;
		this.medicine_desc= null;
		this.reminding_time= null;
		this.fk_person_id = 0;
	}
	
	public MedicationReminderService(
			int medication_reminder_service_id,
			String medicine_desc,
			String reminding_time,
			int fk_person_id) {
		
		this.medication_reminder_service_id= medication_reminder_service_id;
		this.medicine_desc = medicine_desc;
		this.reminding_time = reminding_time;
		this.fk_person_id = fk_person_id;
	}

	public int getMedication_reminder_service_id() {
		return medication_reminder_service_id;
	}

	public String getMedicine_desc() {
		return medicine_desc;
	}

	public String getReminding_time() {
		return reminding_time;
	}

	public int getFk_person_id() {
		return fk_person_id;
	}
	
	public void setMedicine_desc(String medicine_desc) {
		this.medicine_desc = medicine_desc;
	}

	public void setReminding_time(String reminding_time) {
		this.reminding_time = reminding_time;
	}
	
}
