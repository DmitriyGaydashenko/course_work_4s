package edu.cw.cbr.model;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import edu.cw.cbr.controller.security.SecurityUtil;
import edu.cw.cbr.model.dao.GenericDAO;
import edu.cw.cbr.model.domain.Sysuser;

/**
 * The class {@code SysuserUtil} contains methods for performing basic
 * operations with {@code Sysuser}.
 * @author Dmitriy Gaydashenko
 *
 */
public class SysuserUtil extends GenericTableAble<Sysuser>{
	
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
		
	public SysuserUtil() {
		super(Sysuser.class);
	}
	/**
	 * Checks if user with id {@code sysuserId} is valid.
	 * @param sysuserId - id of user to be checked.
	 * @return - <tt>true</tt> if user exists, was not deleted and is verified.
	 * @throws SQLException
	 */
	public static boolean isValid(int sysuserId) throws SQLException {
		return SysuserUtil.isValid(new SysuserUtil().getEntity(sysuserId));
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
	 * Returns instance of {@code Sysuser} if there user exists with email and
	 * password equal to {@code email, password}, else returns <tt>null</tt>.
	 * @param email - user's email.
	 * @param password - user's password.
	 * @return instance of Sysuser if it exists with email and password
	 * equal to
	 * {@code email, password} exists, else returns <tt>null</tt>.
	 */
	public Sysuser getSysuser(String email, String password)
			throws IllegalArgumentException{
		if(email.isEmpty() || password.isEmpty())
			throw new IllegalArgumentException();
		SysuserDAO dao = getNewDAO();
		Sysuser user = dao.getSysuser(email, 
				SecurityUtil.hashInput(password));
		dao.closeSession();
		return user;
	}
	
	/**
	 * Validates user's registration form. If user with same email as in for 
	 * already exists - return {@code RegistrationState.EMAIL_EXISTS} else 
	 * return {RegistrationState.SUCCESS}.
	 * @param user - user's registration form.
	 * @return state of registration.
	 */
	private RegistrationState userRegValidation(String fName, String lName,
			String email, String password, int usertypeId) {
		// check user's attributes
		if(!Sysuser.isValidParams(fName, lName, email, password, usertypeId))
			return SysuserUtil.RegistrationState.INVALID_PARAM;
		if (!isEmailExists(email))
			return RegistrationState.EMAIL_EXISTS;
		return RegistrationState.SUCCESS;
	}
	
	/**
	 * Validates user email. If user with same email already exists returns
	 * <tt>false</tt>.
	 * @param email - user's email to be checked
	 * @return - true if same email does not exist.
	 */
	public static boolean isEmailExists(String email) {
		SysuserUtil util = new SysuserUtil();
		SysuserDAO dao = util.getNewDAO();
		boolean exist = dao.emailExist(email);
		dao.closeSession();
		return !exist;
	}
	
	/**
	 * Tries to add new user. If {@code SysuserUtil.userRegValidation} returns
	 * {@code  SysuserUtil.RegistrationState.SUCCESS} adds new user. Returns registration
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
	public RegistrationState addNewUser(String fName, String lName,
			String email, String password, int usertypeId) {
		SysuserUtil.RegistrationState rState = userRegValidation(fName, lName,
				email, password, usertypeId);
		if (rState == SysuserUtil.RegistrationState.SUCCESS) {
			Sysuser user = Sysuser.unsafeSysuserFactory(fName, lName, email,
					password, usertypeId);
			SysuserDAO dao = getNewDAO();
			try {
				dao.addEntity(user);
			} catch (SQLException e) {
				rState = SysuserUtil.RegistrationState.INVALID_PARAM;
				e.printStackTrace();
			}
			dao.closeSession();
		}
		return rState;
	}
	@Override
	public List<String> getAllCols() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	protected SysuserDAO getNewDAO() {
		return new SysuserDAO();
	}
}

class SysuserDAO extends GenericDAO<Sysuser>{
	
	/**
	 * HQL script, which returns user by email and password.
	 */
	private static final String HQL_SUSER_BY_EMAIL_PASS = 
			"from Sysuser where email = '%s' and password = '%s'";
	
	public SysuserDAO() {
		super(Sysuser.class);
	}
	
	/**
	 * Returns, if exists, instance of {@code Sysuser} by email and hash of
	 * password, else return <tt>null</tt>.
	 * @param email - instance's of {@code Sysuser} email.
	 * @param hPassword - instance's of {@code Sysuser} password's hash.
	 * @return - if instance of {@code Sysuser} with such email and password 
	 * exists returns it, else returns <tt>null</tt>.
	 */
	@SuppressWarnings("unchecked")
	public Sysuser getSysuser(String email, String hPassword) {
		List<Sysuser> users = this.session.createQuery(
				String.format(HQL_SUSER_BY_EMAIL_PASS, email, hPassword))
				.list();
		return users.isEmpty() ? null : users.get(0);
	}
	
	/**
	 * Returns <tt>true</tt> if instance of {@code Sysuser} with email equals
	 * to {@code email} exists.
	 * @param email - instance's of {@code Sysuser} email. 
	 * @return <tt>true</tt> if instance of {@code Sysuser} with email equals
	 * to {@code email} exists.
	 */
	public boolean emailExist(String email) {
		Long count = (Long)session.createCriteria(Sysuser.class)
				.add(Restrictions.eq("email", email))
				.setProjection(Projections.rowCount()).uniqueResult();
		return count > 0;
	}
}
