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
import com.espertech.esper.client.EPStatementException;
import com.espertech.esper.client.EPStatementSyntaxException;
import com.espertech.esperha.client.EPStatementExistsException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;

/**
 * Class to create and register a prepare EPL statement.
 * This kind of staments has not any listener associated. They are used
 * to prepare CEP windows, variables, insertions, etc. and its execution
 * don't trigger any listener.
 * 
 * @author J.L. Canales
 */
@ManagedResource( description="EPL Statement", log=true,
				  logFile="jmx.log", currencyTimeLimit=15, 
				  persistPolicy="OnUpdate", persistPeriod=200,
				  persistLocation="foo", persistName="bar")
public class StatmntPrepare implements Statement {
	private static Logger  log = LoggerFactory.getLogger(StatmntPrepare.class);

	/**
	 * EPL Statement for this Item
	 */
	private String eplStatement;
	
	/**
	 * Flag to indicate if statement stay active after registering
	 */
	private boolean defaultActive;



	/**
	 * Esper Statement Object to manage statements in Esper core
	 */
	protected EPStatement statementObj;
	


	/**
	 * Create a new StatmntSingleQuery, for bean-style usage.
	 */
	public StatmntPrepare() {
		eplStatement = "";
		defaultActive = true;
	}

	/**
	 * Create a new StatmntSingleQuery, given a EPL statement
	 * 
	 * @param aiEplStatement
	 *            EPL statement to initialize this Item
	 */
	public StatmntPrepare(String aiEplStatement) {
		setEplStatement(aiEplStatement);
		defaultActive = true;
	}

	/**
	 * Method to Statement registering in a EventProcessor engine
	 * 
	 * @param cepEngine Esper Event Processor engine where register the statement.
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
				if(!defaultActive){
					statementObj.stop();
				}

			
		} catch (EPStatementExistsException exception) {
			log.warn(exception.getMessage());
			
			
			// EPL statement can be named using @Name() notation in EPL sentence.
			// Statement object hasn't any Name reference to recover the EPL so
			// Its necessary to parse the EPL to recover the EPL Name
			String eplName = null;
			String expression = ".*@Name\\('.*'\\).*";
			//Make the comparison case-insensitive.  
			Pattern pattern = Pattern.compile(expression,Pattern.CASE_INSENSITIVE);  
			Matcher matcher = pattern.matcher(eplStatement); 
			if(matcher.matches()){
				//TODO: get name value from matcher
				eplName = eplStatement.split("@Name\\('")[1].split("'\\)")[0];
				log.debug("Localized EPL Name in EPL Statement: " + eplName);
			}
			
			
			statementObj = cepEngine.getEPAdministrator().getStatement( eplName);
			log.warn("Recovered EplName={}", eplName);

		}catch (EPStatementSyntaxException exception) {
			
			
			log.error("Syntax error exception registering EPL; nested Message {}",exception.getMessage());
			throw exception;

		}catch (EPStatementException exception) {						
			log.error("Error registering EPL; nested Message {}",exception.getMessage());
			throw exception;

		}
		
		
		log.info("Successfull registered Statement: {}", getEplName());
	}

	/**
	 * Method to Statement unregistering in a EventProcessor engine when destoy
	 * is called, Event Processor Engine stops to use this statement
	 *
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
	}

	
	/**
	 * Returns eplName for this object
	 * @return EPL Statement name
	 */
    @ManagedAttribute(description="EPL Statement Name") 
	public String getEplName() {
		
		if ( statementObj != null)
			return 	statementObj.getName();
		else
			return null;
	}

	/**
	 * Returns epl Statement for this object
	 * @return EPL Statement
	 */
    @ManagedAttribute(description="EPL Statement") 
	public String getEplStatement() {
		return eplStatement;
	}

	/**
	 * Returns statement registering status in Cep Engine
	 * @return registering status
	 */
    @ManagedAttribute(description="CEP Engine Registering Status") 
	public boolean isRegistered() {
		if( statementObj != null)
			return true;
		else
			return false;
	}

    @ManagedAttribute(description="Statement Activation Status") 
	@Override
	public boolean isStarted() {
		if( statementObj != null)
			return statementObj.isStarted();
		else
			return false;
	}
    
    
    @ManagedOperation(description="Deactive Statement")
	@Override
	public void stop() {
		if( statementObj != null){
			log.warn("Proceding to STOP Statement: {}", getEplName());
			statementObj.stop();
		}
		
	}

    @ManagedOperation(description="Active Statement")
	@Override
	public void start() {
		if( statementObj != null){
			log.warn("Proceding to START Statement: {}", getEplName());
			statementObj.start();
		}
		
	}
    
	public boolean isDefaultActive() {
		return defaultActive;
	}

	public void setDefaultActive(boolean defaultActive) {
		this.defaultActive = defaultActive;
	}
    
}
