package org.rotarysource.listener.mail;

import java.util.HashMap;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;

import static org.mockito.Mockito.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.rotarysource.listener.mail.domain.MailDataBean;
import org.rotarysource.listener.mail.html.VelocityHTMLMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


import junit.framework.TestCase;



@RunWith(SpringJUnit4ClassRunner.class)

@ContextConfiguration(locations = { "classpath:MailListenerTest-config.xml" })
public class VelocityMailSenderTest extends TestCase {

	/**
	 * Cep Engine to support listener execution
	 */
	@Resource
	private VelocityHTMLMailSender velocityHTMLMailSender;

	@Resource 
	private JavaMailSenderImpl javaMailSenderMock;
	
	@Mock
	MimeMessage mimeMessageMock;
	
	private MailDataBean mailData;


	/**
	 * Initialize Mocks in order to create a Mock structure that allow
	 * contractStubMock return correct Axis2 interface objects to he 
	 * Object Under Test
	 */
	@Before
	public void setUp(){
		MockitoAnnotations.initMocks( this );
			
			// Prepare javaMailSenderMock to return a mock object representing
			// MimeMessage
			when(javaMailSenderMock.createMimeMessage()).thenReturn(mimeMessageMock);
		
				
		
		// Mail Data inicialization
		mailData = new MailDataBean();
		
		mailData.setFromAddress("mmm@test.com");
		mailData.setFromName("Test origin");
		mailData.setSubject("HTML test ");
		mailData.setTo("aaa@test.com");
	}
	
	/**
	 * Test AppContext Loading to assure all needed beans
	 * are properly configured.
	 */
	@Test
	public void loadAppContextTest(){
		Assert.assertTrue(true);
	}

	/**
	 * Basic smoke test for send method.
	 */
	@Test
	public void sendSmokeTest(){
		
		HashMap<String, Object> composeParams = new HashMap<String, Object>();
		composeParams.put("param1", "param1 value");
		
		velocityHTMLMailSender.sendMail(mailData, composeParams, null, null);
		
	}

}
