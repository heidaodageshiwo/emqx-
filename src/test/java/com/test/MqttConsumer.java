package com.test;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

@Slf4j
public class MqttConsumer {

  public static MqttClient sampleClient;

  public static void main(String[] args) {
    connect("tcp://127.0.0.1:11883", "con1");
    //  send("/asdf", "{'fileName':'Java.zip','filePackUrl':'group1/M00/00/00/GduVZmCsn2OAdUrzD6fvgiUKmlg692.zip?attname=Java.zip'}");
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

      sampleClient.setCallback(new MqttPullCallBack2());
      // 建立连接
      sampleClient.connect(connOpts);
      log.info("连接成功！！！");
//            sampleClient.subscribe("/v1/sdsd/device/command", 1);
      sampleClient.subscribe("/asdf", 1);
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


}
