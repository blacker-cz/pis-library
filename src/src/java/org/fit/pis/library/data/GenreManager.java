/**
 * 
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
    	
	public void clear() {
		em.clear();
	}

    @SuppressWarnings("unchecked")
    public List<Genre> findAll()
    {
		return em.createNamedQuery("Genre.findAll").getResultList();
    }
	    
	public void flush()
	{
		em.flush();
	}

	/**
	 * Find Genre by id
	 * @param id
	 * @return Genre or null
	 */
	public Genre findByIdgenre(Integer id) {
		em.flush();
		
		try {
			Query query = em.createNamedQuery("Genre.findByIdgenre");
			query.setParameter("idgenre", id);
			return (Genre) query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}

	public List<Genre> find(String name) {
		Query query = em.createQuery(
				"SELECT g FROM Genre g "
				+ "WHERE "
				+ "g.name LIKE :name "
				+ "ORDER BY g.name ASC");
		query.setParameter("name", "%" + name + "%");

		return (List<Genre>) query.getResultList();
	}
	
	/**
	 * Find genre by name
	 * @param name	genre name
	 * @return genre
	 */
	public Genre findByName(String name) {
		try {
			// Query queryGenreName = em.createNamedQuery("Genre.findByName");
			Query queryGenreName = em.createQuery("SELECT g FROM Genre g WHERE g.name LIKE :name");
			queryGenreName.setParameter("name", name);
			return (Genre) queryGenreName.getSingleResult();
		} catch (NoResultException e) {
			return null;
		} catch (NonUniqueResultException e) {
			return null;
		} catch (IllegalStateException e) {
			return null;
		}
	}
}
