package com.zkane.remote;

import org.springframework.stereotype.Component;

/**
 * @author: 594781919@qq.com
 * @date: 2018/5/8
 */
@Component
public class ProducerRemoteHystrix implements ProducerRemote {
    @Override
    public String get() {
        return "Producer Server 的服务调用失败";
    }

}
