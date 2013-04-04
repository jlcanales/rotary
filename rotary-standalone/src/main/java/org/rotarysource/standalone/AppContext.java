package org.rotarysource.standalone;

import org.springframework.context.ApplicationContext;  
  
/** 
 * This class provides application-wide access to the Spring ApplicationContext. 
 * The ApplicationContext is injected by the class "ApplicationContextProvider". 
 * 
 * @author Siegfried Bolz 
 */  
public class AppContext {  
  
    /**
     * Spring Application Context reference stored in this object
     */
	private static ApplicationContext ctx;  
  
    /** 
     * Injected from the class "ApplicationContextProvider" which is automatically 
     * loaded during Spring-Initialization. 
     * 
     * @param applicationContext Spring Application Context reference to be stored
     */  
    public static void setApplicationContext(ApplicationContext applicationContext) {  
        ctx = applicationContext;  
    }  
  
    /** 
     * Get access to the Spring ApplicationContext from everywhere in your Application. 
     * 
     * @return Spring Application Context reference
     */  
    public static ApplicationContext getApplicationContext() {  
        return ctx;  
    }  
} // .EOF  