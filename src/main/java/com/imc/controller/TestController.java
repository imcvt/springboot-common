package com.imc.controller;

import com.alibaba.fastjson.JSONObject;
import com.imc.dto.BannerInfoDto;
import com.imc.filter.BannerFilter;
import com.imc.service.TestService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author luoly
 * @date 2019/4/12 09:47
 * @description
 */
@Api(value="测试controller",tags={"测试controller"})
@RestController
@RequestMapping("/testController")
public class TestController {

    @Autowired
    TestService testService;

    @RequestMapping(value = "/test", method = {RequestMethod.POST, RequestMethod.GET})
    public void test() {
        System.out.println("==========启动成功==================");
    }

    @RequestMapping(value = "/bannerDetail", method = {RequestMethod.POST})
    public String bannerDetail(@RequestBody BannerFilter bannerFilter) {
        BannerInfoDto bannerInfoDto = testService.bannerDetail("63b318a2-cba9-4f0a-b46d-4727e6557a41");
        return JSONObject.toJSONString(bannerInfoDto);
    }
}
