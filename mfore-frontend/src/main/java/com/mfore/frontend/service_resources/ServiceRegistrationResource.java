package com.mfore.frontend.service_resources;

import io.dropwizard.jersey.sessions.Session;

import java.net.URISyntaxException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;
import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.skife.jdbi.v2.DBI;

import com.mfore.core.dao.EnrollmentDAO;
import com.mfore.core.dao.MedicationReminderServiceDAO;
import com.mfore.core.dao.MnyrsSpecificDetailDAO;
import com.mfore.core.dao.PersonContactDetailDAO;
import com.mfore.core.dao.PersonDAO;
import com.mfore.core.dao.ReadingProfileServiceDAO;
import com.mfore.core.dao.UserDAO;
import com.mfore.core.representations.MedicationReminderService;
import com.mfore.core.representations.MnyrsSpecificDetail;
import com.mfore.core.representations.Person;
import com.mfore.core.representations.PersonContactDetail;
import com.mfore.core.representations.ReadingProfileService;
import com.mfore.core.representations.User;
import com.mfore.frontend.utilities.*;

@Path("/registration")
@Produces(MediaType.APPLICATION_JSON)
public class ServiceRegistrationResource {

	private UserDAO userDao= null;
	private PersonContactDetailDAO personContactDetailDao = null;
	private PersonDAO personDao = null;
	private MnyrsSpecificDetailDAO mnyrsSpecificDetailDao = null;
	private EnrollmentDAO enrollmentDao = null;
	private MedicationReminderServiceDAO medicationReminderServiceDao = null;
	private ReadingProfileServiceDAO readingProfileServiceDao = null;
	
	public ServiceRegistrationResource(DBI jdbi) {
		this.personDao = jdbi.onDemand(PersonDAO.class);
		this.userDao = jdbi.onDemand(UserDAO.class);
		this.personContactDetailDao = jdbi.onDemand(PersonContactDetailDAO.class);
		this.mnyrsSpecificDetailDao = jdbi.onDemand(MnyrsSpecificDetailDAO.class);
		this.enrollmentDao = jdbi.onDemand(EnrollmentDAO.class);
		this.medicationReminderServiceDao = jdbi.onDemand(MedicationReminderServiceDAO.class); 
		this.readingProfileServiceDao = jdbi.onDemand(ReadingProfileServiceDAO.class); 

	}

	// Create database table "user" record and use the primary key to create "person" table record 
	@GET
	@Path("/newProfile")
	public void newProfile (
			@QueryParam("firstName") String fn,
			@QueryParam("lastName") String ln,
			@QueryParam("userName") String userName,
			@QueryParam("password") String plain_password,
			@QueryParam("phoneNumber") String phone_number
			) throws ParseException, URISyntaxException {
		System.out.println("User name: " + fn);
		PageNavigator nav = new PageNavigator("/html/signup-success-form.html");		

		Date currentDateTime = new Date();
		String generatePasswordHash = BCrypt.hashpw(plain_password, BCrypt.gensalt());
		String generateSalt = BCrypt.gensalt(10);
		int newUserId = userDao.createUser(userName, generatePasswordHash, generateSalt, currentDateTime );
		int newContactDetailId = personContactDetailDao.createPersonContactDetail(null, phone_number, null, null, null, null);
		// Notice 2nd and 3rd parameter are just dummy variable, it has to be updated later
		int newPersonId = personDao.createPerson(newUserId, 1, newContactDetailId, fn, ln, null );

		mnyrsSpecificDetailDao.createMnyrsSpecificDetail(newPersonId, null, null, 0, 0, null);
		medicationReminderServiceDao.createMedicationReminderService(null, null, newPersonId);
		readingProfileServiceDao.createReadingProfileService(null, 0, newPersonId);

		nav.go();
	}

