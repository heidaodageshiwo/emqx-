package com.emqx.emaxdemo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
public class MqttConfig {

  // @Value("${mqtt.url}")
  public static String url;
  // @Value("${mqtt.name}")
  public static String name;
  // @Value("${mqtt.pwd}")
  public static String pwd;

  public static final String file_topic_prefix = "/file_down_";
  // 通道
  public static final Integer upgradeType_channel_constant = 4;
  public static final String upgradeType_channel_prefix = "_td";
  // 规约
  public static final Integer upgradeType_statute_constant = 5;
  public static final String upgradeType_statute_prefix = "_gy";
  // 点表
  public static final Integer upgradeType_pointTable_constant = 6;
  public static final String upgradeType_pointTable_prefix = "_db";

  public static Integer getUpgradeType(String fileName) {

    fileName = fileName.toLowerCase().split("\\.")[0];
    // 通道
    if (fileName.endsWith(upgradeType_channel_prefix)) {
      return upgradeType_channel_constant;
    }
    // 规约
    if (fileName.endsWith(upgradeType_statute_prefix)) {
      return upgradeType_statute_constant;
    }
    // 点表
    if (fileName.endsWith(upgradeType_pointTable_prefix)) {
      return upgradeType_pointTable_constant;
    }
    return null;
  }
}
