package com.imc.controller;

import com.alibaba.fastjson.JSONObject;
import com.imc.dao.common.CommonDao;
import com.imc.dto.BannerInfoDto;
import com.imc.filter.BannerFilter;
import com.imc.po.BannerInfoPo;
import com.imc.po.TagTypeInfoPo;
import com.imc.service.TestService;
import com.imc.utils.ConvertUtil;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    CommonDao commonDao;

    @RequestMapping(value = "/test", method = {RequestMethod.POST, RequestMethod.GET})
    public void test() {
        System.out.println("==========启动成功==================");
    }

    @RequestMapping(value = "/bannerDetail.json", method = {RequestMethod.POST})
    public String bannerDetail(@RequestBody BannerFilter bannerFilter) {
        BannerInfoDto bannerInfoDto = testService.bannerDetail(bannerFilter.getBannerId());

        BannerInfoPo bannerInfoPo = commonDao.findObjectById(BannerInfoPo.class, bannerFilter.getBannerId());

        TagTypeInfoPo tagTypeInfoPo = commonDao.findObjectById(TagTypeInfoPo.class, "1b92de85-df33-4ea1-a96f-6975041f86da");

        return JSONObject.toJSONString(bannerInfoDto);
    }

    @RequestMapping(value = "/createBanner.do", method = {RequestMethod.POST})
    public String createBanner(@RequestBody BannerInfoDto bannerInfoDto) {
//        {
//            "bannerDesc": "banner描述11",
//                "bannerId": "adifasodfkl",
//                "bannerImage": "123lklj2fwre",
//                "bannerImageKey": "string",
//                "bannerImageUrl": "string",
//                "bannerName": "bbbbname",
//                "bannerSn": 0,
//                "bannerStatus": 2,
//                "createUser": "11",
//                "effectiveEndDate": "2019-05-15 10:21:20",
//                "effectiveStartDate": "2019-05-15 10:21:20",
//                "eventDesc": "eventdesc",
//                "eventEndTime": "2019-05-15 10:21:20",
//                "eventStartTime": "2019-05-15 10:21:20",
//                "eventTitle": "title11",
//                "hrefUrl": "http://www.baidu.com",
//                "isDel": 2,
//                "isLiveEvent": 2,
//                "updateUser": "11"
//        }
        BannerInfoPo bannerInfoPo = ConvertUtil.convertBean(bannerInfoDto, BannerInfoPo.class);
        return String.valueOf(commonDao.insertObject(bannerInfoPo));
    }

    @RequestMapping(value = "/updateBanner.do", method = {RequestMethod.POST})
    public String updateBanner(@RequestBody BannerInfoDto bannerInfoDto) {
        BannerInfoPo bannerInfoPo = ConvertUtil.convertBean(bannerInfoDto, BannerInfoPo.class);
        return String.valueOf(commonDao.updateObject(bannerInfoPo));
    }

    @RequestMapping(value = "/deleteBanner.do", method = {RequestMethod.POST})
    public String deleteBanner(@RequestBody BannerInfoDto bannerInfoDto) {
        return String.valueOf(commonDao.deleteObject(BannerInfoPo.class, bannerInfoDto.getId()));
    }
}
