/*
 */
package org.fit.pis.library.data;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Lukáš Černý <cerny.l@gmail.com>
 */
@Stateless
public class ExemplarManager {

	@PersistenceContext
	private EntityManager em;

	public void save(Exemplar e) {
		em.merge(e);
	}

	public void remove(Exemplar e) {
		em.remove(em.merge(e));
	}

	public void flush() {
		em.flush();
	}
	
	public void clear() {
		em.clear();
	}

	/**
	 * Refreah Exemplar
	 */
	public void refresh(Exemplar e) {
		em.flush();
		em.refresh(e);
	}
	
	public Exemplar findByIdexemplar(Integer id) {
		em.flush();

		try {
			Query query = em.createNamedQuery("Exemplar.findByIdexemplar");
			query.setParameter("idexemplar", id);
			return (Exemplar) query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Find book exemplars
	 * @param book
	 * @return 
	 */
	public List<Exemplar> findByBook(Book book) {
//		em.flush();
		
//		try {
			Query query = em.createNamedQuery("Exemplar.findByBook");
			query.setParameter("book", book);
			
			// refresh info
			List<Exemplar> exemplars = query.getResultList();
			for (Exemplar e : exemplars) {
				refresh(e);
			}
			
			return exemplars;
//		} catch (Exception e) {
//			return null;
//		}
	}

	/**
	 * Find book exemplars
	 * @param book
	 * @return 
	 */
	public List<Exemplar> findByIdbook(Integer id) {
		em.flush();

		try {
			Query query = em.createQuery("SELECT e FROM Exemplar e WHERE e.book.idbook = :idbook");
			query.setParameter("idbook", id);
			return (List<Exemplar>) query.getResultList();
		} catch (Exception e) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<Exemplar> findAll() {
		return em.createNamedQuery("Exemplar.findAll").getResultList();
	}
}
