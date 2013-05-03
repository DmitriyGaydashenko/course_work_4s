package edu.cw.cbr.controller;

import java.sql.SQLException;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.cw.cbr.domain.Task;
import edu.cw.cbr.model.TaskUtil;

@Controller
public class TaskController {
	
	@RequestMapping(value = "/tasks", method = RequestMethod.GET)
	public String home(HttpSession httpSession, Model model) {
		//if (!UserSession.isHttpSessionValid(httpSession))
			//return "redirect:" + StartpageController.SIGN_IN;
		//UserInfController.mapUserInf(model, (UserSession)httpSession.
				//getAttribute(UserSession.U_SESSION_NAME));
		return "/tasks";
	}
	
	@RequestMapping(value = "/gettask", method = RequestMethod.POST)
	public @ResponseBody Object[][] getTasks(int from, int max) {
		return TaskUtil.getTasks(from, max);
	}
	
	@RequestMapping(value = "/gettaskNum", method = RequestMethod.POST)
	public @ResponseBody int getTasksNum() {
		return TaskUtil.getTasksNum();
	}
	
	@RequestMapping(value = "/addTask", method = RequestMethod.POST)
	public String add(Model model, @RequestParam float complex,
			@RequestParam float memoryNeed, @RequestParam float dataToDown,
			@RequestParam float dataToUp, @RequestParam float timeReq) {
		try {
			if(!TaskUtil.addNewTask(complex, memoryNeed, dataToDown, dataToUp,
					timeReq))
				model.addAttribute("Error", "Error. Task is invalid.");
		} catch (SQLException e) {
			model.addAttribute("Error", "Error. Can not add this task.");
			e.printStackTrace();
		}
		return "/tasks";
	}

}
