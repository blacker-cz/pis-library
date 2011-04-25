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
	public List<Book> find(String name, String author, Date yearFrom, Date yearTo, Genre genre, String isbn_issn) {
		// genre SQL
		String genreSQL = "";
		if (genre != null) {
			genreSQL = " AND b.genre.idgenre = :idgenreFilter";
		}
		
		System.out.println("from: " + yearFrom + ", to:" + yearTo);
		
		Query query = em.createQuery("SELECT b FROM Book b WHERE b.name LIKE :name AND b.year BETWEEN :yearFrom AND :yearTo AND b.code LIKE :isbn_issn" + genreSQL);
		//  b.year <= :yearTo AND
		//Query query = em.createQuery("SELECT b FROM Book b WHERE b.name LIKE :name AND b.authors.name LIKE :authorname" + genreSQL);
		//  AND u.year BETWEEN :yearFrom AND :yearTo LIKE :forename AND u.surname LIKE :surname AND u.email LIKE :email AND u.level LIKE :level
		query.setParameter("name", "%" + name + "%");
		// TODO: author
		//query.setParameter("authorname", "%" + author + "%");
	
		query.setParameter("isbn_issn", "%" + isbn_issn + "%");
		// genre
		if (genre != null) {
			query.setParameter("idgenreFilter", genre.getIdgenre());
		}
		query.setParameter("yearFrom", yearFrom);
		query.setParameter("yearTo", yearTo);
		return (List<Book>) query.getResultList();
	}
}
