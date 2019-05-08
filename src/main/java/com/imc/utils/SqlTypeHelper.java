package com.imc.utils;

/**
 * @author luoly
 * @date 2019/4/15 14:26
 * @description
 */
public class SqlTypeHelper {
    public SqlTypeHelper() {
    }

    public static String property2Column(String property) {
        return Java2Sql.getColumnName(property);
    }

    public static String column2Property(String column) {
        return SqlJavaNameUtil.getVariableName(column, false);
    }
}
