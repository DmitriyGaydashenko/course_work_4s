package edu.cw.cbr.model;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import edu.cw.cbr.domain.Task;

/**
 * The class {@code TaskUtil} contains methods for performing basic
 * operations with {@code Task}.
 * @author Dmitriy Gaydashenko
 *
 */
public class TaskUtil {
	
	/**
	 * Number of task's attributes available for users.
	 */
	private final static int TASK_ATTR_NUM = 6;
	
	/**
	 * Returns data for first {@code max} tasks starting from task with
	 * number {@code from}. 
	 * @param from - starting point.
	 * @param max - max amount of elements returned.
	 * @return {@code max} tasks, starting from {@code from}.
	 */
	public static Object[][] getTasks(int from, int max) {
		GenericDAO dao = new GenericDAO();
		List<Task> ltasks = dao.getEntities(Task.class, from, max, "taskId",
				true);
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
	
	/**
	 * Returns total number of tasks.
	 * @return - total number of tasks;
	 */
	public static int getTasksNum() {
		GenericDAO dao = new GenericDAO();
		int num = dao.count(Task.class);
		dao.closeSession();
		return num;
	}
	
	/**
	 * Tries to add new instance of {@code Task} to the database.
	 * If instance is invalid returns <tt>false</tt>.
	 * @param complex - computational complexity of application. 
	 * @param memoryNeed - memory requirements.
	 * @param dataToDown - amount of downloading data.
	 * @param dataToUp - amount of uploading data.
	 * @param timeReq - running time requirements.
	 * @return <tt>True</tt> if task was successfully added.
	 */
	public static boolean addNewTask(float complex, float memoryNeed,
			float dataToDown, float dataToUp, float timeReq) {
		Task task = TaskUtil.createTask(complex, memoryNeed, dataToDown, 
				dataToUp, timeReq);
		if (task == null)
			return false;
		GenericDAO dao = new GenericDAO();
		boolean succeeded = true;
		try {
			dao.addEntity(task);
		} catch (SQLException e) {
			succeeded = false;
			e.printStackTrace();
		}
		dao.closeSession();
		return succeeded;
	}
	
	/**
	 * Returns new instance of  {@code Task}. If arguments are invalid returns
	 * <tt>null</tt>.
	 * @param complex - computational complexity of application. 
	 * @param memoryNeed - memory requirements.
	 * @param dataToDown - amount of downloading data.
	 * @param dataToUp - amount of uploading data.
	 * @param timeReq - running time requirements.
	 * @return if arguments are valid - new instance of {@code Task}, else null.
	 */
	private static Task createTask(float complex, float memoryNeed,
			float dataToDown, float dataToUp, float timeReq) {
		if(complex < 0 || memoryNeed < 0 || dataToDown < 0 || dataToUp < 0
				|| timeReq < 0)
			return null;
		Task task = new Task();
		task.setComputationalComplexity(complex);
		task.setAmountOfMemory(memoryNeed);
		task.setDownloadingAmountOfData(dataToDown);
		task.setUploadingAmountOfData(dataToUp);
		task.setMaxRunningTime(timeReq);
		return task;
	}
	/**
	 * Returns <tt>true</tt> if task was successfully deleted. 
	 * @param taskId - identifier of {@code Task}'s instance to be deleted.
	 * @return <tt>True if was successfully deleted.</tt>
	 */
	public static boolean deleteTask(int taskId) {
		GenericDAO dao = new GenericDAO();
		boolean succeeded = true;
		try {
			dao.deleteEntity(Task.class, taskId);
		} catch (SQLException e) {
			e.printStackTrace();
			succeeded = false;
		}
		dao.closeSession();
		return succeeded;
	}
	
	/**
	 * Returns map of dependent informational relations' names and it's number.
	 * @param taskId - identifier of instance of {@code Task}, for which 
	 * dependent entities will be found.
	 * @return map of dependent informational relations' names and it's number.
	 */
	public static Map<String, Integer> getDependentEntitiesNum(int taskId) {
		TaskDAO dao = new TaskDAO();
		Map<String, Integer> depEnt = dao.getDependentEntitiesNum(taskId);
		dao.closeSession();
		return depEnt;
	}
	
	/**
	 * Tries to delete dependent instances and update instance of {@code Task}.
	 *  If succeeded returns <tt>true</tt>.
	 * @param taskId - identifier of {@code Task} instance.
	 * @param complex - computational complexity of application. 
	 * @param memoryNeed - memory requirements.
	 * @param dataToDown - amount of downloading data.
	 * @param dataToUp - amount of uploading data.
	 * @param timeReq - running time requirements.
	 * @return <tt>true</tt> if deletion of dependent instances and update of
	 * instance of {@code Task}, with identifier equals to {@code taskId},
	 * succeeded. 
	 */
	public static boolean updateTask(int taskId, float complex, 
			float memoryNeed, float dataToDown, float dataToUp, float timeReq) {
		Task task = TaskUtil.createTask(complex, memoryNeed, dataToDown, 
				dataToUp, timeReq);	
		if (task == null)
			return false;
		task.setTaskId(taskId);
		boolean succeeded = true;
		TaskDAO dao = new TaskDAO();
		try {
			dao.deleteDependentEntities(task.getTaskId(), dao);
			dao.updateEntity(task);
		} catch (SQLException e) {
			succeeded = false;
			dao.rollbackTransaction();
			e.printStackTrace();
		}
		dao.closeSession();
		return succeeded;
	}
}
