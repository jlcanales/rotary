package org.rotarysource.listener;

/**
 * Non abstract class used to be Mocked by
 * Mockito
 * @author J. L. Canales
 *
 */
public class ObjectBaseMock extends ObjectBaseListener<Integer> {

	@Override
	public void processEvent(Integer event) {
		// This method is intenctionaly Empty.
		// It will be Mocked by Mockito
		
	}

}
