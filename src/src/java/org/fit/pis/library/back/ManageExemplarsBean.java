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
import javax.faces.el.ValueBinding;
import org.fit.pis.library.data.*;
import org.richfaces.component.UIDataTable;

/**
 * 
 * @author Vojtěch Sysel <xsysel03@setud.fit.vutbr.cz>
 */
@ManagedBean
@SessionScoped
public class ManageExemplarsBean {

	@EJB
	private ExemplarManager exemplarMgr;
	@EJB
	private GenreManager genreMgr;
	@EJB
	private UserManager userMgr;
	@EJB
	private PublisherManager publisherMgr;
	@EJB
	private BorrowManager borrowMgr;
	@EJB
	private AuthorManager authorMgr;
	private Exemplar exemplar;
	private UIDataTable listTable;
	private UIDataTable exemplarListTable;
	private UIDataTable exemplaringListTable;
	@ManagedProperty(value = "#{authenticationBean}")
	private AuthenticationBean authBean;
	@ManagedProperty(value = "#{searchBooksBean}")
	private SearchBooksBean bookBean;
	// variables used for filtering table
	private String nazov_zanru;
	private String nazov_vydavatelstva;
	private String rok_vydania;
	private String rok_vydania2;
	private String filter_name;
	private String filter_author;
	private Genre exemplar_genre;
	private Author autor;
	private Book book;
	private Calendar filter_dateFrom;
	private Calendar filter_dateTo;
	private String filter_genre;
	private String filter_isbn_issn;
	private String nazov_1_autora;
	private String nazov_2_autora;
	private String nazov_3_autora;
	private String nazov_4_autora;
	private String[] autori;
	private Collection<Author> autorovia;
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
	private int minExemplarYear;
	private int maxExemplarYear;

	/** Creates a new instance of ManageUsersBean */
	public ManageExemplarsBean() {
		clearFilter();
	}

	/**
	 * Clear filter variables
	 */
	private void clearFilter() {
		exemplar = new Exemplar();

		// set empty filtering
		filter_name = "";
		filter_author = "";
		filter_genre = "";
		filter_isbn_issn = "";
		filter_dateFrom = Calendar.getInstance();

		filter_dateTo = Calendar.getInstance();

	}

	/**
	 * Get user
	 * @return 
	 */
	public Exemplar getExemplar() {
		return exemplar;
	}

