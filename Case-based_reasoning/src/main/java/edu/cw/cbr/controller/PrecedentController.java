package edu.cw.cbr.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.cw.cbr.model.PrecedentUtil;

/**
 * Provides methods that response on requests for creating, editing and 
 * deletion instances of {@code Precedent}.
 * @author Dmitriy Gaydashenko
 */
@Controller
public class PrecedentController {
	
	/**
	 * Processes <tt>/precedents</tt> request. If {@code httpSession} is valid 
	 * redirects to @code {DelaultAddress.HOME_PAGE} else returns view of 
	 * {@code DelaultAddress.SIGN_IN} page.
	 * @param httpSession - HTTP session.
	 * @param model - instance of class, which implements interface that 
	 * defines a holder for model attributes
	 * @return - view, which corresponds to request.
	 */
	@RequestMapping(value = "/precedents", method = RequestMethod.GET)
	public String home(HttpSession httpSession, Model model) {
		//if (!UserSession.isHttpSessionValid(httpSession))
			//return "redirect:" + StartpageController.SIGN_IN;
		//UserInfController.mapUserInf(model, (UserSession)httpSession.
				//getAttribute(UserSession.U_SESSION_NAME));
		return "/precedents";
	}
	
	/**
	 * Processes <tt>/getprecedent</tt> request. If {@code httpSession} is 
	 * valid returns data for first {@code max} precedents starting from 
	 * precedent with number {@code from}, else return <tt>null</tt> 
	 * @param httpSession - HTTP session. 
	 * @param from - starting point.
	 * @param max - max amount of elements returned.
	 * @return {@code max} precedents, starting from {@code from} if 
	 * user's session is valid, else null.
	 */
	@RequestMapping(value = "/getprecedent", method = RequestMethod.POST)
	public @ResponseBody Object[][] getPrecedents(HttpSession httpSession,
			int from, int max) {
		//if (!UserSession.isHttpSessionValid(httpSession))
		//return null;
		return PrecedentUtil.getPrecedents(from, max);
	}
	
	/**
	 * Processes <tt>/getprecedentNum</tt> request. If {@code httpSession} is 
	 * valid returns total number of precedents, else return -1. 
	 * @param httpSession - HTTP session. 
	 * @return number of precedents, else -1.
	 */
	@RequestMapping(value = "/getprecedentNum", method = RequestMethod.POST)
	public @ResponseBody int getPrecedentsNum(HttpSession httpSession ) {
		//if (!UserSession.isHttpSessionValid(httpSession))
		//return -1;
		return PrecedentUtil.getTotalNum();
	}
}
