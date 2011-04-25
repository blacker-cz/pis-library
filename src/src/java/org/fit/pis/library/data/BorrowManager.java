/**
 * Stateless bean pro práci s výpůjčkami
 */

package org.fit.pis.library.data;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Vojtěch Sysel <xsysel03@setud.fit.vutbr.cz>
 */
@Stateless
public class BorrowManager {
    @PersistenceContext
    private EntityManager em;
	
	/**
	 * Save new borrow
	 * @param b 
	 */
	public void Save(Borrow b) {
		em.merge(b);
	}
	
	/**
	 * Remove Borrow
	 * @param b borrow instance
	 */
	public void Remove(Borrow b) {
		em.remove(em.merge(b));
	}
	
	/**
	 * Find all borrows
	 * @return 
	 */
    @SuppressWarnings("unchecked")
    public List<Borrow> findAll() {
    	return em.createNamedQuery("Borrow.findAll").getResultList();
    }
	
	/**
	 * Find borrows for user defined by id
	 * @param user
	 * @return 
	 */
    public List<Borrow> find(User user) {
    	Query query = em.createNamedQuery("Borrow.findByUser");
		
		query.setParameter("user", user);
		return (List<Borrow>) query.getResultList();
    }
}
