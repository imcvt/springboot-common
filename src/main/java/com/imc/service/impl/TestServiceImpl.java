package com.imc.service.impl;

import com.imc.dao.mysql.BannerInfoDao;
import com.imc.dto.BannerInfoDto;
import com.imc.po.BannerInfoPo;
import com.imc.service.TestService;
import com.imc.utils.ConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author luoly
 * @date 2019/4/12 10:47
 * @description
 */
@Service
public class TestServiceImpl implements TestService{

    @Autowired
    BannerInfoDao bannerInfoDao;

    @Override
    public BannerInfoDto bannerDetail(String id) {
        BannerInfoPo bannerInfoPo = bannerInfoDao.selectByPrimaryKey(id);
        return ConvertUtil.convertBean(bannerInfoPo, BannerInfoDto.class);
    }
}
