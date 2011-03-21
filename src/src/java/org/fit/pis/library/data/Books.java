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
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
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
@Table(name = "books")
@NamedQueries({
	@NamedQuery(name = "Books.findAll", query = "SELECT b FROM Books b"),
	@NamedQuery(name = "Books.findByIdbooks", query = "SELECT b FROM Books b WHERE b.idbooks = :idbooks"),
	@NamedQuery(name = "Books.findByName", query = "SELECT b FROM Books b WHERE b.name = :name"),
	@NamedQuery(name = "Books.findByYear", query = "SELECT b FROM Books b WHERE b.year = :year"),
	@NamedQuery(name = "Books.findByPages", query = "SELECT b FROM Books b WHERE b.pages = :pages"),
	@NamedQuery(name = "Books.findByEdition", query = "SELECT b FROM Books b WHERE b.edition = :edition"),
	@NamedQuery(name = "Books.findByPlace", query = "SELECT b FROM Books b WHERE b.place = :place"),
	@NamedQuery(name = "Books.findByType", query = "SELECT b FROM Books b WHERE b.type = :type"),
	@NamedQuery(name = "Books.findByCode", query = "SELECT b FROM Books b WHERE b.code = :code")})
public class Books implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @NotNull
    @Column(name = "idbooks")
	private Integer idbooks;
	@Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "name")
	private String name;
	@Basic(optional = false)
    @NotNull
    @Column(name = "year")
    @Temporal(TemporalType.DATE)
	private Date year;
	@Column(name = "pages")
	private Integer pages;
	@Column(name = "edition")
    @Temporal(TemporalType.DATE)
	private Date edition;
	@Size(max = 255)
    @Column(name = "place")
	private String place;
	@Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 5)
    @Column(name = "type")
	private String type;
	@Size(max = 255)
    @Column(name = "code")
	private String code;
	@ManyToMany(mappedBy = "booksCollection")
	private Collection<Author> authorCollection;
	@JoinColumn(name = "idpublisher", referencedColumnName = "idpublisher")
    @ManyToOne(optional = false)
	private Publisher idpublisher;
	@JoinColumn(name = "idgenre", referencedColumnName = "idgenre")
    @ManyToOne(optional = false)
	private Genre idgenre;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "idbooks")
	private Collection<Booking> bookingCollection;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "idbooks")
	private Collection<Exemplar> exemplarCollection;

	public Books() {
	}

	public Books(Integer idbooks) {
		this.idbooks = idbooks;
	}

	public Books(Integer idbooks, String name, Date year, String type) {
		this.idbooks = idbooks;
		this.name = name;
		this.year = year;
		this.type = type;
	}

	public Integer getIdbooks() {
		return idbooks;
	}

	public void setIdbooks(Integer idbooks) {
		this.idbooks = idbooks;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getYear() {
		return year;
	}

	public void setYear(Date year) {
		this.year = year;
	}

	public Integer getPages() {
		return pages;
	}

	public void setPages(Integer pages) {
		this.pages = pages;
	}

	public Date getEdition() {
		return edition;
	}

	public void setEdition(Date edition) {
		this.edition = edition;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Collection<Author> getAuthorCollection() {
		return authorCollection;
	}

	public void setAuthorCollection(Collection<Author> authorCollection) {
		this.authorCollection = authorCollection;
	}

	public Publisher getIdpublisher() {
		return idpublisher;
	}

	public void setIdpublisher(Publisher idpublisher) {
		this.idpublisher = idpublisher;
	}

	public Genre getIdgenre() {
		return idgenre;
	}

	public void setIdgenre(Genre idgenre) {
		this.idgenre = idgenre;
	}

	public Collection<Booking> getBookingCollection() {
		return bookingCollection;
	}

	public void setBookingCollection(Collection<Booking> bookingCollection) {
		this.bookingCollection = bookingCollection;
	}

	public Collection<Exemplar> getExemplarCollection() {
		return exemplarCollection;
	}

	public void setExemplarCollection(Collection<Exemplar> exemplarCollection) {
		this.exemplarCollection = exemplarCollection;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (idbooks != null ? idbooks.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof Books)) {
			return false;
		}
		Books other = (Books) object;
		if ((this.idbooks == null && other.idbooks != null) || (this.idbooks != null && !this.idbooks.equals(other.idbooks))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "org.fit.pis.library.data.Books[ idbooks=" + idbooks + " ]";
	}

}
