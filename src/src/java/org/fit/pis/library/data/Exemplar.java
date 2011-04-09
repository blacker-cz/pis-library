/*
 */

package org.fit.pis.library.data;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Lukáš Černý <cerny.l@gmail.com>
 */
@Entity
@Table(name = "exemplar")
@NamedQueries({
	@NamedQuery(name = "Exemplar.findAll", query = "SELECT e FROM Exemplar e"),
	@NamedQuery(name = "Exemplar.findByIdexemplar", query = "SELECT e FROM Exemplar e WHERE e.idexemplar = :idexemplar"),
	@NamedQuery(name = "Exemplar.findByAquired", query = "SELECT e FROM Exemplar e WHERE e.aquired = :aquired"),
	@NamedQuery(name = "Exemplar.findByState", query = "SELECT e FROM Exemplar e WHERE e.state = :state")})
public class Exemplar implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @NotNull
    @Column(name = "idexemplar")
	private Integer idexemplar;
	@Basic(optional = false)
    @NotNull
    @Column(name = "aquired")
    @Temporal(TemporalType.DATE)
	private Date aquired;
	@Basic(optional = false)
    @NotNull
    @Column(name = "state")
	private int state;
	@JoinColumn(name = "idbook", referencedColumnName = "idbook")
    @ManyToOne(optional = false)
	private Book idbook;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "idexemplar")
	private Collection<Borrow> borrowCollection;

	public Exemplar() {
	}

	public Exemplar(Integer idexemplar) {
		this.idexemplar = idexemplar;
	}

	public Exemplar(Integer idexemplar, Date aquired, int state) {
		this.idexemplar = idexemplar;
		this.aquired = aquired;
		this.state = state;
	}

	public Integer getIdexemplar() {
		return idexemplar;
	}

	public void setIdexemplar(Integer idexemplar) {
		this.idexemplar = idexemplar;
	}

	public Date getAquired() {
		return aquired;
	}

	public void setAquired(Date aquired) {
		this.aquired = aquired;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public Book getIdbook() {
		return idbook;
	}

	public void setIdbook(Book idbook) {
		this.idbook = idbook;
	}

	public Collection<Borrow> getBorrowCollection() {
		return borrowCollection;
	}

	public void setBorrowCollection(Collection<Borrow> borrowCollection) {
		this.borrowCollection = borrowCollection;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (idexemplar != null ? idexemplar.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof Exemplar)) {
			return false;
		}
		Exemplar other = (Exemplar) object;
		if ((this.idexemplar == null && other.idexemplar != null) || (this.idexemplar != null && !this.idexemplar.equals(other.idexemplar))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "org.fit.pis.library.data.Exemplar[ idexemplar=" + idexemplar + " ]";
	}

}
