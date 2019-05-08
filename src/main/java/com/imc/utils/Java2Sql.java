package com.imc.utils;

/**
 * @author luoly
 * @date 2019/4/15 14:25
 * @description
 */
public class Java2Sql {
    public Java2Sql() {
    }

    public static String getColumnName(String variableName) {
        for(char ch = 'A'; ch <= 'Z'; ++ch) {
            variableName = variableName.replaceAll(String.valueOf(ch), "_" + (char)(ch + 32));
        }

        return variableName;
    }

    public static void main(String[] args) {
        System.out.println(32);
        System.out.println(getColumnName("seqNo"));
        System.out.println(getColumnName("userName"));
        System.out.println(getColumnName("orginalUserName"));
        System.out.println(getColumnName("abCdEfAbCdEf"));
        System.out.println(getColumnName("abCdEfAbCdEfZz"));
    }
}
