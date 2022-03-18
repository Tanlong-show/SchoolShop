package com.tl.consumer.Receiver;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.impl.AMQImpl;
import com.tl.common.entity.Information;
import com.tl.consumer.Configure.RabbitmqConfig;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.util.Map;


//@Component
//public class ReceiveHandler {
//    //监听sms队列，公告消息
//    @RabbitListener(queues = {RabbitmqConfig.msg_queue})
//    public void receive_sms(Object msg, Message message, Channel channel) throws Exception {
//        System.out.println("SSS: "+msg);
//        Information information = (Information)getObjectFromBytes(message.getBody());
//        System.out.println("QUEUE_INFORM_SMS msg"+information.toString());
//    }
//
//
//    //字节码转化为对象
//    public  Object getObjectFromBytes(byte[] objBytes) throws Exception {
//        if (objBytes == null || objBytes.length == 0) {
//            return null;
//        }
//        ByteArrayInputStream bi = new ByteArrayInputStream(objBytes);
//        ObjectInputStream oi = new ObjectInputStream(bi);
//        return oi.readObject();
//    }
//
//}

//@Component
//@RabbitListener(queues = {RabbitmqConfig.msg_queue}, containerFactory="rabbitListenerContainerFactory")//监听的队列名称 TestDirectQueue
//public class ReceiveHandler {
//
//    @RabbitHandler
//    public void process(Information information) {
//        System.out.println("DirectReceiver消费者收到消息  : " + information.toString());
//    }
//
//
//
//}