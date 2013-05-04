package edu.cw.cbr.model;

import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;


/**
 * The class {@code GenericDAO} contains methods for performing
 * basic queries to database.
 * @author Dmitriy Gaydashenko
 *
 */
@Repository @Service class GenericDAO {
	
	 protected Session session;
	 private static final Logger logger = 
			 LoggerFactory.getLogger(GenericDAO.class);
	 
	 /** 
	  * Class {@code DBSessionUtil} provides methods to perform basic
	  * operations with  {@code org.hibernate.SessionFactory}. 
	  * @author Dmitriy Gaydashenko 
	  *
	  */
	 private static class DBSessionUtil {
	 	
	 	/**
	 	 * Instance of {@code DBSessionUtil}. 
	 	 */
	 	private static DBSessionUtil dBSessionUtil;
	 	/**
	 	 * Instance of {@code SessionFactory}.
	 	 * @see org.hibernate.SessionFactory
	 	 */
	 	private SessionFactory sessionFactory;

	 	/** 
	 	 * Constructs new instance of DBSessionUtil, runs configuration of Hibernate
	 	 * and initializes {@code sessionFactory}.
	 	 */
	 	private DBSessionUtil() {
	 		Configuration configuration = new Configuration();
	 		 configuration.configure();
	 		 ServiceRegistry serviceRegistry = 
	 				 new ServiceRegistryBuilder().applySettings(
	 						 configuration.getProperties()).buildServiceRegistry(); 
	 		 sessionFactory = configuration.buildSessionFactory(serviceRegistry);
	 	}

	 	/**
	 	 * If not null, then returns instance of dBSessionUtil, else creates new
	 	 * instance. 
	 	 * @return instance of @code { DBSessionUtil}.
	 	 */
	 	public static DBSessionUtil getInstance() {
	 		if (dBSessionUtil == null)
	 			dBSessionUtil = new DBSessionUtil();
	 		return dBSessionUtil;
	 	}

	 	public SessionFactory getSessionFactory() {
	 		return sessionFactory;
	 	}
	 }
	 public GenericDAO() {
		 session = DBSessionUtil.getInstance().getSessionFactory()
				 .openSession();
		 session.beginTransaction();
	 }
	
    /**
     * Persist the given transient instance, first assigning a generated
     *  identifier.
     * @param entity - saving instance 
     * @throws SQLException
     * @see org.hibernate.Session.save(Object arg0)
     */
	public <Entity> void addEntity(Entity entity) throws SQLException {
		session.save(entity);
	}
	
	/**
	 * Update the persistent instance with the identifier of the given detached
	 *  instance.
	 * @param entity a detached instance containing updated state
	 * @throws SQLException
     * @see org.hibernate.Session.update(Object arg0)
	 */
	public <Entity> void updateEntity(Entity entity) throws SQLException {
		session.update(entity);
	}

	/**
	 * Return the persistent instance of the given entity class with the given
	 *  identifier.
	 * @param entityClass a persistent class
	 * @param id a valid identifier.
	 * @return the persistent instance.
	 * @throws SQLException
	 * @see org.hibernate.Session.get(Class theClass, Serializable id)
	 */
	public <T> T getEntityById(Class<T> entityClass,
			int id) throws SQLException {
	    return entityClass.cast(session.get(entityClass, id));
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	/**
	 * Return all persistent instances of the given entity class.
	 * @param entityClass a persistent class
	 * @return list of persistent instances
	 * @throws SQLException
	 * @see org.hibernate.Session.createCriteria(Class persistentClass)
	 */
	public <Entity> List<Entity> getAllEntities(Class entityClass) 
			throws SQLException {
		return (List<Entity>) session.createCriteria(entityClass).list();
	}
	
	/**
	 * Remove a persistent instance with id - {@code id} from the database. 
	 * @param id deleting instance's identifier.
	 * @entityClass - class of deleting instance.
	 * @throws SQLException
	 */
	public <T> void deleteEntity(Class<T> entityClass, int id) throws SQLException {
		T entity = this.getEntityById(entityClass, id);
		session.delete(entity);
	}
	
	/**
	 * Roll back transaction if it was not committed and close session.  
	 */
	public void finalize() {
		if (session == null || !session.isOpen())
			return;
		if (session.getTransaction().isActive()) {
			logger.error("Transaction was not finished before " +
					"finalize => rollback");
			session.getTransaction().rollback();
		}
		logger.warn("Session was not closed");
		session.close();
	}
	
	/**
	 * Rolls back current transaction.
	 */
	public void rollbackTransaction() {
		session.getTransaction().rollback();
	}
	/**
	 * Closes session.
	 * @throws IllegalStateException if session is not open.
	 */
	public void closeSession() throws IllegalStateException {
		if(session == null || !session.isOpen())
			throw new IllegalStateException();
		if (session.getTransaction().isActive())
			session.getTransaction().commit();
		session.close();
		session = null;
	}
	
	/**
	 * Return result of passed function execution.
	 * @param function - function to run.
	 * @return result of passed function execution.
	 * @throws SQLException
	 */
	/*public Object runStoredFunction(String function) throws SQLException {
		return this.session.createSQLQuery("SELECT "+function).list();
	}*/
	
	/**
	 * Returns first {@code max} instances of {@code cl} starting from 
	 * {@code from} in order specified by {@code orderBy} and {@code asc}.
	 * @param cl - a persistent class.
	 * @param from - starting point.
	 * @param max - max number of entities to be returned.
	 * @param orderBy - specifies field by which list will be ordered.
	 * @param asc - specifies if list will be in ascending order.
	 * @return list of first {@code max} entities, starting from {@code from}.
	 */
	@SuppressWarnings("unchecked")
	public <Entity> List<Entity> getEntities(Class<Entity> cl, int from,
			int max, String orderBy, boolean asc) {
		Order order = asc ? Order.asc(orderBy) : Order.desc(orderBy);
		return (List<Entity>)session.createCriteria(cl).addOrder(order)
				.setFirstResult(from).setMaxResults(max).list();
	}
	
	/**
	 * Returns number of all entities of {@code cl}.
	 * @param cl - a persistent class.
	 * @return  number of all entities of {@code cl}.
	 */
	public <Entity> int count(Class<Entity> cl) {
		return ((Long)session.createCriteria(cl)
				.setProjection(Projections.rowCount()).list().get(0))
				.intValue();
	}
}
