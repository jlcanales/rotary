package org.rotarysource.core.statements;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.UpdateListener;
import com.espertech.esperha.client.EPStatementExistsException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Class to create and register a single EPL statement associated to multiple
 * listener. This is the simplest way to declare statements and joint it to a
 * listener.
 * 
 * @author J.L. Canales
 */
public class StatmntSingleEPL implements Statement {
	private static Log log = LogFactory.getLog(StatmntSingleEPL.class);

	/**
	 * EPL Statement for this Item
	 */
	private String eplStatement;
	
	/**
	 * EPL Statement for this Item
	 */
	private String eplName;	

	/**
	 * Esper to manage statements in Esper core
	 */
	private EPStatement statementObj;

    /**
     * Listener list linked to this Statement Item
     */
    private List<UpdateListener> listeners;

	/**
	 * Create a new StatmntSingleQuery, for bean-style usage.
	 */
	public StatmntSingleEPL() {
		eplStatement = "";
	}

	/**
	 * Create a new StatmntSingleQuery, given a EPL statement
	 * 
	 * @param aiEplStatement
	 *            EPL statement to initialize this Item
	 */
	public StatmntSingleEPL(String aiEplStatement) {
		setEplStatement(aiEplStatement);
    	this.listeners = new ArrayList<UpdateListener>();
	}

	/**
	 * Create a new StatmntSingleQuery, given a EPL statement
	 * 
	 * @param aiEplStatement
	 *            EPL statement to initialize this Item
	 */
	public StatmntSingleEPL(String aiEplStatement, String aiEplName) {
		this.eplStatement = aiEplStatement;
		this.eplName      = aiEplName;
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
		log.info("Registering Statement: " + eplStatement);
		if (statementObj != null) {
			log.debug("Statement registered yet. Destroying");
			statementObj.destroy();
			statementObj = null;
		}
		try {
				statementObj = cepEngine.getEPAdministrator().createEPL( eplStatement);

			
		} catch (EPStatementExistsException exception) {
			log.warn(exception.getMessage());
			
			// Processing to recover eplName from exception Message.
			// EPL statement can be named using @Name() notation in EPL sentence.
			// In that case, setEplStatement has extract the EPL Name, so
			// we recover the statement Object related with that eplName to inject
			// the listener
			
			statementObj = cepEngine.getEPAdministrator().getStatement( eplName);
			log.warn("Recovering EplName= " +eplName);

		}
		
		// Joining listeners to EPL
		for (int i = 0; i < listeners.size(); i++){
			statementObj.addListener(listeners.get(i));
		}
	}

	/**
	 * Method to Statement unregistering in a EventProcessor engine when destoy
	 * is called, Event Processor Engine stops to use this statement
	 * 
	 * @param EPServiceProvider
	 *            . Esper Event Processor engine where register the statement.
	 */
	@Override
	public void destroy() {
		if (statementObj != null) {
			log.info("Unregistering Statement: " + statementObj.getText());
			statementObj.destroy();
			statementObj = null;
		}
	}

	/**
	 * Set the EPL Statement for this item.
	 * 
	 * @param aiEplStatement  EPL Statement
	 */
	public void setEplStatement(String aiEplStatement) {
		this.eplStatement = aiEplStatement;
		
		// EPL statement can be named using @Name() notation in EPL sentence.
		// Statement object hasnt any Name reference to recover the EPL so
		// Its necessary to parse the EPL to recover the EPL Name
		// and attach the listener.	
		
		String expression = ".*@Name\\('.*'\\).*";
		//Make the comparison case-insensitive.  
		Pattern pattern = Pattern.compile(expression,Pattern.CASE_INSENSITIVE);  
		Matcher matcher = pattern.matcher(aiEplStatement); 
		if(matcher.matches()){  
			eplName = aiEplStatement.split("@Name\\('")[1].split("'\\)")[0];
			log.debug("Localized EPL Name in EPL Statement: " + eplName);
		}
	}

	/**
	* Set the Listeners list for this item.
	* @param aiListeners Listeners List to initialize this item
	*/
	public void setListeners(List<UpdateListener> aiListeners) {
		this.listeners = aiListeners;
	}
	

	public String getEplName() {
		return eplName;
	}

	/**
	 * Set the Statement Name to locate it in the engine
	 * 
	 * @param aiEplName
	 *            EPL Statement name
	 */	
	public void setEplName(String aiEplName) {
		this.eplName = aiEplName;
	}
}
