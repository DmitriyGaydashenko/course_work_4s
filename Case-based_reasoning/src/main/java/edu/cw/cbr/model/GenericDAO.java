package edu.cw.cbr.model;

import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;


/**
 * The class {@code GenericDAO} contains methods for performing
 * basic queries to database.
 * @author Dmitriy Gaydashenko
 *
 */
@Repository @Service
final class GenericDAO {
	
	 private Session session;
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

	@SuppressWarnings({ "unchecked", "rawtypes" })
	/**
	 * Return the persistent instance of the given entity class with the given
	 *  identifier,
	 * <b>assuming that the instance exists</b>.
	 * @param entityClass a persistent class
	 * @param id a valid identifier of an existing persistent instance of 
	 * the class
	 * @return the persistent instance
	 * @throws SQLException
	 * @see org.hibernate.Session.load(Class theClass, Serializable id)
	 */
	public <Entity> Entity getEntityById(Class entityClass,
			int id) throws SQLException {
	    return (Entity) session.load(entityClass, id);
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
	 * Return result of processing of Query for the given HQL query string.
	 * @param query HQL query string
	 * @return result of processing of Query
	 */
	@SuppressWarnings("unchecked")
	public <Entity> List<Entity> runSelectQuery(String query) {
		return (List<Entity>) session.createQuery(query).list();
	}
	
	/**
	 * Remove a persistent instance from the database. 
	 * The argument may be an instance associated with the receiving Session or
	 * a transient instance 
	 * with an identifier associated with existing persistent state. 
	 * @param entity deleting instance
	 * @throws SQLException
	 */
	public <Entity> void deleteEntity(Entity entity) throws SQLException {
        if (entity == null)
        	throw new IllegalArgumentException();
        session.delete(entity);
	}
	
	/**
	 * Return instance of {@code Criteria}, which represents a query against 
	 * {@code clazz}.
	 * @param clazz - particular persistent class for which instance of
	 * {@code Criteria} will be created
	 * @return instance of {@code Criteria} for {@code clazz}.
	 */
	public Criteria  getCriteria(Class clazz) {
		return session.createCriteria(clazz);
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
	 * Closes session.
	 * @throws IllegalStateException if session is not open.
	 */
	public void closeSession() throws IllegalStateException {
		if(session == null || !session.isOpen())
			throw new IllegalStateException();
		session.getTransaction().commit();
		session.close();
		session = null;
	}
}
