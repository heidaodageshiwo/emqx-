package com.emqx.emaxdemo.kafka0;

import java.util.Optional;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * java类简单作用描述
 *
 * @ProjectName: emaxdemo
 * @Package: com.emqx.emaxdemo.kafka0
 * @ClassName: Consumer
 * @Description: java类作用描述
 * @Author: zhangq
 * @CreateDate: 2021-11-17 16:57
 * @UpdateUser: zhangq
 * @UpdateDate: 2021-11-17 16:57
 * @UpdateRemark: The modified content
 * @Version: 1.0 *
 */
@Component
public class Consumer {

  @KafkaListener(topics = {"test"})
  public void listen(ConsumerRecord<?, ?> record) {

    Optional<?> kafkaMessage = Optional.ofNullable(record.value());

    if (kafkaMessage.isPresent()) {

      Object message = kafkaMessage.get();
      System.out.println("---->" + record);
      System.out.println("---->" + message);

    }

  }
}
