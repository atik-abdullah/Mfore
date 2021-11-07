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

import com.mfore.core.dao.UserDAO;
import com.mfore.core.representations.User;

@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {


	private UserDAO userDao = null;


	public UserResource(DBI jdbi) {
		userDao = jdbi.onDemand(UserDAO.class);

	}

	@GET
	@Path("/{id}")
	public Response getUser(@PathParam("id") int id) {
		// retrieve information about the contact with the provided id
		User user = userDao.getUserById(id);
		return Response
				.ok(user)
				.build();
	}
	
	@POST
	public Response createUser(User user) throws URISyntaxException {
		// store the new contact
		int newUserId = userDao.createUser( 
				user.getUser_name(),
				user.getPassword_hash(),
				user.getPassword_salt(),
				user.getRegistered_on());
		return Response.created(new URI(String.valueOf(newUserId))).build();
	}

}
