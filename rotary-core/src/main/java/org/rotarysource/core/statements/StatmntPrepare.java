package org.rotarysource.core.statements;


import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPStatement;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/** 
 * This class provides functionality to register a single prepared EPL.
 *   EPL statements that don't have a associated listener such as Create window,
 *   insert into, on, etc.
 * 
 * @author J.L. Canales
 */
public class StatmntPrepare implements Statement{
    private static Log     log = LogFactory.getLog(StatmntPrepare.class);
    private String         eplStatement;
    private EPStatement    statementObj;
    
    public StatmntPrepare(){
    	eplStatement="";
    }
        
    public StatmntPrepare( String aiEplStatement){
    	this.eplStatement = aiEplStatement;
    }
 
    @Override
    public void register(EPServiceProvider cepEngine)
    {
    	log.info("Registering Prepare Statement: " + eplStatement);
    	if(statementObj!=null){
	    	log.debug("Prepare Statement registered yet. Destroying");
    		statementObj.destroy();
    		statementObj=null;
    	}
     	statementObj=cepEngine.getEPAdministrator().createEPL( eplStatement);
    }
 
    @Override
    public void destroy(){
    	if(statementObj!=null){
	    	log.info("Unregistering Preprare Statement: " + statementObj.getText());
    		statementObj.destroy();
    		statementObj=null;
    	}
    }
    public void setEplStatement( String aiEplStatement){
    	this.eplStatement = aiEplStatement;
    }
}
