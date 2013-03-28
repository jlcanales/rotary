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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPStatement;
import com.espertech.esperha.client.EPStatementExistsException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class to create and register a prepare EPL statement.
 * This kind of staments has not any listener associated. They are used
 * to prepare CEP windows, valiables, insertions, etc. and its execution
 * dont trigger any listener.
 * 
 * @author J.L. Canales
 */
public class StatmntPrepare implements Statement {
	private static Logger  log = LoggerFactory.getLogger(StatmntPrepare.class);

	/**
	 * EPL Statement for this Item
	 */
	private String eplStatement;
	
	/**
	 * EPL Statement Name for this Item
	 */
	private String eplName;	

	/**
	 * Esper Statement Object to manage statements in Esper core
	 */
	protected EPStatement statementObj;


	/**
	 * Create a new StatmntSingleQuery, for bean-style usage.
	 */
	public StatmntPrepare() {
		eplStatement = "";
	}

	/**
	 * Create a new StatmntSingleQuery, given a EPL statement
	 * 
	 * @param aiEplStatement
	 *            EPL statement to initialize this Item
	 */
	public StatmntPrepare(String aiEplStatement) {
		setEplStatement(aiEplStatement);
	}

	/**
	 * Create a new StatmntSingleQuery, given a EPL statement
	 * 
	 * @param aiEplStatement
	 *            EPL statement to initialize this Item
	 */
	public StatmntPrepare(String aiEplStatement, String aiEplName) {
		this.eplStatement = aiEplStatement;
		this.eplName      = aiEplName;
	}
	/**
	 * Method to Statement registering in a EventProcessor engine
	 * 
	 * @param EPServiceProvider
	 *            . Esper Event Processor engine where register the statement.
	 */
	@Override
	public void register(EPServiceProvider cepEngine) {
		log.info("Registering Statement: {}", eplStatement);

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
			log.warn("Recovered EplName={}", eplName);

		}
		
		log.info("Successfull registered Statement: {}", eplName);
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
			log.info("Unregistering Statement: {}", statementObj.getText());
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
			log.debug("Localized EPL Name in EPL Statement: {}", eplName);
		}
	}

	
	/**
	 * Returns eplName for this object
	 * @return EPL Statement name
	 */
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
