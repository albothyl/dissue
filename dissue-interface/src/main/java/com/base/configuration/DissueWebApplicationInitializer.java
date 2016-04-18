package com.base.configuration;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

public class DissueWebApplicationInitializer extends AbstractWebApplicationInitializer {

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		loadRootApplicationContext(servletContext, DissueDomainApplicationContextConfig.class);
		loadDefaultFilters(servletContext);
		addDispatcherServlet(servletContext, "webServlet", DissueWebServletApplicationContextConfig.class, "/");
	}
}
