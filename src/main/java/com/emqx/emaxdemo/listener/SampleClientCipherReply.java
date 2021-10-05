package com.emqx.emaxdemo.listener;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttMessage;

@Slf4j
public class SampleClientCipherReply implements MqttCallbackExtended {

  public SampleClientCipherReply() {
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
      Integer code = (Integer) jsonObjectRes.get("code") ;
      String type =  jsonObjectRes.get("type") +"";
      System.out.println("mid:"+mid+",deviceId:"+deviceId+",timestamp:"+timestamp+",code:"+code+",type:"+type);
      /*if (code==200) {
        System.out.println("code为200");
        System.out.println("入库操作。。完成");
      }*/
      MqttCommon.commonMap.put(MqttCommon.cipherreplyMap,msg);

      MqttCommon.commandlatch.countDown();
    } catch (Exception e) {
      log.error("异常：{}", e.getStackTrace(), e);
    }
  }
  public Integer getRandom(){
    int b= (int) (System.currentTimeMillis()%100000);
    int a=(int)(Math.random()*(1000000-1)+1);
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