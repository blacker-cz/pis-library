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

	public void save(Author a) {
		em.merge(a);
	}

	public void remove(Author a) {
		em.remove(em.merge(a));
	}

	public void flush() {
		em.flush();
	}

	@SuppressWarnings("unchecked")
	public List<Author> findAll() {
		//return em.createNamedQuery("Author.findAll").getResultList();
		return em.createQuery("SELECT a FROM Author a ORDER BY a.name ASC").getResultList();
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

	public List<Author> find(String name) {
		Query query = em.createQuery(
				"SELECT DISTINCT a FROM Author a "
				+ "WHERE "
				+ "a.name LIKE :name "
				+ "ORDER BY a.name ASC");
		query.setParameter("name", "%" + name + "%");

		return (List<Author>) query.getResultList();
	}

	/**
	 * Find Author by name
	 * @param name	Author name
	 * @return Author
	 */
	public Author findByName(String name) {
		try {
			// Query queryAuthorName = em.createNamedQuery("Author.findByName");
			Query queryAuthorName = em.createQuery("SELECT DISTINCT a FROM Author a WHERE a.name LIKE :name");
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
