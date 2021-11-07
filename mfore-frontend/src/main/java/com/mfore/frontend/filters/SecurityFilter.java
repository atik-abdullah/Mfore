package com.mfore.frontend.filters;

/* Author: Madamsetty Suman Kumar
 * Date: 04.Oct.2104
 * Email: suman.madamsetty@mfore.fi 
 */

import java.io.IOException;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.Filter;

import org.skife.jdbi.v2.DBI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.code.useragent.UserAgentParser;
import com.mfore.core.dao.EnrollmentDAO;
import com.mfore.core.representations.User;
import com.mfore.frontend.utilities.UserSession;
import com.mfore.frontend.utilities.PageNavigator;

public class SecurityFilter implements Filter {
	//private static final Logger LOGGER = LoggerFactory.getLogger(com.mfore.frontend.filters.SecurityFilter.class);
	private UserSession user_session;

	public SecurityFilter(UserSession trans) {
    	this.user_session = trans;
    }
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		PageNavigator nav = new PageNavigator();
		String url = ((HttpServletRequest)request).getRequestURL().toString();
		System.out.println("it doesn't");

		/*if(url.contains("/auth/") == true || url.contains("/resources/") == true) {
			HttpSession session = ((HttpServletRequest)request).getSession();
			String userName = (String) session.getAttribute("user");
			String token = (String) session.getAttribute("token");
			String uaString = ((HttpServletRequest)request).getHeader("user-agent");
	    	UserAgentParser uaParser = new UserAgentParser(uaString);
						
			if((token != null && token.length() > 0) && (userName != null && userName.length() > 0)) {				
				List<UserCredentials> users = trans.getUserByName(userName);				
				int len = (users == null) ? 0 : users.size();
				
				System.out.println("Filter Url: " + url + ", User:" + userName + ", Token:" + token);
				System.out.println("Users isNull:" + ((users == null) ? "yes" : "No") + ", Length:" + len);

				if(users != null && users.size() > 0) {
					UserCredentials user = users.get(0);
					String ipAddr = request.getRemoteAddr();
					String userAgentVer = uaParser.getBrowserName() + ":" + uaParser.getBrowserVersion();
					
					//System.out.println("doFilter() DB User:" + user.getName() + ", Token:" + user.getSession());
					if(trans.isValidSession(token, ipAddr, userAgentVer, uaParser.getBrowserOperatingSystem()) != 1) {
						System.out.println("	User session is valid. User:" + userName + ", Token:" + token);
						
						// Redirect to bye page
						nav.go((HttpServletResponse) response);
						return;						
					}
				}	
			} else {				
				// Redirect to bye page
				nav.go((HttpServletResponse) response);
				return;
			}
		}
*/
		// Go to requested resource
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub		
	}
}
