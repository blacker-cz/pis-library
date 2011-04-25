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
	
    @SuppressWarnings("unchecked")
    public List<Booking> findAll()
    {
    	return em.createNamedQuery("Booking.findAll").getResultList();
    }
}
