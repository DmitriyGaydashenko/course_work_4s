package edu.cw.cbr.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import edu.cw.cbr.model.TaskUtil;
import edu.cw.cbr.model.domain.Task;

/**
 * Provides methods that response on requests for creating, editing and 
 * deletion instances of {@code Task}
 * @author Dmitriy Gaydashenko
 */
@Controller
@RequestMapping("/tasks")
public class TaskController extends ConnectedController<Task>{
	
	/**
	 * Instantiates a new processor controller, which provides methods to 
	 * process requests to instances of {@code Task}.
	 */
	public TaskController() {
		super(new TaskUtil(), "tasks",
				"/tasks/");
	}


	/**
	 * Processes <tt>/tasks</tt> request. If {@code httpSession} is valid 
	 * returns view of {@code Task} else returns view of 
	 * {@code DelaultAddress.SIGN_IN} page.
	 * @param httpSession - HTTP session.
	 * @param model - instance of class, which implements interface that 
	 * defines a holder for model attributes.
	 * @return - view, which corresponds to request.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(HttpSession httpSession, Model model) {
		//if (!UserSession.isHttpSessionValid(httpSession))
			//return "redirect:" + StartpageController.SIGN_IN;
		//UserInfController.mapUserInf(model, (UserSession)httpSession.
				//getAttribute(UserSession.U_SESSION_NAME));
		return MAPPED_CLASS_VIEW;
	}
	
	/**
	 * Processes <tt>/addTask</tt> request. If user's session is valid 
	 * tries to add new instance of {@code Task}, if did not succeeded, 
	 * adds error message to the model, returns view of {@code Task} else 
	 * returns view of {@code DelaultAddress.SIGN_IN} page.
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
		//if (!UserSession.isHttpSessionValid(httpSession))
		//return "redirect:" + StartpageController.SIGN_IN;
		if(!((TaskUtil) UTIL).addNewTask(complex, memoryNeed, dataToDown,
				dataToUp, timeReq))
			model.addAttribute(Error.ADD.getFlag(), Error.ADD.getMessage());
		return "redirect:" + INDEX;
	}

	/**
	 * Processes <tt>/update</tt> request. If user's session is valid 
	 * tries to update instance of {@code Task}, if did not succeeded, 
	 * adds error message to the model, returns view of {@code Task} else 
	 * returns view of {@code DelaultAddress.SIGN_IN} page.
	 * @param httpSession - HTTP session.
	 * @param model - instance of class, which implements interface that 
	 * defines a holder for model attributes.
	 * @taskId - identifier of instance of {@code Task} to be updated.
	 * @param complex - computational complexity of application. 
	 * @param memoryNeed - memory requirements.
	 * @param dataToDown - amount of downloading data.
	 * @param dataToUp - amount of uploading data.
	 * @param timeReq - running time requirements.
	 * @return - view, which corresponds to request.
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String updateTask(HttpSession httpSession, Model model,
			@RequestParam int taskId, @RequestParam float complex, 
			@RequestParam float memoryNeed,	@RequestParam float dataToDown,
			@RequestParam float dataToUp, @RequestParam float timeReq) {
		//if (!UserSession.isHttpSessionValid(httpSession))
		//return "redirect:" + StartpageController.SIGN_IN;
		if(!((TaskUtil) UTIL).updateTask(taskId, complex, memoryNeed,
				dataToDown, dataToUp, timeReq))
			model.addAttribute(Error.UPDATE.getFlag(), Error.UPDATE.getMessage());
		return "redirect:" + INDEX;
	}
}
