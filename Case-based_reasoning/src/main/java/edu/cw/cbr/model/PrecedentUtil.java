package edu.cw.cbr.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.Order;
import edu.cw.cbr.model.dao.ConnectedDAO;
import edu.cw.cbr.model.domain.DeviceState;
import edu.cw.cbr.model.domain.Precedent;
import edu.cw.cbr.model.domain.Task;


public class PrecedentUtil extends ConnectedUtil<Precedent>{
	
	/** The need special treat. */
	private static List<String> needSpecialTreat;
	static {	
		needSpecialTreat = new ArrayList<String>();
		needSpecialTreat.add("taskId");
		needSpecialTreat.add("deviceId");
		needSpecialTreat.add("stateId");
		needSpecialTreat.add("isRunnable");
	}
	
	/**
	 * Instantiates a new deviceState utilities.
	 */
	public PrecedentUtil() {
		super(Precedent.class);
	}
	
	
	public boolean addNew(int deviceStateId, int stateId) {
		DeviceState deviceState = null;
		Task task = null;
		try {
			task = new TaskUtil().getEntity(stateId);
			if (task == null)
				throw new IllegalArgumentException();
			 deviceState = new DeviceStateUtil().getEntity(deviceStateId);
			 if (deviceState == null)
					throw new IllegalArgumentException();
		} catch (SQLException | IllegalArgumentException e1) {
			e1.printStackTrace();
			return false;
		}
		Precedent precedent = new Precedent();
		precedent.setTask(task);
		precedent.setDeviceState(deviceState);
		PrecedentDAO dao = getNewDAO();
		try {
			dao.addEntity(precedent);
		} catch (SQLException e) {
			dao.closeSession();
			e.printStackTrace();
			return false;
		}
		dao.closeSession();
		return true;
	}
	
	public boolean update(int entId, boolean isSolvedCorrectly) {
		PrecedentDAO dao = getNewDAO();
		try {
			Precedent precedent = dao.getEntityById(entId);
			precedent.setIsSolvedCorrectly(isSolvedCorrectly);
			dao.updateEntity(precedent);
		} catch (SQLException | IllegalArgumentException e1) {
			dao.closeSession();
			e1.printStackTrace();
			return false;
		}
		dao.closeSession();
		return true;
	}

	
	public static List<String> getNeedSpecialTreat() {
		return needSpecialTreat;
	}
	
	public Object[] getTaskData(int precedentId) {
		PrecedentDAO pDao = getNewDAO();
		Object[] taskData = new Object[0];
		try {
			taskData = pDao.getEntityById(precedentId).getTask().toArray();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return taskData;
	}
	
	public Object[] getDeviceStateData(int precedentId) {
		PrecedentDAO pDao = getNewDAO();
		Object[] deviceStateData = new Object[0];
		try {
			deviceStateData = pDao.getEntityById(precedentId).getDeviceState()
					.toArray();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return deviceStateData;
	}

	@Override
	public List<String> getAllCols() {
		List<String> cols = new ArrayList<String>();
		cols.add("precedentId");
		cols.add("taskId");
		cols.add("deviceId");
		cols.add("stateId");
		cols.add("isRunnable");
		cols.add("isSolvedCorrectly");
		return cols;
	}

	@Override
	protected PrecedentDAO getNewDAO() {
		return new PrecedentDAO();
	}
}

class PrecedentDAO extends ConnectedDAO<Precedent>{
	
	public PrecedentDAO() {
		super(Precedent.class);
	}

	/**
	 * Represents data structure which store names of stored functions.
	 * @author Dmitriy Gaydashenko.
	 *
	 */
	private enum StoredFunction implements IStoredFunction{
		/**
		 * Name of stored function, which counts number of dependent instances
		 * of {@code Precedent} from instance of {@code DeviceState}
		 * with identifier equals to passed parameter. 
		 */
		PREC_DEP_PREC_NUM_F ("SELECT ds_dep_prec_num(%d)",
				"Precedents");
		
		private String func;
		private String IRName;
		
		StoredFunction(String func) {
			this.func = func;
		}
		
		StoredFunction(String func, String IRName) {
			this.func = func;
			this.IRName = IRName;
		}
		
		/**
		 * Return array of count functions names.
		 * @return array of count functions names.
		 */
		public static IStoredFunction[] getCountFunctions() {
			return new StoredFunction[]{PREC_DEP_PREC_NUM_F};
		}

		@Override
		public String getFunction() {
			return this.func;
		}

		@Override
		public String getIRName() {
			return this.IRName;
		}
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Precedent> getEntities(int from, int max,
			String orderBy, boolean asc) {
		Criteria crit = session.createCriteria(CLASS);
		if (PrecedentUtil.getNeedSpecialTreat().contains(orderBy)) {
			switch (orderBy) {
			case "taskId" : {
				crit.createAlias("task", "pTask")
				.setFetchMode("pTask", FetchMode.JOIN);
				orderBy = "pTask." + orderBy;
				break;
			}
			case "deviceId" : {
				crit.createAlias("deviceState", "pDevState")
				.setFetchMode("pDevState", FetchMode.JOIN)
				.createAlias("pDevState.deviceData", "pDev")
				.setFetchMode("pDev", FetchMode.JOIN);
				orderBy = "pDev." + orderBy;
				break;
			}
			case "stateId" : {
				crit.createAlias("deviceState", "pDevState")
				.setFetchMode("pDevState", FetchMode.JOIN)
				.createAlias("pDevState.state", "pState")
				.setFetchMode("pState", FetchMode.JOIN);
				orderBy = "pState." + orderBy;
				break;
			}
			case "isRunnable" : 
				orderBy = "timeToSolve";
			}
		}
		Order order = asc ? Order.asc(orderBy) : Order.desc(orderBy);
		return (List<Precedent>)crit.addOrder(order)
				.setFirstResult(from).setMaxResults(max).list();
	}

	@Override
	public Map<String, Integer> getDependentEntitiesNum(int entId) {
		return getDependentEntitiesNum(entId,
				StoredFunction.getCountFunctions());
	}
}
