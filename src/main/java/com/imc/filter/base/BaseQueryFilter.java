package com.imc.filter.base;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

/**
 * @author luoly
 * @date 2019/3/15 11:25
 * @description
 */
public class BaseQueryFilter implements Serializable{
    private static final long serialVersionUID = -1855574134779989167L;

    /**
     * 当前页码
     */
    private Integer pageNum = 1;

    /**
     * 每页行数
     */
    private Integer pageSize = 10;

    /**
     * 分页起始行，前台无需显示
     */
    @JsonIgnore
    private Integer start;

    public Integer getPageNum() {
        if (pageNum < 0) {
            pageNum = 1;
        }
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getStart() {
        return (this.pageNum - 1 <= 0?0:this.pageNum - 1) * this.pageSize;
    }

}
