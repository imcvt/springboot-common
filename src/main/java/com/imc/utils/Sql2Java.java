package com.imc.utils;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * @author luoly
 * @date 2019/4/15 14:25
 * @description
 */
public class Sql2Java {
    private static final Sql2Java.IntStringMap _javaTypesForSqlType = new Sql2Java.IntStringMap();
    private static final Sql2Java.IntStringMap _preferredJavaTypeForSqlType = new Sql2Java.IntStringMap();
    private static final Comparator _typeComparator = new Comparator() {
        public int compare(Object o1, Object o2) {
            String s1 = (String)o1;
            String s2 = (String)o2;
            boolean isS1Class = s1.indexOf(46) != -1;
            boolean isS2Class = s2.indexOf(46) != -1;
            if (isS1Class && isS2Class || !isS1Class && !isS2Class) {
                return s1.compareTo(s2);
            } else {
                return isS1Class ? 1 : -1;
            }
        }

        public boolean equals(Object o1, Object o2) {
            return o1.equals(o2);
        }
    };
    private static final HashMap _primitiveToClassMap = new HashMap();
    private static final String[] _allJavaTypes = new String[]{"boolean", "byte", "byte[]", "double", "float", "int", "long", "short", "java.io.InputStream", "java.io.Reader", "java.lang.Boolean", "java.lang.Byte", "java.lang.Double", "java.lang.Float", "java.lang.Integer", "java.lang.Long", "java.lang.Short", "java.lang.String", "java.lang.Object", "java.math.BigDecimal", "java.math.BigInteger", "java.sql.Array", "java.sql.Blob", "java.sql.Clob", "java.sql.Date", "java.sql.Ref", "java.sql.Time", "java.sql.Timestamp", "java.util.Date"};
    private static final Set _numericClasses = new HashSet();

    public Sql2Java() {
    }

    public static Comparator getTypeComparator() {
        return _typeComparator;
    }

    public static String getPreferredJavaType(int sqlType, int size, int decimalDigits) {
        if ((sqlType == 3 || sqlType == 2) && decimalDigits == 0) {
            if (size == 1) {
                return "boolean";
            } else if (size < 3) {
                return "byte";
            } else if (size < 5) {
                return "short";
            } else if (size < 10) {
                return "int";
            } else {
                return size < 19 ? "long" : "java.math.BigDecimal";
            }
        } else {
            String result = _preferredJavaTypeForSqlType.getString(sqlType);
            if (result == null) {
                result = "java.lang.Object";
            }

            return result;
        }
    }

    public static String getPreferredJavaTypeNoPrimitives(int sqlType, int size, int decimalDigits) {
        String pjt = getPreferredJavaType(sqlType, size, decimalDigits);
        String pjtc = getClassForPrimitive(pjt);
        return pjtc != null ? pjtc : pjt;
    }

    public static String[] getJavaTypes(int sqlType) {
        String[] result = _javaTypesForSqlType.getStrings(sqlType);
        if (result == null) {
            result = _allJavaTypes;
        }

        return result;
    }

    public static String getClassForPrimitive(String primitive) {
        return (String)_primitiveToClassMap.get(primitive);
    }

    public static boolean isPrimitive(String type) {
        return getClassForPrimitive(type) != null;
    }

    public static boolean isNumericClass(String type) {
        return _numericClasses.contains(type);
    }

    public static void overridePreferredJavaTypeForSqlType(int sqlType, String javaType) {
        _preferredJavaTypeForSqlType.put(sqlType, javaType);
    }

    public static void overrideAllowedJavaTypesForSqlType(int sqlType, String[] javaTypes) {
        _javaTypesForSqlType.put(sqlType, javaTypes);
    }

