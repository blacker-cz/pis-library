/**
 * Stateless bean pro práci s daty knihy
 */

package org.fit.pis.library.data;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author vojta
 */
@Stateless
public class BookManager {
    @PersistenceContext
    private EntityManager em;

    public void save(Book b)
    {
    	em.merge(b);
    }
	
    public void remove(Book b)
    {
    	em.remove(em.merge(b));
    }
    
    @SuppressWarnings("unchecked")
    public List<Book> findAll()
    {
    	return em.createQuery("SELECT b FROM Book b").getResultList();
    }
}
