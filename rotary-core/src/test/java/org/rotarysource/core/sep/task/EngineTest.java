package org.rotarysource.core.sep.task;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.annotation.Resource;
import static org.mockito.Mockito.*;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.quartz.SchedulerException;
import org.rotarysource.core.sep.SepEngine;
import org.rotarysource.core.sep.job.JobDescription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


import junit.framework.TestCase;



@RunWith(SpringJUnit4ClassRunner.class)

@ContextConfiguration(locations = { "classpath:SepEngine-TestConfig.xml" })
public class EngineTest extends TestCase {

    /** 
     * Apache commons login logger instance
     */	
	private static Logger log = LoggerFactory.getLogger(EngineTest.class);	
	/**
	 * Sep Engine to support listener execution
	 */
	@Resource
	SepEngine sepEngine;
	
	@Resource
	TaskMock taskMockSpy;

	
	@Before
	public void setup(){
	    Mockito.reset(taskMockSpy);
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
	 * Test normal task scheduling
	 *    This test show how to use sepEngine to schedule tasks in fixed
	 * dates.
	 *    It assure scheduling works fine.
	 */
	@Test
	public void scheduleJobNormalCaseTest(){
		
		log.info("==============================");
		log.info("Schedule Job Normal Case Test ");
		log.info("==============================");

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		//Given
		JobDescription jobDesk = new JobDescription("NormalCaseJob", "TestGroup", "taskMockJob");

		Calendar scheduleCal = Calendar.getInstance();
		scheduleCal.add(Calendar.SECOND, 5);		
		log.info("Scheduling Task to date: {}", df.format(scheduleCal.getTime()));

		jobDesk.setFireDate(scheduleCal.getTime());
		//No params set yet
		//jobDesk.setTaskParams(aiTaskParams);
		

		try {
			
			//When
			//Scheduled job
			sepEngine.scheduleJob(jobDesk);

			//Then
			//After 10 seconds Task will run
			
			//Then
			verify(taskMockSpy,timeout(7000)).run();

		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		
	}

	/**
	 * Test normal task scheduling
	 *    This test show how to use sepEngine to schedule tasks in fixed
	 * dates.
	 *    It assure scheduling works fine.
	 */
	@Test
	public void scheduleJobMultipleTest(){
		
		log.info("================================");
		log.info("Schedule Multiple Job Type Test ");
		log.info("================================");

		
		//Given
		JobDescription jobDesk = new JobDescription("MultipleJob1", "TestGroup", "taskMockJob");
		JobDescription jobDesk2 = new JobDescription("MultipleJob2", "TestGroup", "taskMockJobPair");
		
		Calendar scheduleCal = Calendar.getInstance();

		scheduleCal.add(Calendar.SECOND, 5);		
		jobDesk.setFireDate(scheduleCal.getTime());

		scheduleCal.add(Calendar.SECOND, 2);		
		jobDesk2.setFireDate(scheduleCal.getTime());		
		
		//No params set yet
		//jobDesk.setTaskParams(aiTaskParams);
		

		try {
			
			//When
			//Scheduled job
			sepEngine.scheduleJob(jobDesk);
			sepEngine.scheduleJob(jobDesk2);

			//Then
			//After 10 seconds Task will run

			verify(taskMockSpy,timeout(9000).times(2)).run();

		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
