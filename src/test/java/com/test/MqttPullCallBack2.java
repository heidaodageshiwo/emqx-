package com.test;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.nio.charset.StandardCharsets;

@Slf4j
public class MqttPullCallBack2 implements MqttCallbackExtended {

  private static final String EVENT_HEARTBEAT = "EVENT_HEARTBEAT";

  /**
   * 掉线操作
   *
   * @param cause
   */
  @Override
  public void connectionLost(Throwable cause) {
    log.error("MQTT连接丢失", cause);
  }

  @Override
  public void messageArrived(String topic, MqttMessage message) {
    if (topic == null) {
      log.error("topic为空");
    } else {
      String payload = new String(message.getPayload(), StandardCharsets.UTF_8);
      System.out.println("主题：" + topic);
      System.out.println("订阅消息：" + payload);
    }
  }

  @Override
  public void deliveryComplete(IMqttDeliveryToken token) {

  }

  @Override
  public void connectComplete(boolean reconnect, String serverURI) {
  }
}
