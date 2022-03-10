package com.crop.hellorocketmq;

import com.crop.hellorocketmq.model.OrderPaidEvent;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.support.MessageBuilder;

import javax.annotation.Resource;
import java.math.BigDecimal;

@SpringBootTest
class HelloRocketmqApplicationTests {

	@Resource
	private RocketMQTemplate rocketMQTemplate;

	/**
	 * rocketMQTemplate 同步、异步、有序发送消息
	 *
	 * @author linmeng
	 * @date 2022年3月10日 15:21
	 * @return void
	 */
	@Test
	void messageSend(){
		//send message synchronously
		rocketMQTemplate.convertAndSend("test-topic-1", "Hello, World!");
		//send spring message
		rocketMQTemplate.send("test-topic-1", MessageBuilder.withPayload("Hello, World! I'm from spring message").build());
		//send messgae asynchronously
		rocketMQTemplate.asyncSend("test-topic-2", new OrderPaidEvent("T_001", new BigDecimal("88.00")), new SendCallback() {
			@Override
			public void onSuccess(SendResult var1) {
				System.out.printf("async onSucess SendResult=%s %n", var1);
			}

			@Override
			public void onException(Throwable var1) {
				System.out.printf("async onException Throwable=%s %n", var1);
			}

		});
		//Send messages orderly
		rocketMQTemplate.syncSendOrderly("orderly_topic", MessageBuilder.withPayload("Hello, World").build(),"hashkey");

		//rocketMQTemplate.destroy(); // notes:  once rocketMQTemplate be destroyed, you can not send any message again with this rocketMQTemplate

	}
}
