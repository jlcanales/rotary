/*
Copyright (c) 2013 J. L. Canales Gasco
 
This program is free software; you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation; either version 2 of the License, or
(at your option) any later version.
 
This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.
 
You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA}]
*/

package org.rotarysource.signals;

import org.rotarysource.core.CepEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.jms.listener.AbstractMessageListenerContainer;

public class ShutdownEventListener implements ApplicationListener<ShutdownEvent>{

	private static Logger  log = LoggerFactory.getLogger(ShutdownEventListener.class);
	
	private static final String CEPENGINE_BEAN_NAME 		 = "cepEngine";
	private static final String LISTENER_CONTAINER_BEAN_NAME = "listenerContainer";
	
	/**
	 * This method implements the systems shutdown procedure to be executed
	 * before the Application Context will be executed.
	 * 
	 * @author J.L. Canales
	 */
	@Override
	public void onApplicationEvent(ShutdownEvent event) {
		// TODO Auto-generated method stub
		log.info("SHUTDOWN signal received. Requester Message: {}", event.getMessage());

		ApplicationContext ctx = AppContext.getApplicationContext();

		CepEngine statementEngine = (CepEngine) ctx.getBean(CEPENGINE_BEAN_NAME);
		AbstractMessageListenerContainer jmsListenerContainer  = (AbstractMessageListenerContainer) ctx.getBean(LISTENER_CONTAINER_BEAN_NAME);
		
    	log.info("EVENT DRIVEN CONTROL SYSTEM: Starting Shutdown server process");
    	log.info("Disconecting Input queues"); 
    	jmsListenerContainer.stop();
    	jmsListenerContainer.shutdown();
    	log.info("Shutting down CEP Engine"); 
    	statementEngine.shutdown();
		
	}

}
