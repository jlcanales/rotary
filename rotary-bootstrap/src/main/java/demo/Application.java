package demo;

import org.rotarysource.signals.shutdown.ShutdownEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.jms.listener.AbstractMessageListenerContainer;

import java.util.Arrays;

@Configuration
//@ComponentScan
//@EnableAutoConfiguration
@ImportResource("AppContext.xml")
public class Application {

    private static Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {


        log.info("CEP System version: 1.1.1.SNAPSHOT");
        log.info("Starting server shell");


        // Initialize engine
        log.info("Getting Esper engine instance");

        ApplicationContext ctx = SpringApplication.run(Application.class, args);

        // Connect to JMS
        log.info("Connecting to JMS server through a Listener Container");
        AbstractMessageListenerContainer jmsListenerContainer  = (AbstractMessageListenerContainer) ctx.getBean("listenerContainer");

        jmsListenerContainer.start();


        // Register shutdown hook
        Runtime.getRuntime().addShutdownHook(
                new Thread(){
                    public void run()
                    {
/*		                ApplicationContext springAppContext = new ClassPathXmlApplicationContext("AppContext.xml");

		            	ShutdownEvent event = new ShutdownEvent(this, "Shutdown Event triggered by main shutdownHook");
		            	springAppContext.publishEvent( event);
*/
                    }
                }
        );

        log.info("Statements Processor started.");


        ShutdownEventListener shutdownEventListener = (ShutdownEventListener) ctx.getBean("shutdownEventListener");


        do
        {
            // sleep
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        while (!shutdownEventListener.isShudownExecuted());

        ((ConfigurableApplicationContext)ctx).close();
        log.info("EVENT DRIVEN CONTROL SYSTEM: Finished");

        System.exit(-1);

    }
}
