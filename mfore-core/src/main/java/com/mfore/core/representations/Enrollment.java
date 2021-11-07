package com.mfore.core.representations;

import java.sql.Date;

public class Enrollment {
	private final Date enrollment_date;
	private final int fk_person_id;
	private final int fk_service_id;
	
	
	public Enrollment() {
		this.enrollment_date= null;
		this.fk_person_id= 0;
		this.fk_service_id= 0;
	}
	
	public Enrollment(Date enrollment_date, int fk_person_id, int fk_service_id) {
		
		this.enrollment_date= enrollment_date;
		this.fk_person_id = fk_person_id;
		this.fk_service_id = fk_service_id;
	}

	public Date getEnrollment_date() {
		return enrollment_date;
	}

	public int getFk_person_id() {
		return fk_person_id;
	}

	public int getFk_service_id() {
		return fk_service_id;
	}
	
	
}
