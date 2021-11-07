package com.mfore.frontend.database_resources;

import java.net.URI;
import java.net.URISyntaxException;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.skife.jdbi.v2.DBI;

import com.mfore.core.dao.PersonDAO;
import com.mfore.core.representations.Person;

@Path("/person")
@Produces(MediaType.APPLICATION_JSON)
public class PersonResource {

	private PersonDAO personDao = null;


	public PersonResource(DBI jdbi) {
		personDao = jdbi.onDemand(PersonDAO.class);
	}

	@GET
	@Path("/{id}")
	public Response getPerson(@PathParam("id") int id) {
		// retrieve information about the contact with the provided id
		Person person = personDao.getPersonById(id);
		return Response
				.ok(person)
				.build();
	}
	
	@POST
	public Response createPerson(Person person) throws URISyntaxException {
		// store the new contact
		int newContactId = personDao.createPerson( 
				person.getFk_user_id(),
				person.getFk_identification_type_id(),
				person.getFk_person_contact_detail_id(),
				person.getFirst_name(),
				person.getLast_name(),
				person.getIdentification_number());
		return Response.created(new URI(String.valueOf(newContactId))).build();
	}
	
/*	@PUT
	@Path("/{id}")
	public Response upatePerson(@PathParam("id") int id, Person person) throws URISyntaxException {
		// store the new contact
		personDao.updatePerson(
				id,
				person.getFk_user_id(),
				person.getFk_identification_type_id(),
				person.getFk_person_contact_detail_id(),
				person.getFirst_name(),
				person.getLast_name(),
				person.getIdentification_number());
		return Response.ok(
				new Person(id, person.getFk_user_id(),
						person.getFk_identification_type_id(),
						person.getFk_person_contact_detail_id(),
						person.getFirst_name(),
						person.getLast_name(),
						person.getIdentification_number())).build();	
		}*/
}
