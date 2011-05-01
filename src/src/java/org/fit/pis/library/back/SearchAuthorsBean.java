/*
 */
package org.fit.pis.library.back;

import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
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
public class SearchAuthorsBean {

	@EJB
	private AuthorManager authorMgr;
	private Author author;
	private UIDataTable listTable;
	// variables used for filtering table
	private String filter_name;

	/** Creates a new instance of ManageUsersBean */
	public SearchAuthorsBean() {
		filter_name = "";
	}

	/**
	 * Get user
	 * @return 
	 */
	public Author getAuthor() {
		return author;
	}

	/**
	 * Set user
	 * @param user 
	 */
	public void setAuthor(Author author) {
		this.author = author;
	}

	public String getFilter_name() {
		return filter_name;
	}

	public void setFilter_name(String filter_name) {
		this.filter_name = filter_name;
	}

	public List<Author> getFilteredAuthors() {
		return authorMgr.find(filter_name);
	}

	public List<Author> getAuthors() {
		return authorMgr.findAll();
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
	 * Edit user
	 * @return "edit"
	 */
	public String actionEdit() {
		setAuthor((Author) listTable.getRowData());
		return "edit";
	}

	/**
	 * Action for redirecting to new user page
	 * @return 
	 */
	public String actionCreateNew() {
		setAuthor(new Author());
		return "new";
	}

	public String actionDelete() {
		Author selected = (Author) listTable.getRowData();

		if(!selected.getBooksCollection().isEmpty()) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Nelze odstranit autora, který má přiřazeny knihy."));
			return "";
		}

		try {
			authorMgr.remove(selected);
		} catch (javax.ejb.EJBException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Nepodařilo se odstranit autora, zkuste to prosím později."));
			return "";
		}
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Autor byl úspěšně odstraněn."));
		return "";
	}

	public String actionUpdate() {

		try {
			authorMgr.save(author);
		} catch (javax.ejb.EJBException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Změny se nepodařilo uložit, zkuste to prosím později."));
			return "";
		}

		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Změny byly úspěšně uloženy."));

		return "";
	}

	public String actionInsert() {
		try {
			authorMgr.save(author);
		} catch (javax.ejb.EJBException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Autora se nepodařilo vložit, zkuste to prosím později."));
			return "";
		}

		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Autor byl úspěšně vytvořen."));

		return "edit";
	}
}
