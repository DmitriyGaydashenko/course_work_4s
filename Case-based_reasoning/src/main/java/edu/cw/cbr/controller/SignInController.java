package edu.cw.cbr.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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
	
	/**
	 * Processes <tt>/signIn</tt> request. Returns view of <tt>/signIn</tt> 
	 * page;
	 * @return reference on view of <tt>/signIn</tt> page.
	 */
	@Override
	@RequestMapping(value = "/signIn", method = RequestMethod.GET)
	public String home(HttpSession httpSession, Model model) {
		if (UserSession.initModelAndSession(httpSession))
			return "redirect:" + HOME_PAGE;
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
			UserSession uSes = (UserSession) session
					.getAttribute(UserSession.U_SESSION_NAME);
			if (uSes != null)
				uSes.setUser(user);
			else
				session.setAttribute(UserSession.U_SESSION_NAME, 
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
	
	@RequestMapping(value = "/exit", method = RequestMethod.POST)
	public void exit(HttpSession httpSession, Model model) {
		UserSession.removeUSession(httpSession);
	}
}
