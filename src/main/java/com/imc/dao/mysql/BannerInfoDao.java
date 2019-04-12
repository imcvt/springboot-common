package com.imc.dao.mysql;

import com.imc.filter.BannerFilter;
import com.imc.po.BannerInfoPo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface BannerInfoDao {
    int deleteByPrimaryKey(String bannerId);

    int batchDelete(List<String> bannerIds);

    int insertSelective(BannerInfoPo record);

    BannerInfoPo selectByPrimaryKey(@Param("bannerId") String bannerId);

    int updateByPrimaryKeySelective(BannerInfoPo record);

    List<BannerInfoPo> bannerShowList(@Param("currentDate") Date currentDate, @Param("bannerStatus") Integer bannerStatus, @Param("count") int count);

    List<BannerInfoPo> bannerList(BannerFilter bannerFilter);

    int getTotalCount(BannerFilter bannerFilter);

    List<BannerInfoPo> bannerListByIds(List<String> bannerIds);

    int getCountByStatus(@Param("bannerStatus") int bannerStatus, @Param("bannerId") String bannerId);

}