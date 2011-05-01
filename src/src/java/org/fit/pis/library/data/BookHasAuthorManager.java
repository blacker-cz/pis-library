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
 * @author Vojtěch Sysel <xsysel03@setud.fit.vutbr.cz>
 */
@Stateless
public class BookHasAuthorManager {
    @PersistenceContext
    private EntityManager em;

    public void save(BookHasAuthor b)
    {
    	em.merge(b);
      
    }
	
    public void remove(BookHasAuthor b)
    {
    	em.remove(em.merge(b));
    }
    
	public void flush()
	{
		em.flush();
	}
    
    @SuppressWarnings("unchecked")
    public List<BookHasAuthor> findAll()
    {
    	return em.createQuery("SELECT b FROM BookHasAuthor b ORDER BY b.name ASC, b.year ASC").getResultList();
    }
	
	/**
	 * Find book by id
	 * @param id
	 * @return BookHasAuthor or null
	 */
	public BookHasAuthor findByIdbook(Integer id) {
		em.flush();
		
		try {
			Query query = em.createNamedQuery("BookHasAuthor.findByIdbook");
			query.setParameter("idbook", id);
			return (BookHasAuthor) query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
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
	public List<BookHasAuthor> find(String name, String author, Date yearFrom, Date yearTo, Genre genre, String isbn_issn) {
		// genre SQL
		String genreSQL = "";
		if (genre != null) {
			genreSQL = " AND b.genre.idgenre = :idgenreFilter ";
		}
		
		Query query = em.createQuery(
				"SELECT DISTINCT b FROM BookHasAuthor b "
//				+ "JOIN b.Author a "
				+ "WHERE "
				+	"b.name LIKE :name AND "
				+	"b.year BETWEEN :yearFrom AND :yearTo AND "
				+	"b.code LIKE :isbn_issn "
//				+	"AND a.name LIKE :author "
				+ genreSQL 
				+ "ORDER BY b.name ASC, b.year ASC"
				);
		//Query query = em.createQuery("SELECT b FROM BookHasAuthor b WHERE b.name LIKE :name AND b.authors.name LIKE :authorname" + genreSQL);
		query.setParameter("name", "%" + name + "%");
//		query.setParameter("author", "%" + author + "%");
	
		query.setParameter("isbn_issn", "%" + isbn_issn + "%");
		// genre
		if (genre != null) {
			query.setParameter("idgenreFilter", genre.getIdgenre());
		}
		query.setParameter("yearFrom", yearFrom);
		query.setParameter("yearTo", yearTo);
		return (List<BookHasAuthor>) query.getResultList();
	}
}