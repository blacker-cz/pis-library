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
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.fit.pis.library.back.converters.IntegerAdapter;

/**
 *
 * @author Lukáš Černý <cerny.l@gmail.com>
 */
@Entity
@Table(name = "book")
@NamedQueries({
	@NamedQuery(name = "Book.findAll", query = "SELECT b FROM Book b"),
	@NamedQuery(name = "Book.findByIdbook", query = "SELECT b FROM Book b WHERE b.idbook = :idbook"),
	@NamedQuery(name = "Book.findByName", query = "SELECT b FROM Book b WHERE b.name = :name"),
	@NamedQuery(name = "Book.findByYear", query = "SELECT b FROM Book b WHERE b.year = :year"),
	@NamedQuery(name = "Book.findByPages", query = "SELECT b FROM Book b WHERE b.pages = :pages"),
	@NamedQuery(name = "Book.findByEdition", query = "SELECT b FROM Book b WHERE b.edition = :edition"),
	@NamedQuery(name = "Book.findByPlace", query = "SELECT b FROM Book b WHERE b.place = :place"),
	@NamedQuery(name = "Book.findByType", query = "SELECT b FROM Book b WHERE b.type = :type"),
	@NamedQuery(name = "Book.findByCode", query = "SELECT b FROM Book b WHERE b.code = :code")})
public class Book implements Serializable {
	public static final int MIN_BOOK_YEAR = 1000;
	public static final int MAX_BOOK_YEAR = 2100;
	
	private static final long serialVersionUID = 1L;
	@Id
//        @GeneratedValue(strategy = GenerationType.IDENTITY)
//        @Basic(optional = false)
//        @NotNull
        @Column(name = "idbook")
	@XmlID
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	private Integer idbook;
        
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
	private Integer edition;
        
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
	@XmlIDREF
	private Collection<Author> authorCollection;
        
	@JoinColumn(name = "idpublisher", referencedColumnName = "idpublisher")
        @ManyToOne(optional = false)
	@XmlIDREF
	private Publisher publisher;
        
	@JoinColumn(name = "idgenre", referencedColumnName = "idgenre")
        @ManyToOne(optional = false)
	@XmlIDREF
	private Genre genre;
        
		@OneToMany(cascade = CascadeType.ALL, mappedBy = "book")
	@XmlIDREF
	private Collection<Booking> bookingCollection;
        
		@OneToMany(cascade = CascadeType.ALL, mappedBy = "book")
	@XmlIDREF
	private Collection<Exemplar> exemplarCollection;

	public Book() {
	}

	public Book(Integer idbook) {
		this.idbook = idbook;
	}

	public Book(Integer idbook, String name, Date year, String type) {
		this.idbook = idbook;
		this.name = name;
		this.year = year;
		this.type = type;
	}

	public Integer getIdbook() {
		return this.idbook;
	}

	public void setIdbook(Integer idbook) {
		this.idbook = idbook;
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

	public Integer getEdition() {
		return edition;
	}

	public void setEdition(Integer edition) {
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

	public Publisher getPublisher() {
		return publisher;
	}

	public void setPublisher(Publisher idpublisher) {
		this.publisher = idpublisher;
	}

	public Genre getGenre() {
		return genre;
	}

	public void setGenre(Genre genre) {
		this.genre = genre;
	}
        
        public Integer getIdGenre() {
            return genre.getIdgenre();
        }
        
        public void setIdGenre(Integer idGenre) {
            genre.setIdgenre(idGenre);
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
		hash += (idbook != null ? idbook.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof Book)) {
			return false;
		}
		Book other = (Book) object;
		if ((this.idbook == null && other.idbook != null) || (this.idbook != null && !this.idbook.equals(other.idbook))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "org.fit.pis.library.data.Book[ idbook=" + idbook + " ]";
	}

}
