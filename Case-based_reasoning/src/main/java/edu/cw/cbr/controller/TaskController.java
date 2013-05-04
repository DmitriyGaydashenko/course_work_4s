package edu.cw.cbr.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.cw.cbr.model.TaskUtil;

/**
 * Provides methods that response on requests for creating, editing and 
 * deletion instances of {@code Task}
 * @author Dmitriy Gaydashenko
 */
@Controller
public class TaskController {
	
	/**
	 * Processes <tt>/tasks</tt> request. If {@code httpSession} is valid 
	 * returns view of {@code Task} else returns view of 
	 * {@code DelaultAddress.SIGN_IN} page.
	 * @param httpSession - HTTP session.
	 * @param model - instance of class, which implements interface that 
	 * defines a holder for model attributes.
	 * @return - view, which corresponds to request.
	 */
	@RequestMapping(value = "/tasks", method = RequestMethod.GET)
	public String home(HttpSession httpSession, Model model) {
		//if (!UserSession.isHttpSessionValid(httpSession))
			//return "redirect:" + StartpageController.SIGN_IN;
		//UserInfController.mapUserInf(model, (UserSession)httpSession.
				//getAttribute(UserSession.U_SESSION_NAME));
		return "/tasks";
	}
	
	/**
	 * Processes <tt>/gettask</tt> request. If {@code httpSession} is 
	 * valid returns data for first {@code max} tasks starting from 
	 * task with number {@code from}, else return <tt>null</tt> 
	 * @param httpSession - HTTP session. 
	 * @param from - starting point.
	 * @param max - max amount of elements returned.
	 * @return {@code max} tasks, starting from {@code from} if 
	 * user's session is valid, else null.
	 */
	@RequestMapping(value = "/gettask", method = RequestMethod.POST)
	public @ResponseBody Object[][] getTasks(HttpSession httpSession, int from,
			int max) {
		//if (!UserSession.isHttpSessionValid(httpSession))
		//return null;
		return TaskUtil.getTasks(from, max);
	}
	
	/**
	 * Processes <tt>/gettaskNum</tt> request. If {@code httpSession} is 
	 * valid returns total number of tasks, else return -1. 
	 * @param httpSession - HTTP session. 
	 * @return number of tasks, else -1.
	 */
	@RequestMapping(value = "/gettaskNum", method = RequestMethod.POST)
	public @ResponseBody int getTasksNum(HttpSession httpSession) {
		//if (!UserSession.isHttpSessionValid(httpSession))
		//return -1;
		return TaskUtil.getTasksNum();
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
	@RequestMapping(value = "/addTask", method = RequestMethod.POST)
	public String add(HttpSession httpSession, Model model, 
			@RequestParam float complex, @RequestParam float memoryNeed,
			@RequestParam float dataToDown, @RequestParam float dataToUp,
			@RequestParam float timeReq) {
		//if (!UserSession.isHttpSessionValid(httpSession))
		//return "redirect:" + StartpageController.SIGN_IN;
		if(!TaskUtil.addNewTask(complex, memoryNeed, dataToDown, dataToUp,
				timeReq))
			model.addAttribute("Error", "Error. Task is invalid.");
		return "redirect:/tasks";
	}
	
	/**
	 * Processes <tt>/deletetask</tt> request. If {@code httpSession} is valid 
	 * tries to delete instance of {@code Task} with identifier {@code rowId}.
	 * If deletion did not succeeded adds to model error message.
	 * @param model - instance of class, which implements interface that 
	 * defines a holder for model attributes.
	 * @param rowId - {@code Task} instance's id to be deleted.
	 */
	@RequestMapping(value = "/deletetask", method = RequestMethod.POST)
	public@ResponseBody void remove(Model model, int rowId) {
		//if (!UserSession.isHttpSessionValid(httpSession))
		//return;
		if(!TaskUtil.deleteTask(rowId));
			model.addAttribute("Error", "Error. Can not delete this task.");
	}
	
	/**
	 * Processes <tt>/deptask</tt> request. If {@code httpSession} is valid 
	 * returns map of {@code Task}'s instance, with identifier equals to 
	 * {@code rowId}, dependent informational relations' names and it's number,
	 * else returns <tt>null</tt>.
	 * @param model - instance of class, which implements interface that 
	 * defines a holder for model attributes.
	 * @param rowId - identifier of instance of {@code Task}, for which 
	 * dependent entities will be found.
	 * @return if {@code httpSession} is valid returns map of dependent 
	 * informational relations' names and it's number, else returns 
	 * <tt>null</tt>.
	 */
	@RequestMapping(value = "/deptask", method = RequestMethod.POST)
	public@ResponseBody Map<String, Integer> getDependentPrecedentsNum(
			Model model, int rowId) {
		//if (!UserSession.isHttpSessionValid(httpSession))
		//return;
		return TaskUtil.getDependentEntitiesNum(rowId);
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
	@RequestMapping(value = "/updatetask", method = RequestMethod.POST)
	public String updateTask(HttpSession httpSession, Model model,
			@RequestParam int taskId, @RequestParam float complex, 
			@RequestParam float memoryNeed,	@RequestParam float dataToDown,
			@RequestParam float dataToUp, @RequestParam float timeReq) {
		//if (!UserSession.isHttpSessionValid(httpSession))
		//return "redirect:" + StartpageController.SIGN_IN;
		if(!TaskUtil.updateTask(taskId, complex, memoryNeed, dataToDown, dataToUp, timeReq))
			model.addAttribute("UpdateError", "Error! Task is invalid.");
		return "/tasks";
	}
}
