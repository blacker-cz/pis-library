/**
 * Stateless bean pro práci s výpůjčkami
 */

package org.fit.pis.library.data;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.fit.pis.library.back.AuthenticationBean;

/**
 *
 * @author Vojtěch Sysel <xsysel03@setud.fit.vutbr.cz>
 */
@Stateless
public class BorrowManager {
    @PersistenceContext
    private EntityManager em;
	
    @SuppressWarnings("unchecked")
    public List<Borrow> findAll()
    {
    	return em.createNamedQuery("Borrow.findAll").getResultList();
    }
	
	/**
	 * Find borrows for user defined by id
	 * @param idUser
	 * @return 
	 */
    public List<Borrow> find(User user)
    {
    	Query query =  em.createNamedQuery("Borrow.findByUser");
		
		query.setParameter("user", user);
		return (List<Borrow>) query.getResultList();
    }
}
