package me.zw.mq.topic;

import javax.jms.Connection;  
import javax.jms.JMSException;  
import javax.jms.Message;  
import javax.jms.MessageConsumer;  
import javax.jms.MessageListener;  
import javax.jms.MessageProducer;  
import javax.jms.Session;  
import javax.jms.TextMessage;  
import javax.jms.Topic;  
  
import org.apache.activemq.ActiveMQConnectionFactory;  
import org.apache.activemq.command.ActiveMQTopic;  
  

/*
 * @see http://blog.csdn.net/wl_ldy/article/details/7884577
 * 基于Topic的发布订阅模型
 * 
 */
public class TopicTest {  
  
    /** 
     * @param args 
     * @throws JMSException  
     */  
    public static void main(String[] args) throws JMSException {  
        // TODO Auto-generated method stub  
        ActiveMQConnectionFactory factory=new ActiveMQConnectionFactory("vm://localhost");  
        Connection connection=factory.createConnection();  
        connection.start();  
        //创建一个Topic  
        Topic topic=new ActiveMQTopic("testTopic");  
        Session session=connection.createSession(false, Session.AUTO_ACKNOWLEDGE);  
        //注册消费者1  
        MessageConsumer consumer1=session.createConsumer(topic);  
        consumer1.setMessageListener(new MessageListener(){  
            public void onMessage(Message m) {  
                try {  
                    System.out.println("Consumer1 get: "+((TextMessage)m).getText());  
                } catch (JMSException e) {  
                    e.printStackTrace();  
                }  
            }  
              
        });  
        //注册消费者2  
        MessageConsumer consumer2=session.createConsumer(topic);  
        consumer2.setMessageListener(new MessageListener(){  
            public void onMessage(Message m) {  
                try {  
                    System.out.println("Consumer2 get: "+((TextMessage)m).getText());  
                } catch (JMSException e) {  
                    e.printStackTrace();  
                }  
            }  
              
        });  
        //创建一个生产者，然后发送多个消息。  
        MessageProducer producer=session.createProducer(topic);  
        for(int i=0;i<10;i++){  
            producer.send(session.createTextMessage("produce Message:"+i));  
        }
    }  
  
}

