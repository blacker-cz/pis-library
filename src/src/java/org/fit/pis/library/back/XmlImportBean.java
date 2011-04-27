/*
 */
package org.fit.pis.library.back;

import java.text.ParseException;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.xml.bind.DatatypeConverter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.fit.pis.library.data.*;
import org.richfaces.event.FileUploadEvent;
import org.richfaces.model.UploadedFile;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author Lukáš Černý <cerny.l@gmail.com>
 */
@ManagedBean
@RequestScoped
public class XmlImportBean {

	@EJB
	private UserManager userMgr;
	@EJB
	private BookManager bookMgr;
	@EJB
	private AuthorManager authorMgr;
	@EJB
	private BookingManager bookingMgr;
	@EJB
	private ExemplarManager exemplarMgr;
	@EJB
	private GenreManager genreMgr;
	@EJB
	private PublisherManager publisherMgr;
	@EJB
	private BorrowManager borrowMgr;

	/** Creates a new instance of XmlImportBean */
	public XmlImportBean() {
	}

	/**
	 * File upload listener
	 * @param event
	 * @throws Exception 
	 */
	public void listener(FileUploadEvent event) throws Exception {
		UploadedFile file = event.getUploadedFile();

		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(file.getInputStream());
			doc.getDocumentElement().normalize();

			processUsers(doc.getElementsByTagName("users"));
			processAuthors(doc.getElementsByTagName("authors"));
			processPublishers(doc.getElementsByTagName("publishers"));
			processGenres(doc.getElementsByTagName("genres"));

		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Import se nezdařil."));
			e.printStackTrace(System.out);

			return;
		}

		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Import se nezdařil."));
	}

	/**
	 * Import users
	 * @param nodes list of users nodes (wrapper node, thus only one)
	 * @return 
	 */
	private Boolean processUsers(NodeList nodes) {
		NodeList users = nodes.item(0).getChildNodes();

		for (int i = 0; i < users.getLength(); i++) {
			if (users.item(i).getNodeType() == Node.ELEMENT_NODE) {
				Element userNode = (Element) users.item(i);

				User user = new User();

				user.setIduser(Integer.parseInt(getTagValue("iduser", userNode)));
				user.setForename(getTagValue("forename", userNode));
				user.setSurname(getTagValue("surname", userNode));
				user.setPermitNumber(getTagValue("permitNumber", userNode));
				user.setAddress(getTagValue("address", userNode));
				user.setEmail(getTagValue("email", userNode));
				user.setRegistered(DatatypeConverter.parseDateTime(getTagValue("registered", userNode)).getTime());
				user.setExpire(DatatypeConverter.parseDateTime(getTagValue("expire", userNode)).getTime());
				user.setPassword(getTagValue("password", userNode));
				user.setLevel(getTagValue("level", userNode));
				
				try {
					userMgr.Save(user);
				} catch (EJBException ex) {
					ex.printStackTrace(System.out);
				}
			}
		}
		return true;
	}

	/**
	 * Import authors
	 * @param nodes
	 * @return 
	 */
	private Boolean processAuthors(NodeList nodes) {
		NodeList authors = nodes.item(0).getChildNodes();

		for (int i = 0; i < authors.getLength(); i++) {
			if (authors.item(i).getNodeType() == Node.ELEMENT_NODE) {
				Element authorNode = (Element) authors.item(i);

				Author author = new Author();
				
				author.setIdauthor(Integer.parseInt(getTagValue("idauthor", authorNode)));
				author.setName(getTagValue("name", authorNode));
				
				try {
					authorMgr.save(author);
				} catch (EJBException ex) {
					ex.printStackTrace(System.out);
				}
			}
		}
		return true;
	}
	
	/**
	 * Import publishers
	 * @param nodes
	 * @return 
	 */
	private Boolean processPublishers(NodeList nodes) {
		NodeList publishers = nodes.item(0).getChildNodes();

		for (int i = 0; i < publishers.getLength(); i++) {
			if (publishers.item(i).getNodeType() == Node.ELEMENT_NODE) {
				Element publisherNode = (Element) publishers.item(i);

				Publisher publisher = new Publisher();
				
				publisher.setIdpublisher(Integer.parseInt(getTagValue("idpublisher", publisherNode)));
				publisher.setName(getTagValue("name", publisherNode));
				publisher.setAddress(getTagValue("address", publisherNode));
				
				try {
					publisherMgr.save(publisher);
				} catch (EJBException ex) {
					ex.printStackTrace(System.out);
				}
			}
		}
		return true;
	}
	
	/**
	 * Import genres
	 * @param nodes
	 * @return 
	 */
	private Boolean processGenres(NodeList nodes) {
		NodeList genres = nodes.item(0).getChildNodes();

		for (int i = 0; i < genres.getLength(); i++) {
			if (genres.item(i).getNodeType() == Node.ELEMENT_NODE) {
				Element genreNode = (Element) genres.item(i);

				Genre genre = new Genre();
				
				genre.setIdgenre(Integer.parseInt(getTagValue("idgenre", genreNode)));
				genre.setName(getTagValue("name", genreNode));
				
				try {
					genreMgr.save(genre);
				} catch (EJBException ex) {
					ex.printStackTrace(System.out);
				}
			}
		}
		return true;
	}
	
	/**
	 * Get tag value
	 * @param sTag
	 * @param eElement
	 * @return string or null
	 */
	private static String getTagValue(String sTag, Element eElement) {
		NodeList nlList = eElement.getElementsByTagName(sTag).item(0).getChildNodes();
		
		if(nlList.getLength() == 0)
			return null;
		
		Node nValue = (Node) nlList.item(0);

		return nValue.getNodeValue();
	}
}
