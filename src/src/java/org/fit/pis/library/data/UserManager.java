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
	public User find(String id) {
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
}
