/**
 * Stateless bean pro práci s daty knihy
 */

package org.fit.pis.library.data;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

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
    	return em.createQuery("SELECT b FROM Book b ORDER BY b.name ASC, b.year ASC").getResultList();
    }
	
	/**
	 * Filter user list by some parameters
	 * @param permitNumber
	 * @param forename
	 * @param surname
	 * @param email
	 * @param level
	 * @return 
	 */
	public List<Book> find(String name, Date yearFrom, Date yearTo, Genre genre) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		
		// genre SQL
		String genreSQL = "";
		if (genre != null) {
			genreSQL = " AND b.genre.idgenre = :idgenreFilter";
		}
		
		Query query = em.createQuery("SELECT b FROM Book b WHERE b.name LIKE :name" + genreSQL);
		//  AND u.year BETWEEN :yearFrom AND :yearTo LIKE :forename AND u.surname LIKE :surname AND u.email LIKE :email AND u.level LIKE :level
		query.setParameter("name", "%" + name + "%");
		
		// genre
		if (genre != null) {
			query.setParameter("idgenreFilter", genre.getIdgenre());
		}
//		query.setParameter("yearFrom", "1850-01-01");
//		query.setParameter("yearTo", "2100-01-01");
//		query.setParameter("email", "%" + email + "%");
//		query.setParameter("level", "%" + level + "%");
		return (List<Book>) query.getResultList();
	}
}
