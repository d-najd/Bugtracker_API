package com.bugtracker;

import org.h2.server.web.WebServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfiguration implements WebMvcConfigurer{
	
	public static String dbLocation = "jdbc:mysql://localhost:3305/bugtracker_db?useSSL=false";
	public static String dbUname = "root";
	public static String dbPass = "j6t2gu6k46ek";
	
	/*
	@Bean
	ServletRegistrationBean h2ServletRegistration() {
		ServletRegistrationBean registrationBean = 
				new ServletRegistrationBean(new WebServlet());
		registrationBean.addUrlMappings("/h2/*");
		return registrationBean;
	}
	*/

    
	/*
	 * logging.level.org.hibernate.SQL=DEBUG
	 	logging.level.org.hibernate.type=TRACE
	 * 
	 */
}
