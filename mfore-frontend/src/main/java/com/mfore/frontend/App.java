package com.mfore.frontend;

import java.io.ByteArrayOutputStream;
import java.util.EnumSet;

import javax.servlet.DispatcherType;

import org.eclipse.jetty.server.session.SessionHandler;
import org.skife.jdbi.v2.DBI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mfore.frontend.filters.AuthenticationFilter;
import com.mfore.frontend.configurations.FrontEndConfiguration;
import com.mfore.frontend.database_resources.PersonContactDetailResource;
import com.mfore.frontend.database_resources.PersonResource;
import com.mfore.frontend.database_resources.EnrollmentResource;
import com.mfore.frontend.database_resources.UserResource;
import com.mfore.frontend.service_resources.ServiceLoginResource;
import com.mfore.frontend.service_resources.ServiceRegistrationResource;
import com.mfore.frontend.service_resources.ServiceReportResource;
import com.mfore.frontend.utilities.UserSession;

import io.dropwizard.Application;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.jersey.sessions.HttpSessionProvider;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.assets.AssetsBundle;
/**
 * Hello world!
 *
 */
public class App extends Application<FrontEndConfiguration> {
	private static final Logger LOGGER =LoggerFactory.getLogger(App.class);
	@Override
	public void initialize(Bootstrap<FrontEndConfiguration> bootstrap) {
	   // bootstrap.addBundle(new AssetsBundle("/assets/", "/mnyrs"));
	   // bootstrap.addBundle(new AssetsBundle("/mnyrs/js", "/js", null, "js"));
	   // bootstrap.addBundle(new AssetsBundle("/mnyrs/css", "/css", null, "css"));
	    bootstrap.addBundle(new AssetsBundle("/assets/html", "/html", null, "/html"));
	    bootstrap.addBundle(new AssetsBundle("/assets/css", "/css", null, "/css"));
	    bootstrap.addBundle(new AssetsBundle("/assets/js", "/js", null, "/js"));
	}
	@Override
	public void run(FrontEndConfiguration c, Environment e) throws Exception {
		LOGGER.info("Method App#run() called");
		for (int i=0; i < c.getMessageRepetitions(); i++) {
			System.out.println(c.getMessage());
		}
	    final DBIFactory factory = new DBIFactory();
	    final DBI jdbi = factory.build(e, c.getDataSourceFactory(), "mysql");
	    
	    // build the client and add the resource to the environment
	    //final Client client = new JerseyClientBuilder(e).build("REST Client");
	    
	    e.jersey().register(HttpSessionProvider.class);
	    e.jersey().register(ByteArrayOutputStream.class);
	    e.servlets().setSessionHandler(new SessionHandler());
	    
	    // Add the resources to the environment
	    e.jersey().register(new PersonResource(jdbi));
	    e.jersey().register(new PersonContactDetailResource(jdbi));
	    e.jersey().register(new EnrollmentResource(jdbi));
	    e.jersey().register(new UserResource(jdbi));

	    // Add the client resources to the environment
	    e.jersey().register(new ServiceRegistrationResource(jdbi));
	    e.jersey().register(new ServiceLoginResource(new UserSession(jdbi)));
	    e.jersey().register(new ServiceReportResource(jdbi));

	    // Add the servlet filter 
	    e.servlets().addFilter("AuthenticationFilter", new AuthenticationFilter(new UserSession(jdbi))).addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), true, "/*");
	    
	    //final FilterRegistration.Dynamic filterBuilder = e.servlets().addFilter("Security Filter", new SecurityFilter(new UserSession(jdbi)));
	    //filterBuilder.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
	}

	public static void main( String[] args ) throws Exception{
		new App().run(args);
	}
}
