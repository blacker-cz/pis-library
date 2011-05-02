/*
 */
package org.fit.pis.library.back;

import java.util.ArrayList;
import java.util.List;
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
			processBooks(doc.getElementsByTagName("books"));
			processExemplars(doc.getElementsByTagName("exemplars"));
			processBookings(doc.getElementsByTagName("bookings"));
			processBorrows(doc.getElementsByTagName("borrows"));

			userMgr.flush();
			bookMgr.flush();
			authorMgr.flush();
			bookingMgr.flush();
			exemplarMgr.flush();
			genreMgr.flush();
			publisherMgr.flush();
			borrowMgr.flush();

			userMgr.clear();
			bookMgr.clear();
			authorMgr.clear();
			bookingMgr.clear();
			exemplarMgr.clear();
			genreMgr.clear();
			publisherMgr.clear();
			borrowMgr.clear();

		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Import se nezdařil."));
			e.printStackTrace(System.out);

			return;
		}

		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Import proběhl úspěšně."));
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
	 * Import books
	 * @param nodes
	 * @return 
	 */
	private Boolean processBooks(NodeList nodes) {
		NodeList books = nodes.item(0).getChildNodes();

		for (int i = 0; i < books.getLength(); i++) {
			if (books.item(i).getNodeType() == Node.ELEMENT_NODE) {
				Element bookNode = (Element) books.item(i);

				Book book = new Book();

				book.setIdbook(Integer.parseInt(getTagValue("idbook", bookNode)));
				book.setCode(getTagValue("code", bookNode));
				book.setEdition(Integer.parseInt(getTagValue("edition", bookNode)));
				book.setPages(Integer.parseInt(getTagValue("pages", bookNode)));
				book.setPlace(getTagValue("place", bookNode));
				book.setYear(DatatypeConverter.parseDateTime(getTagValue("year", bookNode)).getTime());
				book.setType(getTagValue("type", bookNode));
				book.setName(getTagValue("name", bookNode));

				// find and set publisher
				Publisher publisher = publisherMgr.findByIdpublisher(Integer.parseInt(getTagValue("publisher", bookNode)));
				if (publisher == null) {
					continue;
				}

				book.setPublisher(publisher);

				// find and set genre
				Genre genre = genreMgr.findByIdgenre(Integer.parseInt(getTagValue("genre", bookNode)));
				if (genre == null) {
					continue;
				}

				book.setGenre(genre);

				// setup book authors
				List<String> authors = getTagsValues("authorCollection", bookNode);

				if (book.getAuthorCollection() == null) {
					book.setAuthorCollection(new ArrayList<Author>());
				}

				for (String authorId : authors) {
					Author author = authorMgr.findByIdauthor(Integer.parseInt(authorId));
					if (author != null) {
//						book.getAuthorCollection().add(author);
						author.getBooksCollection().add(book);
						authorMgr.save(author);
					}
				}

				try {
					bookMgr.save(book);
				} catch (EJBException ex) {
					ex.printStackTrace(System.out);
				}
			}
		}
		return true;
	}

	/**
	 * Import book exemplars
	 * @param nodes
	 * @return 
	 */
	private Boolean processExemplars(NodeList nodes) {
		NodeList exemplars = nodes.item(0).getChildNodes();

		for (int i = 0; i < exemplars.getLength(); i++) {
			if (exemplars.item(i).getNodeType() == Node.ELEMENT_NODE) {
				Element exemplarNode = (Element) exemplars.item(i);

				Exemplar exemplar = new Exemplar();

				exemplar.setIdexemplar(Integer.parseInt(getTagValue("idexemplar", exemplarNode)));
				exemplar.setAquired(DatatypeConverter.parseDateTime(getTagValue("aquired", exemplarNode)).getTime());
				exemplar.setState(Integer.parseInt(getTagValue("state", exemplarNode)));

				Book book = bookMgr.findByIdbook(Integer.parseInt(getTagValue("book", exemplarNode)));
				if (book == null) {
					continue;
				}

				exemplar.setBook(book);

				try {
					exemplarMgr.save(exemplar);
				} catch (EJBException ex) {
					ex.printStackTrace(System.out);
				}
			}
		}
		return true;
	}

	/**
	 * Import bookings
	 * @param nodes
	 * @return 
	 */
	private Boolean processBookings(NodeList nodes) {
		NodeList bookings = nodes.item(0).getChildNodes();

		for (int i = 0; i < bookings.getLength(); i++) {
			if (bookings.item(i).getNodeType() == Node.ELEMENT_NODE) {
				Element bookingNode = (Element) bookings.item(i);

				Booking booking = new Booking();

				booking.setIdbooking(Integer.parseInt(getTagValue("idbooking", bookingNode)));
				booking.setState(Integer.parseInt(getTagValue("state", bookingNode)));
				booking.setDate(DatatypeConverter.parseDateTime(getTagValue("date", bookingNode)).getTime());

				Book book = bookMgr.findByIdbook(Integer.parseInt(getTagValue("book", bookingNode)));
				if (book == null) {
					continue;
				}

				booking.setBook(book);

				User user = userMgr.findByIduser(Integer.parseInt(getTagValue("user", bookingNode)));
				if (user == null) {
					continue;
				}

				booking.setUser(user);

				try {
					bookingMgr.Save(booking);
				} catch (EJBException ex) {
					ex.printStackTrace(System.out);
				}
			}
		}
		return true;
	}

	/**
	 * Import borrows
	 * @param nodes
	 * @return 
	 */
	public Boolean processBorrows(NodeList nodes) {
		NodeList borrows = nodes.item(0).getChildNodes();

		for (int i = 0; i < borrows.getLength(); i++) {
			if (borrows.item(i).getNodeType() == Node.ELEMENT_NODE) {
				Element borrowNode = (Element) borrows.item(i);

				Borrow borrow = new Borrow();

				borrow.setIdborrow(Integer.parseInt(getTagValue("idborrow", borrowNode)));
				borrow.setProlongations(Integer.parseInt(getTagValue("prolongations", borrowNode)));
				borrow.setBorrowed(DatatypeConverter.parseDateTime(getTagValue("borrowed", borrowNode)).getTime());

				// set returned date (can be null)
				try {
					if (getTagValue("returned", borrowNode) != null) {
						borrow.setReturned(DatatypeConverter.parseDateTime(getTagValue("returned", borrowNode)).getTime());
					}
				} catch (NullPointerException e) {
				}

				User user = userMgr.findByIduser(Integer.parseInt(getTagValue("user", borrowNode)));
				if (user == null) {
					continue;
				}

				borrow.setUser(user);

				Exemplar exemplar = exemplarMgr.findByIdexemplar(Integer.parseInt(getTagValue("exemplar", borrowNode)));
				if (exemplar == null) {
					continue;
				}

				borrow.setExemplar(exemplar);

				try {
					borrowMgr.Save(borrow);
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

		if (nlList.getLength() == 0) {
			return null;
		}

		Node nValue = (Node) nlList.item(0);

		return nValue.getNodeValue();
	}

	/**
	 * Get list of tags values
	 * @param sTag
	 * @param eElement
	 * @return 
	 */
	private static List<String> getTagsValues(String sTag, Element eElement) {
		List<String> values = new ArrayList();

		NodeList nlList = eElement.getElementsByTagName(sTag);

		for (int i = 0; i < nlList.getLength(); i++) {
			NodeList children = nlList.item(i).getChildNodes();

			if (children.getLength() == 0) {
				continue;
			}

			values.add(((Node) (children.item(0))).getNodeValue());
		}

		return values;
	}
}
