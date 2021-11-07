package com.mfore.core.dao;

import java.util.Date;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.GetGeneratedKeys;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;

import com.mfore.core.mappers.UserMapper;
import com.mfore.core.representations.User;

public interface UserDAO {
	@Mapper(UserMapper.class)
	@SqlQuery("select * from `user` where user_id= :id")
	User getUserById(@Bind("id") int id);

	@Mapper(UserMapper.class)
	@SqlQuery("select * from `user` where user_name= :un")
	User getUserByName(@Bind("un") String un);

	@GetGeneratedKeys
	@SqlUpdate("insert into `user` "
			+ "(user_name, password_hash, password_salt, registered_on) "
			+ "values (:user_name, :password_hash, :password_salt, :registered_on)")
	int createUser( 
			@Bind("user_name") String userName,
			@Bind("password_hash") String passwordHash,
			@Bind("password_salt") String passwordSalt,
			@Bind("registered_on") Date registeredOn);

	@SqlUpdate("INSERT INTO `user_session` (session, session_end_reason, user_fid, ipa, in_time, user_agent, user_os, service_id)" +
			" VALUES (:session, :endReason, :id, :ipa, :inTime, :userAgent, :userOS, :serviceId)")
	int setUserSession(
			@Bind("session") String token,
			@Bind("endReason") String endReason,
			@Bind("id") int id,
			@Bind("ipa") String ipa,
			@Bind("inTime") String inTime,
			@Bind("userAgent") String userAgent, 
			@Bind("userOS") String userOS,
			@Bind("serviceId") String serviceId);
	
    @SqlUpdate("UPDATE user_session SET out_time = :outTime, session_end_reason = :endReason WHERE session = :token")
    int closeUserSession(@Bind("token") String token, @Bind("outTime") String outTime, @Bind("endReason") String endReason);
    
    @SqlQuery("SELECT COUNT(session) from user_session where session = :token AND ipa = :ipa AND user_agent = :userAgentVer AND user_os = :userOS")
    int isValidSession(@Bind("token") String token, @Bind("ipa") String ipa, @Bind("userAgentVer") String userAgentVer, @Bind("userOS") String userOS);

    @SqlUpdate("INSERT INTO intrusion_attempt (reason, user_name, password, ipa, attempt_time, user_agent, user_os, service_id)" +
			" VALUES (:reason, :userName, :password, :ipa, :aTime, :userAgent, :userOS, :serviceId)")
	int reportInvalidLoginAttempt(@Bind("reason") String reason, @Bind("userName") String userName, @Bind("password") String password,
			@Bind("ipa") String ipa, @Bind("aTime") String aTime,
			@Bind("userAgent") String userAgent, @Bind("userOS") String userOS,
			@Bind("serviceId") String serviceId);
 
}
