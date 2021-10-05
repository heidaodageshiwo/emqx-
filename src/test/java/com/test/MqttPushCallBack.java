package com.test;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttMessage;

@Slf4j
public class MqttPushCallBack implements MqttCallbackExtended {

  public MqttPushCallBack() {
    log.info("");
  }

  public void connectionLost(Throwable cause) {
    log.error("MQTT连接丢失", cause);
  }

  public void deliveryComplete(IMqttDeliveryToken token) {
    if (token.getException() != null) {
      log.error("消息发送异常", token.getException());
    }

  }

  public void messageArrived(String topic, MqttMessage message) {
    log.info(topic);
  }

  public void connectComplete(boolean b, String s) {
    log.info("MQTT client连接成功");
  }
}
