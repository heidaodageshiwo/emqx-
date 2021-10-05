package com.emqx.emaxdemo.listener;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttMessage;

@Slf4j
public class SampleClientQueryRes implements MqttCallbackExtended {

  public SampleClientQueryRes() {
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
      String clinetId = String.valueOf(jsonObject.get("clinetId"));
      System.out.println("接收到的消息：" + jsonObject);
      Integer mid = (Integer) jsonObject.get("mid");
      String deviceId = jsonObject.get("deviceId") + "";
      String timestamp = jsonObject.get("timestamp") + "";
      JSONObject param = (JSONObject) jsonObject.get("param");
      String policy = param.get("policy") + "";
      JSONArray rules = (JSONArray) param.get("rules");
      rules.forEach(e -> {
//        "protocol":"tcp","dip":"0.0.0.0/32",""sport":"1234"
        JSONObject jsonObject1 = (JSONObject) e;
        String interfaces = jsonObject1.get("interface") + "";
        String sip = jsonObject1.get("sip") + "";
        String sport = jsonObject1.get("sport") + "";
        String dip = jsonObject1.get("dip") + "";
        String dport = jsonObject1.get("dport") + "";
        String protocol = jsonObject1.get("protocol") + "";
        /* "interface":"eth0",                 //网口    √
            "sip":"192.168.3.5",                //源IP    √
            "sport":"1234",                     //源端口    √
            "dip":"0.0.0.0",                    //目的IP    √
            "dport":"8888",                     //目的端口    √
            "protocol":"TCP/UDP/ICMP/ALL",      //协议    √
            "mac":"E8-B1-FC-31-D1-15"           //源MAC    √ */
        System.out.println(
            "interface:" + interfaces + ",sip:" + sip + ",sport:" + sport + ",dip:" + dip
                + ",dport:" + dport + ",protocol:" + protocol);
      });
      MqttCommon.commonMap.put(MqttCommon.queryResMap,msg);
      MqttCommon.querylatch.countDown();

      System.out.println(
          "mid:" + mid + ",deviceId:" + deviceId + ",timestamp:" + timestamp + ",policy:" + policy);
      /*if (code==200) {
        System.out.println("code为200");
      }*/

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