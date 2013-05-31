package edu.cw.cbr.controller;

import java.sql.SQLException;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.cw.cbr.controller.security.UserSession;
import edu.cw.cbr.model.SysuserUtil;
import edu.cw.cbr.model.UsertypeUtil;

/**
 * Provides methods that response on requests for registration.
 * @author Dmitriy Gaydashenko
 */
@Controller
public class SignUpController implements IController{
	
	/**
	 * Processes <tt>/signUp</tt> request. If current user session is valid 
	 * redirects to @code {HOME_PAGE} else return view of sign up page.
	 * @param httpSession - HTTP session. 
	 * (@see javax.servlet.http.HttpSession)
	 * @return view, which corresponds to request.
	 */
	@RequestMapping(value = "/signUp", method = RequestMethod.GET)
	public String home(HttpSession httpSession, Model model) {
		if (UserSession.initModelAndSession(httpSession))
			return "redirect:" + HOME_PAGE;
		try {
			model.addAttribute("userTypes", new UsertypeUtil()
														.getUsertypeNames());
		} catch (SQLException e) {
			model.addAttribute("Error", "Internal error.");
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
		return (!SysuserUtil.isEmailExists(email))+"";
	}
	
	/**
	 * Processes <tt>/signUp POST</tt> request. If user is valid, adds to the 
	 * database and returns {@code SysuserUtil.RegistrationState.SUCCESS}, else
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
	public String signUp(HttpSession httpSession, Model model,
			@RequestParam String fName,
			@RequestParam String lName, @RequestParam String email,
			@RequestParam String password, @RequestParam int usertypeId) {
		SysuserUtil.RegistrationState rState = null;
		SysuserUtil util = new SysuserUtil();
		rState = util.addNewUser(fName, lName, email, password,
				usertypeId);
		if (rState == SysuserUtil.RegistrationState.SUCCESS) {
			return "redirect:" + SIGN_IN;
		}
		else if (rState == SysuserUtil.RegistrationState.EMAIL_EXISTS)
			model.addAttribute("Error", "User with this email already exists");
		else
			model.addAttribute("Error", "Invalid parameter.");
		return home(httpSession, model);
	}

}
