/*
 */
package org.fit.pis.library.back;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.fit.pis.library.data.*;
import org.richfaces.component.UIDataTable;

/**
 *
 * @author Vojtěch Sysel <xsysel03@setud.fit.vutbr.cz>
 */
@ManagedBean
@SessionScoped
public class SearchBooksBean {
	@EJB
	private BookManager bookMgr;
	@EJB
	private GenreManager genreMgr;
	private Book book;
	private UIDataTable listTable;
	
	@ManagedProperty(value="#{authenticationBean}")
	private AuthenticationBean authBean;
	
	// variables used for filtering table
	private String filter_name;
	private String filter_author;
	private Calendar filter_dateFrom;
	private Calendar filter_dateTo;
	private String filter_genre;
	
	private int minBookYear;
	private int maxBookYear;
	
	/** Creates a new instance of ManageUsersBean */
	public SearchBooksBean() {
		clearFilter();
	}

	/**
	 * Clear filter variables
	 */
	private void clearFilter() {
		book = new Book();

		// set empty filtering
		filter_name = "";
		filter_author = "";
		filter_genre = "";
		filter_dateFrom = Calendar.getInstance();
		filter_dateFrom.set(Book.MIN_BOOK_YEAR, 1, 3);
		filter_dateTo = Calendar.getInstance();
		filter_dateTo.set(Book.MAX_BOOK_YEAR, 1, 3);
		
		minBookYear = Book.MIN_BOOK_YEAR;
		maxBookYear = Book.MAX_BOOK_YEAR;
	}
	
	/**
	 * Get user
	 * @return 
	 */
	public Book getBook() {
		return book;
	}

	/**
	 * Set user
	 * @param user 
	 */
	public void setBook(Book book) {
		this.book = book;
	}

	/**
	 * Authentication bean setter
	 * @param authBean 
	 */
	public void setAuthBean(AuthenticationBean authBean) {
		this.authBean = authBean;
	}

	// <editor-fold defaultstate="collapsed" desc="Filter getters and setters">
	public String getFilter_name() {
		return filter_name;
	}

	public void setFilter_name(String filter_name) {
		this.filter_name = filter_name;
	}

	// <editor-fold defaultstate="collapsed" desc="Filter getters and setters">
	public String getFilter_author() {
		return filter_author;
	}

	public void setFilter_author(String filter_author) {
		this.filter_author = filter_author;
	}
	
	public String getFilter_genre() {
		return filter_genre;
	}
	
	public void setFilter_genre(String genre) {
		filter_genre = genre;
	}
	
	public Date getFilter_dateFrom() {
		return filter_dateFrom.getTime();
	}

	public void setFilter_dateFrom(Date dateFrom) {
		this.filter_dateFrom.setTime(dateFrom);
	}

	public String getFilter_yearFrom() {
		if (filter_dateFrom == null)
			return "";

		// format date
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy");
        return dateformat.format(filter_dateFrom.getTime());
	}

	public int getMinBookYear() {
		return minBookYear;
	}
	
	public void setMinBookYear(int year) {
		minBookYear = year;
	}

	public int getMaxBookYear() {
		return maxBookYear;
	}
	
	public void setMaxBookYear(int year) {
		maxBookYear = year;
	}
	
	public void setFilter_yearFrom(String yearFrom) {
		int year = Integer.parseInt(yearFrom);
		
		if (filter_dateFrom == null)
			filter_dateFrom = Calendar.getInstance();
		
		filter_dateFrom.set(year, 1, 3);
	}
	
	public String getFilter_yearTo() {
		if (filter_dateTo == null)
			return "";

		// format date
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy");
        return dateformat.format(filter_dateTo.getTime());
	}

	public void setFilter_yearTo(String yearFrom) {
		int year = Integer.parseInt(yearFrom);
		
		if (filter_dateTo == null)
			filter_dateTo = Calendar.getInstance();
		
		filter_dateTo.set(year, 1, 3);
	}
	// </editor-fold>
	/**
	 * Get list of users
	 * @return 
	 */
	public List<Book> getBooks() throws Exception {
		Date yearFrom, yearTo;
		// create new date
		if (filter_dateFrom == null) {
			Calendar c = Calendar.getInstance();
			c.set(1000, 1, 3);
			yearFrom = c.getTime();
		// get year
		} else {
			yearFrom = filter_dateFrom.getTime();
		}
		
		// create new date
		if (filter_dateFrom == null) {
			Calendar c = Calendar.getInstance();
			c.set(1000, 1, 3);
			yearTo = c.getTime();
		// get year
		} else {
			yearTo = filter_dateFrom.getTime();
		}
		
		// genre
		Genre genre = null;
		if (!filter_genre.isEmpty() && !filter_genre.equalsIgnoreCase("all")) {
			genre = genreMgr.findByName(filter_genre);
			if (genre == null) {
				throw new Exception("NULL!!!");
			}
		}
		
		return bookMgr.find(filter_name, yearFrom, yearTo, genre);
	}

	/**
	 * Get records table
	 * @return 
	 */
	public UIDataTable getListTable() {
		return null;	// force rebuild of table
	}

	/**
	 * Set records table
	 * @param table 
	 */
	public void setListTable(UIDataTable table) {
		this.listTable = table;
	}

	/**
	 * Search books
	 * @return 
	 */
	public String actionSearchBooks() {
		return "searchBooks";
	}
	
	/**
	 * Just view catalog
	 * @return 
	 */
	public String actionViewBooks() {
		clearFilter();
		
		return "viewBooks";
	}
	
	/**
	 * Book detail
	 * @return 
	 */
	public String actionDetail() {
		setBook((Book) listTable.getRowData());
		return "detail";
	}
	
	/**
	 * Edit user
	 * @return "edit"
	 */
	public String actionEdit() {
//		setUser((User) listTable.getRowData());
		return "edit";
	}

	/**
	 * Action for redirecting to new user page
	 * @return 
	 */
	public String actionCreateNew() {
//		setUser(new User());
//		getUser().setExpire(new Date(System.currentTimeMillis() + 31536000000L));

		return "new";
	}

	public String actionInsert() {
//		user.setRegistered(new Date(System.currentTimeMillis()));
//
//		user.setPassword(ManageUsersBean.generatePassword());
//		
//		try {
//			userMgr.Save(user);
//		} catch(javax.ejb.EJBException e) {
//			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("User wasn't created. Please try again later (or try to change permit number)."));
//			return "";
//		}
//
//		Mailer mailer = new Mailer();
//		
//		if(!mailer.send(user.getEmail(), "Knihovna: Váš nový účet", "Byl Vám vytvořen účet v naší knihovně.<br />" +
//				"Pro přístup do systému použijte následující údaje: <br />" + 
//				"Číslo průkazky: " + user.getPermitNumber() + "<br />" +
//				"Heslo: " + user.getPassword())) {
//			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Nepodařilo se odeslat email s potvrzením registrace a heslem."));
//		}
//
//		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("User was successfully created."));
		
		return "edit";
	}
}
