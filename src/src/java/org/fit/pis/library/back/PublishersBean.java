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
public class PublishersBean {

	@EJB
	private PublisherManager publisherMgr;
	private Publisher publisher;
	private UIDataTable listTable;
	
	// variables used for filtering table
	private String filter_name;
	private String filter_address;

	//Iterator iterator = autorovia.iterator();
	/** Creates a new instance of ManageUsersBean */
	public PublishersBean() {
		filter_name = "";
		filter_address = "";
		this.publisher = null;
	}

	/**
	 * Get user
	 * @return 
	 */
	public Publisher getPublisher() {
		return publisher;
	}

	public void setPublisher(Publisher publisher) {
		this.publisher = publisher;
	}

	public String getFilter_name() {
		return filter_name;
	}

	public void setFilter_name(String filter_name) {
		this.filter_name = filter_name;
	}

	public String getFilter_address() {
		return filter_address;
	}

	public void setFilter_address(String filter_address) {
		this.filter_address = filter_address;
	}
	
	public List<Publisher> getFilteredPublishers() {
		return publisherMgr.find(filter_name, filter_address);

	}

    public List<Publisher> getPublishers() {
        return publisherMgr.findAll();
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

	public String actionEdit() {
		setPublisher((Publisher) listTable.getRowData());
		return "edit";
	}

	public String actionCreateNew() {
		setPublisher(new Publisher());
		return "new";
	}

	public String actionDelete() {

		Publisher selected = (Publisher) listTable.getRowData();
		if (!selected.getBooksCollection().isEmpty()) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Nakladatelství nelze odstranit, protože jsou na něj navázány knihy."));
			return "";
		}

		try {
			publisherMgr.remove(selected);
		} catch (javax.ejb.EJBException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Nepodařilo se odstranit nakladatele, zkuste to prosím později."));
			return "";
		}
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Nakladatel byl úspěšně odstraněn."));
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
		} catch (javax.ejb.EJBException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Nakladatelství se nepodařilo vytvořit, zkuste to prosím později."));
			return "";
		}

		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Nakladatelství bylo úspěšně vytvořeno."));

		return "edit";
	}
}
