package edu.cw.cbr.controller;

import org.springframework.ui.Model;

import edu.cw.cbr.controller.security.UserSession;

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
	public static void mapUserInf(Model model, UserSession userSession) {
		model.addAttribute("fName", userSession.getFName());
		model.addAttribute("lName", userSession.getLName());
	}

}
