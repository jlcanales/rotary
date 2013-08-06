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

package org.rotarysource.signals.shutdown;

import java.util.List;

import org.rotarysource.signals.SignalCapable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;


public class ShutdownEventListener implements ApplicationListener<ShutdownEvent>{

	private static Logger  log = LoggerFactory.getLogger(ShutdownEventListener.class);
	
	
	/**
	 * List of SignalCapable Objects to be executed shutdown
	 */
	private List<SignalCapable> componentList;
	
	private boolean isShudownExecuted;
	
	public ShutdownEventListener(List<SignalCapable> aiComponentList){
		this.componentList = aiComponentList;	
		isShudownExecuted = false;
	}
	
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
    	log.info("EVENT DRIVEN CONTROL SYSTEM: Starting Shutdown server process");
    	
		for (int i = 0; i < componentList.size(); i++){
			componentList.get(i).shutdown();
		} 
		
		isShudownExecuted = true;

	}

	public boolean isShudownExecuted() {
		return isShudownExecuted;
	}

}
