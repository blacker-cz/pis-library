/*
 */
package org.fit.pis.library.back;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
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
public class ManageBorrowsBean {
	@EJB
	private BorrowManager borrowMgr;
	private Borrow borrow;
	@EJB
	private UserManager userMgr;
	private User user;
	private UIDataTable listTable;
	
	@ManagedProperty(value="#{authenticationBean}")
	private AuthenticationBean authBean;
	
	/**
	 * Constructor
	 */
	public ManageBorrowsBean() {
		borrow = new Borrow();
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
		
//		user = userMgr.findByIduser(idUser);
//		
//		return user.getBorrowCollection();
	}
	
	/**
	 * Prolongate 
	 * @return 
	 */
	public String actionProlongate() {
		Borrow selected = (Borrow) listTable.getRowData();
		
		return "";
	}
}