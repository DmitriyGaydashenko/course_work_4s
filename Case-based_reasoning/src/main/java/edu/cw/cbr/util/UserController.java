/*package edu.cw.cbr.util;

import java.util.List;

import edu.cw.cbr.model.dao.GenericDAO;
import edu.cw.cbr.model.Sysuser;

public class UserController {
	public static boolean checkAvailability(String email) {
		String query = "from User where email = '"+email+"'";
		List<Sysuser> users = GenericDAO.runSelectQuery(query);
		return users.isEmpty();
	}
	public static boolean checkUserTypeId(int userTypeId) {
		String query = "from UserType where userTypeId = '"+userTypeId+"'";
		return !GenericDAO.runSelectQuery(query).isEmpty();
	}

}
*/