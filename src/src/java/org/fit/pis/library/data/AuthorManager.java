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
public class AuthorManager {
    @PersistenceContext
    private EntityManager em;

    public void save(Author a)
    {
    	em.merge(a);
    }
	
    public void remove(Author a)
    {
    	em.remove(em.merge(a));
    }
    
	public void flush()
	{
		em.flush();
	}
	
    @SuppressWarnings("unchecked")
    public List<Author> findAll()
    {
		return em.createNamedQuery("Author.findAll").getResultList();
    }
	
	/**
	 * Find author by id
	 * @param id	Author id
	 * @return Author or null
	 */
	public Author findByIdauthor(Integer id) {
		em.flush();
		
		try {
			Query query = em.createNamedQuery("Author.findByIdauthor");
			query.setParameter("idauthor", id);
			return (Author) query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * Find Author by name
	 * @param name	Author name
	 * @return Author
	 */
	public Author findByName(String name) {
		try {
			Query queryAuthorName = em.createNamedQuery("Author.findByName");
			queryAuthorName.setParameter("name", name);
			return (Author) queryAuthorName.getSingleResult();
		} catch (NoResultException e) {
			return null;
		} catch (NonUniqueResultException e) {
			return null;
		} catch (IllegalStateException e) {
			return null;
		}
	}
}
