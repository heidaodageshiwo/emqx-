package com.emqx.emaxdemo.listener;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttMessage;

@Slf4j
public class SampleClientPolicyRes implements MqttCallbackExtended {

  public SampleClientPolicyRes() {
    log.info("");
  }

  @Override
  public void connectionLost(Throwable cause) {
    log.error("MQTT连接丢失", cause);
  }

  @Override
  public void messageArrived(String topic, MqttMessage message) throws Exception {
    log.info(topic);
    log.info("Topic: " + topic + " Message: " + message.toString());
    String msg = new String(message.getPayload());
    try {
      JSONObject jsonObject = JSON.parseObject(msg);
      System.out.println("接收到的消息：" + jsonObject);
      Integer mid = (Integer) jsonObject.get("mid");
      String deviceId = jsonObject.get("deviceId") + "";
      String timestamp = jsonObject.get("timestamp") + "";
      Integer code = (Integer) jsonObject.get("status") ;
      System.out.println("mid:"+mid+",deviceId:"+deviceId+",timestamp:"+timestamp+",code:"+code);
      /*if (code==200) {
        System.out.println("code为200");
        System.out.println("入库操作。。完成");
      }*/
      MqttCommon.commonMap.put(MqttCommon.policyResMap,msg);
      MqttCommon.policylatch.countDown();
      String clinetId = String.valueOf(jsonObject.get("clinetId"));
    } catch (Exception e) {
      log.error("异常：{}", e.getStackTrace(), e);
    }
  }

  @Override
  public void deliveryComplete(IMqttDeliveryToken token) {
    if (token.getException() != null) {
      log.error("消息发送异常", token.getException());
    }
  }

  @Override
  public void connectComplete(boolean b, String s) {
    log.info("MQTT client连接成功");
  }
}