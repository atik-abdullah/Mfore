package com.mfore.frontend.database_resources;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.skife.jdbi.v2.DBI;

import com.mfore.core.dao.EnrollmentDAO;
import com.mfore.core.representations.Enrollment;

@Path("/enrollment")
@Produces(MediaType.APPLICATION_JSON)
public class EnrollmentResource {

	private EnrollmentDAO enrollment_dao = null;


	public EnrollmentResource(DBI jdbi) {
		enrollment_dao = jdbi.onDemand(EnrollmentDAO.class);

	}
	
	@GET
	@Path("/{id}")
	public Response getEnrollment(@PathParam("id") int id) {
		// retrieve information about the contact with the provided id
		List <Enrollment> enrollment = enrollment_dao.getEnrollmentById(id);
		return Response
				.ok(enrollment)
				.build();
	}
	
	@POST
	public Response createEnrollment(Enrollment enrollment) throws URISyntaxException {
		// store the new contact
		int newContactId = enrollment_dao.createEnrollment( 
				enrollment.getEnrollment_date(),
				enrollment.getFk_person_id(),
				enrollment.getFk_service_id());
		return Response.created(new URI(String.valueOf(newContactId))).build();
	}

}