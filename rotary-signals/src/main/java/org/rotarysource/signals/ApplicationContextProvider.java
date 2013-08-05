package org.rotarysource.signals;

import org.springframework.beans.BeansException;  
import org.springframework.context.ApplicationContext;  
import org.springframework.context.ApplicationContextAware;  
  
/** 
 * This class provides an application-wide access to the 
 * Spring ApplicationContext! The ApplicationContext is 
 * injected in a static method of the class "AppContext". 
 * 
 * Use AppContext.getApplicationContext() to get access 
 * to all Spring Beans. 
 * 
 * @author Siegfried Bolz 
 */ 

public class ApplicationContextProvider implements ApplicationContextAware{
   
	/**
	 * Override setApplicationContext method to allow Spring inject a reference
	 * to the Application Context.
	 * 
	 * @param ctx Application Context injectect by spring
	 */
	public void setApplicationContext(ApplicationContext ctx) throws BeansException {  
        // Wiring the ApplicationContext into a static method  
        AppContext.setApplicationContext(ctx);  
    }
}

