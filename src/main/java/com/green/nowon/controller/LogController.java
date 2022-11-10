package com.green.nowon.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LogController {

    @GetMapping("/signin")
    public String signin(){
        return "sign/signin";
    }

    @GetMapping("/user/test")
    public void userTest(){}        //return 없으면 요청주소가 파일구조, 찾아감

    @GetMapping("/seller/goods")
    public void goods(){}
}
