package edu.cw.cbr.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.cw.cbr.controller.security.UserSession;
import edu.cw.cbr.model.PrecedentUtil;

/**
 * Provides methods that response on requests for creating, editing and 
 * deleting precedents.
 * @author Dmitriy Gaydashenko
 */
@Controller
public class PrecedentController {
	
	/**
	 * Homepage's address.
	 */
	public static final String HOME_PAGE = "/precedents";
	
	@RequestMapping(value = "/precedents", method = RequestMethod.GET)
	public String home(HttpSession httpSession, Model model) {
		//if (!UserSession.isHttpSessionValid(httpSession))
			//return "redirect:" + StartpageController.SIGN_IN;
		//UserInfController.mapUserInf(model, (UserSession)httpSession.
				//getAttribute(UserSession.U_SESSION_NAME));
		return "/precedents";
	}
	
	@RequestMapping(value = "/getprecedent", method = RequestMethod.POST)
	public @ResponseBody Object[][] getPrecedents(int from, int max) {
		return PrecedentUtil.getPrecedents(from, max);
	}
	
	@RequestMapping(value = "/getprecedentNum", method = RequestMethod.POST)
	public @ResponseBody int getPrecedentsNum() {
		return PrecedentUtil.getPrecedentsNum();
	}
}
