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

import java.util.ArrayList;
import java.util.List;

import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.UpdateListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class to create and register a single EPL statement associated to multiple
 * listener. This is the simplest way to declare statements and joint it to a
 * listener.
 * 
 * @author J.L. Canales
 */
public class StatmntSingleEPL extends StatmntPrepare {
	private static Logger  log = LoggerFactory.getLogger(StatmntSingleEPL.class);


    /**
     * Listener list linked to this Statement Item
     */
    private List<UpdateListener> listeners;

	/**
	 * Create a new StatmntSingleQuery, for bean-style usage.
	 */
	public StatmntSingleEPL() {
		super();
	}

	/**
	 * Create a new StatmntSingleQuery, given a EPL statement
	 * 
	 * @param aiEplStatement
	 *            EPL statement to initialize this Item
	 */
	public StatmntSingleEPL(String aiEplStatement) {
		super(aiEplStatement);
    	this.listeners = new ArrayList<UpdateListener>();
	}

	/**
	 * Create a new StatmntSingleQuery, given a EPL statement
	 * 
	 * @param aiEplStatement
	 *            EPL statement to initialize this Item
	 */
	public StatmntSingleEPL(String aiEplStatement, String aiEplName) {
		super( aiEplStatement, aiEplName);
    	this.listeners    = new ArrayList<UpdateListener>();
	}
	/**
	 * Method to Statement registering in a EventProcessor engine
	 * 
	 * @param EPServiceProvider
	 *            . Esper Event Processor engine where register the statement.
	 */
	@Override
	public void register(EPServiceProvider cepEngine) {
		super.register(cepEngine);

		log.info("Adding Listeners to : {}", this.getEplName());
		
		// Joining listeners to EPL object
		for (int i = 0; i < listeners.size(); i++){
			this.statementObj.addListener(listeners.get(i));
		}
		
		log.info("Successfull listener registration for: {}", this.getEplName());
	}



	/**
	* Set the Listeners list for this item.
	* @param aiListeners Listeners List to initialize this item
	*/
	public void setListeners(List<UpdateListener> aiListeners) {
		this.listeners = aiListeners;
	}

}