	@GET
	@Path("/updatePerson")
	public void updatePerson(
			@Session HttpSession session, 
			@QueryParam("firstName") String fn,
			@QueryParam("lastName") String ln,
			@QueryParam("phone") String pn,
			@QueryParam("birthDate") String bd,
			@QueryParam("gender") String gn,
			@QueryParam("height") int ht,
			@QueryParam("weight") int wt,
			@QueryParam("bloodGroup") String bg,
			@QueryParam("streetAddress") String sa,
			@QueryParam("postalCode") String pc,
			@QueryParam("city") String ct,
			@QueryParam("country") String cn,
			@QueryParam("socialIDtype") int sid,
			@QueryParam("idNumber") String in,
			@QueryParam("serviceType") List<Integer> servType,
			@QueryParam("medName") List<String> medName,
			@QueryParam("reminderTime") List<String> reminderTime,
			@QueryParam("readingDay") List<String> readingDay,
			@QueryParam("readingType") int readingType) throws ParseException{

		// Initializing and formatting
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date birthDate = sdf.parse(bd);
		Date enrollmentDateTime = new Date();
		PageNavigator nav = new PageNavigator("/html/success-form.html");		

		// Get the user record that matches user_name
		User user = userDao.getUserByName((String) session.getAttribute("user_name"));

		// Get the person record where there user_id field matches with the retrieved user's pk "id"
		Person person = personDao.getPersonByUserId(user.getUser_id());

		// Update the person_contact_detail information that matches retrieved person's pk "id" 
		personContactDetailDao.updatePersonContactDetail(person.getFk_person_contact_detail_id(), "new@yahoo.com", pn, sa, pc, ct, cn );

		// Update the person record
		personDao.updatePerson(person.getPerson_id(), sid, fn, ln, in );

		// Update mnyrs_specific_detail
		mnyrsSpecificDetailDao.updateMnyrsSpecificDetail(person.getPerson_id(), birthDate, gn, ht, wt, bg);

		// Delete previous enrollment records
		enrollmentDao.deleteEnrollment(person.getPerson_id());

		// Insert new entries in enrollment
		ListIterator<Integer> litr = servType.listIterator();
		while(litr.hasNext()) {
			enrollmentDao.createEnrollment(enrollmentDateTime, person.getPerson_id(), (int)litr.next());
		}
		
		// Delete previous enrollment records
		medicationReminderServiceDao.deleteMedicationReminderService(person.getPerson_id());

		// Insert medicine name and reminding time preference in medication_reminder_service
		ListIterator<String> medIterator = medName.listIterator();
		ListIterator<String> reminderIterator = reminderTime.listIterator();

		while(medIterator.hasNext() && reminderIterator.hasNext()) {
			//if((medIterator.next()!=null )&&( reminderIterator.next() != null)){
			medicationReminderServiceDao.createMedicationReminderService(medIterator.next(),reminderIterator.next(), person.getPerson_id());
			//}
			}
		// Delete previous choices
		readingProfileServiceDao.deleteReadingProfileService(person.getPerson_id());
		ListIterator<String> readingDayIterator = readingDay.listIterator();

		while(readingDayIterator.hasNext()) {
			readingProfileServiceDao.createReadingProfileService(readingDayIterator.next(),readingType, person.getPerson_id());
			}
		nav.go();
	}

	@GET
	@Path("/prefillperson")
	public Response prefillPerson(@Session HttpSession session) throws ParseException{

		// Get the user record that matches user_name
		User user = userDao.getUserByName((String) session.getAttribute("user_name"));

		// Get the person record where there user_id field matches with the retrieved user's pk "id"
		Person person = personDao.getPersonByUserId(user.getUser_id());

		return Response
				.ok(person)
				.build();
	}
	
	@GET
	@Path("/prefillpersoncontactdetail")
	public Response prefillPersonContactDetail(@Session HttpSession session) throws ParseException{

		// Get the user record that matches user_name
		User user = userDao.getUserByName((String) session.getAttribute("user_name"));

		// Get the person record where there user_id field matches with the retrieved user's pk "id"
		Person person = personDao.getPersonByUserId(user.getUser_id());
		PersonContactDetail personContactDetail = personContactDetailDao.getPersonContactDetailById(person.getFk_person_contact_detail_id());

		return Response
				.ok(personContactDetail)
				.build();
	}

	@GET
	@Path("/prefillmnyrsspecificdetail")
	public Response prefillMnyrsSpecificDetail(@Session HttpSession session) throws ParseException{

		// Get the user record that matches user_name
		User user = userDao.getUserByName((String) session.getAttribute("user_name"));

		// Get the person record where there user_id field matches with the retrieved user's pk "id"
		Person person = personDao.getPersonByUserId(user.getUser_id());
		
		// Get mnyrsSpecificDetail record for person id
		MnyrsSpecificDetail mnyrsSpecificDetail = mnyrsSpecificDetailDao.getMnyrsSpecificDetailByPersonId(person.getPerson_id());
		
		return Response
				.ok(mnyrsSpecificDetail)
				.build();
	}
	
