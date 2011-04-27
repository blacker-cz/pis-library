/*
 */
package org.fit.pis.library.data;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Lukáš Černý <cerny.l@gmail.com>
 */
@Stateless
public class UserManager {

	@PersistenceContext
	private EntityManager em;

	/**
	 * Find user by id
	 * @param id user id
	 * @return user instance
	 */
	public User find(Integer id) {
		return em.find(User.class, id);
	}

	/**
	 * Save new user
	 * @param u user instance
	 */
	public void Save(User u) {
		em.merge(u);
	}

	/**
	 * Remove user
	 * @param u user instance
	 */
	public void Remove(User u) {
		em.remove(em.merge(u));
	}

	/**
	 * Refreah user
	 */
	public void Refresh(User u) {
		em.flush();
		em.refresh(u);
	}
    
	public void flush()
	{
		em.flush();
	}
	
	/**
	 * Get list of all users
	 * @return List of all users
	 */
	public List<User> findAll() {
		return em.createNamedQuery("User.findAll").getResultList();
	}

	/**
	 * Find user by his/her permit number
	 * @param permitNumber	permit number
	 * @return user instance or null when not found
	 */
	public User findByPermitNumber(String permitNumber) {
		em.flush();
		
		try {
			Query queryUserByPermitNumber = em.createNamedQuery("User.findByPermitNumber");
			queryUserByPermitNumber.setParameter("permitNumber", permitNumber);
			return (User) queryUserByPermitNumber.getSingleResult();
		} catch (NoResultException e) {
			return null;
		} catch (NonUniqueResultException e) {
			return null;
		} catch (IllegalStateException e) {
			return null;
		}
	}
	
	public User findByIduser(int idUser) {
		// synchronize with db
		em.flush();
		
		try {
			Query queryUserByIduser = em.createNamedQuery("User.findByIduser");
			queryUserByIduser.setParameter("iduser", idUser);
			return (User) queryUserByIduser.getSingleResult();
		} catch (NoResultException e) {
			return null;
		} catch (NonUniqueResultException e) {
			return null;
		} catch (IllegalStateException e) {
			return null;
		}
	}
	
	/**
	 * Filter user list by some parameters
	 * @param permitNumber
	 * @param forename
	 * @param surname
	 * @param email
	 * @param level
	 * @return 
	 */
	public List<User> find(String permitNumber, String forename, String surname, String email, String level) {
		Query query = em.createQuery("SELECT u FROM User u WHERE u.permitNumber LIKE :permitNumber AND u.forename LIKE :forename AND u.surname LIKE :surname AND u.email LIKE :email AND u.level LIKE :level");
		query.setParameter("permitNumber", "%" + permitNumber + "%");
		query.setParameter("forename", "%" + forename + "%");
		query.setParameter("surname", "%" + surname + "%");
		query.setParameter("email", "%" + email + "%");
		query.setParameter("level", "%" + level + "%");
		return (List<User>) query.getResultList();
	}
}
