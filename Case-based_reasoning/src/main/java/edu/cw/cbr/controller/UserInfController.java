package edu.cw.cbr.controller;

import java.sql.SQLException;

import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;

import edu.cw.cbr.controller.security.UserSession;
import edu.cw.cbr.model.SysuserUtil;
import edu.cw.cbr.model.UsertypeUtil;

/**
 * Provides methods for adding, mapping and editing user's information.
 * @author User
 *
 */
public class UserInfController {
	/**
	 * Adds user's information into the model.
	 * @param model - instance of Model, where user's information will be 
	 * mapped.
	 * @param userSession - user's information provider.
	 */
	public static void mapUserInf(Model model, HttpSession httpSession) {
		UserSession userSession = UserSession.getUSession(httpSession);
		model.addAttribute("fName", userSession.getFName());
		model.addAttribute("lName", userSession.getLName());
	}
	
	public static void mapUserAddData(Model model, UserSession userSession) throws SQLException {
		model.addAttribute("uType", new UsertypeUtil().getEntity(
				userSession.getTypeId()).getUserTypeName());
		model.addAttribute("email", new SysuserUtil()
		.getEntity(userSession.getId()).getEmail());
	}

}
