/*
 */

package org.fit.pis.library.data;

import java.util.List;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Lukáš Černý <cerny.l@gmail.com>
 */
@XmlRootElement(name = "library")
public class DatabaseWrapper {
	
	@XmlElementWrapper(name="authors")
	private List<Author> author;

	@XmlElementWrapper(name="books")
	private List<Book> book;

	@XmlElementWrapper(name="bookings")
	private List<Booking> booking;
	
	@XmlElementWrapper(name="borrows")
	private List<Borrow> borrow;
	
	@XmlElementWrapper(name="exemplars")
	private List<Exemplar> exemplar;
	
	@XmlElementWrapper(name="genres")
	private List<Genre> genre;
	
	@XmlElementWrapper(name="publishers")
	private List<Publisher> publisher;

	@XmlElementWrapper(name="users")
	private List<User> user;

	public List<Author> getAuthors() {
		return author;
	}

	public void setAuthors(List<Author> authors) {
		this.author = authors;
	}

	public List<Booking> getBookings() {
		return booking;
	}

	public void setBookings(List<Booking> bookings) {
		this.booking = bookings;
	}

	public List<Book> getBooks() {
		return book;
	}

	public void setBooks(List<Book> books) {
		this.book = books;
	}

	public List<Borrow> getBorrows() {
		return borrow;
	}

	public void setBorrows(List<Borrow> borrows) {
		this.borrow = borrows;
	}

	public List<Exemplar> getExemplars() {
		return exemplar;
	}

	public void setExemplars(List<Exemplar> exemplars) {
		this.exemplar = exemplars;
	}

	public List<Genre> getGenres() {
		return genre;
	}

	public void setGenres(List<Genre> genres) {
		this.genre = genres;
	}

	public List<Publisher> getPublishers() {
		return publisher;
	}

	public void setPublishers(List<Publisher> publishers) {
		this.publisher = publishers;
	}

	public List<User> getUsers() {
		return user;
	}

	public void setUsers(List<User> users) {
		this.user = users;
	}
	
}
