/*
 */
package org.fit.pis.library.back.filters;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.fit.pis.library.back.AuthenticationBean;

/**
 *
 * @author Lukáš Černý <cerny.l@gmail.com>
 */
public class AdminScopeFilter implements Filter {

	// The filter configuration object we are associated with.  If
	// this value is null, this filter instance is not currently
	// configured. 
	private FilterConfig filterConfig = null;

	public AdminScopeFilter() {
	}

	/**
	 *
	 * @param request The servlet request we are processing
	 * @param response The servlet response we are creating
	 * @param chain The filter chain we are processing
	 *
	 * @exception IOException if an input/output error occurs
	 * @exception ServletException if a servlet error occurs
	 */
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain)
			throws IOException, ServletException {

		HttpSession session = ((HttpServletRequest) request).getSession();
		AuthenticationBean auth = (AuthenticationBean) session.getAttribute("authenticationBean");
		if (auth != null) {
			if (auth.isInRole("admin")) {
				chain.doFilter(request, response);
			} else {
				String path = ((HttpServletRequest) request).getContextPath();
				((HttpServletResponse) response).sendRedirect(path + "/faces/access_denied.xhtml");
			}
		} else {
			throw new ServletException();
		}
	}

	/**
	 * Return the filter configuration object for this filter.
	 */
	public FilterConfig getFilterConfig() {
		return (this.filterConfig);
	}

	/**
	 * Set the filter configuration object for this filter.
	 *
	 * @param filterConfig The filter configuration object
	 */
	public void setFilterConfig(FilterConfig filterConfig) {
		this.filterConfig = filterConfig;
	}

	/**
	 * Destroy method for this filter 
	 */
	public void destroy() {
	}

	/**
	 * Init method for this filter 
	 */
	public void init(FilterConfig filterConfig) {
		this.filterConfig = filterConfig;
	}
}
