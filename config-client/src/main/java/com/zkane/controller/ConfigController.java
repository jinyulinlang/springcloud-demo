package com.zkane.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: 594781919@qq.com
 * @date: 2018/5/8
 */
@RestController
public class ConfigController {
    @Value("${my.name}")
    private String myName;

    @GetMapping("/getName")
    public String getName() {
        return myName;
    }
}
