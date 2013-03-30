package org.rotarysource.listener;

import javax.annotation.Resource;
import static org.mockito.Mockito.*;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.rotarysource.core.CepEngine;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


import junit.framework.TestCase;



@RunWith(SpringJUnit4ClassRunner.class)

@ContextConfiguration(locations = { "classpath:CepEngine-config.xml" })
public class ObjectBaseListenerTest extends TestCase {

	/**
	 * Cep Engine to support listener execution
	 */
	@Resource
	CepEngine cepEngine;
	
	@Resource
	ObjectBaseMock objectBaseListenerSpy;


	
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
		Integer event = 10;
		
		//When
		//Send Event
		cepEngine.getCepEngine().getEPRuntime().sendEvent(event);
		
		//Then
		verify(objectBaseListenerSpy).processEvent(event);
	}
	
	
}
