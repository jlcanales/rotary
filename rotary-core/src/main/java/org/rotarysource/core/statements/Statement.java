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
package org.rotarysource.core.statements;

import com.espertech.esper.client.EPServiceProvider;

/**
 * Specifies interface for all statement objects managed by Event Processor engine.
 *
 * @author J. L. Canales
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
