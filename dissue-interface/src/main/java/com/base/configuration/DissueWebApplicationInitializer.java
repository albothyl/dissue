package com.base.configuration;

import org.springframework.context.ApplicationContext;
import org.springframework.util.Assert;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.*;
import java.util.EnumSet;

public class DissueWebApplicationInitializer implements WebApplicationInitializer {

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		loadRootApplicationContext(servletContext, DissueDomainApplicationContextConfig.class);
		loadDefaultFilters(servletContext);
		addDispatcherServlet(servletContext, "webServlet", DissueWebServletApplicationContextConfig.class, "/");
	}

	/**
	 * Spring Root Application Context를 적재한다.
	 *
	 * @param servletContext servletContext
	 * @param configClasses  Root Application Context Config classes
	 * @return Spring Root Application Context
	 */
	protected ApplicationContext loadRootApplicationContext(ServletContext servletContext, Class<?>... configClasses) {
		return loadRootApplicationContext(servletContext, true, configClasses);
	}

	/**
	 * Spring Root Application Context를 적재한다.
	 *
	 * @param servletContext                servletContext
	 * @param allowBeanDefinitionOverriding bean name의 중복 가능여부
	 * @param configClasses                 Root Application Context Config classes
	 * @return Spring Root Application Context
	 */
	protected ApplicationContext loadRootApplicationContext(ServletContext servletContext, boolean allowBeanDefinitionOverriding,
		Class<?>... configClasses) {
		AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
		rootContext.setAllowBeanDefinitionOverriding(allowBeanDefinitionOverriding);
		rootContext.register(configClasses);

		servletContext.addListener(new ContextLoaderListener(rootContext));

		return rootContext;
	}

	/**
	 * 모든 프로젝트의 기본 필터를 적재한다.
	 *
	 * @param servletContext servletContext
	 */
	protected void loadDefaultFilters(ServletContext servletContext) {
		addEncodingFilter(servletContext);
	}

	/**
	 * Character Encoding Filter
	 */
	protected void addEncodingFilter(ServletContext servletContext) {
		FilterRegistration encodingFilter = servletContext.addFilter("encodingFilter", CharacterEncodingFilter.class);
		encodingFilter.setInitParameter("encoding", "UTF-8");
		encodingFilter.setInitParameter("forceEncoding", "true");
		encodingFilter.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD), true, "/*");
	}

	/**
	 * Spring {@link org.springframework.web.servlet.DispatcherServlet DispatcherServlet} 추가
	 *
	 * @param servletContext            servletContext
	 * @param servletName               서블릿 이름. 기본은 'webServlet'
	 * @param servletContextConfigClass Spring Servlet Application Context Config class
	 * @param mappings                  Servlet Mappings
	 * @return Servlet 객체
	 */
	protected ServletRegistration.Dynamic addDispatcherServlet(ServletContext servletContext, String servletName, Class<?> servletContextConfigClass,
		String... mappings) {
		return addDispatcherServlet(servletContext, servletName, new Class<?>[] { servletContextConfigClass }, true, mappings);
	}

	/**
	 * Spring {@link org.springframework.web.servlet.DispatcherServlet DispatcherServlet} 추가
	 *
	 * @param servletContext                servletContext
	 * @param servletName                   서블릿 이름. 기본은 'webServlet'
	 * @param servletContextConfigClasses   Spring Servlet Application Context Config classes
	 * @param allowBeanDefinitionOverriding bean name의 중복 가능여부
	 * @param mappings                      Servlet Mappings
	 * @return Servlet 객체
	 */
	protected ServletRegistration.Dynamic addDispatcherServlet(ServletContext servletContext, String servletName,
		Class<?>[] servletContextConfigClasses, boolean allowBeanDefinitionOverriding, String... mappings) {
		Assert.notNull(servletName, "Please enter the servletName.");
		Assert.notEmpty(servletContextConfigClasses, "Please set Spring ApplicationContext Config Class for Servlet.");
		Assert.notEmpty(mappings, "Please set Servlet mapping.");

		AnnotationConfigWebApplicationContext servletApplicationContext = new AnnotationConfigWebApplicationContext();
		servletApplicationContext.setAllowBeanDefinitionOverriding(allowBeanDefinitionOverriding);
		servletApplicationContext.register(servletContextConfigClasses);

		ServletRegistration.Dynamic dispatcherServlet = servletContext.addServlet(servletName, new DispatcherServlet(servletApplicationContext));

		dispatcherServlet.setLoadOnStartup(1);
		dispatcherServlet.addMapping(mappings);

		return dispatcherServlet;
	}
}
