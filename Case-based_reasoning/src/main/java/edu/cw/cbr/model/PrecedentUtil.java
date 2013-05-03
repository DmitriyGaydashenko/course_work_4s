package edu.cw.cbr.model;

import java.util.List;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;

import edu.cw.cbr.domain.Precedent;

/**
 * The class {@code PrecedentUtil} contains methods for performing basic
 * operations with {@code Precedent}.
 * @author Dmitriy Gaydashenko
 *
 */
public class PrecedentUtil {

	private final static int PRECEDENT_ATTR_NUM = 6;
	@SuppressWarnings("unchecked")
	public static Object[][] getPrecedents(int from, int max) {
		GenericDAO dao = new GenericDAO();
		List<Precedent> lprecedents = dao.getCriteria(Precedent.class).addOrder(Order.asc("precedentId")).setFirstResult(from)
				.setMaxResults(max).list();
		Object[][] precedents = new Object[lprecedents.size()]
				[PRECEDENT_ATTR_NUM];
		for (int i = 0; i < lprecedents.size(); i++) {
			precedents[i][0] = lprecedents.get(i).getPrecedentId();
			precedents[i][1] = lprecedents.get(i).getTask().getTaskId();
			precedents[i][2] = lprecedents.get(i).getDevicestate().getState().getStateId();
			precedents[i][3] = lprecedents.get(i).getDevicestate().getDevice().getDeviceId();
			precedents[i][4] = lprecedents.get(i).getTimeToSolve();
			precedents[i][5] = lprecedents.get(i).isSolvedCorrectly();
		}
		dao.closeSession();
		return precedents;
	}
	
	public static int getPrecedentsNum() {
		GenericDAO dao = new GenericDAO();
		int num = ((Long)dao.getCriteria(Precedent.class).
				setProjection(Projections.rowCount()).list().get(0)).intValue();
		dao.closeSession();
		return num;
	}
}
