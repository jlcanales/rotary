package org.rotarysource.inputadapter.jmsinputadapter;

import java.io.StringReader;
import java.io.StringWriter;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.rotarysource.events.BasicEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.MessageConverter;


/**
 * ProdEvent Message Converter.
 * It make conversion between XML ProdEvent representation and ProdEvent Bean
 * @implements MessageConverter Spring framework interface for Converters to be 
 * 			   injected in its JMS support beans
 @author J. L. Canales
 * 
 */
public class BasicEventXMLMessageConverter implements MessageConverter{
	private static Logger log = LoggerFactory.getLogger(BasicEventXMLMessageConverter.class);

	/**
	* Create a new ProdEventXMLMessageConverter, for bean-style usage.
	*/	 
	 public BasicEventXMLMessageConverter(){
		 
	 }
	 
	 
	/**
	* Convert a JMS message XML payload to a BasicEvent Object
	* @param Message JMS TextMessage to be converted
	* @return BasicEvent object
	*/ 
	@Override
	public Object fromMessage(Message message) throws JMSException,
	MessageConversionException {
       
		if(!(message instanceof TextMessage)){
            log.error("Message is not a Text XML Message "+ message);
			throw new MessageConversionException(" Message is not a Text XML Message");
		}
		
		BasicEvent 	       		basEvent   = new BasicEvent();
		TextMessage        		textMsg    = (TextMessage) message;
		
		try {
			//the following 2 lines of code will load the message and map its contents 
			// to the BasicEvent Object
			JAXBContext context = JAXBContext.newInstance(BasicEvent.class);
			
			// Parse the XML and return an instance of the AppConfig class
			StringReader reader = new StringReader(textMsg.getText());
			basEvent = (BasicEvent) context.createUnmarshaller().unmarshal(reader);
			
		} catch (JAXBException excpt) {
	        log.error("Message has not right XMLBeans Format. Nexted Exception: {}", excpt);
	        log.debug("Failed Message: {}", textMsg.getText());
			
	        throw new MessageConversionException("Message has not right XML Format");
		} catch (Exception excpt){
	        log.error("Message could not be interpreted. Not identified exception: {}", excpt);
	        log.debug("Failed Message: {}", textMsg.getText());
	        throw new MessageConversionException("Message could not be interpreted. Not identified exception");
		}
		return basEvent;
	}

	/**
	* Convert a BasicEvent Object to a a JMS message XML payload
	* @param Object BasicEvent object
	* @return Message TextMessage with BasicEvent XML payload
	*/ 
	@Override
	public Message toMessage(Object eventObject, Session session) throws JMSException,
			MessageConversionException {

		if(!(eventObject instanceof BasicEvent)){
            log.error("Event is not a BasicEvent Object ");
			throw new MessageConversionException("Event is not an EventObject");
		}
		
		
		BasicEvent 	basEvent   = (BasicEvent) eventObject;
		String      stXMLMarshalled = null;
		try {
			//the following 2 lines of code will load the message and map its contents 
			// to the BasicEvent Object
			JAXBContext context = JAXBContext.newInstance(BasicEvent.class);
			
			// Parse teh XML and return an instance of the AppConfig class
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
			
			StringWriter stWriter = new StringWriter();
			marshaller.marshal(basEvent, stWriter);
			stXMLMarshalled = stWriter.toString(); 
			
		} catch (JAXBException excpt) {
	        log.error("Object could not be Marshalled. Nexted Exception: {}", excpt);
			
	        throw new MessageConversionException("Message has not right XML Format");
		}
		
        Message message = session.createTextMessage(stXMLMarshalled);
		return message;
	}
}
