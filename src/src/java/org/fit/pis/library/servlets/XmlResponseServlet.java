/*
 */
package org.fit.pis.library.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import org.fit.pis.library.data.*;

/**
 *
 * @author Lukáš Černý <cerny.l@gmail.com>
 */
public class XmlResponseServlet extends HttpServlet {

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

	/** 
	 * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
	 * @param request servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException if an I/O error occurs
	 */
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/xml;charset=UTF-8");
		response.setHeader("Content-Disposition", "attachment; filename=export.xml");
		PrintWriter out = response.getWriter();
		try {
			DatabaseWrapper db = new DatabaseWrapper();

			// fill database wrapper with data
			db.setUsers(userMgr.findAll());
			db.setBooks(bookMgr.findAll());
			db.setAuthors(authorMgr.findAll());
			db.setBookings(bookingMgr.findAll());
			db.setBorrows(borrowMgr.findAll());
			db.setExemplars(exemplarMgr.findAll());
			db.setGenres(genreMgr.findAll());
			db.setPublishers(publisherMgr.findAll());

			// Step 2 - convert to xml
			JAXBContext jaxbContext = JAXBContext.newInstance(DatabaseWrapper.class);

			Marshaller marshaller = jaxbContext.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			marshaller.marshal(db, out);

		} catch (Exception ex) {
			ex.printStackTrace(out);
		} finally {
			out.close();
		}
	}

	// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
	/** 
	 * Handles the HTTP <code>GET</code> method.
	 * @param request servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException if an I/O error occurs
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	/** 
	 * Handles the HTTP <code>POST</code> method.
	 * @param request servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException if an I/O error occurs
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	/** 
	 * Returns a short description of the servlet.
	 * @return a String containing servlet description
	 */
	@Override
	public String getServletInfo() {
		return "Short description";
	}// </editor-fold>
}
