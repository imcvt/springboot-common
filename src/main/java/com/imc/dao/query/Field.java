package com.imc.dao.query;

/**
 * @author luoly
 * @date 2019/4/15 10:40
 * @description
 */
public class Field {
    private String name;
    private Object value;
    private String type;

    public Field() {
    }

    public Field(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    public Field(String name, Object value, String type) {
        this.name = name;
        this.value = value;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
