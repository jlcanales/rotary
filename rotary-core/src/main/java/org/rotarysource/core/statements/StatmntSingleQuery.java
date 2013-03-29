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
