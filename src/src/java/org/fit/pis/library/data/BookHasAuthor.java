/*
 */

package org.fit.pis.library.data;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.fit.pis.library.back.converters.IntegerAdapter;

/**
 *
 * @author Lukáš Černý <cerny.l@gmail.com>
 */
@Entity
@Table(name = "book_has_author")
@NamedQueries({
	@NamedQuery(name = "BookHasAuthor.findAll", query = "SELECT a FROM BookHasAuthor a"),
	@NamedQuery(name = "BookHasAuthor.findByIdauthor", query = "SELECT a FROM BookHasAuthor a WHERE a.author_idauthor = :author_idauthor"),
	@NamedQuery(name = "BookHasAuthor.findByIdbook", query = "SELECT a FROM BookHasAuthor a WHERE a.book_idbook = :book_idbook")})
public class BookHasAuthor implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
        //    @GeneratedValue(strategy = GenerationType.IDENTITY)
        //    @Basic(optional = false)
        //    @NotNull     
        @Column(name = "book_idbook")
	@XmlID
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	private Integer book_idbook;
	
        @Basic(optional = false)
        @NotNull
        @Size(min = 1, max = 255)
        @Column(name = "author_idauthor")    
	private Integer author_idauthor;
        
        
	/*@JoinTable(name = "book_has_author", joinColumns = {
    	@JoinColumn(name = "author_idauthor", referencedColumnName = "idauthor")}, inverseJoinColumns = {
    	@JoinColumn(name = "book_idbook", referencedColumnName = "idbook")})
    @ManyToMany
	//@XmlIDREF
	//private Collection<Book> booksCollection;
   */
        
        
	public BookHasAuthor() {
	}

	public BookHasAuthor(Integer book_idbook,Integer author_idauthor) {
                this.book_idbook = book_idbook;
		this.author_idauthor = author_idauthor;
               
	}
        
        
        public void setIdbook(Integer book_idbook) {
            this.book_idbook = book_idbook;
        }
        
         public int getIdbook() {
            return this.book_idbook;
        }
        
	public void setIdauthor(Integer author_idauthor) {
		this.author_idauthor = author_idauthor;
	}
        
        public int getIdauthor() {
            return this.author_idauthor;
	}

   /*
	public Collection<Book> getBooksCollection() {
		return booksCollection;
	}

	public void setBooksCollection(Collection<Book> booksCollection) {
		this.booksCollection = booksCollection;
	}
*/
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (author_idauthor != null ? author_idauthor.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof BookHasAuthor)) {
			return false;
		}
		BookHasAuthor other = (BookHasAuthor) object;
		if ((this.author_idauthor == null && other.author_idauthor != null) || (this.author_idauthor != null && !this.author_idauthor.equals(other.author_idauthor))) {
			return false;
		}
		return true;
	}

   
}
