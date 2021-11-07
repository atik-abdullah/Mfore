package com.mfore.frontend.service_resources;

import io.dropwizard.jersey.sessions.Session;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

import com.mfore.core.representations.User;
import com.mfore.frontend.utilities.PageNavigator;
import com.mfore.frontend.utilities.UserSession;
import com.mfore.frontend.utilities.BCrypt;
import com.google.code.useragent.UserAgentParser;

@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
public class ServiceLoginResource {
	//private static final Logger LOGGER = LoggerFactory.getLogger(com.mfore.frontend.service_resources.ServiceLoginResource.class);

	private UserSession user_session;

	public ServiceLoginResource(UserSession trans) {
		this.user_session = trans;
	}

	@GET
	@Path("/login")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public void login(@Session HttpSession session, @Context HttpServletRequest request, @QueryParam("user_name") String name, @QueryParam("password") String password) {    	
		PageNavigator nav = new PageNavigator("/html/relogin-form.html");		
		String ipAddr = request.getRemoteAddr();
		String uaString = request.getHeader("user-agent");
		UserAgentParser uaParser = new UserAgentParser(uaString);
		String userAgentVer = uaParser.getBrowserName() + ":" + uaParser.getBrowserVersion();
		String userOS = uaParser.getBrowserOperatingSystem();		
		SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String timeNow = sdfDate.format(new Date());
		String serviceRealm = "nidan";

		// Check credentials and redirect page
		if(name.length() > 0 && password.length() > 0) {
			User user = user_session.getUserByName(name);

			if(user!=null) {	
				int id = user.getUser_id();
				String pwHash = user.getPassword_hash();

				// Check if user system credentials matches given credentials 
				if(BCrypt.checkpw(password, pwHash)) {
					String token = BCrypt.gensalt();

					// Set access token and return user instance
					if(user_session.setUserSession(token, "LoggedIn-ACTIVE", id, ipAddr, timeNow, userAgentVer, uaParser.getBrowserOperatingSystem(), serviceRealm) == 1) {

						//userToken.setToken(token);
						session.setAttribute("user_name", name);
						session.setAttribute("token", token);

						// Set timeout to 10 minutes
						session.setMaxInactiveInterval(600);

						// Redirect to registration page
						//nav.setUri("/mnyrs/registration-form.html");
						nav.setUri("/html/multipage.html");

					}
				}else{
					user_session.reportInvalidLoginAttempt("Invalid user credentials", name, password, ipAddr, timeNow, userAgentVer, userOS, serviceRealm);
				}
			}else{
				user_session.reportInvalidLoginAttempt("Invalid user account", name, password, ipAddr, timeNow, userAgentVer, userOS, serviceRealm);
			}
		}
		// Redirect to next page
		nav.go();
	}

	@GET
	@Path("/logout")    
	public void logout(@Session HttpSession session, @Context HttpServletRequest request) {
		PageNavigator nav = new PageNavigator("/html/relogin-form.html");
		String userName = (String) session.getAttribute("user");
		String token = (String) session.getAttribute("token");
		String ipAddr = request.getRemoteAddr();
		System.out.println("Logging out user: " + userName + ", IP: " + ipAddr);

		// Make current user session invalid
		user_session.closeUserSession(token, "Logout-Closed");

		// Rest client session
		session.setAttribute("user", "");
		session.setAttribute("token", "");

		// Show relogin page
		nav.go();
	}  
}
