package org.rotarysource.jms.listener;

import org.rotarysource.signals.SignalCapable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultMessageListenerContainer extends
		org.springframework.jms.listener.DefaultMessageListenerContainer
		implements SignalCapable {

    /** 
     * Apache commons login logger instance
     */	
	private static Logger log = LoggerFactory.getLogger(DefaultMessageListenerContainer.class);	
	
	
	/* (non-Javadoc)
	 * @see org.springframework.jms.listener.AbstractJmsListeningContainer#shutdown()
	 */
	@Override
	public void shutdown(){
		log.info("Starting Listener Container  SHUTDOWN process.");
		super.stop();
		super.shutdown();
	}
	
	/**
	 * Connect this Listener Container to JMS server and activate listening.
	 */
	public void startConnection(){
		log.info("Connecting to JMS server: {}", getDestination().toString());	
		
		super.initialize();
		super.start();
		log.info("Listener Container Connected.");	
	}
	
}
