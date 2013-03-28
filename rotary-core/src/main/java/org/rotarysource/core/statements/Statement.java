package org.rotarysource.core.statements;

import com.espertech.esper.client.EPServiceProvider;

/**
 * Specifies interface for all statement objects managed by Event Processor engine.

 @author J. L. Canales
 * 
 */
public interface Statement {
	
	 /** 
     * Method to Statement registering in a EventProcessor engine
     * 
     * @param cepEngine. Esper Event Processor engine where register the statement.
     */		
	public void register(EPServiceProvider cepEngine);
	
	 /** 
     * Method to Statement unregistering in a EventProcessor engine
     * when destoy is called, Event Processor Engine stops to use this statement
     * 
     */
	public void destroy();
}
