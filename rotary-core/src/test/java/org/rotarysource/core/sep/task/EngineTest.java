package org.rotarysource.core.sep.task;

import javax.annotation.Resource;
import static org.mockito.Mockito.*;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.rotarysource.core.sep.SepEngine;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


import junit.framework.TestCase;



@RunWith(SpringJUnit4ClassRunner.class)

@ContextConfiguration(locations = { "classpath:SepEngine-TestConfig.xml" })
public class EngineTest extends TestCase {

	/**
	 * Cep Engine to support listener execution
	 */
	@Resource
	SepEngine sepEngine;
	


	
	/**
	 * Test AppContext Loading to assure all needed beans
	 * are properly configured.
	 */
	@Test
	public void loadAppContextTest(){
		Assert.assertTrue(true);
	}

	
}
