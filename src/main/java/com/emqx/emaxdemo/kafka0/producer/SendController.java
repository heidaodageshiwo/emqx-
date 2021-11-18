package com.emqx.emaxdemo.kafka0.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * java类简单作用描述
 *
 * @ProjectName: emaxdemo
 * @Package: com.emqx.emaxdemo.kafka0.producer
 * @ClassName: SendController
 * @Description: java类作用描述
 * @Author: zhangq
 * @CreateDate: 2021-11-17 17:00
 * @UpdateUser: zhangq
 * @UpdateDate: 2021-11-17 17:00
 * @UpdateRemark: The modified content
 * @Version: 1.0 *
 */
@RestController
@RequestMapping("/kafka")
public class SendController {
  @Autowired
  private Producer producer;

  @RequestMapping(value = "/send")
  public String send() {
    producer.send();
    return "{\"code\":0}";
  }
}