    static {
        _primitiveToClassMap.put("byte", "java.lang.Byte");
        _primitiveToClassMap.put("short", "java.lang.Short");
        _primitiveToClassMap.put("int", "java.lang.Integer");
        _primitiveToClassMap.put("long", "java.lang.Long");
        _primitiveToClassMap.put("float", "java.lang.Float");
        _primitiveToClassMap.put("boolean", "java.lang.Boolean");
        _primitiveToClassMap.put("double", "java.lang.Double");
        _numericClasses.add("java.lang.Byte");
        _numericClasses.add("java.lang.Short");
        _numericClasses.add("java.lang.Integer");
        _numericClasses.add("java.lang.Long");
        _numericClasses.add("java.lang.Float");
        _numericClasses.add("java.lang.Boolean");
        _numericClasses.add("java.lang.Double");
        _javaTypesForSqlType.put(-6, (String[])(new String[]{"boolean", "byte", "double", "float", "int", "long", "short", "java.lang.Boolean", "java.lang.Byte", "java.lang.Double", "java.lang.Float", "java.lang.Integer", "java.lang.Long", "java.lang.Short", "java.lang.String", "java.lang.Object", "java.math.BigDecimal", "java.math.BigInteger"}));
        _javaTypesForSqlType.put(5, (String[])(new String[]{"boolean", "byte", "double", "float", "int", "long", "short", "java.lang.Boolean", "java.lang.Byte", "java.lang.Double", "java.lang.Float", "java.lang.Integer", "java.lang.Long", "java.lang.Short", "java.lang.String", "java.lang.Object", "java.math.BigDecimal", "java.math.BigInteger"}));
        _javaTypesForSqlType.put(4, (String[])(new String[]{"boolean", "byte", "double", "float", "int", "long", "short", "java.lang.Boolean", "java.lang.Byte", "java.lang.Double", "java.lang.Float", "java.lang.Integer", "java.lang.Long", "java.lang.Short", "java.lang.String", "java.lang.Object", "java.math.BigDecimal", "java.math.BigInteger"}));
        _javaTypesForSqlType.put(-5, (String[])(new String[]{"boolean", "byte", "double", "float", "int", "long", "short", "java.lang.Boolean", "java.lang.Byte", "java.lang.Double", "java.lang.Float", "java.lang.Integer", "java.lang.Long", "java.lang.Short", "java.lang.String", "java.lang.Object", "java.math.BigDecimal", "java.math.BigInteger"}));
        _javaTypesForSqlType.put(7, (String[])(new String[]{"boolean", "byte", "double", "float", "int", "long", "short", "java.lang.Boolean", "java.lang.Byte", "java.lang.Double", "java.lang.Float", "java.lang.Integer", "java.lang.Long", "java.lang.Short", "java.lang.String", "java.lang.Object", "java.math.BigDecimal", "java.math.BigInteger"}));
        _javaTypesForSqlType.put(6, (String[])(new String[]{"boolean", "byte", "double", "float", "int", "long", "short", "java.lang.Boolean", "java.lang.Byte", "java.lang.Double", "java.lang.Float", "java.lang.Integer", "java.lang.Long", "java.lang.Short", "java.lang.String", "java.lang.Object", "java.math.BigDecimal", "java.math.BigInteger"}));
        _javaTypesForSqlType.put(8, (String[])(new String[]{"boolean", "byte", "double", "float", "int", "long", "short", "java.lang.Boolean", "java.lang.Byte", "java.lang.Double", "java.lang.Float", "java.lang.Integer", "java.lang.Long", "java.lang.Short", "java.lang.String", "java.lang.Object", "java.math.BigDecimal", "java.math.BigInteger"}));
        _javaTypesForSqlType.put(3, (String[])(new String[]{"boolean", "byte", "double", "float", "int", "long", "short", "java.lang.Boolean", "java.lang.Byte", "java.lang.Double", "java.lang.Float", "java.lang.Integer", "java.lang.Long", "java.lang.Short", "java.lang.String", "java.lang.Object", "java.math.BigDecimal", "java.math.BigInteger"}));
        _javaTypesForSqlType.put(2, (String[])(new String[]{"boolean", "byte", "double", "float", "int", "long", "short", "java.lang.Boolean", "java.lang.Byte", "java.lang.Double", "java.lang.Float", "java.lang.Integer", "java.lang.Long", "java.lang.Short", "java.lang.String", "java.lang.Object", "java.math.BigDecimal", "java.math.BigInteger"}));
        _javaTypesForSqlType.put(-7, (String[])(new String[]{"boolean", "byte", "double", "float", "int", "long", "short", "java.lang.Boolean", "java.lang.Byte", "java.lang.Double", "java.lang.Float", "java.lang.Integer", "java.lang.Long", "java.lang.Short", "java.lang.String", "java.lang.Object", "java.math.BigDecimal", "java.math.BigInteger"}));
        _javaTypesForSqlType.put(1, (String[])(new String[]{"boolean", "byte", "double", "float", "int", "long", "short", "java.io.InputStream", "java.io.Reader", "java.lang.Boolean", "java.lang.Byte", "java.lang.Double", "java.lang.Float", "java.lang.Integer", "java.lang.Long", "java.lang.Object", "java.lang.Short", "java.lang.String", "java.math.BigDecimal", "java.math.BigInteger", "java.sql.Date", "java.sql.Time", "java.sql.Timestamp", "java.util.Date"}));
        _javaTypesForSqlType.put(12, (String[])(new String[]{"boolean", "byte", "double", "float", "int", "long", "short", "java.io.InputStream", "java.io.Reader", "java.lang.Boolean", "java.lang.Byte", "java.lang.Double", "java.lang.Float", "java.lang.Integer", "java.lang.Long", "java.lang.Object", "java.lang.Short", "java.lang.String", "java.math.BigDecimal", "java.math.BigInteger", "java.sql.Date", "java.sql.Time", "java.sql.Timestamp", "java.util.Date"}));
        _javaTypesForSqlType.put(-1, (String[])(new String[]{"boolean", "byte", "double", "float", "int", "long", "short", "java.io.InputStream", "java.io.Reader", "java.lang.Boolean", "java.lang.Byte", "java.lang.Double", "java.lang.Float", "java.lang.Integer", "java.lang.Long", "java.lang.Object", "java.lang.Short", "java.lang.String", "java.math.BigDecimal", "java.math.BigInteger", "java.sql.Date", "java.sql.Time", "java.sql.Timestamp", "java.util.Date"}));
        _javaTypesForSqlType.put(-2, (String[])(new String[]{"byte[]", "java.lang.String", "java.lang.Object", "java.io.InputStream", "java.io.Reader"}));
        _javaTypesForSqlType.put(-3, (String[])(new String[]{"byte[]", "java.lang.String", "java.lang.Object", "java.io.InputStream", "java.io.Reader"}));
        _javaTypesForSqlType.put(-4, (String[])(new String[]{"byte[]", "java.lang.String", "java.lang.Object", "java.io.InputStream", "java.io.Reader"}));
        _javaTypesForSqlType.put(91, (String[])(new String[]{"java.lang.String", "java.lang.Object", "java.sql.Date", "java.sql.Timestamp", "java.util.Date"}));
        _javaTypesForSqlType.put(92, (String[])(new String[]{"java.lang.String", "java.lang.Object", "java.sql.Time", "java.sql.Timestamp", "java.util.Date"}));
        _javaTypesForSqlType.put(93, (String[])(new String[]{"java.lang.String", "java.lang.Object", "java.sql.Date", "java.sql.Time", "java.sql.Timestamp", "java.util.Date"}));
        _javaTypesForSqlType.put(2005, (String[])(new String[]{"java.lang.Object", "java.sql.Clob"}));
        _javaTypesForSqlType.put(2004, (String[])(new String[]{"java.lang.Object", "java.sql.Blob"}));
        _javaTypesForSqlType.put(2003, (String[])(new String[]{"java.lang.Object", "java.sql.Array"}));
        _javaTypesForSqlType.put(2006, (String[])(new String[]{"java.lang.Object", "java.sql.Ref"}));
        _javaTypesForSqlType.put(2002, (String[])(new String[]{"java.lang.Object"}));
        _javaTypesForSqlType.put(2000, (String[])(new String[]{"java.lang.Object"}));
        _preferredJavaTypeForSqlType.put(-6, (String)"byte");
        _preferredJavaTypeForSqlType.put(5, (String)"short");
        _preferredJavaTypeForSqlType.put(4, (String)"int");
        _preferredJavaTypeForSqlType.put(-5, (String)"long");
        _preferredJavaTypeForSqlType.put(7, (String)"float");
        _preferredJavaTypeForSqlType.put(6, (String)"double");
        _preferredJavaTypeForSqlType.put(8, (String)"double");
        _preferredJavaTypeForSqlType.put(3, (String)"java.math.BigDecimal");
        _preferredJavaTypeForSqlType.put(2, (String)"java.math.BigDecimal");
        _preferredJavaTypeForSqlType.put(-7, (String)"boolean");
        _preferredJavaTypeForSqlType.put(1, (String)"java.lang.String");
        _preferredJavaTypeForSqlType.put(12, (String)"java.lang.String");
        _preferredJavaTypeForSqlType.put(-1, (String)"java.lang.String");
        _preferredJavaTypeForSqlType.put(-2, (String)"byte[]");
        _preferredJavaTypeForSqlType.put(-3, (String)"byte[]");
        _preferredJavaTypeForSqlType.put(-4, (String)"java.io.InputStream");
        _preferredJavaTypeForSqlType.put(91, (String)"java.util.Date");
        _preferredJavaTypeForSqlType.put(92, (String)"java.util.Date");
        _preferredJavaTypeForSqlType.put(93, (String)"java.util.Date");
        _preferredJavaTypeForSqlType.put(2005, (String)"java.sql.Clob");
        _preferredJavaTypeForSqlType.put(2004, (String)"java.sql.Blob");
        _preferredJavaTypeForSqlType.put(2003, (String)"java.sql.Array");
        _preferredJavaTypeForSqlType.put(2006, (String)"java.sql.Ref");
        _preferredJavaTypeForSqlType.put(2002, (String)"java.lang.Object");
        _preferredJavaTypeForSqlType.put(2000, (String)"java.lang.Object");
    }

    private static class IntStringMap extends HashMap {
        private IntStringMap() {
        }

        public String getString(int i) {
            return (String)this.get(new Integer(i));
        }

        public String[] getStrings(int i) {
            return (String[])((String[])this.get(new Integer(i)));
        }

        public void put(int i, String s) {
            this.put(new Integer(i), s);
        }

        public void put(int i, String[] sa) {
            this.put(new Integer(i), sa);
        }
    }
}
