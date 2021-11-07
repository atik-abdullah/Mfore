/* Author: Madamsetty Suman Kumar
 * Date: 04.Oct.2104
 * Email: suman.madamsetty@mfore.fi 
 */

package com.mfore.frontend.utilities;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

public class PageNavigator {	
	private String uri;
	
	public PageNavigator() {
		setUri("/mfore/mnyrs/relogin-form.html");
	}
	
	public PageNavigator(String uri) {
		setUri(uri);
	}
	
	public void goToNoAccessPage(HttpServletResponse response) {		
		go(response);
	}

	public String getUri() {
		return this.uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}
	
	public void go(HttpServletResponse response) {		
    	// Redirect to next page
		try {
			response.sendRedirect(this.uri);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void go() {		
		try {
			Response response = Response.seeOther(new URI(this.uri)).build();
			throw new WebApplicationException(response);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
}
