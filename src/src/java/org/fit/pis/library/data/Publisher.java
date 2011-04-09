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
@Table(name = "publisher")
@NamedQueries({
	@NamedQuery(name = "Publisher.findAll", query = "SELECT p FROM Publisher p"),
	@NamedQuery(name = "Publisher.findByIdpublisher", query = "SELECT p FROM Publisher p WHERE p.idpublisher = :idpublisher"),
	@NamedQuery(name = "Publisher.findByName", query = "SELECT p FROM Publisher p WHERE p.name = :name"),
	@NamedQuery(name = "Publisher.findByAddress", query = "SELECT p FROM Publisher p WHERE p.address = :address")})
public class Publisher implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @NotNull
    @Column(name = "idpublisher")
	private Integer idpublisher;
	@Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "name")
	private String name;
	@Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "address")
	private String address;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "idpublisher")
	private Collection<Book> booksCollection;

	public Publisher() {
	}

	public Publisher(Integer idpublisher) {
		this.idpublisher = idpublisher;
	}

	public Publisher(Integer idpublisher, String name, String address) {
		this.idpublisher = idpublisher;
		this.name = name;
		this.address = address;
	}

	public Integer getIdpublisher() {
		return idpublisher;
	}

	public void setIdpublisher(Integer idpublisher) {
		this.idpublisher = idpublisher;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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
		hash += (idpublisher != null ? idpublisher.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof Publisher)) {
			return false;
		}
		Publisher other = (Publisher) object;
		if ((this.idpublisher == null && other.idpublisher != null) || (this.idpublisher != null && !this.idpublisher.equals(other.idpublisher))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "org.fit.pis.library.data.Publisher[ idpublisher=" + idpublisher + " ]";
	}

}
