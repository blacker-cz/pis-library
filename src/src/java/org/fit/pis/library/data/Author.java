/*
 */

package org.fit.pis.library.data;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Lukáš Černý <cerny.l@gmail.com>
 */
@Entity
@Table(name = "author")
@NamedQueries({
	@NamedQuery(name = "Author.findAll", query = "SELECT a FROM Author a"),
	@NamedQuery(name = "Author.findByIdauthor", query = "SELECT a FROM Author a WHERE a.idauthor = :idauthor"),
	@NamedQuery(name = "Author.findByName", query = "SELECT a FROM Author a WHERE a.name = :name")})
public class Author implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Basic(optional = false)
//    @NotNull
    @Column(name = "idauthor")
	private Integer idauthor;
	@Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "name")
	private String name;
	@JoinTable(name = "book_has_author", joinColumns = {
    	@JoinColumn(name = "author_idauthor", referencedColumnName = "idauthor")}, inverseJoinColumns = {
    	@JoinColumn(name = "book_idbook", referencedColumnName = "idbook")})
    @ManyToMany
	private Collection<Book> booksCollection;

	public Author() {
	}

	public Author(Integer idauthor) {
		this.idauthor = idauthor;
	}

	public Author(Integer idauthor, String name) {
		this.idauthor = idauthor;
		this.name = name;
	}

	public Integer getIdauthor() {
		return idauthor;
	}

	public void setIdauthor(Integer idauthor) {
		this.idauthor = idauthor;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Collection<Book> getBooksCollection() {
		return booksCollection;
	}

	public void setBooksCollection(Collection<Book> booksCollection) {
		this.booksCollection = booksCollection;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (idauthor != null ? idauthor.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof Author)) {
			return false;
		}
		Author other = (Author) object;
		if ((this.idauthor == null && other.idauthor != null) || (this.idauthor != null && !this.idauthor.equals(other.idauthor))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "org.fit.pis.library.data.Author[ idauthor=" + idauthor + " ]";
	}

}
