/**
 * 
 */
package org.fit.pis.library.data;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Genre manager
 * 
 * @author Vojtěch Sysel <xsysel03@setud.fit.vutbr.cz>
 */
@Stateless
public class GenreManager {
    @PersistenceContext
    private EntityManager em;

    public void save(Genre g)
    {
    	em.merge(g);
    }
	
    public void remove(Genre g)
    {
    	em.remove(em.merge(g));
    }
    
    @SuppressWarnings("unchecked")
    public List<Genre> findAll()
    {
		return em.createNamedQuery("Genre.findAll").getResultList();
    }
}
