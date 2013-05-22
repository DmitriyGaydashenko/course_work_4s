package edu.cw.cbr.model;

import java.util.List;

import edu.cw.cbr.model.dao.GenericDAO;
import edu.cw.cbr.model.domain.Precedent;
import edu.cw.cbr.model.domain.Task;

/**
 * The class {@code PrecedentUtil} contains methods for performing basic
 * operations with {@code Precedent}.
 * @author Dmitriy Gaydashenko
 *
 */
public class PrecedentUtil {//extends GenericTableAble{
	
	/**
	 * Number of precedent's attributes available for users.
	 */
	private final static int PRECEDENT_ATTR_NUM = 6;
	
	/**
	 * Returns data for first {@code max} precedents starting from precedent with
	 * number {@code from}. 
	 * @param from - starting point.
	 * @param max - max amount of elements returned.
	 * @return {@code max} precedents, starting from {@code from}.
	 */
	public static Object[][] getPrecedents(int from, int max) {
		GenericDAO dao = new GenericDAO();
		List<Precedent> lprecedents = dao.getEntities(Precedent.class, from,
				max, "precedentId", true);
		Object[][] precedents = new Object[lprecedents.size()]
				[PRECEDENT_ATTR_NUM];
		for (int i = 0; i < lprecedents.size(); i++) {
			precedents[i][0] = lprecedents.get(i).getPrecedentId();
			Task task = lprecedents.get(i).getTask();
			precedents[i][1] = task.getTaskId();
			precedents[i][2] = lprecedents.get(i).getDevicestate().getState().getStateId();
			precedents[i][3] = lprecedents.get(i).getDevicestate().getDevice().getDeviceId();
			//precedents[i][4] = lprecedents.get(i).getTimeToSolve() <=
					//task.getMaxRunningTime(); set if application satisfies 
											//running time requirements.
			precedents[i][5] = lprecedents.get(i).isSolvedCorrectly();
		}
		dao.closeSession();
		return precedents;
	}
	
	/**
	 * Returns total number of precedents.
	 * @return - total number of precedents;
	 */
	public static int getTotalNum() {
		return 0;
		//return getTotalNum(Precedent.class);
	}
}
