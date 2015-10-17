package me.zw.mq.test;

/** 
 * @author Administrator 
 * @desctiption 结合一个例子，深入理解JMS的基本概念 
 * 消息的消费者接收消息可以采用两种方式： 
 * 1、consumer.receive() 或 consumer.receive(int timeout)； 
 * 2、注册一个MessageListener。 
 * 采用第一种方式，消息的接收者会一直等待下去，直到有消息到达，或者超时。 
 * 后一种方式会注册一个监听器，当有消息到达的时候，会回调它的onMessage()方法。 
 * 
 * @see http://blog.csdn.net/wl_ldy/article/details/7884534
 */
  
import javax.jms.Connection;  
import javax.jms.ConnectionFactory;  
import javax.jms.JMSException;  
import javax.jms.Message;  
import javax.jms.MessageConsumer;  
import javax.jms.MessageListener;  
import javax.jms.MessageProducer;  
import javax.jms.Queue;  
import javax.jms.Session;  
import javax.jms.TextMessage;  
  
import org.apache.activemq.ActiveMQConnectionFactory;  
import org.apache.activemq.command.ActiveMQQueue;  
  
public class MessageSendAndReceive {  
  
    /** 
     * @param args 
     * @throws JMSException  
     */  
    public static void main(String[] args) throws JMSException {  
        // TODO Auto-generated method stub  
        ConnectionFactory factory = new ActiveMQConnectionFactory("vm://localhost");    
           
        Connection connection = factory.createConnection();    
        connection.start();    
        //创建消息的Destination   
        Queue queue = new ActiveMQQueue("testQueue");    
           
        final Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);   
        //创建将要发送的消息  
        Message message = session.createTextMessage("Hello JMS!");    
        //创建消息生产者发送消息   
        MessageProducer producer = session.createProducer(queue);    
        producer.send(message);    
       
        System.out.println("Send Message Completed!");    
        //创建消息的接收者   
        MessageConsumer comsumer = session.createConsumer(queue);   
        //消息的消费者接收消息的第一种方式：consumer.receive() 或 consumer.receive(int timeout)；  
        //Message recvMessage = comsumer.receive();    
        //System.out.println(((TextMessage)recvMessage).getText());   
        //消息的消费者接收消息的第二种方式：注册一个MessageListener  
        comsumer.setMessageListener(new MessageListener(){  
            public void onMessage(Message msg) {  
                // TODO Auto-generated method stub  
                TextMessage textMsg=(TextMessage)msg;  
                try {  
                    System.out.println(textMsg.getText());  
                } catch (JMSException e) {  
                    e.printStackTrace();  
                }  
            }  
              
        });  
    }
}