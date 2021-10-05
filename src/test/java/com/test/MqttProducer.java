package com.test;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

@Slf4j
public class MqttProducer {

  public static MqttClient sampleClient;
  static int qos = 1;

  public static void main(String[] args) {
    connect("tcp://127.0.0.1:11883", "pro1");
    send("/asdf",
        "{'fileName':'Java.zip','filePackUrl':'group1/M00/00/00/GduVZmCsn2OAdUrzD6fvgiUKmlg692.zip?attname=Java.zip'}");
  }

  public static void connect(String broker, String clientId) {

    // 内存存储
    MemoryPersistence persistence = new MemoryPersistence();

    try {
      // 创建客户端
      sampleClient = new MqttClient(broker, clientId, persistence);
      // 创建链接参数
      MqttConnectOptions connOpts = new MqttConnectOptions();
      // 在重新启动和重新连接时记住状态
      connOpts.setCleanSession(true);
      //connOpts.setUserName("subpub");
      //connOpts.setPassword("123456".toCharArray());
      connOpts.setAutomaticReconnect(true);
      // 设置超时时间 单位为秒
      //connOpts.setMaxReconnectDelay(64000);
      // 设置自动重连
      connOpts.setAutomaticReconnect(true);

      sampleClient.setCallback(new MqttPushCallBack());
      // 建立连接
      sampleClient.connect(connOpts);
      log.info("连接成功！！！");
//            sampleClient.connect();

    } catch (MqttException me) {
      log.info("reason " + me.getReasonCode());
      log.info("msg " + me.getMessage());
      log.info("loc " + me.getLocalizedMessage());
      log.info("cause " + me.getCause());
      log.info("excep " + me);
      me.printStackTrace();
      log.error("边设备上报设备运行状态,创建链接异常: {}", me.getMessage(), me);
    } catch (Exception e) {
      //e.printStackTrace();
      log.error("边设备上报设备运行状态,创建链接异常: {}", e.getMessage(), e);
    }
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

  public static String send(String topic, String msg) {
    try {
      // 创建消息
      MqttMessage message = new MqttMessage(msg.getBytes());
      // 设置消息的服务质量
      message.setQos(qos);
      // 发布消息
      MqttDeliveryToken token = sampleClient.getTopic(topic).publish(message);
      token.waitForCompletion();
      log.info("发送的消息内容为: {}", message.toString());
      return "成功!";
    } catch (MqttException e) {
      //e.printStackTrace();
      //log.error("【"+topic+"】发送消息异常: {}", e.getMessage(), e);
      log.error("MQTT client消息发送异常：topic=[{}]", topic, e);
      // return new Result("MQTT client消息发送异常：topic=" + topic);
      return "失败!";
    }
  }
}
