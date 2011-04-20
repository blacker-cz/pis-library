/*
 */
package org.fit.pis.library.back;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.fit.pis.library.data.User;
import org.fit.pis.library.data.UserManager;
import org.richfaces.component.UIDataTable;

/**
 *
 * @author Lukáš Černý <cerny.l@gmail.com>
 */
@ManagedBean
@SessionScoped
public class ManageUsersBean {

	@EJB
	private UserManager userMgr;
	private User user;
	private UIDataTable listTable;
	
	@ManagedProperty(value="#{authenticationBean}")
	private AuthenticationBean authBean;
	
	// variables used for filtering table
	private String filter_forename;
	private String filter_surname;
	private String filter_email;
	private String filter_permitNumber;
	private String filter_level;
	
	/** Creates a new instance of ManageUsersBean */
	public ManageUsersBean() {
		user = new User();

		// set empty filtering
		filter_forename = "";
		filter_surname = "";
		filter_email = "";
		filter_permitNumber = "";
		filter_level = "";
	}

	/**
	 * Get user
	 * @return 
	 */
	public User getUser() {
		return user;
	}

	/**
	 * Set user
	 * @param user 
	 */
	public void setUser(User user) {
		this.user = user;
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

	public String getFilter_level() {
		return filter_level;
	}

	public void setFilter_level(String filter_level) {
		if (filter_level.compareTo("all") == 0) {
			this.filter_level = "";
		} else {
			this.filter_level = filter_level;
		}
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
	public List<User> getUsers() {
		return userMgr.find(filter_permitNumber, filter_forename, filter_surname, filter_email, filter_level);
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
		setUser((User) listTable.getRowData());
		return "edit";
	}

	/**
	 * Action for redirecting to new user page
	 * @return 
	 */
	public String actionCreateNew() {
		setUser(new User());
		getUser().setExpire(new Date(System.currentTimeMillis() + 31536000000L));

		return "new";
	}

	public String actionInsert() {
		user.setRegistered(new Date(System.currentTimeMillis()));

		user.setPassword(ManageUsersBean.generatePassword());
		
		try {
			userMgr.Save(user);
		} catch(javax.ejb.EJBException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("User wasn't created. Please try again later (or try to change permit number)."));
			return "";
		}

		Mailer mailer = new Mailer();
		
		if(!mailer.send(user.getEmail(), "Knihovna: Váš nový účet", "Byl Vám vytvořen účet v naší knihovně.<br />" +
				"Pro přístup do systému použijte následující údaje: <br />" + 
				"Číslo průkazky: " + user.getPermitNumber() + "<br />" +
				"Heslo: " + user.getPassword())) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Nepodařilo se odeslat email s potvrzením registrace a heslem."));
		}

		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("User was successfully created."));
		
		return "edit";
	}

	/**
	 * Update user data
	 * @return self
	 */
	public String actionUpdate() {
		try {
			userMgr.Save(user);
		} catch (javax.ejb.EJBException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Changes couldn't be saved. Please try again later."));
			return "";
		}

		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Changes were successfully saved."));

		return "";
	}

	/**
	 * Delete user
	 * @return self
	 */
	public String actionDelete() {
		User selected = (User) listTable.getRowData();
		try {
			userMgr.Remove(selected);
		} catch (javax.ejb.EJBException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Couldn't delete user. Please try again later."));
			return "";
		}

		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("User was successfully deleted."));

		return "";
	}
	
	/**
	 * Generate new password for user.
	 * @return 
	 */
	public String actionGenerateNewPassword() {
		User selected = (User) listTable.getRowData();

		selected.setPassword(ManageUsersBean.generatePassword());

		try {
			userMgr.Save(selected);
		} catch (javax.ejb.EJBException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Změny se nepodařilo uložit zkuste to prosím později."));
			return "";
		}

		Mailer mailer = new Mailer();
		
		if(!mailer.send(selected.getEmail(), "Knihovna: Nové heslo", "Bylo Vám vygenerováno nové heslo.<br />" +
				"Pro přístup do systému použijte následující údaje: <br />" + 
				"Číslo průkazky: " + selected.getIduser() + "<br />" +
				"Heslo: " + selected.getPassword())) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Nepodařilo se odeslat email s potvrzením registrace a heslem."));
		}

		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Nové heslo bylo úspěšně vygenerováno."));

		return "";
	}
	
	/**
	 * Generate password
	 * @return 
	 */
	public static String generatePassword() {
		// generate password
		String hashtext;
		try {
			MessageDigest m = MessageDigest.getInstance("MD5");
			m.reset();
			m.update(Long.toString(System.currentTimeMillis()).getBytes());
			byte[] digest = m.digest();
			BigInteger bigInt = new BigInteger(1, digest);
			hashtext = bigInt.toString(16);

			while (hashtext.length() < 32) {
				hashtext = "0" + hashtext;
			}
		} catch (NoSuchAlgorithmException e) {
			hashtext = Long.toString(System.currentTimeMillis(), 12);
		}
		
		// for debugging purposes all passwords are set to: heslo
		return "heslo";
		
		//return hashtext.substring(1, 8);
	}
}
