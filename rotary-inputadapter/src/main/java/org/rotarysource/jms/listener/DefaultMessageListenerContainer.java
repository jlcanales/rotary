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
	
	
	@Override
	public void shutdown(){
		log.info("Starting Listener Container  SHUTDOWN process.");
		super.stop();
		super.shutdown();
	}
}
