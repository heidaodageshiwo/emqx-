package com.emqx.emaxdemo.listener;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.nio.charset.StandardCharsets;

@Slf4j
public class MqttClientService {

  public static MqttClient sampleClient;

  public static MqttClient connect(String broker, String clientId) {

    // 内存存储
    MemoryPersistence persistence = new MemoryPersistence();

    try {
      // 创建客户端
      sampleClient = new MqttClient(broker, clientId, persistence);
      // 创建链接参数
      MqttConnectOptions connOpts = new MqttConnectOptions();
      // 设置超时时间 单位为秒
      connOpts.setConnectionTimeout(100);
      // 设置会话心跳时间 单位为秒
      connOpts.setKeepAliveInterval(180);
      // 设置自动重连
      connOpts.setAutomaticReconnect(true);
      // 在重新启动和重新连接时记住状态
      connOpts.setCleanSession(true);
//      sampleClient.setCallback(new SampleClient());
      // 设置连接的用户名
//            connOpts.setUserName(userName);
//            connOpts.setPassword(password.toCharArray());
      // 建立连接
      sampleClient.connect(connOpts);
//      sampleClient.subscribe("/v1/test/security/fw/cmd/policyRes");
      log.info("连接成功！！！");
//            sampleClient.connect();
      return sampleClient;
    } catch (MqttException me) {
            /*log.info("reason " + me.getReasonCode());
            log.info("msg " + me.getMessage());
            log.info("loc " + me.getLocalizedMessage());
            log.info("cause " + me.getCause());
            log.info("excep " + me);*/
      log.error("mqtt生产者,创建链接异常: {}", me.getMessage(), me);
    } catch (Exception e) {
      //e.printStackTrace();
      log.error("mqtt生产者,创建链接异常: {}", e.getMessage(), e);
    }
    return null;
  }


  public static void close() {
    // 断开连接
    try {
      sampleClient.disconnect();
      // 关闭客户端
      sampleClient.close();
    } catch (MqttException e) {
      //e.printStackTrace();
      log.error("边设备上报设备运行状态， 断开连接异常: {}", e.getMessage(), e);
    }

  }


  public static String send(String topic, String msg, Integer qos) {
    MqttClient connect = connect(MqttCommon.tcpUrl, MqttCommon.pre + UUID.randomUUID().toString().replace("-", ""));
    try {
      MqttMessage message = new MqttMessage();
      message.setQos(qos);
      message.setRetained(false);
      message.setPayload(msg.getBytes(StandardCharsets.UTF_8.name()));
      MqttDeliveryToken token = connect.getTopic(topic).publish(message);
      token.waitForCompletion();
      log.info("发送的消息内容为: {}", message.toString());
      return "成功!";
    } catch (Exception e) {
      log.error("MQTT client消息发送异常：topic=[{}]", topic, e);
      return "失败!";
    }
  }

  //安全密钥获取命令，终端向平台请求订阅

  public static void xgmm() {
    String topic = "/v1/test/security/cipher/request";
    // 订阅
    try {
      sampleClient.subscribe(topic);
    } catch (MqttException e) {
      e.printStackTrace();
    }
    // 设置回调
    sampleClient.setCallback(new MqttCallback() {
      @Override
      public void connectionLost(Throwable throwable) {
        log.info("connectionLost");
      }

      @Override
      public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
        log.info("Topic: " + topic + " Message: " + mqttMessage.toString());
        String msg = new String(mqttMessage.getPayload());
        try {
          JSONObject jsonObject = JSON.parseObject(msg);
          String clinetId = String.valueOf(jsonObject.get("clinetId"));

          send("/v1/test/security/cipher/response",
              "{\n"
                  + "\t\"mid\": 7,\n"
                  + "    \"deviceId\": \"test\",\n"
                  + "    \"timestamp\":\"2021-08-12 08:29:45\",\n"
                  + "    \"type\":\"EVENT_SKEY_STATUS\",\n"
                  + "    \"param\": {\n"
                  + "\t\t\t\"keyvalue\":\"1qaz2wsx\"\n"
                  + "        }\n"
                  + "}", 1);
          /*if (topic.endsWith("disconnected")) {
            log.info("设备【" + clinetId + "】已掉线");
          }

          if (topic.endsWith("connected")) {
            log.info("设备【" + clinetId + "】已上线");
          }*/
        } catch (Exception e) {
          log.error("监听设备上下线异常：{}", e.getStackTrace(), e);
        }
      }

      @Override
      public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

      }

    });
  }

}
