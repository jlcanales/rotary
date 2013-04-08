package org.rotarysource.jmx.jmsinputadapter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jms.listener.AbstractMessageListenerContainer;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;



/**
 * MBean responsible for the input listener management. It controls the
 * remote operations to connect and disconnect the CEP engine form the 
 * input queue
 *  
 * @author J.L. Canales
 *
 */
@ManagedResource(objectName="org.rotarysource.mbean.inputadapters:name=AbstractMessageListenerContainer", description="JMS Message Listener Management", log=true,
logFile="jmx.log", currencyTimeLimit=15, persistPolicy="OnUpdate", persistPeriod=200,
persistLocation="foo", persistName="bar")
public class AbstractMessageListenerContainerJMX {

	private static Log        logger = LogFactory.getLog(AbstractMessageListenerContainerJMX.class);

	/**
	 * Reference to Managed listener container
	 */
	protected AbstractMessageListenerContainer jmsListenerContainer;

	/**
	 * Create a InputLisnerJMX to manage an Input Listener Container
	 * @param listenerContainer
	 */
	public AbstractMessageListenerContainerJMX( AbstractMessageListenerContainer listenerContainer){
		this.jmsListenerContainer = listenerContainer;
	}
	
	/**
	 * Connect Listener to the configured queue
	 */
    @ManagedOperation(description="Connect Listener to the queue")
	public void Connect() {
    	
		logger.warn("Proceding to CONNECT JMS Listener to " + jmsListenerContainer.getDestination().toString());
		
		jmsListenerContainer.initialize(); 
		jmsListenerContainer.start();

	}
	
    
    /**
     * Disconnect Listener from the configured queue
     */
    @ManagedOperation(description="Disconnect Listener from the queue")
	public void Disconnect() {
		logger.warn("Proceding to DISCONNECT JMS Listener from " + jmsListenerContainer.getDestination().toString());
		jmsListenerContainer.stop();
		jmsListenerContainer.shutdown();
	}
    

}
