package com.emqx.emaxdemo.listener;

import java.util.UUID;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Slf4j
@Component
public class MqttListenerApplication implements ApplicationListener<ApplicationReadyEvent> {


  @SneakyThrows
  @Override
  public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
    log.info("监听器开始启动mqtt");

    MqttClient connect = MqttClientService
        .connect(MqttCommon.tcpUrl, MqttCommon.pre + UUID.randomUUID().toString().replace("-", ""));
    connect.setCallback(new SampleClientPolicyRes());
    connect.subscribe(MqttCommon.policyResUrl);

    MqttClient connect1 = MqttClientService
        .connect(MqttCommon.tcpUrl, MqttCommon.pre + UUID.randomUUID().toString().replace("-", ""));
    connect1.setCallback(new SampleClientRulesRes());
    connect1.subscribe(MqttCommon.rulesResUrl);

    MqttClient connect2 = MqttClientService
        .connect(MqttCommon.tcpUrl, MqttCommon.pre + UUID.randomUUID().toString().replace("-", ""));
    connect2.setCallback(new SampleClientQueryRes());
    connect2.subscribe(MqttCommon.queryResUrl);

    MqttClient connect3 = MqttClientService
        .connect(MqttCommon.tcpUrl, MqttCommon.pre + UUID.randomUUID().toString().replace("-", ""));
    connect3.setCallback(new SampleClientCipherRequest());
    connect3.subscribe(MqttCommon.cipherrequestUrl);

    MqttClient connect4 = MqttClientService
        .connect(MqttCommon.tcpUrl, MqttCommon.pre + UUID.randomUUID().toString().replace("-", ""));
    connect4.setCallback(new SampleClientCipherReply());
    connect4.subscribe(MqttCommon.cipherreplyUrl);

    MqttClient connect5 = MqttClientService
        .connect(MqttCommon.tcpUrl, MqttCommon.pre + UUID.randomUUID().toString().replace("-", ""));
    connect5.setCallback(new SampleClientInterfaceCmdResponse());
    connect5.subscribe(MqttCommon.interfacecmdResponseUrl);

    MqttClient connect7 = MqttClientService
        .connect(MqttCommon.tcpUrl, MqttCommon.pre + UUID.randomUUID().toString().replace("-", ""));
    connect7.setCallback(new SampleClientInterfaceCmdRes());
    connect7.subscribe(MqttCommon.interfacecmdResUrl);

    MqttClient connect6 = MqttClientService
        .connect(MqttCommon.tcpUrl, MqttCommon.pre + UUID.randomUUID().toString().replace("-", ""));
    connect6.setCallback(new SampleClientDevopsCmdRes());
    connect6.subscribe(MqttCommon.devopscmdResUrl);
  }

}
