/*
 */

package org.fit.pis.library.data;

import java.util.List;

/**
 *
 * @author Lukáš Černý <cerny.l@gmail.com>
 */
public class DatabaseWrapper {

	private List<User> users;
	
	private List<Book> books;

	public List<Book> getBooks() {
		return books;
	}

	public void setBooks(List<Book> books) {
		this.books = books;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	
}
