package com.emqx.emaxdemo.lnsoft;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.emqx.emaxdemo.listener.MqttClientService;
import com.emqx.emaxdemo.listener.MqttCommon;
import com.sun.javafx.collections.MappingChange.Map;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Handler;
import java.util.logging.SimpleFormatter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * java类简单作用描述
 *
 * @ProjectName: emaxdemo
 * @Package: com.emqx.emaxdemo.lnsoft
 * @ClassName: test
 * @Description: java类作用描述
 * @Author: zhangq
 * @CreateDate: 2021-09-13 15:07
 * @UpdateUser: zhangq
 * @UpdateDate: 2021-09-13 15:07
 * @UpdateRemark: The modified content
 * @Version: 1.0 *
 */
@RestController
public class test {

  @RequestMapping("/")
  public String get() {
    MqttClientService.send("/abcd",
        "{'fileName':'张强','filePackUrl':'group1/M00/00/00/GduVZmCsn2OAdUrzD6fvgiUKmlg692.zip?attname=Java.zip'}",
        1);
    return "1";
  }


  @RequestMapping("/policy")
  public String policy() throws InterruptedException {
    String deviceId=MqttCommon.deviceId;
//    "policy":"ACCEPT/DROP"黑。白
    String policy="ACCEPT/DROP";
//    String deviceId=MqttCommon.deviceId;
    MqttCommon mqttCommon = new MqttCommon();
    mqttCommon.policy(deviceId,policy);
    String o = MqttCommon.commonMap.get(MqttCommon.policyResMap);
    return o;
  }

  @RequestMapping("/rules")
  public String rules() throws InterruptedException {
    String deviceId=MqttCommon.deviceId;
//    String cmd="append/delete";
    String cmd="append";
    String interfaces="eth0";
    String sip="192.168.3.5";
    String sport="1234";
    String dip="0.0.0.0";
//    String dport="8888";
    String dport="";
    String protocol="TCP";
    String mac="E8-B1-FC-31-D1-15";

    MqttCommon mqttCommon = new MqttCommon();
    Integer rules = mqttCommon.rules(deviceId,cmd,interfaces,sip,sport,dip,dport,protocol,mac);
    String s = MqttCommon.commonMap.get(MqttCommon.rulesResMap);
    return s;
  }

  @RequestMapping("/query")
  public String query() throws InterruptedException {

    String deviceId=MqttCommon.deviceId;
    MqttCommon mqttCommon = new MqttCommon();
    mqttCommon.query(deviceId);
    String o = MqttCommon.commonMap.get(MqttCommon.queryResMap);
    return o;
  }


  @RequestMapping("/command")
  public String command() throws InterruptedException {
    String deviceId=MqttCommon.deviceId;
    String keyvalue="1qaz2wsx";
    MqttCommon mqttCommon = new MqttCommon();
    Integer command = mqttCommon.command(deviceId,keyvalue);
    String s = MqttCommon.commonMap.get(MqttCommon.cipherreplyMap);
    return s;
  }


  @RequestMapping("/cmd")
  public String cmd() throws InterruptedException {
    String deviceId=MqttCommon.deviceId;
    MqttCommon mqttCommon = new MqttCommon();
    Integer cmd = mqttCommon.cmd(deviceId);
    String s = MqttCommon.commonMap.get(MqttCommon.interfacecmdResponseMap);
    return s;
  }

  @RequestMapping("/interfacecmd")
  public String interfacecmd() throws InterruptedException {
    String deviceId=MqttCommon.deviceId;
    String if_name="if_name";
    String operate="operate";
    MqttCommon mqttCommon = new MqttCommon();
    Integer interfacecmd = mqttCommon.interfacecmd(deviceId,if_name,operate);
    String s = MqttCommon.commonMap.get(MqttCommon.interfacecmdResMap);
    return s;
  }


  @RequestMapping("/devopscmd")
  public String devopscmd() throws InterruptedException {
    String deviceId=MqttCommon.deviceId;
    MqttCommon mqttCommon = new MqttCommon();
    Integer devopscmd = mqttCommon.devopscmd(deviceId);
    String s = MqttCommon.commonMap.get(MqttCommon.devopscmdMap);
    return s;
  }

  @RequestMapping("/devopscmdoptions")
  public String devopscmdoptions() throws InterruptedException {
    String deviceId=MqttCommon.deviceId;
    MqttCommon mqttCommon = new MqttCommon();
    Integer devopscmdoptions = mqttCommon.devopscmdoptions(deviceId, new HashMap<>());
    String s = MqttCommon.commonMap.get(MqttCommon.devopscmdoptions);
    return s ;
  }
}
