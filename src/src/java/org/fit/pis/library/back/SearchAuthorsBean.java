/*
 */
package org.fit.pis.library.back;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
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

public class SearchAuthorsBean {
	@EJB
	private AuthorManager authorMgr;  
	private Author author;
        
       
	private UIDataTable listTable;
	private UIDataTable exemplarListTable;
	
	@ManagedProperty(value="#{authenticationBean}")
	private AuthenticationBean authBean;
	
	// variables used for filtering table
	private String filter_name;

        //Iterator iterator = autorovia.iterator();
	

	/** Creates a new instance of ManageUsersBean */
	public SearchAuthorsBean() {
		clearFilter();
	}

	/**
	 * Clear filter variables
	 */
	private void clearFilter() {
		author = new Author();
		// set empty filtering
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

	/**
	 * Get list of users
	 * @return 
	 */
	public List<Author> getAuthors() {
		// switch dates	
		return authorMgr.find(filter_name);
                
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
	 * Search authors
	 * @return 
	 */
	public String actionSearchAuthors() {
		return "searchAuthors";
	}
	
	/**
	 * Just view catalog
	 * @return 
	 */
	public String actionViewAuthors() {
		clearFilter();	
		return "viewAuthors";
	}
	
	/**
	 * Author detail
	 * @return 
	 */
	public String actionDetail() {
		setAuthor((Author) listTable.getRowData());
		return "detail";
	}

	/**
	 * Author cancel detail
	 * @return 
	 */
	public String actionCancelDetail() {
		author = null;	
		return "cancelDetail";
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
		try {
			authorMgr.remove(selected);
		} catch (javax.ejb.EJBException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Nepodařilo se odstranit knihu, zkuste to prosím později."));
			return "";
		}
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Kniha byla úspěšně odstraněna."));
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
		} catch(javax.ejb.EJBException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("User wasn't created. Please try again later (or try to change permit number)."));
			return "";
		}

		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Title was successfully created."));
		
		return "edit";
	}
}
