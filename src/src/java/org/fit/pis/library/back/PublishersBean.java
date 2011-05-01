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

public class PublishersBean {
	@EJB
	private PublisherManager publisherMgr;  
	private Publisher publisher;
        
       
	private UIDataTable listTable;
	private UIDataTable exemplarListTable;
	
	@ManagedProperty(value="#{authenticationBean}")
	private AuthenticationBean authBean;
	
	// variables used for filtering table
	private String filter_name;

        //Iterator iterator = autorovia.iterator();
	

	/** Creates a new instance of ManageUsersBean */
	public PublishersBean() {
		clearFilter();
	}

	/**
	 * Clear filter variables
	 */
	private void clearFilter() {
		publisher = new Publisher();
		// set empty filtering
		filter_name = "";	
	}
	
	/**
	 * Get user
	 * @return 
	 */
	public Publisher getPublisher() {
		return publisher;
	}

	/**
	 * Set user
	 * @param user 
	 */
	public void setPublisher(Publisher publisher) {
		this.publisher = publisher;
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
	public List<Publisher> getPublishers() {
		// switch dates	
		return publisherMgr.find(filter_name);
                
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
	 * Search publishers
	 * @return 
	 */
	public String actionSearchPublishers() {
		return "searchPublishers";
	}
	
	/**
	 * Just view catalog
	 * @return 
	 */
	public String actionViewPublishers() {
		clearFilter();	
		return "viewPublishers";
	}
	
	/**
	 * Publisher detail
	 * @return 
	 */
	public String actionDetail() {
		setPublisher((Publisher) listTable.getRowData());
		return "detail";
	}

	/**
	 * Publisher cancel detail
	 * @return 
	 */
	public String actionCancelDetail() {
		publisher = null;	
		return "cancelDetail";
	}
		
	/**
	 * Edit user
	 * @return "edit"
	 */
        public String actionEdit() {	      
                setPublisher((Publisher) listTable.getRowData());
                return "edit";
	}
        
        
	/**
	 * Action for redirecting to new user page
	 * @return 
	 */
	public String actionCreateNew() {
                setPublisher(new Publisher());
		return "new";
	}
        
        public String actionDelete() {
                  
		Publisher selected = (Publisher) listTable.getRowData();
		try {
			publisherMgr.remove(selected);
		} catch (javax.ejb.EJBException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Nepodařilo se odstranit knihu, zkuste to prosím později."));
			return "";
		}
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Kniha byla úspěšně odstraněna."));
		return "";
	}
        
        public String actionUpdate() {
                
                try {
			publisherMgr.save(publisher);
		} catch (javax.ejb.EJBException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Změny se nepodařilo uložit, zkuste to prosím později."));
			return "";
		}

		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Změny byly úspěšně uloženy."));

		return "";
        }
                
        
	public String actionInsert() {	
		try {
			publisherMgr.save(publisher);
		} catch(javax.ejb.EJBException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("User wasn't created. Please try again later (or try to change permit number)."));
			return "";
		}

		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Title was successfully created."));
		
		return "edit";
	}
}
