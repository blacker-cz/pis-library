/*
 */
package org.fit.pis.library.back;

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
import org.fit.pis.library.data.*;
import org.richfaces.component.UIDataTable;

/**
 *
 * @author Vojtěch Sysel <xsysel03@setud.fit.vutbr.cz>
 */
@ManagedBean
@SessionScoped
public class ManageBookingBean {
	@EJB
	private BookingManager bookingMgr;
	private Booking	booking;
	private UIDataTable listTable;
	
	@ManagedProperty(value="#{authenticationBean}")
	private AuthenticationBean authBean;
	
	public ManageBookingBean() {
		booking = new Booking();
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
	 * Get list of my Bookings
	 * @return 
	 */
	public Collection<Booking> getMyBooking() {
		int idUser = authBean.getIduser();
		User user = new User(idUser);
		return bookingMgr.find(user); 
	}
	
	/**
	 * Get list of Bookings
	 * @return 
	 */
	public Collection<Booking> getBooking(User user) {
		return bookingMgr.find(user); 
	}
	
	/**
	 * Display table
	 * @return 
	 */
	public String actionDisplay() {
		listTable = null;	// force rebuild of table 
		return "viewMyBooking";
	}
	
	/**
	 * Cancel booking
	 * @return 
	 */
	public String actionCancelBooking() {
		Booking selected = (Booking) listTable.getRowData();
		
		try {
			bookingMgr.Remove(selected);
		} catch (javax.ejb.EJBException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Changes couldn't be saved. Please try again later."));
			return "viewMyBooking";
		}

		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Changes were successfully saved."));
		
		// clear list table
		listTable = null;
		
		return "viewMyBooking";
	}
}
