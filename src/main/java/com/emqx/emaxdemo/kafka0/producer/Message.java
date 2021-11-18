package com.emqx.emaxdemo.kafka0.producer;

import java.util.Date;

/**
 * java类简单作用描述
 *
 * @ProjectName: emaxdemo
 * @Package: com.emqx.emaxdemo.kafka0.producer
 * @ClassName: Message
 * @Description: java类作用描述
 * @Author: zhangq
 * @CreateDate: 2021-11-17 16:58
 * @UpdateUser: zhangq
 * @UpdateDate: 2021-11-17 16:58
 * @UpdateRemark: The modified content
 * @Version: 1.0 *
 */
public class Message {
  private String id;

  private String msg;

  private Date sendTime;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }

  public Date getSendTime() {
    return sendTime;
  }

  public void setSendTime(Date sendTime) {
    this.sendTime = sendTime;
  }
}
