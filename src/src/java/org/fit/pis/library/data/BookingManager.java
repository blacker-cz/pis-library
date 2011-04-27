/**
 * Stateless bean pro práci s rezervací titulů
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
public class BookingManager {
    @PersistenceContext
    private EntityManager em;
	
	/**
	 * Save new booking
	 * @param b booking instance
	 */
	public void Save(Booking b) {
		em.merge(b);
	}
	
	/**
	 * Remove Booking
	 * @param b booking instance
	 */
	public void Remove(Booking b) {
		em.remove(em.merge(b));
	}
    
	public void flush()
	{
		em.flush();
	}
	
	/**
	 * Find all bookings
	 * @return 
	 */
    @SuppressWarnings("unchecked")
    public List<Booking> findAll() {
    	return em.createNamedQuery("Booking.findAll").getResultList();
    }
	
	/**
	 * Find booking for user defined by id
	 * @param user
	 * @return 
	 */
    public List<Booking> find(User user) {
    	Query query = em.createNamedQuery("Booking.findByUser");
		
		query.setParameter("user", user);
		return (List<Booking>) query.getResultList();
    }
}