	/**
	 * Set user
	 * @param user 
	 */
	public void setExemplar(Exemplar exemplar) {
		this.exemplar = exemplar;
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

	public void setBookBean(SearchBooksBean bookBean) {
		this.bookBean = bookBean;
	}

	public String getRok_vydania() {
		return rok_vydania;
	}

	public void setrok_vydania(String rok_vydania) {
		this.rok_vydania = rok_vydania;
	}

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

	public String getFilter_isbn_issn() {
		return filter_isbn_issn;
	}

	public void setFilter_isbn_issn(String filter_isbn_issn) {
		this.filter_isbn_issn = filter_isbn_issn;
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
		if (filter_dateFrom == null) {
			return "";
		}

		// format date
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy");
		return dateformat.format(filter_dateFrom.getTime());
	}

	public int getMinExemplarYear() {
		return minExemplarYear;
	}

	public void setMinExemplarYear(int year) {
		minExemplarYear = year;
	}

	public int getMaxExemplarYear() {
		return maxExemplarYear;
	}

	public void setMaxExemplarYear(int year) {
		maxExemplarYear = year;
	}

	public void setFilter_yearFrom(String yearFrom) {
		int year = Integer.parseInt(yearFrom);
		filter_dateFrom.set(year, Calendar.JANUARY, 1);
	}

	public String getFilter_yearTo() {
		if (filter_dateTo == null) {
			return "";
		}

		// format date
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy");
		return dateformat.format(filter_dateTo.getTime());
	}

	public void setFilter_yearTo(String yearFrom) {
		int year = Integer.parseInt(yearFrom);
		filter_dateTo.set(year, Calendar.DECEMBER, 31);
	}
	// </editor-fold>

	/**
	 * Get list of users
	 * @return 
	 */
	public List<Exemplar> getExemplars() {
		// switch dates
		/*if (filter_dateFrom.compareTo(filter_dateTo) > 0) {
		Calendar tmp = filter_dateFrom;
		filter_dateFrom = filter_dateTo;
		filter_dateTo = tmp;
		}
		
		Date yearFrom = filter_dateFrom.getTime();
		Date yearTo = filter_dateTo.getTime();
		
		// genre
		 */
		//setSearchBooksBean(bookBean); 
		int a = bookBean.getA();
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("a" + a));




		//Integer idbook = book.getIdbook();

		return exemplarMgr.findByIdbook(a);
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
	/**
	 * Get exemplaring table
	 * @return 
	 */
	public UIDataTable getExemplaringListTable() {
		return null;	// force rebuild of table
	}

	/**
	 * Set exemplaring list table
	 * @return 
	 */
	public void setExemplaringListTable(UIDataTable table) {
		this.exemplaringListTable = table;
	}

	/**
	 * Return exemplar collection
	 * @param exemplar
	 * @return 
	 */
	//public List<Exemplar> getExemplarCollection(Exemplar exemplar) {
	//	return exemplarMgr.findByExemplar(exemplar);
	//}
	/**
	 * Search exemplars
	 * @return 
	 */
	public String actionSearchExemplars() {
		return "searchExemplars";
	}

	/**
	 * Just view catalog
	 * @return 
	 */
	public String actionViewExemplars() {
		clearFilter();

		return "viewExemplars";
	}

	/**
	 * Exemplar detail
	 * @return 
	 */
	public String actionDetail() {


		setBook((Book) listTable.getRowData());
		return "detail";
	}

	/**
	 * Exemplar cancel detail
	 * @return 
	 */
	public String actionCancelDetail() {
		exemplar = null;

		return "cancelDetail";
	}

	/**
	 * Edit user
	 * @return "edit"
	 */
	public String actionEdit() {
		setExemplar((Exemplar) listTable.getRowData());

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
		//this.rok_vydania = (String)formatter.format(exemplar.getYear()) ;
		return "edit";
	}

	/**
	 * Action for redirecting to new user page
	 * @return 
	 */
	public String actionCreateNew() {
		setExemplar(new Exemplar());
		return "new";
	}

	public String actionDelete() {

		Exemplar selected = (Exemplar) listTable.getRowData();
		try {
			exemplarMgr.remove(selected);
		} catch (javax.ejb.EJBException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Nepodařilo se odstranit knihu, zkuste to prosím později."));
			return "";
		}

		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Kniha byla úspěšně odstraněna."));

		return "";
	}

	public String actionUpdate() throws ParseException {

		//exemplar.setYear(formatter.parse(rok_vydania));

		try {
			exemplarMgr.save(exemplar);
		} catch (javax.ejb.EJBException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Změny se nepodařilo uložit, zkuste to prosím později."));
			return "";
		}

		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Změny byly úspěšně uloženy."));

		return "";
	}

	public String actionInsert() throws ParseException {
		Collection<Author> novy_autorovia;

		novy_autorovia = (authorMgr.find(nazov_1_autora));



		try {
			exemplarMgr.save(exemplar);


		} catch (javax.ejb.EJBException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("User wasn't created. Please try again later (or try to change permit number)."));
			return "";
		}

		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Title was successfully created."));


		/* 
		exemplarHasAuthor.setIdexemplar(exemplar.getIdexemplar());
		exemplarHasAuthor.setIdauthor(autor.getIdauthor());
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("+exemplarHasAuthor.getIdauthor();"+exemplarHasAuthor.getIdauthor()));
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("autor.toString();"+exemplarHasAuthor.getIdexemplar()));
		 */
		return "edit";
	}
}
