/*
 */
package org.fit.pis.library.back;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
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
public class ManageBooksBean {

	@EJB
	private BookManager bookMgr;
	@EJB
	private GenreManager genreMgr;
	@EJB
	private ExemplarManager exemplarMgr;
	@EJB
	private PublisherManager publisherMgr;
	@EJB
	private AuthorManager authorMgr;
	private Book book;
	private Exemplar exemplar;
	private UIDataTable listTable;
	private UIDataTable exemplarListTable;

	// variables used for filtering table
	private int idbook;

	private String nazov_zanru;
	private String nazov_vydavatelstva;
	private String rok_vydania;
	private Author autor;

	private String filter_name;
	private String filter_publisher;
	private String filter_author;
	private String filter_genre;
	private String filter_isbn_issn;
	private String filter_city;
	
	private String nazov_1_autora;
	private String nazov_2_autora;
	private String nazov_3_autora;
	private String nazov_4_autora;
	private Collection<Author> autorovia;
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy");

	/** Creates a new instance of ManageUsersBean */
	public ManageBooksBean() {
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
		filter_isbn_issn = "";
		filter_publisher = "";
		filter_city = "";
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

	public Exemplar getExemplar() {
		return exemplar;
	}

	public List<Author> getAuthors() {
		return authorMgr.findAll();
	}
	
	public List<Genre> getGenres() {
		return genreMgr.findAll();
	}
	
	public List<Publisher> getPublishers() {
		return publisherMgr.findAll();
	}
	
	public int getA() {
		return this.idbook;
	}

	public String getFilter_author() {
		return filter_author;
	}

	public void setFilter_author(String filter_author) {
		this.filter_author = filter_author;
	}

	public String getFilter_genre() {
		return filter_genre;
	}

	public void setFilter_genre(String filter_genre) {
		this.filter_genre = filter_genre;
	}

	public String getFilter_isbn_issn() {
		return filter_isbn_issn;
	}

	public void setFilter_isbn_issn(String filter_isbn_issn) {
		this.filter_isbn_issn = filter_isbn_issn;
	}

	public String getFilter_name() {
		return filter_name;
	}

	public void setFilter_name(String filter_name) {
		this.filter_name = filter_name;
	}

	public String getFilter_publisher() {
		return filter_publisher;
	}

	public void setFilter_publisher(String filter_publisher) {
		this.filter_publisher = filter_publisher;
	}

	public String getFilter_city() {
		return filter_city;
	}

	public void setFilter_city(String filter_city) {
		this.filter_city = filter_city;
	}
	
	
	/**
	 * Set user
	 * @param user 
	 */
	public void Exemplar(Exemplar exemplar) {
		this.exemplar = exemplar;
	}

	/**
	 * Get list of users
	 * @return 
	 */
	public List<Book> getBooks() {
		// genre
		Author author = null;
		if (!filter_author.isEmpty() && !filter_author.equalsIgnoreCase("all")) {
			author = authorMgr.findByName(filter_author);
			if (author == null) {
				System.err.println("Badly working database encoding!");
			}
		}

		// genre
		Genre genre = null;
		if (!filter_genre.isEmpty() && !filter_genre.equalsIgnoreCase("all")) {
			genre = genreMgr.findByName(filter_genre);
			if (genre == null) {
				System.err.println("Badly working database encoding!");
			}
		}

		// publisher
		Publisher publisher = null;
		if (!filter_publisher.isEmpty() && !filter_publisher.equalsIgnoreCase("all")) {
			publisher = publisherMgr.findByName(filter_publisher);
			if (publisher == null) {
				System.err.println("Badly working database encoding!");
			}
		}

		return bookMgr.find(filter_name, author, genre, publisher, filter_isbn_issn, filter_city);
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
	 * Get records table
	 * @return 
	 */
	public UIDataTable getExemplarListTable() {
		return null;	// force rebuild of table
	}

	/**
	 * Set records table
	 * @param table 
	 */
	public void setExemplarListTable(UIDataTable table) {
		this.exemplarListTable = table;
	}

	/**
	 * Return exemplar collection
	 * @param book
	 * @return 
	 */
	public List<Exemplar> getExemplarCollection(Book book) {
		return exemplarMgr.findByBook(book);
	}

	/**
	 * Book detail
	 * @return 
	 */
	public String actionDetail() {

		setBook((Book) listTable.getRowData());
		this.idbook = book.getIdbook();

		return "detail";
	}

	/**
	 * Book cancel detail
	 * @return 
	 */
	public String actionCancelDetail() {
		book = null;

		return "cancelDetail";
	}

	/**
	 * Just empty action
	 * @return 
	 */
	public String actionEmptyAction() {
		return "";
	}
	
	/**
	 * Edit user
	 * @return "edit"
	 */
	public String actionEdit() {
		setBook((Book) listTable.getRowData());

		this.nazov_zanru = book.getGenre().toString();


		this.nazov_vydavatelstva = book.getPublisher().toString();


		this.autorovia = book.getAuthorCollection();
		autorovia.size();
		int i = 0, size = autorovia.size();
		for (Author a : autorovia) {
			if (i == 0) {
				this.nazov_1_autora = (String) a.getName().toString();
			}
			if (i == 1) {
				this.nazov_2_autora = (String) a.getName().toString();
			}
			if (i == 2) {
				this.nazov_3_autora = (String) a.getName().toString();
			}
			if (i == 3) {
				this.nazov_4_autora = (String) a.getName().toString();
			}
			i++;

		}
		this.rok_vydania = (String) formatter.format(book.getYear());
		return "edit";
	}

	/**
	 * Action for redirecting to new user page
	 * @return 
	 */
	public String actionCreateNew() {
		setBook(new Book());
		return "new";
	}

	public String actionDelete() {

		Book selected = (Book) listTable.getRowData();
		try {
			bookMgr.remove(selected);
		} catch (javax.ejb.EJBException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Nepodařilo se odstranit knihu, zkuste to prosím později."));
			return "";
		}

		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Kniha byla úspěšně odstraněna."));

		return "";
	}

	public String actionUpdate() throws ParseException {

		bookMgr.new_id();

		book.setGenre(genreMgr.findByName(nazov_zanru));
		book.setPublisher(publisherMgr.findByName(nazov_vydavatelstva));

		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("book.getIdbook()" + book.getIdbook()));




		book.setYear(formatter.parse(rok_vydania));

		try {
			bookMgr.save(book);
		} catch (javax.ejb.EJBException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Změny se nepodařilo uložit, zkuste to prosím později."));
			return "";
		}

		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Změny byly úspěšně uloženy."));

		return "";
	}

	public String actionInsert() throws ParseException {
		Collection<Author> novy_autorovia;


		book.setGenre(genreMgr.findByName(nazov_zanru));
		book.setPublisher(publisherMgr.findByName(nazov_vydavatelstva));
		book.setYear(formatter.parse(rok_vydania));
		book.setType("isbn");

		autor = authorMgr.findByName(nazov_1_autora);


		//bookHasAuthor.setIdauthor(autor.getIdauthor());

		//bookHasAuthor.setIdbook(book.getIdbook());

		//FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("+bookHasAuthor.getIdauthor();"+bookHasAuthor.getIdauthor()));
		//FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("autor.toString();"+bookHasAuthor.getIdbook()));

		//bookHasAuthorMgr.save(bookHasAuthor);



		novy_autorovia = (authorMgr.find(nazov_1_autora));

		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Title was successfully created."));


		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("autor.toString();" + autor.toString()));


		try {
			bookMgr.save(book);


		} catch (javax.ejb.EJBException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("User wasn't created. Please try again later (or try to change permit number)."));
			return "";
		}

		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Title was successfully created."));


		/* 
		bookHasAuthor.setIdbook(book.getIdbook());
		bookHasAuthor.setIdauthor(autor.getIdauthor());
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("+bookHasAuthor.getIdauthor();"+bookHasAuthor.getIdauthor()));
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("autor.toString();"+bookHasAuthor.getIdbook()));
		 */
		return "edit";
	}
}
