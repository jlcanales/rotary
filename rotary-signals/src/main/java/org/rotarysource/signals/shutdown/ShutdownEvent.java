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

import org.springframework.context.ApplicationEvent;


/**
 * Class to create shutdown type events in rotary core subsystem. 
 * This kind of events will trigger the graceful system shutdown 
 * procedure assuring correct ordering in components destroy.
 * 
 * @author J.L. Canales
 */
public class ShutdownEvent extends ApplicationEvent {


	/**
	 * 
	 */
	private static final long serialVersionUID = 7389048391247591948L;
	
	private String message;
	
	public ShutdownEvent(Object source, String aiMessage) {
		super(source);
		this.message = aiMessage;
	}

	/**
	 * Returns The message inserted when Event is created
	 * @return Message text
	 */
	public String getMessage() {
		return message;
	}

}
