package com.emqx.emaxdemo.kafka;

import java.util.Properties;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

/**
 * java类简单作用描述
 *
 * @ProjectName: emaxdemo
 * @Package: com.emqx.emaxdemo.kafka
 * @ClassName: KafkaTest
 * @Description: java类作用描述
 * @Author: zhangq
 * @CreateDate: 2021-11-17 15:22
 * @UpdateUser: zhangq
 * @UpdateDate: 2021-11-17 15:22
 * @UpdateRemark: The modified content
 * @Version: 1.0 *
 */
public class KafkaTestProduct {

  public static void main(String[] args) {
    Properties props = new Properties();
    //kafka 集群，broker-list
    props.put("bootstrap.servers", "192.168.56.211:9092");
    props.put("acks", "all");
    //重试次数
    props.put("retries", 1);
    //批次大小
    props.put("batch.size", 16384);
    //等待时间
    props.put("linger.ms", 1);
    //RecordAccumulator 缓冲区大小
    props.put("buffer.memory", 33554432);
    props.put("key.serializer",
        "org.apache.kafka.common.serialization.StringSerializer");
    props.put("value.serializer",
        "org.apache.kafka.common.serialization.StringSerializer");
    Producer<String, String> producer = new
        KafkaProducer<>(props);
    for (int i = 0; i < 100; i++) {
      producer.send(new ProducerRecord<String, String>("first",
          Integer.toString(i), Integer.toString(i)));
    }
    producer.close();

  }
}
