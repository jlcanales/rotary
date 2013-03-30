package org.rotarysource.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Non abstract class used to be Mocked by
 * Mockito
 * @author J. L. Canales
 *
 */
public class ObjectBaseMock extends ObjectBaseListener<Integer> {

	private static Logger log = LoggerFactory.getLogger(ObjectBaseMock.class);
	
	@Override
	public void processEvent(Integer event) {
		// This method is intenctionaly Empty.
		// It will be Mocked by Mockito
		log.info("Received Integer Event : {}",event);
	}

}
