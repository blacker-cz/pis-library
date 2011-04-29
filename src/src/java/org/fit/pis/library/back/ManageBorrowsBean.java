/*
 */
package org.fit.pis.library.back;

import java.util.Collection;
import java.util.Date;
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
public class ManageBorrowsBean {
	@EJB
	private BorrowManager borrowMgr;
	private Borrow borrow;
	private UIDataTable listTable;
	
	@ManagedProperty(value="#{authenticationBean}")
	private AuthenticationBean authBean;
	
	private User user;
	
	/**
	 * Constructor
	 */
	public ManageBorrowsBean() {
		borrow = new Borrow();
		user = null;
	}
	
	/**
	 * Authentication bean setter
	 * @param authBean 
	 */
	public void setAuthBean(AuthenticationBean authBean) {
		this.authBean = authBean;
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
	 * Get list of my Borrows
	 * @return 
	 */
	public Collection<Borrow> getMyBorrows() {
		int idUser = authBean.getIduser();
		User user = new User(idUser);
		return borrowMgr.find(user); 
	}
	
	/**
	 * Get list of borrows for User
	 * @param user
	 * @return 
	 */
	public Collection<Borrow> getBorrows(User user) {
		this.user = user;
		return borrowMgr.find(user); 
	}
	
	/**
	 * Display table
	 * @return 
	 */
	public String actionDisplay() {
		listTable = null;	// force rebuild of table 
		return "viewMyBorrows";
	}
	
	/**
	 * Prolongate 
	 * @return 
	 */
	public String actionProlongate() {
		Borrow selected = (Borrow) listTable.getRowData();
		
		// add prolongation count
		int prolongationCount = selected.getProlongations();
		prolongationCount++;
		selected.setProlongations(prolongationCount);
		
		try {
			borrowMgr.Save(selected);
		} catch (javax.ejb.EJBException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Changes couldn't be saved. Please try again later."));
			return "myBorrowsProlongate";
		}

		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Changes were successfully saved."));
		
		// clear list table
		listTable = null;
		
		return "myBorrowsProlongate";
	}
	
	/**
	 * Return selected borrow
	 * @return 
	 */
	public String actionReturn() {
		Borrow selected = (Borrow) listTable.getRowData();
		
		selected.setReturned(new Date(System.currentTimeMillis()));
		
		try {
			borrowMgr.Save(selected);
		} catch (javax.ejb.EJBException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Changes couldn't be saved. Please try again later."));
			return "borrowsReturned";
		}
		
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Book successfully returned."));
		
		return "borrowsReturned";
	}
	
	/**
	 * Save borrows
	 * @param user
	 * @return 
	 */
	public String actionSaveBorrows(User user) {
		this.user = null;
		
		return "displayReaders";
	}
	
	/**
	 * New borrow from catalog
	 * @return 
	 */
	public String actionCreateNew(User user) {
		this.user = user;
		
		return "newBorrowCatalog";
	}
}