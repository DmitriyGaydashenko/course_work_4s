package edu.cw.cbr.controller.security;

import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import edu.cw.cbr.model.UsertypeUtil;
import edu.cw.cbr.model.domain.Sysuser;

// TODO: Auto-generated Javadoc
/**
 * Provides a way to identify a user across more than one page request.
 * @author Dmitriy Gaydashenko
 *
 */
public final class UserSession {
	
	/** Name of attribute in HTTP session, which stores instance of. {@code UserSession} */
	public static final String U_SESSION_NAME = "userSession";
	
	private Sysuser user;
	private String IPAddress;
	
	/**
	 * Constructs new user session with user's identifier - {@code id}
	 * and user's IP address - {@code IPAddress}.
	 *
	 * @param user - instance of {@code SysuserController}, which represents the user
	 * whose session is created.
	 */
	public UserSession(Sysuser user) {
		this.user = user;
		// get IP Address of current session
		this.IPAddress = ((ServletRequestAttributes) RequestContextHolder.
			    currentRequestAttributes()).
			    getRequest().getRemoteAddr();
	}
	
	public Sysuser getUser() {
		return user;
	}
	
	public UserSession() {
		this.user = null;
		// get IP Address of current session
		this.IPAddress = ((ServletRequestAttributes) RequestContextHolder.
			    currentRequestAttributes()).
			    getRequest().getRemoteAddr();
	}
	
	public int getTypeId() {
		return user == null ? -1 :this.user.getUsertype().getUserTypeId();
	}
	
	

	public void setUser(Sysuser user) {
		this.user = user;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public int getId() {
		return user == null ? -1 : user.getUserId();
	}
	
	
	/**
	 * Gets the iP address.
	 *
	 * @return the iP address
	 */
	public String getIPAddress() {
		return IPAddress;
	}
	
	/**
	 * Gets the user's first name.
	 *
	 * @return the user's first name
	 */
	public String getFName() {
		return user == null ? "" : user.getFName();
	}

	/**
	 * Gets the user's last name.
	 *
	 * @return the user's last name
	 */
	public String getLName() {
		return user == null ? "" : user.getLName();
	}

	public boolean isAdmin() {
		return user == null ? false : UsertypeUtil.isAdmin(user
				.getUsertype().getUserTypeId());
	}

	/**
	 * Checks if current HTTP session is valid. 
	 * @param session - HTTP session to be checked.
	 * @return <tt>true</tt> if userSession is defined and 
	 * valid in current HTTP session.
	 */
	public static boolean initModelAndSession(HttpSession session) {
		UserSession userSession = getUSession(session);
		// get IP Address of current session
		if(userSession == null || userSession.getUser() == null)
			return false;
		String IPAddress = 
				  ((ServletRequestAttributes) RequestContextHolder.
				    currentRequestAttributes()).
				    getRequest().getRemoteAddr();
		return  userSession != null && userSession.IPAddress.equals(IPAddress);
	}
	
	public static UserSession getUSession(HttpSession session) {
		return (UserSession) session.getAttribute(UserSession.U_SESSION_NAME);
	}
	
	public static void removeUSession(HttpSession session) {
		UserSession userSession = getUSession(session);
		userSession.user = null;
		session.removeAttribute(U_SESSION_NAME);
	}
	
}
