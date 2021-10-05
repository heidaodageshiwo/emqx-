package com.emqx.emaxdemo.listener;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import java.text.SimpleDateFormat;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttMessage;

@Slf4j
public class SampleClientCipherRequest implements MqttCallbackExtended {

  public SampleClientCipherRequest() {
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
      JSONObject jsonObjectRes = JSON.parseObject(msg);
      String clinetId = String.valueOf(jsonObjectRes.get("clinetId"));
      System.out.println("接收到的消息：" + jsonObjectRes);

      Integer mid = (Integer) jsonObjectRes.get("mid");
      String deviceId = jsonObjectRes.get("deviceId") + "";
      String timestamp = jsonObjectRes.get("timestamp") + "";
      Integer type = (Integer) jsonObjectRes.get("type");
      System.out.println(
          "mid:" + mid + ",deviceId:" + deviceId + ",timestamp:" + timestamp + ",type:" + type);
      if ("EVENT_SKEY_STATUS".equals(type)) {
        System.out.println("type为EVENT_SKEY_STATUS");

        //建表 新增数据 查询密码
        String deviceIds=deviceId;
        String keyvalue="1qaz2wsx";




        JSONObject jsonObject = new JSONObject();
        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("keyvalue", keyvalue);
        jsonObject.put("mid", getRandom());
        jsonObject.put("deviceId", deviceIds);
        jsonObject.put("timestamp", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        jsonObject.put("type", "EVENT_SKEY_STATUS");
        jsonObject.put("param", jsonObject1);
        System.out.println("发送的消息:" + jsonObject.toJSONString());

        MqttClientService.send(MqttCommon.cipherresponseUrl, jsonObject.toJSONString(), 1);

      }


    } catch (Exception e) {
      log.error("异常：{}", e.getStackTrace(), e);
    }
  }

  public Integer getRandom() {
    int b = (int) (System.currentTimeMillis() % 100000);
    int a = (int) (Math.random() * (1000000 - 1) + 1);
    return b;
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