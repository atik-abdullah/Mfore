package com.mfore.frontend.utilities;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.skife.jdbi.v2.DBI;

import com.mfore.core.dao.UserDAO;
import com.mfore.core.representations.User;


public class UserSession {
    private UserDAO dao;

    public UserSession(DBI jdbi) {
        this.dao = jdbi.onDemand(UserDAO.class);
    }
    
    public User getUserByName(String userName) {
    	User user = this.dao.getUserByName(userName);
        return user;
    }
    
    public int setUserSession(String token, String endReason, int id, String ipa,
    		String inTime, String userAgent, String userOS, String serviceId) {
    	
    	System.out.println("**********" + token + "," + endReason + "," + id + "," + ipa + "," + inTime + "," + userAgent + "," + userOS + ", " + serviceId);
    	return this.dao.setUserSession(token, endReason, id, ipa, inTime, userAgent, userOS, serviceId);
    }
    
    public int closeUserSession(String token, String endReason) {
    	SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String outTime = sdfDate.format(new Date());
        
        System.out.println("In closeUserSession(), token: " + token + ", reason: " + endReason + ", out time:" + outTime);
    	return this.dao.closeUserSession(token, outTime, endReason);
    }
    
    public int isValidSession(String token, String ipAddr, String userAgentVer, String userOS) {
    	System.out.println("In isValidSession(), token: " + token + ", ipAddr: " + ipAddr);
    	return this.dao.isValidSession(token, ipAddr, userAgentVer, userOS);
    }
    
    public int reportInvalidLoginAttempt(String reason, String userName, String password, String ipa, String aTime, 
    		String userAgent, String userOS, String serviceId) {
    	return this.dao.reportInvalidLoginAttempt(reason, userName, password, ipa, aTime, userAgent, userOS, serviceId);
    } 
}
