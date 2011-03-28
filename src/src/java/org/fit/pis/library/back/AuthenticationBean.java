/*
 */
package org.fit.pis.library.back;

import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.fit.pis.library.data.User;
import org.fit.pis.library.data.UserManager;

/**
 * Authentication (identity) bean
 * @author Lukáš Černý <cerny.l@gmail.com>
 */
public class AuthenticationBean implements Serializable {

	private boolean authorized;
	private Integer iduser;
	private String forename;
	private String surname;
	private String permitNumber;
	private String address;
	private String phone;
	private String email;
	private String password;
	private String level;
	@EJB
	private UserManager userManager;

	/** Creates a new instance of AuthenticationBean */
	public AuthenticationBean() {
		authorized = false;
		level = "guest";
	}

	public Integer getIduser() {
		return iduser;
	}

	public String getForename() {
		return forename;
	}

	public void setForename(String forename) {
		this.forename = forename;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLevel() {
		return level;
	}

	public void setPermitNumber(String permitNumber) {
		this.permitNumber = permitNumber;
	}

	public String getPermitNumber() {
		return permitNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * Logout user
	 * @return 
	 */
	public String actionLogout()
	{
		this.authorized = false;
		
		// @todo: erase all internal variables
		
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Logout was succesful"));
		return "logout";
	}
	
	/**
	 * Check if user is logged in
	 * @return true if user is logged in; false otherwise
	 */
	public boolean isLoggedIn() {
		return authorized;
	}

	/**
	 * Check if user is in specific role
	 * @param role user role
	 * @return true if user is in role; false otherwise
	 */
	public boolean isInRole(String role) {
		return level.compareToIgnoreCase(role) == 0;
	}

	/**
	 * Log in user.
	 * @return view code "login" or "failed"
	 */
	public String actionLogIn() {

		// guest user login
		if (permitNumber.compareToIgnoreCase("guest") == 0 && password.compareToIgnoreCase("guest") == 0) {
			this.authorized = true;
			this.iduser = 0;
			this.forename = "";
			this.surname = "Guest";
			this.permitNumber = "guest";
			this.address = "";
			this.phone = "";
			this.email = "";
			this.level = "guest";
			
			return "login";
		}

		// Normal user login
		User user = userManager.findByPermitNumber(permitNumber);

		if (user != null && password.compareTo(user.getPassword()) == 0) {
			this.iduser = user.getIduser();
			this.permitNumber = user.getPermitNumber();
			this.address = user.getAddress();
			this.forename = user.getForename();
			this.surname = user.getSurname();
			this.address = user.getAddress();
			this.phone = user.getPhone();
			this.email = user.getEmail();
			this.level = user.getLevel();

			this.authorized = true;
			return "login";
		}

		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Invalid login"));
		return "failed";
	}
}
