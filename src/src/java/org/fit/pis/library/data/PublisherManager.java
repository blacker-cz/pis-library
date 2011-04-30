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
        
	public void flush()
	{
		em.flush();
	}

    @SuppressWarnings("unchecked")
    public List<Publisher> findAll()
    {
		//return em.createNamedQuery("Publisher.findAll").getResultList();
                return em.createQuery("SELECT p FROM Publisher p ORDER BY p.name ASC").getResultList();
    }
	
	/**
	 * Find publisher by id
	 * @param id
	 * @return Publisher or null
	 */
	public Publisher findByIdpublisher(Integer id) {
		em.flush();
		
		try {
			Query query = em.createNamedQuery("Publisher.findByIdpublisher");
			query.setParameter("idpublisher", id);
			return (Publisher) query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}
	
        public List<Publisher> find(String name) {
		// genre SQL
		String genreSQL = "";	
		Query query = em.createQuery(
				"SELECT DISTINCT p FROM Publisher p "
//				+ "JOIN a.publisher a "
				+ "WHERE "
				+	"p.name LIKE :name "
//				+	"AND a.name LIKE :publisher "
				+ genreSQL 
				+ "ORDER BY p.name ASC"
				);
		//Query query = em.createQuery("SELECT b FROM Book b WHERE b.name LIKE :name AND b.publishers.name LIKE :publishername" + genreSQL);
		query.setParameter("name", "%" + name + "%");
//		query.setParameter("publisher", "%" + publisher + "%");
		// TODO: publisher
		//query.setParameter("publishername", "%" + publisher + "%");
	
                
            
		return (List<Publisher>) query.getResultList();
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
