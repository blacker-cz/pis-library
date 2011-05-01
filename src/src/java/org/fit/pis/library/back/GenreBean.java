/**
 * 
 */
package org.fit.pis.library.back;

import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.richfaces.component.UIDataTable;

import org.fit.pis.library.data.*;

/**
 *
 * @author Lukáš Černý <cerny.l@gmail.com>
 */
@ManagedBean
@SessionScoped
public class GenreBean {
    @EJB
    private GenreManager genreMgr;
    private Genre genre;
    private UIDataTable listTable;
	// variables used for filtering table
	private String filter_name;

	/** Creates a new instance of ManageUsersBean */
	public GenreBean() {
		filter_name = "";
	}

	public Genre getGenre() {
		return genre;
	}

	public void setGenre(Genre genre) {
		this.genre = genre;
	}

	public String getFilter_name() {
		return filter_name;
	}

	public void setFilter_name(String filter_name) {
		this.filter_name = filter_name;
	}

	public List<Genre> getGenres() {
		// switch dates	
		return genreMgr.find(filter_name);
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
	 * Edit user
	 * @return "edit"
	 */
	public String actionEdit() {
		setGenre((Genre) listTable.getRowData());
		return "edit";
	}

	/**
	 * Action for redirecting to new user page
	 * @return 
	 */
	public String actionCreateNew() {
		setGenre(new Genre());
		return "new";
	}

	public String actionDelete() {
		Genre selected = (Genre) listTable.getRowData();

		if(!selected.getBooksCollection().isEmpty()) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Nelze odstranit žánr, který má přiřazeny tituly."));
			return "";
		}

		try {
			genreMgr.remove(selected);
		} catch (javax.ejb.EJBException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Nepodařilo se odstranit žánr, zkuste to prosím později."));
			return "";
		}
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Žánr byl úspěšně odstraněn."));
		return "";
	}

	public String actionUpdate() {

		try {
			genreMgr.save(genre);
		} catch (javax.ejb.EJBException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Změny se nepodařilo uložit, zkuste to prosím později."));
			return "";
		}

		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Změny byly úspěšně uloženy."));

		return "";
	}

	public String actionInsert() {
		try {
			genreMgr.save(genre);
		} catch (javax.ejb.EJBException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Žánr se nepodařilo vložit, zkuste to prosím později."));
			return "";
		}

		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Žánr byl úspěšně vytvořen."));

		return "edit";
	}
}
