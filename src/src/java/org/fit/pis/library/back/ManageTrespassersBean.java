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
	
	// variables used for filtering table
	private String filter_forename;
	private String filter_surname;
	private String filter_email;
	private String filter_permitNumber;
	
	/** Creates a new instance of ManageUsersBean */
	public ManageTrespassersBean() {
		borrow = new Borrow();

		// set empty filtering
		filter_forename = "";
		filter_surname = "";
		filter_email = "";
		filter_permitNumber = "";
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
	public String getFilter_email() {
		return filter_email;
	}

	public void setFilter_email(String filter_email) {
		this.filter_email = filter_email;
	}

	public String getFilter_forename() {
		return filter_forename;
	}

	public void setFilter_forename(String filter_forename) {
		this.filter_forename = filter_forename;
	}

	public String getFilter_permitNumber() {
		return filter_permitNumber;
	}

	public void setFilter_permitNumber(String filter_permitNumber) {
		this.filter_permitNumber = filter_permitNumber;
	}

	public String getFilter_surname() {
		return filter_surname;
	}

	public void setFilter_surname(String filter_surname) {
		this.filter_surname = filter_surname;
	}

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
