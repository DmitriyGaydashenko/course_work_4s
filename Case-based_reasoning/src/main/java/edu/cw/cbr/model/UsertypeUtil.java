package edu.cw.cbr.model;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.cw.cbr.domain.Usertype;

/**
 * The class {@code UsertypeUtil} contains methods for performing basic
 * operations with {@code Usertype}.
 * @author Dmitriy Gaydashenko
 *
 */
public class UsertypeUtil {

	/**
	 * Return map of usertype's Ids and names.
	 * @return map of usertype's Ids and names.
	 * @throws SQLException
	 */
	public static Map<Integer, String> getUsertypeNames() throws SQLException {
		GenericDAO dao = new GenericDAO();
		List<Usertype> userTypes = dao.getAllEntities(Usertype.class);
		dao.closeSession();
		Map<Integer, String> userTypeNames = new  HashMap<Integer, String>();
		for(Usertype userType : userTypes)
			userTypeNames.put(userType.getUserTypeId(),
					userType.getUserTypeName());
		return userTypeNames;
	}
	
	/**
	 * Returns <true> if instance of {@code Usertype} with identifier equals to
	 * {@code id} exists.
	 * @param id - instance's identifier.
	 * @return <tt>true</tt> if instance of {@code Usertype} with identifier 
	 * equals to {@code id} exists.
	 */
	public static boolean exist(int id) {
		if (id < 0)
			return false;
		GenericDAO dao = new GenericDAO();
		boolean succeeded = true;
		try {
			return dao.getEntityById(Usertype.class, id) != null;
		} catch (SQLException e) {
			succeeded = false;
			e.printStackTrace();
		}
		dao.closeSession();
		return succeeded;
	}
}
