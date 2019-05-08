package com.imc.po;

import com.imc.annotation.PoParam;
import com.imc.po.common.BasePo;

import java.util.Date;

@PoParam(table = "BANNER_INFO")
public class BannerInfoPo extends BasePo{
    private String bannerId;

    private String bannerName;

    private String hrefUrl;

    private String bannerImage;

    private String bannerDesc;

    private Integer bannerSn;

    private Integer bannerStatus;

    private Date effectiveStartDate;

    private Date effectiveEndDate;

    private Integer isLiveEvent;

    private String eventTitle;

    private Date eventStartTime;

    private Date eventEndTime;

    private String eventDesc;

    private Integer isDel;

    private String createUser;

    private Date createTime;

    private String updateUser;

    private Date updateTime;

    public String getBannerId() {
        return bannerId;
    }

    public void setBannerId(String bannerId) {
        this.bannerId = bannerId == null ? null : bannerId.trim();
    }

    public String getBannerName() {
        return bannerName;
    }

    public void setBannerName(String bannerName) {
        this.bannerName = bannerName == null ? null : bannerName.trim();
    }

    public String getHrefUrl() {
        return hrefUrl;
    }

    public void setHrefUrl(String hrefUrl) {
        this.hrefUrl = hrefUrl == null ? null : hrefUrl.trim();
    }

    public String getBannerImage() {
        return bannerImage;
    }

    public void setBannerImage(String bannerImage) {
        this.bannerImage = bannerImage == null ? null : bannerImage.trim();
    }

    public String getBannerDesc() {
        return bannerDesc;
    }

    public void setBannerDesc(String bannerDesc) {
        this.bannerDesc = bannerDesc == null ? null : bannerDesc.trim();
    }

    public Integer getBannerSn() {
        return bannerSn;
    }

    public void setBannerSn(Integer bannerSn) {
        this.bannerSn = bannerSn;
    }

    public Integer getBannerStatus() {
        return bannerStatus;
    }

    public void setBannerStatus(Integer bannerStatus) {
        this.bannerStatus = bannerStatus;
    }

    public Date getEffectiveStartDate() {
        return effectiveStartDate;
    }

    public void setEffectiveStartDate(Date effectiveStartDate) {
        this.effectiveStartDate = effectiveStartDate;
    }

    public Date getEffectiveEndDate() {
        return effectiveEndDate;
    }

    public void setEffectiveEndDate(Date effectiveEndDate) {
        this.effectiveEndDate = effectiveEndDate;
    }

    public Integer getIsLiveEvent() {
        return isLiveEvent;
    }

    public void setIsLiveEvent(Integer isLiveEvent) {
        this.isLiveEvent = isLiveEvent;
    }

    public String getEventTitle() {
        return eventTitle;
    }

    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle == null ? null : eventTitle.trim();
    }

    public Date getEventStartTime() {
        return eventStartTime;
    }

    public void setEventStartTime(Date eventStartTime) {
        this.eventStartTime = eventStartTime;
    }

    public Date getEventEndTime() {
        return eventEndTime;
    }

    public void setEventEndTime(Date eventEndTime) {
        this.eventEndTime = eventEndTime;
    }

    public String getEventDesc() {
        return eventDesc;
    }

    public void setEventDesc(String eventDesc) {
        this.eventDesc = eventDesc == null ? null : eventDesc.trim();
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser == null ? null : updateUser.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}