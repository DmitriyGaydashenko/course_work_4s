package edu.cw.cbr.controller.security;

import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import edu.cw.cbr.domain.Sysuser;

/**
 * Provides a way to identify a user across more than one page request.
 * @author Dmitriy Gaydashenko
 *
 */
public final class UserSession {
	
	/**
	 * Name of attribute in HTTP session, which stores instance of 
	 * {@code UserSession}
	 */
	public static final String U_SESSION_NAME = "userSession";
	/**
	 * User's unique identifier.
	 */
	private int userId;
	
	/**
	 * User's first name. Is designed to prevent frequent queries to the 
	 * database
	 */
	private String fName;
	
	/**
	 * User's last name. Is designed to prevent frequent queries to the 
	 * database
	 */
	private String lName;
	/**
	 * User's IP address.
	 */
	private String IPAddress;
	
	/**
	 * Constructs new user session with user's identifier - {@code id} 
	 * and user's IP address - {@code IPAddress}.
	 * @param user - instance of {@code Sysuser}, which represents the user 
	 * whose session is created.
	 * @param IPAddress - user's IP address.
	 */
	public UserSession(Sysuser user) {
		this.userId = user.getUserId();
		this.fName = user.getFName();
		this.lName = user.getLName();
		// get IP Address of current session
		this.IPAddress = ((ServletRequestAttributes) RequestContextHolder.
			    currentRequestAttributes()).
			    getRequest().getRemoteAddr();;
	}
	
	public int getId() {
		return userId;
	}
	
	public String getIPAddress() {
		return IPAddress;
	}
	
	public String getFName() {
		return fName;
	}

	public String getLName() {
		return lName;
	}

	/**
	 * Checks if current HTTP session is valid. 
	 * @param session - HTTP session to be checked.
	 * @return <tt>true</tt> if userSession is defined and 
	 * valid in current HTTP session.
	 */
	public static boolean isHttpSessionValid(HttpSession session) {
		UserSession userSession = (UserSession)
				session.getAttribute(UserSession.U_SESSION_NAME);
		// get IP Address of current session
		String IPAddress = 
				  ((ServletRequestAttributes) RequestContextHolder.
				    currentRequestAttributes()).
				    getRequest().getRemoteAddr();
		return  userSession != null && userSession.IPAddress.equals(IPAddress);
	}
	
	
}
