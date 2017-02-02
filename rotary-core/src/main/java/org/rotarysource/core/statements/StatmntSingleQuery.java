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

import com.espertech.esper.client.UpdateListener;


/**
 * This class is mantained only for compatibility with version before 3.0.
 * In the future will be deprecated.
 * 
 * @author J.L. Canales
 */
@Deprecated
public class StatmntSingleQuery extends StatmntSingleEPL {

	/**
	 * Create a new StatmntSingleQuery, for bean-style usage.
	 */
	public StatmntSingleQuery() {
		super();
	}

	/**
	 * Create a new StatmntSingleQuery, given a EPL statement
	 * 
	 * @param aiEplStatement
	 *            EPL statement to initialize this Item
	 */
	StatmntSingleQuery(String aiEplStatement) {
		super(aiEplStatement);
	}


	/**
	 * Set the Listener for this item.
	 * 
	 * @param aiListener
	 *            Listener to initialize this item
	 */
	public void setListener(UpdateListener aiListener) {
		
		List<UpdateListener> listeners = new ArrayList<UpdateListener>();
		listeners.add(aiListener);
		
		this.setListeners(listeners);
	}

}
