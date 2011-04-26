/*
 */

package org.fit.pis.library.data;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Lukáš Černý <cerny.l@gmail.com>
 */
@Stateless
public class ExemplarManager {
    @PersistenceContext
    private EntityManager em;

    public void save(Exemplar e)
    {
    	em.merge(e);
    }
	
    public void remove(Exemplar e)
    {
    	em.remove(em.merge(e));
    }
    
    @SuppressWarnings("unchecked")
    public List<Exemplar> findAll()
    {
		return em.createNamedQuery("Exemplar.findAll").getResultList();
    }
}
