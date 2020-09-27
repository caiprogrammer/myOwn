package com.itmayiedu.common.kafka;

import java.util.Optional;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.log4j.Logger;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @author pengdan
 * 监听服务器上的kafka是否有相关的消息发过来
 * @date by 2020/09/21
 */
@Component
public class Consumer {

	private static Logger log = Logger.getLogger(Consumer.class);
	/**
     * 定义此消费者接收topics = "demo"的消息，与controller中的topic对应上即可
     * @param record 变量代表消息本身，可以通过ConsumerRecord<?,?>类型的record变量来打印接收的消息的各种信息
     */
		//@KafkaListener(topics = "demo9211")
		public void consumer(ConsumerRecord<?,?> consumerRecord){
	        //判断是否为null
	        Optional<?> kafkaMessage = Optional.ofNullable(consumerRecord.value());
	        log.info(">>>>>>>>>> record =" + kafkaMessage);
	        if(kafkaMessage.isPresent()){
	            //得到Optional实例中的值
	            Object message = kafkaMessage.get();
	            System.err.println("消费消息:"+message);
	        }
	    }

//		log.info("record.topic():"+record.topic());
//        System.out.printf("topic is %s, offset is %d, value is %s \n", record.topic(), record.offset(), record.value());
	}
