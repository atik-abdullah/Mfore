package com.mfore.frontend.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.*;


import com.google.code.useragent.UserAgentParser;
import com.mfore.core.representations.User;
import com.mfore.frontend.utilities.PageNavigator;
import com.mfore.frontend.utilities.UserSession;

public class AuthenticationFilter implements Filter 
{
	int limit;
	int count;
	private UserSession userSession = null;

	public AuthenticationFilter(UserSession us) {
		this.userSession = us;
	}

	public void init( FilterConfig filterConfig )
			throws ServletException
	{
		// TO DO
	}

	public void doFilter ( ServletRequest request, ServletResponse response, FilterChain chain ) 
			throws IOException, ServletException 
	{
		PageNavigator nav = new PageNavigator();
		String url = ((HttpServletRequest)request).getRequestURL().toString();

		if(url.contains("/updatePerson/")  == true || url.contains("/multipage.html")== true) {
			HttpSession session = ((HttpServletRequest)request).getSession();
			String userName = (String) session.getAttribute("user_name");
			String token = (String) session.getAttribute("token");
			String uaString = ((HttpServletRequest)request).getHeader("user-agent");
			UserAgentParser uaParser = new UserAgentParser(uaString);

			if((token != null && token.length() > 0) && (userName != null && userName.length() > 0)) {				
				User user = userSession.getUserByName(userName);				

				if(user != null) {
					String ipAddr = request.getRemoteAddr();
					String userAgentVer = uaParser.getBrowserName() + ":" + uaParser.getBrowserVersion();

					if(userSession.isValidSession(token, ipAddr, userAgentVer, uaParser.getBrowserOperatingSystem()) != 1) {
						System.out.println("	User session is valid. User:" + userName + ", Token:" + token);

						// Redirect to requested page
						nav.go((HttpServletResponse) response);
						return;						
					}
				}
			}else {				
				// Redirect to bye page
				nav.go((HttpServletResponse) response);
				return;
			}
		}
		// Go to requested resource
		chain.doFilter(request, response);
	}

	public void destroy() { }

}
