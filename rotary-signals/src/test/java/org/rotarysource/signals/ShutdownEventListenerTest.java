package org.rotarysource.signals;

import javax.annotation.Resource;
import static org.mockito.Mockito.*;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.rotarysource.signals.shutdown.ShutdownEvent;
import org.rotarysource.signals.shutdown.ShutdownEventListener;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


import junit.framework.TestCase;



@RunWith(SpringJUnit4ClassRunner.class)

@ContextConfiguration(locations = { "classpath:CepEngine-TestConfig.xml" })
public class ShutdownEventListenerTest extends TestCase {

	/**
	 * Shutdown listener to support execution
	 */
	@Resource
	ShutdownEventListener shutdownEventListenerSpy;

	@Resource
	SignaledComponentMock signaledComponentMockSpy;
	
	
	/**
	 * Test AppContext Loading to assure all needed beans
	 * are properly configured.
	 */
	@Test
	public void loadAppContextTest(){
		Assert.assertTrue(true);
	}

	
	/**
	 * Test AppContext Loading to assure all needed beans
	 * are properly configured.
	 */
	@Test
	public void normalCaseTest(){
		
		//Given
		ApplicationContext ctx = AppContext.getApplicationContext();
		
		//When
		//Send Shutdown Event
		Assert.assertFalse(shutdownEventListenerSpy.isShudownExecuted());
		ShutdownEvent event = new ShutdownEvent(this, "Test shutdown Event");
		ctx.publishEvent( event);
		
		//Then

		
		verify(shutdownEventListenerSpy).onApplicationEvent(event);
		verify(signaledComponentMockSpy, times(6)).shutdown(); //Even the aapcontext define only 1 listener. Mockito create a new object with teh same
		               										 //same interface as shutdownEventListner. Spring detects 2 Listener (the original and the mockito one)
		 													 // so, it call listener twice.
		
		Assert.assertTrue(shutdownEventListenerSpy.isShudownExecuted());
	}
	

}
