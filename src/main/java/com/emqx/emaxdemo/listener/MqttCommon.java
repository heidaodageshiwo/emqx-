package com.emqx.emaxdemo.listener;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sun.javafx.collections.MappingChange.Map;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * java类简单作用描述
 *
 * @ProjectName: emaxdemo
 * @Package: com.emqx.emaxdemo.listener
 * @ClassName: MqttCommon
 * @Description: java类作用描述
 * @Author: zhangq
 * @CreateDate: 2021-09-16 15:43
 * @UpdateUser: zhangq
 * @UpdateDate: 2021-09-16 15:43
 * @UpdateRemark: The modified content
 * @Version: 1.0 *
 */
public class MqttCommon {

  public static String deviceId = "+";
//  public static String deviceId = "test";
  public static String deviceURL = "/v1/" + deviceId + "/security/";
  public static String pre = "iot_yygl_";
//  public static String tcpUrl = "tcp://192.168.1.100:11883";
  public static String tcpUrl = "tcp://192.168.56.211:1883";

  public static CountDownLatch policylatch;
  public static CountDownLatch ruleslatch;
  public static CountDownLatch querylatch;
  public static CountDownLatch commandlatch;
  public static CountDownLatch cmdlatch;
  public static CountDownLatch interfacecmdlatch;
  public static CountDownLatch devopscmdlatch;
  public static CountDownLatch devopscmdoptionslatch;
  public static ConcurrentHashMap<String, String> commonMap = new ConcurrentHashMap<>();
  public static String queryResMap="queryRes";
  public static String policyResMap="policyRes";
  public static String rulesResMap="rulesRes";
  public static String cipherreplyMap="cipherreply";
  public static String interfacecmdResponseMap="interfacecmdResponse";
  public static String interfacecmdResMap="interfacecmdRes";
  public static String devopscmdMap="devopscmd";
  public static String devopscmdoptions="devopscmdoptions";

  public static Long mills = 5000L;
  public ExecutorService executorService = Executors.newCachedThreadPool();
  public static String policyUrl = deviceURL + "fw/cmd/policy";
  public static String rulesUrl = deviceURL + "fw/cmd/rules";
  public static String queryUrl = deviceURL + "fw/cmd/query";
  public static String commandUrl = deviceURL + "cipher/command";
  public static String cmdUrl = deviceURL + "interface/cmd";
  public static String devopscmdUrl = deviceURL + "devops/cmd";

  public static String cipherresponseUrl =  deviceURL +"cipher/response";

  public static String policyResUrl = deviceURL + "fw/cmd/policyRes";
  public static String rulesResUrl = deviceURL + "fw/cmd/rulesRes";
  public static String queryResUrl = deviceURL + "fw/cmd/queryRes";
  public static String cipherrequestUrl = deviceURL + "cipher/request";
  public static String cipherreplyUrl = deviceURL + "cipher/reply";
  public static String interfacecmdResponseUrl = deviceURL + "interface/cmdResponse";
  public static String interfacecmdResUrl = deviceURL + "interface/cmdRes";
  public static String devopscmdResUrl = deviceURL + "devops/cmdRes";