	@GET
	@Path("/prefillmedicationreminder")
	public Response prefillMedicationReminder(@Session HttpSession session) throws ParseException{

		// Get the user record that matches user_name
		User user = userDao.getUserByName((String) session.getAttribute("user_name"));

		// Get the person record where there user_id field matches with the retrieved user's pk "id"
		Person person = personDao.getPersonByUserId(user.getUser_id());
		
		// Get mnyrsSpecificDetail record for person id
		List<MedicationReminderService> medRemServ = medicationReminderServiceDao.getMedicationReminderServiceByPersonId(person.getPerson_id());
		List<MedicationReminderService> medRemServDummy = new ArrayList<MedicationReminderService>(); 

		for(int i=0; i<3; i++){
			MedicationReminderService medRemService = new MedicationReminderService ();
			medRemService.setMedicine_desc("");
			medRemService.setReminding_time("0000");
			medRemServDummy.add(i, medRemService);
			}
		// Insert new entries in enrollment
		ListIterator<MedicationReminderService> litr = medRemServ.listIterator();
		while(litr.hasNext()) {
			System.out.println((MedicationReminderService)litr.next());
		}
		if(medRemServ.size()<3){
			return Response
					.ok(medRemServDummy)
					.build();
		} else{
			return Response
					.ok(medRemServ)
					.build();
		}
	}
	
	@GET
	@Path("/prefillreadingtype")
	public Response prefillReadingType(@Session HttpSession session) throws ParseException{

		// Get the user record that matches user_name
		User user = userDao.getUserByName((String) session.getAttribute("user_name"));

		// Get the person record where there user_id field matches with the retrieved user's pk "id"
		Person person = personDao.getPersonByUserId(user.getUser_id());
		
		// Get mnyrsSpecificDetail record for person id
		List <ReadingProfileService> redProServ = readingProfileServiceDao.getReadingProfileServiceByPersonId(person.getPerson_id());

		return Response
				.ok(redProServ)
				.build();
	}
	
	@GET
	@Path("/usercheck")  
	@Produces(MediaType.TEXT_PLAIN)
	public String userCheck(@Session HttpSession session,@QueryParam("userName") String un) {

		// Get the user record that matches user_name
		System.out.println(un);
		User user = userDao.getUserByName(un);
		if(user != null){
			String existName = user.getUser_name();

			return existName + "already exists";
		}
		else return "User name available";
	}  
	
	/*@POST
	@Path("updatePerson")
	public void updatePerson(
			@Session HttpSession session, 
			@FormParam("firstName") String fn,
			@FormParam("lastName") String ln,
			@FormParam("phone") String pn,
			@FormParam("birthDate") String bd,
			@FormParam("gender") String gn,
			@FormParam("height") int ht,
			@FormParam("weight") int wt,
			@FormParam("bloodGroup") String bg,
			@FormParam("streetAddress") String sa,
			@FormParam("postalCode") String pc,
			@FormParam("city") String ct,
			@FormParam("country") String cn,
			@FormParam("socialIDtype") String st,
			@FormParam("idNumber") String in,
			@FormParam("idNumber") List<String> serviceType,
			@FormParam("serviceType") Integer[] servType) throws ParseException{

		// Initializing and formatting
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date birthDate = sdf.parse(bd);
		Date enrollmentDateTime = new Date();

		// Get the user record that matches user_name
		User user = userDao.getUserByName((String) session.getAttribute("user_name"));

		// Get the person record where there user_id field matches with the retrieved user's pk "id"
		Person person = personDao.getPersonByUserId(user.getUser_id());

		// Update the person_contact_detail information that matches retrieved person's pk "id" 
		int updatedContactDetailId = personContactDetailDao.updatePersonContactDetail(person.getFk_person_contact_detail_id(), "new@yahoo.com", pn, sa, pc, ct, cn );

		// Update the person record
		int updatedPersonId = personDao.updatePerson(person.getPerson_id(), 1, updatedContactDetailId, fn, ln, in );

		// Update mnyrs_specific_detail
		mnyrsSpecificDetailDao.updateMnyrsSpecificDetail(person.getPerson_id(), birthDate, gn, ht, wt, bg);

		// Delete previous enrollment records

		// Insert new entries in enrollment
		for (int i = 0; i < servType.length; i++) {
			enrollmentDao.createEnrollment(enrollmentDateTime, person.getPerson_id(), servType[i]);
        }

		List servType = serviceType;
	      ListIterator litr = serviceType.listIterator();
	      while(litr.hasNext()) {
	    	  enrollmentDao.createEnrollment(enrollmentDateTime, person.getPerson_id(), (int)litr.next());
	      }


	}*/
}
