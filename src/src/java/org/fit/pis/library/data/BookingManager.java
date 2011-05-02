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
		
	public void clear() {
		em.clear();
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
	 * Find booking for user
	 * @param user
	 * @return 
	 */
    public List<Booking> find(User user) {
    	Query query = em.createNamedQuery("Booking.findByUser");
		
		query.setParameter("user", user);
		return (List<Booking>) query.getResultList();
    }
	
	/**
	 * Find booking for book
	 * @param user
	 * @return 
	 */
    public List<Booking> find(Book book) {
    	Query query = em.createNamedQuery("Booking.findByBook");
		
		query.setParameter("book", book);
		return (List<Booking>) query.getResultList();
    }
}
