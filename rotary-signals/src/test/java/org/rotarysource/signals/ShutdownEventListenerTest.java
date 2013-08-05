package org.rotarysource.signals;

import javax.annotation.Resource;
import static org.mockito.Mockito.*;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


import junit.framework.TestCase;



@RunWith(SpringJUnit4ClassRunner.class)

@ContextConfiguration(locations = { "classpath:CepEngine-TestConfig.xml" })
public class ShutdownEventListenerTest extends TestCase {

	/**
	 * Cep Engine to support listener execution
	 */
	@Resource
	ShutdownEventListener shutdownEventListenerSpy;

	
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
		ShutdownEvent event = new ShutdownEvent(this, "Test shutdown Event");
		ctx.publishEvent( event);
		
		//Then
		verify(shutdownEventListenerSpy).onApplicationEvent(event);
	}
	

}
