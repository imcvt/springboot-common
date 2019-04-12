package com.imc.filter;

import com.imc.filter.base.BaseQueryFilter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author luoly
 * @date 2019/3/15 10:33
 * @description
 */
public class BannerFilter extends BaseQueryFilter implements Serializable {
    private static final long serialVersionUID = -1855574134779989167L;

    private String bannerId;

    private List<String> bannerIds;

    private String bannerName;
    /**
     * 状态1-启用；2-禁用
     */
    private Integer bannerStatus;
    /**
     * 创建时间-开始值
     */
    private Date createTimeStart;
    private String createTimeStartStr;
    /**
     * 创建时间-结束值
     */
    private Date createTimeEnd;
    private String createTimeEndStr;

    public String getBannerId() {
        return bannerId;
    }

    public void setBannerId(String bannerId) {
        this.bannerId = bannerId;
    }

    public List<String> getBannerIds() {
        return bannerIds;
    }

    public void setBannerIds(List<String> bannerIds) {
        this.bannerIds = bannerIds;
    }

    public String getBannerName() {
        return bannerName;
    }

    public void setBannerName(String bannerName) {
        this.bannerName = bannerName;
    }

    public Integer getBannerStatus() {
        return bannerStatus;
    }

    public void setBannerStatus(Integer bannerStatus) {
        this.bannerStatus = bannerStatus;
    }

    public Date getCreateTimeStart() {
        return createTimeStart;
    }

    public void setCreateTimeStart(Date createTimeStart) {
        this.createTimeStart = createTimeStart;
    }

    public String getCreateTimeStartStr() {
        return createTimeStartStr;
    }

    public void setCreateTimeStartStr(String createTimeStartStr) {
        this.createTimeStartStr = createTimeStartStr;
    }

    public Date getCreateTimeEnd() {
        return createTimeEnd;
    }

    public void setCreateTimeEnd(Date createTimeEnd) {
        this.createTimeEnd = createTimeEnd;
    }

    public String getCreateTimeEndStr() {
        return createTimeEndStr;
    }

    public void setCreateTimeEndStr(String createTimeEndStr) {
        this.createTimeEndStr = createTimeEndStr;
    }
}
