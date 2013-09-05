package org.rotarysource.core.sep.task;

import java.util.Date;
import java.util.HashMap;

import org.rotarysource.core.CepEngine;
import org.rotarysource.events.BasicEvent;
import org.rotarysource.signals.AppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;

public abstract class TaskTemplate implements SepTask {


    /**
	 * 
	 */
	private static final long serialVersionUID = 940937369402286384L;

	/** 
     * Apache commons login logger instance
     */	
	private static Logger log = LoggerFactory.getLogger(TaskTemplate.class);
    
    /** 
     * Reference to Cep Engine that events will be sent
     */ 
    private String cepEngineBeanName;

    /**
     * Sets CEP Engine to be able to send events to it
     * @param cepEngine
     */
	public void setCepEngineBeanName(String aiCepEngineBeanName) {
		this.cepEngineBeanName = aiCepEngineBeanName;
	}
 
	/**
	 * Create and send a BasicEvent to cep engine to be managed
	 * by it.
	 * @param eventCode Event Code
	 * @param eventType Event Type
	 * @param compDataMap Additional Data to be attached to event
	 */
	protected void sendEvent( String eventCode, 
						   String eventType, 
						   HashMap<String, String> compDataMap){
		
		BasicEvent event = new BasicEvent();
        Date       date       = new Date();  
		
        event.setEventId(Integer.toString((int)date.getTime()));
        event.setEventCode(eventCode);
        event.setEventTimestamp(date);
        event.setEventType(eventType);
        event.setSystemId("sepTask");
        
        event.setCompData( compDataMap);
        
        
        /*
         * Get the cepEngine instance from the application context.
         *   Cep engine cannot be injected by IOC because a task and all
         *   its elements must be Serializable to be launched correctly by
         *   Quartz.
         *   cepEngine is a singleton in spring and cannot be serialized so,
         *   when the task is recovered from serialization, its necessary to
         *   get cepEngine reference from AppContext
         */
        ApplicationContext ctx = AppContext.getApplicationContext(); 
        
        try{
	        CepEngine cepEngine = (CepEngine) ctx.getBean(cepEngineBeanName);
	        
	        log.debug("Sending event to CEP: {}", event);
	        cepEngine.getCepEngine().getEPRuntime().sendEvent(event);
        }
        catch( NoSuchBeanDefinitionException exception){
        	log.warn("Event could not be sended to CEP; cause: NOT EXISTING CepEngine ({}); Event: {}", cepEngineBeanName, event.toString());
       	
        }

	}
}
