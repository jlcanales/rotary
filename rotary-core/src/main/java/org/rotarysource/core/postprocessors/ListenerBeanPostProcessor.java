package org.rotarysource.core.postprocessors;

import com.espertech.esper.client.StatementAwareUpdateListener;
import com.espertech.esper.client.UpdateListener;
import org.rotarysource.core.annotations.Listener;
import org.rotarysource.core.statements.StatmntSingleEPL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 *  * {@link org.springframework.beans.factory.config.BeanPostProcessor} implementation
 * to create statement objects for all beans annotated as @Listener.
 * <p>
 * The motivation for the existence of this BeanPostProcessor is to make easier the
 * Listeners registration in CepEngine and the association between EPL statement and the
 * listener that have to be executed when the statement is triggered.
 *
 * Addition of multiple listeners to the same EPL is not supported yet.
 */
@Component
public class ListenerBeanPostProcessor implements BeanPostProcessor, Ordered, BeanFactoryAware {

    private static Logger log = LoggerFactory.getLogger(ListenerBeanPostProcessor.class);

    private ConfigurableBeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (ConfigurableBeanFactory) beanFactory;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class clazz = bean.getClass();

        if (clazz.isAnnotationPresent(Listener.class) &&
                (bean instanceof UpdateListener || StatementAwareUpdateListener.class.isAssignableFrom(clazz))) {
            final Listener annotation = (Listener) clazz.getAnnotation(Listener.class);
            log.info("Creating StatemntSingleEPL for Listener Bean '" + beanName + "' EPL : " + annotation.eplStatement());

            StatmntSingleEPL statmntSingleEPL = new StatmntSingleEPL(annotation.eplStatement());
            List<UpdateListener> listeners = new ArrayList<>();
            listeners.add((UpdateListener) bean); //TODO: arreglar para que no produzca class cast exception
            statmntSingleEPL.setListeners(listeners);
            statmntSingleEPL.setDefaultActive(annotation.active());

            beanFactory.registerSingleton(beanName + "- StatementSingleEPL", statmntSingleEPL);

        }

        return bean;
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
