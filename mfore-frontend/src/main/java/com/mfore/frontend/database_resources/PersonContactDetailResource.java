package com.mfore.frontend.database_resources;

import java.net.URI;
import java.net.URISyntaxException;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.skife.jdbi.v2.DBI;

import com.mfore.core.dao.PersonContactDetailDAO;
import com.mfore.core.representations.PersonContactDetail;

@Path("/person_contact_detail")
@Produces(MediaType.APPLICATION_JSON)
public class PersonContactDetailResource {

	private PersonContactDetailDAO person_contact_detail_dao = null;

	public PersonContactDetailResource(DBI jdbi) {
		person_contact_detail_dao = jdbi.onDemand(PersonContactDetailDAO.class);

	}

	@GET
	@Path("/{id}")
	public Response getPersonContactDetail(@PathParam("id") int id) {
		// retrieve information about the contact with the provided id
		PersonContactDetail person_contact_detail = person_contact_detail_dao.getPersonContactDetailById(id);
		return Response
				.ok(person_contact_detail)
				.build();
	}
	
	@POST
	public Response createPersonContactDetail(PersonContactDetail person_contact_detail) throws URISyntaxException {
		// store the new contact
		int newContactId = person_contact_detail_dao.createPersonContactDetail( 
				person_contact_detail.getEmail(),
				person_contact_detail.getPhone(),
				person_contact_detail.getStreet_address(),
				person_contact_detail.getPostal_code(),
				person_contact_detail.getCity(),
				person_contact_detail.getCountry());
		
		return Response.created(new URI(String.valueOf(newContactId))).build();
		//return Response.status(100).entity(newContactId).build();
	}
	
	@PUT
	@Path("/{id}")
	public Response upatePerson(@PathParam("id") int id, PersonContactDetail personCD) throws URISyntaxException {
		// store the new contact
		person_contact_detail_dao.updatePersonContactDetail(
				id,
				personCD.getEmail(),
				personCD.getPhone(),
				personCD.getStreet_address(),
				personCD.getPostal_code(),
				personCD.getCity(),
				personCD.getCountry());
		return Response.ok(
				new PersonContactDetail(
						id,
						personCD.getEmail(),
						personCD.getPhone(),
						personCD.getStreet_address(),
						personCD.getPostal_code(),
						personCD.getCity(),
						personCD.getCountry())).build();	
		}
}
