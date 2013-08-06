package org.rotarysource.signals;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Non abstract class used to be Mocked by
 * Mockito
 * @author J. L. Canales
 *
 */
public class SignaledComponentMock implements SignalCapable{

	private static Logger log = LoggerFactory.getLogger(SignaledComponentMock.class);

	private int callCount;
	
	public SignaledComponentMock(){
		callCount = 1;
	}

	@Override
	public void shutdown() {
		log.info("Received shudown signal. Calls Number: {}", callCount);
		
		callCount++;
		
	}

}
