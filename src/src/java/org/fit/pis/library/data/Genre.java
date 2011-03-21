/*
 */

package org.fit.pis.library.data;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Lukáš Černý <cerny.l@gmail.com>
 */
@Entity
@Table(name = "genre")
@NamedQueries({
	@NamedQuery(name = "Genre.findAll", query = "SELECT g FROM Genre g"),
	@NamedQuery(name = "Genre.findByIdgenre", query = "SELECT g FROM Genre g WHERE g.idgenre = :idgenre"),
	@NamedQuery(name = "Genre.findByName", query = "SELECT g FROM Genre g WHERE g.name = :name")})
public class Genre implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @NotNull
    @Column(name = "idgenre")
	private Integer idgenre;
	@Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "name")
	private String name;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "idgenre")
	private Collection<Books> booksCollection;

	public Genre() {
	}

	public Genre(Integer idgenre) {
		this.idgenre = idgenre;
	}

	public Genre(Integer idgenre, String name) {
		this.idgenre = idgenre;
		this.name = name;
	}

	public Integer getIdgenre() {
		return idgenre;
	}

	public void setIdgenre(Integer idgenre) {
		this.idgenre = idgenre;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Collection<Books> getBooksCollection() {
		return booksCollection;
	}

	public void setBooksCollection(Collection<Books> booksCollection) {
		this.booksCollection = booksCollection;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (idgenre != null ? idgenre.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof Genre)) {
			return false;
		}
		Genre other = (Genre) object;
		if ((this.idgenre == null && other.idgenre != null) || (this.idgenre != null && !this.idgenre.equals(other.idgenre))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "org.fit.pis.library.data.Genre[ idgenre=" + idgenre + " ]";
	}

}