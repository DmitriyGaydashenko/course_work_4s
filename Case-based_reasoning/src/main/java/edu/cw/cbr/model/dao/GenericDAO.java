package edu.cw.cbr.model.dao;

import java.sql.SQLException;


/**
 * The class {@code GenericDAO} contains methods for performing
 * basic queries to database.
 * @author Dmitriy Gaydashenko
 *
 */
public class GenericDAO<T> extends ViewDAO<T>{
	
	 public GenericDAO(Class<T> cl) {
		 super(cl);
	 }
	
    /**
     * Persist the given transient instance, first assigning a generated
     *  identifier.
     * @param entity - saving instance 
     * @throws SQLException
     * @see org.hibernate.Session.save(Object arg0)
     */
	public void addEntity(T entity) throws SQLException {
		session.save(entity);
	}
	
	/**
	 * Update the persistent instance with the identifier of the given detached
	 *  instance.
	 * @param entity a detached instance containing updated state
     * @see org.hibernate.Session.update(Object arg0)
	 */
	public void updateEntity(T entity) {
		session.update(entity);
	}
	
	/**
	 * Remove a persistent instance with id - {@code id} from the database. 
	 * @param id deleting instance's identifier.
	 * @throws SQLException
	 */
	public void deleteEntity(int id) throws SQLException {
		T entity = this.getEntityById(id);
		session.delete(entity);
	}	
}
