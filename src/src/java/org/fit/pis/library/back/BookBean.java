/**
 * 
 */

package org.fit.pis.library.back;

import java.util.List;

// import java.io.Serializable;
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
public class BookBean /*implements Serializable*/ {
    @EJB
    private BookManager bookMgr;
    private Book book;
    private UIDataTable listTable;
    
    public BookBean() {
        book = new Book();
    }
    
    public Book getBook() {
        return book;
    }
    
    public void setBook(Book book) {
        this.book = book;
    }
    
    public List<Book> getBooks() {
        return bookMgr.findAll();
    }
    
    public UIDataTable getListTable() {
        return listTable;
    }
    
    public void setListTable(UIDataTable table) {
        this.listTable = table;
    }
    
    public String actionEdit() {
    	setBook((Book) listTable.getRowData());
    	return "edit";
    }
    
    public String actionDelete() {
    	Book selected = (Book) listTable.getRowData();
    	bookMgr.remove(selected);
    	return "delete";
    }
}
