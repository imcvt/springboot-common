package com.imc.utils;

import org.springframework.util.NumberUtils;

import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author luoly
 * @date 2019/4/15 14:36
 * @description
 */
public class Sql2JavaMapping {
    private static Map<Integer, Class<?>> sql2JavaMapping = new HashMap();

    public Sql2JavaMapping() {
    }

    public static Class<?> getJavaType(int type) {
        return sql2JavaMapping.get(type);
    }

    public static Object convert2JavaObject(String columnName, Class javaObjectClass, Object dbObject) {
        if (dbObject != null) {
            if (javaObjectClass.equals(dbObject.getClass())) {
                return dbObject;
            } else {
                Class<?> dbObjectClass = dbObject.getClass();
                if (javaObjectClass.equals(Date.class)) {
                    if (dbObjectClass.equals(Timestamp.class)) {
                        return new Date(((Timestamp)dbObject).getTime());
                    }
                } else if (dbObject instanceof Number) {
                    if (Number.class.isAssignableFrom(javaObjectClass)) {
                        return NumberUtils.convertNumberToTargetClass((Number)dbObject, javaObjectClass);
                    }

                    if (javaObjectClass.equals(Boolean.class)) {
                        Number number = (Number)Number.class.cast(dbObject);
                        return number.intValue() > 0;
                    }
                }

                throw new RuntimeException(columnName + ":  can't convert DbObject<" + dbObject.getClass().getName() + "> to JavaObject<" + javaObjectClass.getName() + ">");
            }
        } else {
            return dbObject;
        }
    }

    public static void main(String[] args) {
        int a = 10;
        Object b = Integer.valueOf(a);
        System.out.println(b.getClass().getSimpleName());
    }

    static {
        sql2JavaMapping.put(-6, Byte.class);
        sql2JavaMapping.put(5, Short.class);
        sql2JavaMapping.put(4, Integer.class);
        sql2JavaMapping.put(-5, Long.class);
        sql2JavaMapping.put(7, Float.class);
        sql2JavaMapping.put(6, Double.class);
        sql2JavaMapping.put(8, Double.class);
        sql2JavaMapping.put(3, BigDecimal.class);
        sql2JavaMapping.put(2, BigDecimal.class);
        sql2JavaMapping.put(-7, Boolean.class);
        sql2JavaMapping.put(1, String.class);
        sql2JavaMapping.put(12, String.class);
        sql2JavaMapping.put(-1, String.class);
        sql2JavaMapping.put(-2, byte[].class);
        sql2JavaMapping.put(-3, byte[].class);
        sql2JavaMapping.put(-4, InputStream.class);
        sql2JavaMapping.put(91, Date.class);
        sql2JavaMapping.put(92, Date.class);
        sql2JavaMapping.put(93, Date.class);
        sql2JavaMapping.put(2005, Clob.class);
        sql2JavaMapping.put(2004, Blob.class);
        sql2JavaMapping.put(2003, Array.class);
        sql2JavaMapping.put(2006, Ref.class);
        sql2JavaMapping.put(2002, Object.class);
        sql2JavaMapping.put(2000, Object.class);
    }
}
