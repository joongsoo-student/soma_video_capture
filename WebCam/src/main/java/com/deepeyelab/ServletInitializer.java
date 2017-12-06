package com.deepeyelab;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletException;

import org.apache.commons.logging.LogFactory;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;

import com.deepeyelab.capture.Capture;
import com.deepeyelab.capture.WebCamCapture;

public class ServletInitializer extends SpringBootServletInitializer {
	public static Capture capture;
	
	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		super.onStartup(servletContext);
		capture = WebCamCapture.getInstance();
		capture.start();
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(WebCamApplication.class);
	}

}