  public Integer policy(String deviceId,String policy) throws InterruptedException {
    policylatch = new CountDownLatch(1);
    Integer random = getRandom();
    JSONObject jsonObject = new JSONObject();
    JSONObject jsonObject1 = new JSONObject();
    jsonObject1.put("policy", policy);//黑名单
//    jsonObject1.put("policy","DROP");//白名单
    jsonObject.put("mid", random);
    jsonObject.put("deviceId", deviceId);
    jsonObject.put("timestamp", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
    jsonObject.put("param", jsonObject1);
    System.out.println("发送的消息:" + jsonObject.toJSONString());
    MqttClientService.send(policyUrl, jsonObject.toJSONString(), 1);
    executorServiceExecute(policylatch);
    policylatch.await();
    return random;
  }

  public Integer rules(String deviceId,String cmd,String interfaces,String sip,String sport,String dip,String dport,String protocol,String mac) throws InterruptedException {
    ruleslatch = new CountDownLatch(1);
    Integer random = getRandom();
    JSONObject jsonObject = new JSONObject();
    JSONObject jsonObject1 = new JSONObject();
    jsonObject1.put("cmd", cmd);//新增
//    jsonObject1.put("cmd", "delete");//删除
    jsonObject1.put("interface", interfaces);//黑名单
    jsonObject1.put("sip", sip);//黑名单
    jsonObject1.put("sport", sport);//黑名单
    jsonObject1.put("dip", dip);//黑名单
//    jsonObject1.put("dport", "8888");//黑名单
    jsonObject1.put("dport", dport);//黑名单
    jsonObject1.put("protocol", protocol);//黑名单
    jsonObject1.put("mac", mac);//黑名单
    jsonObject.put("mid", random);
    jsonObject.put("deviceId", deviceId);
    jsonObject.put("timestamp", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
    jsonObject.put("param", jsonObject1);
    System.out.println("发送的消息:" + jsonObject.toJSONString());

    MqttClientService.send(rulesUrl, jsonObject.toJSONString(), 1);
    executorServiceExecute(ruleslatch);
    ruleslatch.await();
    return random;
  }

  public Integer query(String deviceId) throws InterruptedException {
    querylatch = new CountDownLatch(1);
    Integer random = getRandom();
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("mid", random);
    jsonObject.put("deviceId", deviceId);
    jsonObject.put("timestamp", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
    jsonObject.put("type", "query");
    System.out.println("发送的消息:" + jsonObject.toJSONString());
    MqttClientService.send(queryUrl, jsonObject.toJSONString(), 1);
    executorServiceExecute(querylatch);
    querylatch.await();
    return random;
  }


  public Integer command(String deviceId,String keyvalue) throws InterruptedException {
    commandlatch = new CountDownLatch(1);
    Integer random = getRandom();
    JSONObject jsonObject = new JSONObject();
    JSONObject jsonObject1 = new JSONObject();
    jsonObject1.put("keyvalue", keyvalue);//黑名单
    jsonObject.put("mid", random);
    jsonObject.put("deviceId", deviceId);
    jsonObject.put("type", "CMD_SKEY_SET_CONFIG");
    jsonObject.put("timestamp", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
    jsonObject.put("param", jsonObject1);
    System.out.println("发送的消息:" + jsonObject.toJSONString());
    MqttClientService.send(commandUrl, jsonObject.toJSONString(), 1);
    executorServiceExecute(commandlatch);
    commandlatch.await();
    return random;
  }


  public Integer cmd(String deviceId) throws InterruptedException {
    cmdlatch = new CountDownLatch(1);
    Integer random = getRandom();
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("mid", random);
    jsonObject.put("deviceId", deviceId);
    jsonObject.put("timestamp", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
    jsonObject.put("type", "CMD_IF_QUERY");
    System.out.println("发送的消息:" + jsonObject.toJSONString());
    MqttClientService.send(cmdUrl, jsonObject.toJSONString(), 1);
    executorServiceExecute(cmdlatch);
    cmdlatch.await();
    return random;
  }

  public Integer interfacecmd(String deviceId,String if_name,String operate) throws InterruptedException {
    interfacecmdlatch = new CountDownLatch(1);
    Integer random = getRandom();
    JSONObject jsonObject = new JSONObject();
    JSONObject jsonObject1 = new JSONObject();
    jsonObject1.put("if_name", if_name);
    jsonObject1.put("operate", operate);
    JSONArray objects = new JSONArray();
    objects.add(jsonObject1);
    jsonObject.put("mid", random);
    jsonObject.put("deviceId", deviceId);
    jsonObject.put("type", "CMD_IF_SET");
    jsonObject.put("timestamp", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
    jsonObject.put("param", objects);
    System.out.println("发送的消息:" + jsonObject.toJSONString());

    MqttClientService.send(cmdUrl, jsonObject.toJSONString(), 1);
    executorServiceExecute(interfacecmdlatch);
    interfacecmdlatch.await();
    return random;
  }


  public Integer devopscmd(String deviceId) throws InterruptedException {
    devopscmdlatch = new CountDownLatch(1);
    Integer random = getRandom();
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("mid", random);
    jsonObject.put("deviceId", deviceId);
    jsonObject.put("timestamp", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
    jsonObject.put("type", "CMD_OPS_QUERY");
    System.out.println("发送的消息:" + jsonObject.toJSONString());
    MqttClientService.send(devopscmdUrl, jsonObject.toJSONString(), 1);
    executorServiceExecute(devopscmdlatch);
    devopscmdlatch.await();
    return random;
  }

  public Integer devopscmdoptions(String deviceId, HashMap param) throws InterruptedException {
    String Compression = param.get("Compression") + "";
    String PermitRootLogin = param.get("PermitRootLogin") + "";
    String PrintLastLog = param.get("PrintLastLog") + "";
    String AllowUsers = param.get("AllowUsers") + "";
    String PermitEmptyPasswords = param.get("PermitEmptyPasswords") + "";
    String IgnoreRhosts = param.get("IgnoreRhosts") + "";
    String X11Forwarding = param.get("X11Forwarding") + "";
    String ListenAddress = param.get("ListenAddress") + "";
    String key_right = param.get("key_right") + "";
    Integer Protocol = (Integer) param.get("Protocol");
    Integer MaxAuthTries = (Integer) param.get("MaxAuthTries");
    Integer ClientAliveInterval = (Integer) param.get("ClientAliveInterval");
    Integer ClientAliveCountMax = (Integer) param.get("ClientAliveCountMax");
    Integer Port = (Integer) param.get("Port");

    devopscmdoptionslatch = new CountDownLatch(1);
    Integer random = getRandom();
    JSONObject jsonObject = new JSONObject();
    JSONObject jsonObject1 = new JSONObject();
    jsonObject1.put("Protocol", Protocol);//黑名单
    jsonObject1.put("Compression", Compression);//黑名单
    jsonObject1.put("MaxAuthTries", MaxAuthTries);//黑名单
    jsonObject1.put("PermitRootLogin", PermitRootLogin);//黑名单
    jsonObject1.put("PrintLastLog",PrintLastLog);//黑名单
    jsonObject1.put("ClientAliveInterval", ClientAliveInterval);//黑名单
    jsonObject1.put("ClientAliveCountMax", ClientAliveCountMax);//黑名单
    jsonObject1.put("AllowUsers", AllowUsers);//新增
    jsonObject1.put("PermitEmptyPasswords", PermitEmptyPasswords);//黑名单
    jsonObject1.put("IgnoreRhosts", IgnoreRhosts);//黑名单
    jsonObject1.put("X11Forwarding", X11Forwarding);//黑名单
    jsonObject1.put("Port", Port);//黑名单
    jsonObject1.put("ListenAddress", ListenAddress);//黑名单
    jsonObject1.put("key_right", key_right);//黑名单

    jsonObject.put("mid", random);
    jsonObject.put("deviceId", deviceId);
    jsonObject.put("type", "CMD_OPS_SET");
    jsonObject.put("timestamp", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
    jsonObject.put("param", jsonObject1);
    System.out.println("发送的消息:" + jsonObject.toJSONString());

    MqttClientService.send(devopscmdUrl, jsonObject.toJSONString(), 1);
    executorServiceExecute(devopscmdoptionslatch);
    devopscmdoptionslatch.await();
    return random;
  }

  public void executorServiceExecute(CountDownLatch latch) {
    executorService.execute(new Runnable() {
      @Override
      public void run() {
        try {
          Thread.sleep(mills);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        latch.countDown();
      }
    });
  }

  /*
   *
   * 功能描述:获取随机数
   * @Function :
   * @param:
   * @return:
   * @auther: zhangq
   * @date: 2021-09-16 14:32
   */
  public Integer getRandom() {
    int b = (int) (System.currentTimeMillis() % 100000);
    int a = (int) (Math.random() * (1000000 - 1) + 1);
    return b;
  }
}
