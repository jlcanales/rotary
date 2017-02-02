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
package org.rotarysource.core;


import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPException;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esperha.client.ConfigurationHA;


import java.util.List;

import org.rotarysource.core.statements.Statement;
import org.rotarysource.signals.SignalCapable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PreDestroy;

/**
 * Implement the CEP Core class.
 * This class instance a Cep engine and register into it all
 * the configured statements.
 *
 * @author J.L. Canales
 */
public class CepEngine implements SignalCapable {

    private static Logger log = LoggerFactory.getLogger(CepEngine.class);

    /**
     * Stand Alone Mode. Statement processor without High Availability behavior
     */
    private static final int STANDALONE_HA_MODE = 0;

    /**
     * BerkeleyDB Mode. Statement processor with disk persistence High Availability behavior
     */
    private static final int BERKELEYDB_HA_MODE = 1;

    /**
     * MySql Mode. Statement processor with MySQL database persistence Mode.
     */
    private static final int MYSQL_HA_MODE = 2;

    /**
     * List of Statement Objects to be register in CEP engine
     */
    private List<Statement> statements;

    /**
     * CEP engine system core
     */
    private EPServiceProvider cepEngine;

    /**
     * Store the configurated EsperHA Mode
     */
    private int haMode;

    /**
     * Create a new StatementProcessor.
     * To Build it correctly, a statement list is needer to register EPL statements in the CEP core.
     * It is necessary configure a HA mode too.
     *
     * @param statementList Statement object list with the EPL statements that maust be active when cep engine starts
     * @param aiHAMode      Esper High Availability Mode. STANDALONE_HA_MODE = 0; BERKELEYDB_HA_MODE = 1; MYSQL_HA_MODE = 2;
     */
    public CepEngine(List<Statement> statementList, int aiHAMode) {

        if (aiHAMode != STANDALONE_HA_MODE &&
                aiHAMode != BERKELEYDB_HA_MODE &&
                aiHAMode != MYSQL_HA_MODE) {
            this.haMode = STANDALONE_HA_MODE; //Default Mode
        } else {
            this.haMode = aiHAMode;
        }
        this.statements = statementList;
        registerEsperStatements();
    }

    /**
     * Register and activate statements into cep engine.
     * Statements to be registered must be availabe as edStatement objects
     * in statements List.
     */
    private void registerEsperStatements() {
        if (statements == null || statements.size() == 0) {
            log.info("No statements defined for this CEP Engine");
            return;
        }

        cepEngine = initCepEngine();

        statements.forEach(statement -> {
            try {
                statement.register(cepEngine);
            } catch (EPException exception) {
                log.error("Failure registering statement, This statement will be avoided. ");
            }
        });
    }

    /**
     * Instantiate a new cep engine and set it up
     * with the indicated configuration in haMode
     * attribute.
     *
     * @return EPServiceProvider Instantiated Esper cep engine
     */
    private EPServiceProvider initCepEngine() {
        log.info("Initializing CEP Engine");
        log.info("Configurating CEP Engine with High Availability Mode: " + haMode);

        Configuration configuration = null;

        switch (haMode) {
            case BERKELEYDB_HA_MODE:
                configuration = new ConfigurationHA();
                // Load Esper base configuration
                configuration.configure("StatementProcessor.esper.xml");

                //Load EsperHA BerkeleyDB configuration
                configuration.configure("StatementProcessor.berkeleydbha.xml");
                break;

            default:
                log.info("EP Engine starting with  STANDALONE_HA_MODE");
                configuration = new Configuration();
                configuration.configure("StatementProcessor.esper.xml");
                break;

        }


        EPServiceProvider engine = EPServiceProviderManager.getDefaultProvider(configuration);

        return engine;
    }

    /**
     * Return a reference to cepEngine
     *
     * @return Cep Engine object.
     */
    public EPServiceProvider getCepEngine() {
        return cepEngine;
    }

    /**
     * Implements the  Esper Engine graceful shutdown.
     * This is the recommended shutdown method in order to assure
     * the correct HA storage
     */
    @Override
    @PreDestroy
    public void shutdown() {
        log.info("Starting CEP Engine graceful SHUTDOWN");
        cepEngine.destroy();
    }


}
