package com.tl.consumer.Configure;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


//topic配置
//@Configuration
//public class RabbitmqConfig {
//
//    public static final String QUEUE_INFORM_EMAIL = "queue_inform_email";
//    public static final String QUEUE_INFORM_SMS = "queue_inform_sms";
//    public static final String EXCHANGE_TOPICS_INFORM="exchange_topics_inform";
//    public static final String ROUTINGKEY_EMAIL="inform.#.email.#";//代表订单信息推送
//    public static final String ROUTINGKEY_SMS="inform.#.sms.#";//代表通知消息
//
//    //声明交换机
//    @Bean(EXCHANGE_TOPICS_INFORM)
//    public Exchange EXCHANGE_TOPICS_INFORM(){
//        //durable(true) 持久化，mq重启之后交换机还在
//        return ExchangeBuilder.topicExchange(EXCHANGE_TOPICS_INFORM).durable(true).build();
//    }
//
//    //声明QUEUE_INFORM_EMAIL队列
//    @Bean(QUEUE_INFORM_EMAIL)
//    public Queue QUEUE_INFORM_EMAIL(){
//        return new Queue(QUEUE_INFORM_EMAIL);
//    }
//    //声明QUEUE_INFORM_SMS队列
//    @Bean(QUEUE_INFORM_SMS)
//    public Queue QUEUE_INFORM_SMS(){
//        return new Queue(QUEUE_INFORM_SMS);
//    }
//
//    //ROUTINGKEY_EMAIL队列绑定交换机，指定routingKey
//    @Bean
//    public Binding BINDING_QUEUE_INFORM_EMAIL(@Qualifier(QUEUE_INFORM_EMAIL) Queue queue,
//                                              @Qualifier(EXCHANGE_TOPICS_INFORM) Exchange exchange){
//        return BindingBuilder.bind(queue).to(exchange).with(ROUTINGKEY_EMAIL).noargs();
//    }
//    //ROUTINGKEY_SMS队列绑定交换机，指定routingKey
//    @Bean
//    public Binding BINDING_ROUTINGKEY_SMS(@Qualifier(QUEUE_INFORM_SMS) Queue queue,
//                                          @Qualifier(EXCHANGE_TOPICS_INFORM) Exchange exchange){
//        return BindingBuilder.bind(queue).to(exchange).with(ROUTINGKEY_SMS).noargs();
//    }
//
//}

//direct直连交换机配置
@Configuration
public class RabbitmqConfig {

    //websocket 消息队列
    public static final String msg_queue = "msg_queue";

    //消息交换机
    public static final String msg_exchang = "tanlong";

    //消息路由键
    public static final String msg_routing_key = "tl";

    /**
     * 消息队列
     * @return
     */
    @Bean
    public Queue msgQueue(){
        return new Queue(msg_queue);
    }

    @Bean
    public DirectExchange directExchange(){
        return new DirectExchange(msg_exchang);
    }

    /**
     * 消息队列绑定消息交换机
     * @return
     */
    @Bean
    public Binding msgBinding(){
        return BindingBuilder.bind(msgQueue()).to(directExchange()).with(msg_routing_key);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(new Jackson2JsonMessageConverter());
        return template;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        return factory;
    }

}