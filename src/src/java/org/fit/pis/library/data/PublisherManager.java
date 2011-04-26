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
public class PublisherManager {
    @PersistenceContext
    private EntityManager em;

    public void save(Publisher p)
    {
    	em.merge(p);
    }
	
    public void remove(Publisher p)
    {
    	em.remove(em.merge(p));
    }
    
    @SuppressWarnings("unchecked")
    public List<Publisher> findAll()
    {
		return em.createNamedQuery("Publisher.findAll").getResultList();
    }
	
	/**
	 * Find Publisher by name
	 * @param name	Publisher name
	 * @return Publisher
	 */
	public Publisher findByName(String name) {
		try {
			Query queryPublisherName = em.createNamedQuery("Publisher.findByName");
			queryPublisherName.setParameter("name", name);
			return (Publisher) queryPublisherName.getSingleResult();
		} catch (NoResultException e) {
			return null;
		} catch (NonUniqueResultException e) {
			return null;
		} catch (IllegalStateException e) {
			return null;
		}
	}
}
