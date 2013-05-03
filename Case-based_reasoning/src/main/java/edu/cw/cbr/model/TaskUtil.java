package edu.cw.cbr.model;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.springframework.web.bind.annotation.RequestParam;

import edu.cw.cbr.domain.Sysuser;
import edu.cw.cbr.domain.Task;
import edu.cw.cbr.model.UserUtil.RegistrationState;

public class TaskUtil {
	
	private final static int TASK_ATTR_NUM = 6;
	@SuppressWarnings("unchecked")
	public static Object[][] getTasks(int from, int max) {
		GenericDAO dao = new GenericDAO();
		List<Task> ltasks = dao.getCriteria(Task.class).addOrder(Order.asc("taskId")).setFirstResult(from)
				.setMaxResults(max).list();
		Object[][] tasks = new Object[ltasks.size()]
				[TASK_ATTR_NUM];
		for (int i = 0; i < ltasks.size(); i++) {
			tasks[i][0] = ltasks.get(i).getTaskId();
			tasks[i][1] = ltasks.get(i).getComputationalComplexity();
			tasks[i][2] = ltasks.get(i).getAmountOfMemory();
			tasks[i][3] = ltasks.get(i).getDownloadingAmountOfData();
			tasks[i][4] = ltasks.get(i).getUploadingAmountOfData();
			tasks[i][5] = ltasks.get(i).getMaxRunningTime();
		}
		dao.closeSession();
		return tasks;
	}
	
	public static int getTasksNum() {
		GenericDAO dao = new GenericDAO();
		int num = ((Long)dao.getCriteria(Task.class).
				setProjection(Projections.rowCount()).list().get(0)).intValue();
		dao.closeSession();
		return num;
	}
	
	public static boolean addNewTask(float complex, float memoryNeed,
			float dataToDown, float dataToUp, float timeReq) 
					throws SQLException {
		if(complex <= 0 || memoryNeed <= 0 || dataToDown <= 0 || dataToUp <= 0
				|| timeReq <= 0)
			return false;
		GenericDAO dao = new GenericDAO();
		Task task = new Task();
		task.setComputationalComplexity(complex);
		task.setAmountOfMemory(memoryNeed);
		task.setDownloadingAmountOfData(dataToDown);
		task.setUploadingAmountOfData(dataToUp);
		task.setMaxRunningTime(timeReq);
		dao.addEntity(task);
		dao.closeSession();
		return true;
	}
}
