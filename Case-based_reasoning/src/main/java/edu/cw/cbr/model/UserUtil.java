package edu.cw.cbr.model;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import edu.cw.cbr.controller.security.SecurityUtil;
import edu.cw.cbr.domain.Sysuser;

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
		EMAIL_EXISTS};
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
		GenericDAO dao = new GenericDAO();
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
	public static Sysuser getSysUser(int sysuserId, GenericDAO dao) 
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
		String query = "from Sysuser where email = '"+email+"' and password = '" 
				+SecurityUtil.hashInput(password)+"'";
		GenericDAO dao = new GenericDAO();
		List<Sysuser> users = dao.runSelectQuery(query);
		dao.closeSession();
		return users.isEmpty() ? null : users.get(0);
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
		if (!UserUtil.isEmailValid(user.getEmail()))
			return RegistrationState.EMAIL_EXISTS;
		return RegistrationState.SUCCESS;
	}
	
	/**
	 * Validates user email. If user with same email already exists returns
	 * <tt>false</tt>.
	 * @param email - user;s email to be checked
	 * @return - true if same email does not exist.
	 */
	public static boolean isEmailValid(String email) {
		GenericDAO dao = new GenericDAO();
		Long count = (Long)dao.getCriteria(Sysuser.class)
				.add(Restrictions.eq("email", email))
				.setProjection(Projections.rowCount()).uniqueResult();
		dao.closeSession();
		return !(count > 0);
	}
	
	/**
	 * Tries to add new user. If {@code UserUtil.userRegValidation} returns
	 * {@code  UserUtil.RegistrationState.SUCCESS} adds new user. Returns registration
	 * state.
	 * @param user - to be added.
	 * @return - registration state.
	 * @throws SQLException
	 */
	public static RegistrationState addNewUser(Sysuser user) throws SQLException {
		UserUtil.RegistrationState rState = UserUtil.userRegValidation(user);
		if (rState == UserUtil.RegistrationState.SUCCESS) {
			GenericDAO dao = new GenericDAO();
			dao.addEntity(user);
			dao.closeSession();
		}
		return rState;
	}
}
