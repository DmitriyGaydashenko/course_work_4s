package edu.cw.cbr.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.cw.cbr.controller.security.UserSession;
import edu.cw.cbr.model.PrecedentUtil;
import edu.cw.cbr.model.domain.Precedent;

/**
 * Provides methods that response on requests for creating, editing and 
 * deletion instances of {@code Precedent}
 * @author Dmitriy Gaydashenko
 */
@Controller
@RequestMapping("/precedents")
public class PrecedentController extends ConnectedController<Precedent>{
	
	/**
	 * Instantiates a new processor controller, which provides methods to 
	 * process requests to instances of {@code Precedent}.
	 */
	
	public PrecedentController() {
		super(new PrecedentUtil(), "precedents",
				"/precedents/");
	}


	/**
	 * Processes <tt>/precedents</tt> request. If {@code httpSession} is valid 
	 * returns view of {@code Precedent} else returns view of 
	 * {@code DefaultAddress.SIGN_IN} page.
	 * @param httpSession - HTTP session.
	 * @param model - instance of class, which implements interface that 
	 * defines a holder for model attributes.
	 * @return - view, which corresponds to request.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(HttpSession httpSession, Model model) {
		if (!UserSession.initModelAndSession(httpSession))
			return "redirect:" + SignInController.SIGN_IN;
		UserInfController.mapUserInf(model, httpSession);
		return MAPPED_CLASS_VIEW;
	}
	
	/**
	 * Processes <tt>/addPrecedent</tt> request. If user's session is valid 
	 * tries to add new instance of {@code Precedent}, if did not succeeded, 
	 * adds error message to the model, returns view of {@code Precedent} else 
	 * returns view of {@code DefaultAddress.SIGN_IN} page.
	 * @param httpSession - HTTP session.
	 * @param model - instance of class, which implements interface that 
	 * defines a holder for model attributes.
	 * @param complex - computational complexity of application. 
	 * @param memoryNeed - memory requirements.
	 * @param dataToDown - amount of downloading data.
	 * @param dataToUp - amount of uploading data.
	 * @param timeReq - running time requirements.
	 * @return - view, which corresponds to request.
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String add(HttpSession httpSession, Model model, 
			@RequestParam float complex, @RequestParam float memoryNeed,
			@RequestParam float dataToDown, @RequestParam float dataToUp,
			@RequestParam float timeReq) {
		if (!UserSession.initModelAndSession(httpSession))
			return "redirect:" + SignInController.SIGN_IN;
//		if(!((PrecedentUtil) UTIL).addNewPrecedent(complex, memoryNeed, dataToDown,
//				dataToUp, timeReq))
//			model.addAttribute(Error.ADD.getFlag(), Error.ADD.getMessage());
		return "redirect:" + INDEX;
	}

	/**
	 * Processes <tt>/update</tt> request. If user's session is valid 
	 * tries to update instance of {@code Precedent}, if did not succeeded, 
	 * adds error message to the model, returns view of {@code Precedent} else 
	 * returns view of {@code DefaultAddress.SIGN_IN} page.
	 * @param httpSession - HTTP session.
	 * @param model - instance of class, which implements interface that 
	 * defines a holder for model attributes.
	 * @taskId - identifier of instance of {@code Precedent} to be updated.
	 * @param complex - computational complexity of application. 
	 * @param memoryNeed - memory requirements.
	 * @param dataToDown - amount of downloading data.
	 * @param dataToUp - amount of uploading data.
	 * @param timeReq - running time requirements.
	 * @return - view, which corresponds to request.
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String updatePrecedent(HttpSession httpSession, Model model,
			@RequestParam int taskId, @RequestParam float complex, 
			@RequestParam float memoryNeed,	@RequestParam float dataToDown,
			@RequestParam float dataToUp, @RequestParam float timeReq) {
		if (!UserSession.initModelAndSession(httpSession))
			return "redirect:" + SignInController.SIGN_IN;
//		if(!((PrecedentUtil) UTIL).updatePrecedent(taskId, complex, memoryNeed,
//				dataToDown, dataToUp, timeReq))
//			model.addAttribute(Error.UPDATE.getFlag(), Error.UPDATE.getMessage());
		return "redirect:" + INDEX;
	}
	
	@RequestMapping(value = "/getTask", method = RequestMethod.POST)
	public @ResponseBody Object[] getTaskData(HttpSession httpSession,
			int precedentId) {
		if (!UserSession.initModelAndSession(httpSession))
			return null;
		return ((PrecedentUtil)UTIL).getTaskData(precedentId);
	}
	
	@RequestMapping(value = "/getDeviceState", method = RequestMethod.POST)
	public @ResponseBody Object[] getDeviceStateData(HttpSession httpSession,
			int precedentId) {
		if (!UserSession.initModelAndSession(httpSession))
			return null;
		return ((PrecedentUtil)UTIL).getDeviceStateData(precedentId);
	}
	
}
