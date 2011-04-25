/*
 */

package org.fit.pis.library.data;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Lukáš Černý <cerny.l@gmail.com>
 */
@Entity
@Table(name = "borrow")
@NamedQueries({
	@NamedQuery(name = "Borrow.findAll", query = "SELECT b FROM Borrow b"),
	@NamedQuery(name = "Borrow.findByIdborrow", query = "SELECT b FROM Borrow b WHERE b.idborrow = :idborrow ORDER BY b.returned DESC, b.borrowed ASC"),
	@NamedQuery(name = "Borrow.findByUser", query = "SELECT b FROM Borrow b WHERE b.user = :user"),
	@NamedQuery(name = "Borrow.findByBorrowed", query = "SELECT b FROM Borrow b WHERE b.borrowed = :borrowed"),
	@NamedQuery(name = "Borrow.findByReturned", query = "SELECT b FROM Borrow b WHERE b.returned = :returned"),
	@NamedQuery(name = "Borrow.findByProlongations", query = "SELECT b FROM Borrow b WHERE b.prolongations = :prolongations")})
public class Borrow implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final int MAX_PROLONGATE_COUNT = 3;
	public static final int PROLONGATE_DAYS_COUNT = 30;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Basic(optional = false)
//    @NotNull
    @Column(name = "idborrow")
	private Integer idborrow;
	@Basic(optional = false)
    @NotNull
    @Column(name = "borrowed")
    @Temporal(TemporalType.DATE)
	private Date borrowed;
	@Column(name = "returned")
    @Temporal(TemporalType.DATE)
	private Date returned;
	@Basic(optional = false)
    @NotNull
    @Column(name = "prolongations")
	private int prolongations;
	@JoinColumn(name = "iduser", referencedColumnName = "iduser")
    @ManyToOne(optional = false)
	private User user;
	@JoinColumn(name = "idexemplar", referencedColumnName = "idexemplar")
    @ManyToOne(optional = false)
	private Exemplar exemplar;

	public Borrow() {
	}

	public Borrow(Integer idborrow) {
		this.idborrow = idborrow;
	}

	public Borrow(Integer idborrow, Date borrowed, int prolongations) {
		this.idborrow = idborrow;
		this.borrowed = borrowed;
		this.prolongations = prolongations;
	}

	public Integer getIdborrow() {
		return idborrow;
	}

	public void setIdborrow(Integer idborrow) {
		this.idborrow = idborrow;
	}

	public Date getBorrowed() {
		return borrowed;
	}

	public void setBorrowed(Date borrowed) {
		this.borrowed = borrowed;
	}

	public Date getReturned() {
		return returned;
	}

	public void setReturned(Date returned) {
		this.returned = returned;
	}

	public int getProlongations() {
		return prolongations;
	}

	public void setProlongations(int prolongations) {
		this.prolongations = prolongations;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Exemplar getExemplar() {
		return exemplar;
	}

	public void setExemplar(Exemplar exemplar) {
		this.exemplar = exemplar;
	}

	/**
	 * Can prolongate book borrow
	 * @return 
	 */
	public boolean getCanProlongate() {
		return returned == null && prolongations < (MAX_PROLONGATE_COUNT - 1);
	}
	
	/**
	 * Maximum return date
	 * @return 
	 */
	public Date getMaximumReturnDate() {
		// already returned
		if (returned != null)
			return null;
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(borrowed);
		
		// count max return date
		cal.add(Calendar.HOUR, (prolongations + 1) * PROLONGATE_DAYS_COUNT * 24);
		return cal.getTime();
	}
	
	/**
	 * Is borrow expired = now is later than maximumReturnDate
	 * @return 
	 */
	public boolean getIsBorrowExpired() {
		Calendar ret = Calendar.getInstance(), now = Calendar.getInstance();
		ret.setTime(getMaximumReturnDate());
		
		if (ret.compareTo(now) < 0)
			return true;
		else		
			return false;
	}
	
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (idborrow != null ? idborrow.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof Borrow)) {
			return false;
		}
		Borrow other = (Borrow) object;
		if ((this.idborrow == null && other.idborrow != null) || (this.idborrow != null && !this.idborrow.equals(other.idborrow))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "org.fit.pis.library.data.Borrow[ idborrow=" + idborrow + " ]";
	}

}
