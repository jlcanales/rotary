package org.rotarysource.signals;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Non abstract class used to be Mocked by
 * Mockito
 * @author J. L. Canales
 *
 */
public class SubscriberMock {

	private static Logger log = LoggerFactory.getLogger(SubscriberMock.class);
	
	public void update(Integer event) {
		// This method is intenctionaly Empty.
		// It will be Mocked by Mockito
		log.info("Received Integer Event : {}",event);
	}

}
