/**
 * 
 */
package org.fit.pis.library.back;

import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.html.HtmlDataTable;
import org.richfaces.component.UIDataTable;

import org.fit.pis.library.data.*;

/**
 *
 * @author Vojtěch Sysel <xsysel03@setud.fit.vutbr.cz>
 */
@ManagedBean
@SessionScoped
public class GenreBean {
    @EJB
    private GenreManager genreMgr;
    private Genre genre;
    private UIDataTable listTable;
    
    public GenreBean() {
        genre = new Genre();
    }
    
    public Genre getGenre() {
        return genre;
    }
    
    public void setGenre(Genre genre) {
        this.genre = genre;
    }
    
    public List<Genre> getGenres() {
        return genreMgr.findAll();
    }
    
    public UIDataTable getListTable() {
        return listTable;
    }
    
    public void setListTable(UIDataTable table) {
        this.listTable = table;
    }
    
    public String actionEdit() {
    	setGenre((Genre) listTable.getRowData());
    	return "edit";
    }
    
    public String actionDelete() {
    	Genre selected = (Genre) listTable.getRowData();
    	genreMgr.remove(selected);
    	return "delete";
    }
}
