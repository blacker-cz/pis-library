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
public class PublisherBean {
    @EJB
    private PublisherManager publisherMgr;
    private Publisher publisher;
    private UIDataTable listTable;
    
    public PublisherBean() {
        publisher = new Publisher();
    }
    
    public Publisher getPublisher() {
        return publisher;
    }
    
    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }
    
    public List<Publisher> getPublishers() {
        return publisherMgr.findAll();
    }
    
    public UIDataTable getListTable() {
        return listTable;
    }
    
    public void setListTable(UIDataTable table) {
        this.listTable = table;
    }
    
    public String actionEdit() {
    	setPublisher((Publisher) listTable.getRowData());
    	return "edit";
    }
    
    public String actionDelete() {
    	Publisher selected = (Publisher) listTable.getRowData();
    	publisherMgr.remove(selected);
    	return "delete";
    }
}
