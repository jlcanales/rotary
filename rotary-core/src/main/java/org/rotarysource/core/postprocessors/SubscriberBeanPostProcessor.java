package org.rotarysource.core.postprocessors;

import org.rotarysource.core.CepEngine;
import org.rotarysource.core.annotations.Subscriber;
import org.rotarysource.core.statements.StatmntEPLSubscriber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;


/**
 * {@link org.springframework.beans.factory.config.BeanPostProcessor} implementation
 * to create statement objects for all beans annotated as @Subscriber.
 * <p>
 * <p>The motivation for the existence of this BeanPostProcessor is to make easier the
 * Subscribers registration in CepEngine and the association between EPL statement and the
 * subscriber that have to be executed when the statement is triggered.
 */
@Component
public class SubscriberBeanPostProcessor implements BeanPostProcessor, Ordered, BeanFactoryAware {

    private static Logger log = LoggerFactory.getLogger(SubscriberBeanPostProcessor.class);

    ConfigurableBeanFactory beanFactory;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class clazz = bean.getClass();

        if (clazz.isAnnotationPresent(Subscriber.class)) {
            final Subscriber annotation = (Subscriber) clazz.getAnnotation(Subscriber.class);
            log.info("Creating StatemntSingleEPL for Subscriber Bean '" + beanName + "' EPL : " + annotation.eplStatement());
            StatmntEPLSubscriber statmntEPLSubscriber = new StatmntEPLSubscriber(annotation.eplStatement());
            statmntEPLSubscriber.setSubscriber(bean);
            statmntEPLSubscriber.setDefaultActive(annotation.active());
            beanFactory.registerSingleton(beanName + "- StatementEPL", statmntEPLSubscriber);
        }

        return bean;
    }

    @Override
    public int getOrder() {
        return 0;
    }


    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (ConfigurableBeanFactory) beanFactory;
    }
}
