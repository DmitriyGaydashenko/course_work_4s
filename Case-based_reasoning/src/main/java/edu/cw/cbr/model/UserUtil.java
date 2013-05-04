package edu.cw.cbr.model;

import java.sql.SQLException;
import edu.cw.cbr.controller.security.SecurityUtil;
import edu.cw.cbr.domain.Sysuser;
import edu.cw.cbr.domain.Usertype;

/**
 * The class {@code UserUtil} contains methods for performing basic
 * operations with {@code Sysuser}.
 * @author Dmitriy Gaydashenko
 *
 */
public class UserUtil {
	
	/**
	 * Enumeration of user's states.
	 */
	public static enum UserState {
		/**
		 * User does not exist.
		 */
		NOT_EXISTS,
		/**
		 * User had not been verified yet.
		 */
		NOT_VERIFIED,
		/**
		 * User is valid.
		 */
		IS_VALID};
	
	/**
	 * Enumeration for registration's states.
	 */
	public static enum RegistrationState {
		/**
		 * Successful registration.
		 */
		SUCCESS,
		/**
		 * User with this email already exists.
		 */
		EMAIL_EXISTS,
		/**
		 * Invalid parameter
		 */
		INVALID_PARAM};
	/**
	 * Checks if user with id {@code sysuserId} is valid.
	 * @param sysuserId - id of user to be checked.
	 * @return - <tt>true</tt> if user exists, was not deleted and is verified.
	 * @throws SQLException
	 */
	public static boolean isValid(int sysuserId) throws SQLException {
		return UserUtil.isValid(UserUtil.getSysUser(sysuserId));
	}
	
	/**
	 * Checks if user {@code user} is valid.
	 * @param user - to be checked.
	 * @return - <tt>true</tt> if {@code user} is not null and was not deleted
	 * and is verified.
	 * @throws SQLException
	 */
	public static boolean isValid(Sysuser user) {
		return user != null && user.isVerified();
	}
	
	/**
	 * Creates new DAO and returns @code {Sysuser} with Id equals to 
	 * @code {sysuserId}.
	 * @param sysuserId - user's unique identifier.
	 * @return @code {Sysuser} with Id equals to @code {sysuserId}.
	 * @throws SQLException
	 */
	public static Sysuser getSysUser(int sysuserId) throws SQLException {
		SysuserDAO dao = new SysuserDAO();
		Sysuser user = UserUtil.getSysUser(sysuserId, dao);
		dao.closeSession();
		return user;
	}
	
	/**
	 * Returns instance of @code {Sysuser} with Id equals to @code {sysuserId}
	 * using provided DAO.
	 * @param sysuserId - user's unique identifier.
	 * @param dao - data access object, which method @code {getSysUser} uses to
	 * get instance of @code {Sysuser}.
	 * @return @code {Sysuser} with Id equals to @code {sysuserId}.
	 * @throws SQLException
	 * @throws IllegalArgumentException if {@code dao} is <tt>null</tt>
	 */
	private static Sysuser getSysUser(int sysuserId, GenericDAO dao) 
			throws IllegalArgumentException, SQLException  {
		if (dao == null)
			throw new IllegalArgumentException();
		Sysuser user = dao.getEntityById(Sysuser.class, sysuserId);
		return user;
	}
	
	/**
	 * Returns instance of {@code Sysuser} if there user exists with email and
	 * password equal to {@code email, password}, else returns <tt>null</tt>.
	 * @param email - user's email.
	 * @param password - user's password.
	 * @return instance of Sysuser if it exists with email and password
	 * equal to
	 * {@code email, password} exists, else returns <tt>null</tt>.
	 */
	public static Sysuser getSysuser(String email, String password) {
		SysuserDAO dao = new SysuserDAO();
		Sysuser user = dao.getSysuser(email, 
				SecurityUtil.hashInput(password));
		dao.closeSession();
		return user;
	}
	
	/**
	 * Returns state of user.
	 * @param user - instance of SysUser, which state will be returned.
	 * @return user's state.
	 */
	public static UserState getUserState(Sysuser user) {
		if (user == null)
			return UserState.NOT_EXISTS;
		if (!user.isVerified())
			return UserState.NOT_VERIFIED;
		return UserState.IS_VALID; 
	}
	
	/**
	 * Validates user's registration form. If user with same email as in for 
	 * already exists - return {@code RegistrationState.EMAIL_EXISTS} else 
	 * return {RegistrationState.SUCCESS}.
	 * @param user - user's registration form.
	 * @return state of registration.
	 */
	/* TODO check if it is possible to omit client side validation on non empty
	*  fields.
	*/
	private static RegistrationState userRegValidation(Sysuser user) {
		// check user's attributes
		if(user.getFName().isEmpty() || user.getLName().isEmpty() || 
				user.getEmail().isEmpty() || user.getPassword().isEmpty() 
				|| !UsertypeUtil.exist(user.getUsertype().getUserTypeId()))
			return UserUtil.RegistrationState.INVALID_PARAM;
		if (!UserUtil.isEmailValid(user.getEmail()))
			return RegistrationState.EMAIL_EXISTS;
		return RegistrationState.SUCCESS;
	}
	
	/**
	 * Validates user email. If user with same email already exists returns
	 * <tt>false</tt>.
	 * @param email - user's email to be checked
	 * @return - true if same email does not exist.
	 */
	public static boolean isEmailValid(String email) {
		SysuserDAO dao = new SysuserDAO();
		boolean exist = dao.emailExist(email);
		dao.closeSession();
		return !exist;
	}
	
	/**
	 * Tries to add new user. If {@code UserUtil.userRegValidation} returns
	 * {@code  UserUtil.RegistrationState.SUCCESS} adds new user. Returns registration
	 * state.
	 * @param model - model of the requesting page.
	 * @param fName - user's first name.
	 * @param lName - user's last name.
	 * @param email - user's email.
	 * @param password - user's password.
	 * @param usertypeId - user's type id.
	 * @return - registration state.
	 * @throws SQLException
	 */
	public static RegistrationState addNewUser(String fName, String lName,
			String email, String password, int usertypeId) {
		// create user
		Sysuser user = new Sysuser();
		user.setFName(fName);
		user.setLName(lName);
		user.setEmail(email);
		// get hash of user's password
		user.setPassword(SecurityUtil.hashInput(password));
		Usertype type = new Usertype();
		type.setUserTypeId(usertypeId);
		user.setUsertype(type);
		UserUtil.RegistrationState rState = UserUtil.userRegValidation(user);
		if (rState == UserUtil.RegistrationState.SUCCESS) {
			GenericDAO dao = new GenericDAO();
			try {
				dao.addEntity(user);
			} catch (SQLException e) {
				rState = UserUtil.RegistrationState.INVALID_PARAM;
				e.printStackTrace();
			}
			dao.closeSession();
		}
		return rState;
	}
}
