package com.emqx.emaxdemo.kafka;

import java.util.Arrays;
import java.util.Properties;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

/**
 * java类简单作用描述
 *
 * @ProjectName: emaxdemo
 * @Package: com.emqx.emaxdemo.kafka
 * @ClassName: KafkaTestConsumer
 * @Description: java类作用描述
 * @Author: zhangq
 * @CreateDate: 2021-11-17 15:58
 * @UpdateUser: zhangq
 * @UpdateDate: 2021-11-17 15:58
 * @UpdateRemark: The modified content
 * @Version: 1.0 *
 */
public class KafkaTestConsumer {

  public static void main(String[] args) {
    Properties props = new Properties();
    props.put("bootstrap.servers", "192.168.56.211:9092");
    props.put("group.id", "test");
    props.put("enable.auto.commit", "true");
    props.put("auto.commit.interval.ms", "5000");
    props.put("key.deserializer",
        "org.apache.kafka.common.serialization.StringDeserializer");
    props.put("value.deserializer",
        "org.apache.kafka.common.serialization.StringDeserializer");
    KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
    consumer.subscribe(Arrays.asList("first"));
    while (true) {
      ConsumerRecords<String, String> records =
//          consumer.poll(100);
          consumer.poll(100);
      for (ConsumerRecord<String, String> record : records) {
        System.out.printf("offset = %d, key = %s, value  = %s%n", record.offset(), record.key(), record.value());
      }
    }

  }

}
