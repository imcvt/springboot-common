package com.imc.po.common;

import org.springframework.util.IdGenerator;

import java.util.UUID;

/**
 * @author luoly
 * @date 2019/4/15 09:53
 * @description
 */
public class BasePo {
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void generateAndAssignID() {
        this.setId(UUID.randomUUID().toString());
    }
}
