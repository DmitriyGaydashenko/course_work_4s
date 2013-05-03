package edu.cw.cbr.controller;

import java.sql.SQLException;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.cw.cbr.controller.security.SecurityUtil;
import edu.cw.cbr.controller.security.UserSession;
import edu.cw.cbr.domain.Sysuser;
import edu.cw.cbr.domain.Usertype;
import edu.cw.cbr.model.UserUtil;
import edu.cw.cbr.model.UsertypeUtil;

/**
 * Provides methods that response on requests for registration.
 * @author Dmitriy Gaydashenko
 */
@Controller
public class SignUpController {
		
	private static final String INTERNAL_ERROR = "Internal error."+ 
			"Please, try again.";
	private static final String MODEL_ERROR_ATT = "Error";
	
	/**
	 * Processes <tt>/signUp</tt> request. If current user session is valid 
	 * redirects to @code {HOME_PAGE} else return view of sign up page.
	 * @param httpSession - HTTP session. 
	 * (@see javax.servlet.http.HttpSession)
	 * @return view, which corresponds to request.
	 * @throws SQLException 
	 */
	@RequestMapping(value = "/signUp", method = RequestMethod.GET)
	public String home(HttpSession httpSession, Model model) {
		if (UserSession.isHttpSessionValid(httpSession))
			return "redirect:" + PrecedentController.HOME_PAGE;
		try {
			model.addAttribute("userTypes", UsertypeUtil.getUsertypeNames());
		} catch (SQLException e) {
			model.addAttribute(MODEL_ERROR_ATT, INTERNAL_ERROR);
			e.printStackTrace();
		}
		return "/signUp";
	}
	
	/**
	 * Processes <tt>/signUp/validEmail</tt> request. If email ({@code email})
	 * is valid, there is no user with same email, returns <tt>true</tt>. 
	 * @param email - email to be validated.
	 * @return <tt>true</tt> if email is valid. 
	 */
	@RequestMapping(value = "/signUp/validEmail", method = RequestMethod.POST)
	public @ResponseBody String isEmailValid(@RequestParam String email) {
		return UserUtil.isEmailValid(email)+"";
	}
	
	/**
	 * Processes <tt>/signUp POST</tt> request. If user is valid, adds to the 
	 * database and returns {@code UserUtil.RegistrationState.SUCCESS}, else
	 * adds model attribute, which describes error, returns registration state.
	 * @param model - model of the requesting page.
	 * @param fName - user's first name.
	 * @param lName - user's last name.
	 * @param email - user's email.
	 * @param password - user's password.
	 * @param usertypeId - user's type id.
	 * @return registration state.
	 */
	@RequestMapping(value = "/signUp", method = RequestMethod.POST)
	public @ResponseBody String signUp(Model model, @RequestParam String fName,
			@RequestParam String lName, @RequestParam String email,
			@RequestParam String password, @RequestParam int usertypeId) {
		Sysuser user = new Sysuser();
		user.setFName(fName);
		user.setLName(lName);
		user.setEmail(email);
		user.setPassword(SecurityUtil.hashInput(password));
		Usertype type = new Usertype();
		type.setUserTypeId(usertypeId);
		user.setUsertype(type);
		UserUtil.RegistrationState rState = null;
		try {
			rState = UserUtil.addNewUser(user);
		} catch (SQLException e) {
			model.addAttribute(MODEL_ERROR_ATT, INTERNAL_ERROR);
			e.printStackTrace();
		}
		if (rState == UserUtil.RegistrationState.SUCCESS) {
			return "redirect:" + StartpageController.SIGN_IN;// redirect via javascript.
		}
		return rState.name();
	}

}