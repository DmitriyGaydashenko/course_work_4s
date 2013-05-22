package edu.cw.cbr.model;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.cw.cbr.model.dao.GenericDAO;
import edu.cw.cbr.model.domain.Usertype;

/**
 * The class {@code UsertypeUtil} contains methods for performing basic
 * operations with {@code Usertype}.
 * @author Dmitriy Gaydashenko
 *
 */
public class UsertypeUtil extends GenericUtil<Usertype>{

	public UsertypeUtil() {
		super(Usertype.class);
	}

	/**
	 * Return map of usertype's Ids and names.
	 * @return map of usertype's Ids and names.
	 * @throws SQLException
	 */
	public Map<Integer, String> getUsertypeNames() throws SQLException {
		GenericDAO<Usertype> dao = getNewDAO();
		List<Usertype> userTypes = dao.getAllEntities();
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
	public boolean exist(int id) {
		if (id < 0)
			return false;
		GenericDAO<Usertype> dao = getNewDAO();
		boolean succeeded = true;
		try {
			return dao.getEntityById(id) != null;
		} catch (SQLException e) {
			succeeded = false;
			e.printStackTrace();
		}
		dao.closeSession();
		return succeeded;
	}

	@Override
	protected GenericDAO<Usertype> getNewDAO() {
		return new GenericDAO<Usertype>(CLASS);
	}
}
