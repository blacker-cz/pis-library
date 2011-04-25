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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Lukáš Černý <cerny.l@gmail.com>
 */
@Entity
@Table(name = "user")
@NamedQueries({
	@NamedQuery(name = "User.findAll", query = "SELECT u FROM User u"),
	@NamedQuery(name = "User.findByIduser", query = "SELECT u FROM User u WHERE u.iduser = :iduser"),
	@NamedQuery(name = "User.findByPermitNumber", query = "SELECT u FROM User u WHERE u.permitNumber = :permitNumber"),
	@NamedQuery(name = "User.findByAddress", query = "SELECT u FROM User u WHERE u.address = :address"),
	@NamedQuery(name = "User.findByPhone", query = "SELECT u FROM User u WHERE u.phone = :phone"),
	@NamedQuery(name = "User.findByEmail", query = "SELECT u FROM User u WHERE u.email = :email"),
	@NamedQuery(name = "User.findByRegistered", query = "SELECT u FROM User u WHERE u.registered = :registered"),
	@NamedQuery(name = "User.findByExpire", query = "SELECT u FROM User u WHERE u.expire = :expire"),
	@NamedQuery(name = "User.findByPassword", query = "SELECT u FROM User u WHERE u.password = :password"),
	@NamedQuery(name = "User.findByLevel", query = "SELECT u FROM User u WHERE u.level = :level")})
public class User implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = true)
//    @NotNull
    @Column(name = "iduser")
	private Integer iduser;
	@Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 128)
    @Column(name = "forename")
	private String forename;
	@Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 128)
    @Column(name = "surname")
	private String surname;
	@Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "permitNumber")
	private String permitNumber;
    @Size(max = 255)
    @Column(name = "address")
	private String address;
	// @Pattern(regexp="^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$", message="Invalid phone/fax format, should be as xxx-xxx-xxxx")//if the field contains phone or fax number consider using this annotation to enforce field validation
	@Size(max = 20)
    @Column(name = "phone")
	private String phone;
	// @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
	@Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "email")
	private String email;
	@Basic(optional = false)
    @NotNull
    @Column(name = "registered")
    @Temporal(TemporalType.DATE)
	private Date registered;
	@Basic(optional = false)
    @NotNull
    @Column(name = "expire")
    @Temporal(TemporalType.DATE)
	private Date expire;
	@Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "password")
	private String password;
	@Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 9)
    @Column(name = "level")
	private String level;
		@OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
	private Collection<Booking> bookingCollection;
		@OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
	private Collection<Borrow> borrowCollection;

	public User() {
	}

	public User(Integer iduser) {
		this.iduser = iduser;
	}

	public User(Integer iduser, String forename, String surname, String permitNumber, String address, String email, Date registered, Date expire, String password, String level) {
		this.iduser = iduser;
		this.forename = forename;
		this.surname = surname;
		this.permitNumber = permitNumber;
		this.address = address;
		this.email = email;
		this.registered = registered;
		this.expire = expire;
		this.password = password;
		this.level = level;
	}

	public Integer getIduser() {
		return iduser;
	}

	public void setIduser(Integer iduser) {
		this.iduser = iduser;
	}

	public String getForename() {
		return forename;
	}

	public void setForename(String forename) {
		this.forename = forename;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getPermitNumber() {
		return permitNumber;
	}

	public void setPermitNumber(String permitNumber) {
		this.permitNumber = permitNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getRegistered() {
		return registered;
	}

	public void setRegistered(Date registered) {
		this.registered = registered;
	}

	public Date getExpire() {
		return expire;
	}

	public void setExpire(Date expire) {
		this.expire = expire;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public Collection<Booking> getBookingCollection() {
		return bookingCollection;
	}

	public void setBookingCollection(Collection<Booking> bookingCollection) {
		this.bookingCollection = bookingCollection;
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
		hash += (iduser != null ? iduser.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof User)) {
			return false;
		}
		User other = (User) object;
		if ((this.iduser == null && other.iduser != null) || (this.iduser != null && !this.iduser.equals(other.iduser))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "org.fit.pis.library.data.User[ iduser=" + iduser + " ]";
	}

}
