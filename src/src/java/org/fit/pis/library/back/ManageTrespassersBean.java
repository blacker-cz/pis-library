/*
 */
package org.fit.pis.library.back;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import org.fit.pis.library.data.*;
import org.richfaces.component.UIDataTable;

/**
 *
 * @author Vojtěch Sysel <xsysel03@setud.fit.vutbr.cz>
 */
@ManagedBean
@SessionScoped
public class ManageTrespassersBean {

	@EJB
	private BorrowManager borrowMgr;
	private Borrow borrow;
	private UIDataTable listTable;
	
	@ManagedProperty(value="#{authenticationBean}")
	private AuthenticationBean authBean;
	
	/** Creates a new instance of ManageUsersBean */
	public ManageTrespassersBean() {
		borrow = new Borrow();
	}

	/**
	 * Get user
	 * @return 
	 */
	public Borrow getBorrow() {
		return borrow;
	}

	/**
	 * Set user
	 * @param user 
	 */
	public void setBorrow(Borrow borrow) {
		this.borrow = borrow;
	}

	/**
	 * Authentication bean setter
	 * @param authBean 
	 */
	public void setAuthBean(AuthenticationBean authBean) {
		this.authBean = authBean;
	}

	// <editor-fold defaultstate="collapsed" desc="Filter getters and setters">
	// </editor-fold>
	/**
	 * Get list of users
	 * @return 
	 */
	public List<Borrow> getTresspassers() {
		ArrayList <Borrow> trespassers = new ArrayList<Borrow>();
		// filter expired
		for (Borrow b : borrowMgr.findNotReturned()) {
			// expired
			if (b.getIsBorrowExpired()) {
				trespassers.add(b);
			}
		}
		
		return trespassers;
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
	 * Display table
	 * @return 
	 */
	public String actionDisplay() {
		listTable = null;	// force rebuild of table 
		return "view_trespassers";
	}
}
