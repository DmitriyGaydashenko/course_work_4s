package edu.cw.cbr.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.cw.cbr.controller.security.UserSession;
import edu.cw.cbr.model.SysuserUtil;
import edu.cw.cbr.model.domain.Sysuser;

/**
 * Provides methods that response on requests such as <tt>/</tt> and 
 * <tt>/signIn</tt> 
 * @author Dmitriy Gaydashenko
 *
 */
@Controller
public class SignInController implements IController{
	
	@Override
	public String home(HttpSession httpSession, Model model) {
		if (UserSession.isHttpSessionValid(httpSession))
			return "redirect:" + HOME_PAGE;
		return "redirect:" + SIGN_IN;
	}
	
	/**
	 * Processes <tt>/signIn</tt> request. Returns view of <tt>/signIn</tt> 
	 * page;
	 * @return reference on view of <tt>/signIn</tt> page.
	 */
	@RequestMapping(value = "/signIn", method = RequestMethod.GET)
	public String signIn() {
		return SIGN_IN;
	}
	
	/**
	 * If user is valid creates new instance of {@code  UserSession} and adds 
	 * it, as attribute, to HTTP session and returns request to js on
	 * redirection to homepage. If user is invalid: is deleted, is not verified
	 * or does not exist, returns user's state.
	 * @param session - HTTP session
	 * @param email - user's email
	 * @param password - user's password
	 * @return user's state or redirection request.
	 */
	@RequestMapping(value = "/signIn", method = RequestMethod.POST)
	public String signIn(Model model, HttpSession session, 
			@RequestParam String email, @RequestParam String password) {
		Sysuser user = new SysuserUtil().getSysuser(email, password);
		Sysuser.UserState userState = Sysuser.getUserState(user);
		if (userState == Sysuser.UserState.IS_VALID) {
			session.setAttribute("userSession", 
					new UserSession(user));
			return "redirect:" + HOME_PAGE;
		}
		String message = "";
		if (userState == Sysuser.UserState.NOT_VERIFIED)
			message = "This account is not verified.";
		else if (userState == Sysuser.UserState.NOT_EXISTS)
			message = "User with this email and password does not exist.";
		model.addAttribute("Error", message);
		return SIGN_IN;
	}
}
