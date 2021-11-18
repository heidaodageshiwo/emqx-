package com.emqx.emaxdemo.kafka0.producer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.Date;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * java类简单作用描述
 *
 * @ProjectName: emaxdemo
 * @Package: com.emqx.emaxdemo.kafka0.producer
 * @ClassName: Producer
 * @Description: java类作用描述
 * @Author: zhangq
 * @CreateDate: 2021-11-17 16:58
 * @UpdateUser: zhangq
 * @UpdateDate: 2021-11-17 16:58
 * @UpdateRemark: The modified content
 * @Version: 1.0 *
 */
@Component
public class Producer {
  @Autowired
  private KafkaTemplate kafkaTemplate;

  private static Gson gson = new GsonBuilder().create();

  //发送消息方法
  public void send() {
    Message message = new Message();
    message.setId("KFK_"+System.currentTimeMillis());
    message.setMsg(UUID.randomUUID().toString());
    message.setSendTime(new Date());
    kafkaTemplate.send("test", gson.toJson(message));
  }
}
