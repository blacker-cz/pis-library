/*
 */
package org.fit.pis.library.back;

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
 * @author Lukáš Černý <cerny.l@gmail.com>
 */
@ManagedBean
@SessionScoped
public class ManageExemplarsBean {

	@EJB
	private ExemplarManager exemplarMgr;
	@EJB
	private BookManager bookMgr;
	
	@ManagedProperty(value="#{manageBooksBean.idbook}")
	private Integer idbook;
	
	private Exemplar exemplar;
	
	private UIDataTable listTable;

	/** Creates a new instance of ManageUsersBean */
	public ManageExemplarsBean() {
		this.exemplar = null;
	}

	/**
	 * Get user
	 * @return 
	 */
	public Exemplar getExemplar() {
		return exemplar;
	}

	public void setExemplar(Exemplar exemplar) {
		this.exemplar = exemplar;
	}

    public List<Exemplar> getExemplars() {
        return exemplarMgr.findByIdbook(idbook);
    }
	
	public Book getBook() {
		return bookMgr.findByIdbook(idbook);
	}

	public void setIdbook(Integer idbook) {
		this.idbook = idbook;
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
		setExemplar((Exemplar) listTable.getRowData());
		return "edit";
	}

	public String actionCreateNew() {
		setExemplar(new Exemplar());
		getExemplar().setAquired(new Date(System.currentTimeMillis()));
		return "new";
	}

	public String actionDelete() {

		Exemplar selected = (Exemplar) listTable.getRowData();
		if (selected.getIsBorrowed()) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Výtisk nelze odstranit, protože je vypůjčený."));
			return "";
		}

		try {
			exemplarMgr.remove(selected);
		} catch (javax.ejb.EJBException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Nepodařilo se odstranit výtisk, zkuste to prosím později."));
			return "";
		}
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Výtisk byl úspěšně odstraněn."));
		return "";
	}

	public String actionUpdate() {

		exemplar.setState(0);
		
		try {
			exemplarMgr.save(exemplar);
		} catch (javax.ejb.EJBException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Změny se nepodařilo uložit, zkuste to prosím později."));
			return "";
		}

		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Změny byly úspěšně uloženy."));

		return "";
	}

	public String actionInsert() {
		try {
			exemplar.setBook(this.getBook());
			exemplar.setState(0);
			exemplarMgr.save(exemplar);
		} catch (javax.ejb.EJBException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Výtisk se nepodařilo vytvořit, zkuste to prosím později."));
			return "";
		}

		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Výtisk byl úspěšně vytvořen."));

		return "edit";
	}
}
