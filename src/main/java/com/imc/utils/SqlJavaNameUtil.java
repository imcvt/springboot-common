package com.imc.utils;

import java.beans.Introspector;

/**
 * @author luoly
 * @date 2019/4/15 14:29
 * @description
 */
public class SqlJavaNameUtil {
    public SqlJavaNameUtil() {
    }

    public static String getQualifiedClassName(String packageName, String className) {
        String result;
        if ("".equals(packageName)) {
            result = className;
        } else {
            result = packageName + "." + className;
        }

        return result;
    }

    public static boolean equals(Object a, Object b) {
        if (a == null && b == null) {
            return true;
        } else {
            return a != null && a.equals(b);
        }
    }

    public static String ensureNotNull(String s) {
        return s == null ? "" : s;
    }

    public static String decapitalise(String s) {
        String result = Introspector.decapitalize(s);
        if ("class".equals(result)) {
            result = "clazz";
        }

        return result;
    }

    public static boolean bool(String s) {
        return "true".equals(s);
    }

    public static String string(boolean b) {
        return b ? "true" : "false";
    }

    public static String pluralise(String name) {
        System.out.println("pluralise:" + name);
        String result = name;
        if (name.length() == 1) {
            result = name + 's';
        } else if (!seemsPluralised(name)) {
            String lower = name.toLowerCase();
            char secondLast = lower.charAt(name.length() - 2);
            if (!isVowel(secondLast) && lower.endsWith("y")) {
                result = name.substring(0, name.length() - 1) + "ies";
            } else if (!lower.endsWith("ch") && !lower.endsWith("s")) {
                result = name + "s";
            } else {
                result = name + "es";
            }
        }

        System.out.println("pluralised " + name + " to " + result);
        return result;
    }

    public static String singularise(String name) {
        System.out.println("singularise:" + name);
        String result = name;
        if (seemsPluralised(name)) {
            String lower = name.toLowerCase();
            if (lower.endsWith("ies")) {
                result = name.substring(0, name.length() - 3) + "y";
            } else if (!lower.endsWith("ches") && !lower.endsWith("ses")) {
                if (lower.endsWith("s")) {
                    result = name.substring(0, name.length() - 1);
                }
            } else {
                result = name.substring(0, name.length() - 2);
            }
        }

        System.out.println("singularised " + name + " to " + result);
        return result;
    }

    public static String capitalise(String s) {
        if (s.equals("")) {
            return "";
        } else if (s.length() == 1) {
            return s.toUpperCase();
        } else {
            String caps = s.substring(0, 1).toUpperCase();
            String rest = s.substring(1);
            return caps + rest;
        }
    }

    private static final boolean isVowel(char c) {
        boolean vowel = false;
        vowel |= c == 'a';
        vowel |= c == 'e';
        vowel |= c == 'i';
        vowel |= c == 'o';
        vowel |= c == 'u';
        vowel |= c == 'y';
        return vowel;
    }

    private static boolean seemsPluralised(String name) {
        name = name.toLowerCase();
        boolean pluralised = false;
        pluralised |= name.endsWith("es");
        pluralised |= name.endsWith("s");
        pluralised &= !name.endsWith("ss") && !name.endsWith("us");
        return pluralised;
    }

    public static String tableNameToVariableName(String tableName) {
        return dbNameToVariableName(tableName);
    }

    public static String columnNameToVariableName(String columnName) {
        return dbNameToVariableName(columnName);
    }

    public static String dbNameToVariableName(String s) {
        if ("".equals(s)) {
            return s;
        } else {
            StringBuffer result = new StringBuffer();
            boolean capitalize = true;
            boolean lastCapital = false;
            boolean lastDecapitalized = false;
            String p = null;

            for(int i = 0; i < s.length(); ++i) {
                String c = s.substring(i, i + 1);
                if (!"_".equals(c) && !" ".equals(c)) {
                    if (c.toUpperCase().equals(c)) {
                        if (lastDecapitalized && !lastCapital) {
                            capitalize = true;
                        }

                        lastCapital = true;
                    } else {
                        lastCapital = false;
                    }

                    if (capitalize) {
                        if (p != null && p.equals("_")) {
                            result.append(c.toLowerCase());
                            capitalize = false;
                            p = c;
                        } else {
                            result.append(c.toUpperCase());
                            capitalize = false;
                            p = c;
                        }
                    } else {
                        result.append(c.toLowerCase());
                        lastDecapitalized = true;
                        p = c;
                    }
                } else {
                    capitalize = true;
                }
            }

            String r = result.toString();
            return r;
        }
    }

    public static String getVariableName(String name, boolean pluralised) {
        if (name != null) {
            name = dbNameToVariableName(name);
            name = pluralised ? pluralise(name) : name;
            name = decapitalise(name);
        }

        return name;
    }

    public static String getJavaTypeName(String name, boolean pluralised) {
        if (name != null) {
            name = dbNameToVariableName(name);
            name = pluralised ? pluralise(name) : name;
            name = capitalise(name);
        }

        return name;
    }

    public static void main(String[] args) {
        System.out.println(getVariableName("USERAPP", true));
        System.out.println(getVariableName("UserApp", true));
        System.out.println(getVariableName("user_app", true));
        System.out.println(getJavaTypeName("USERAPP", true));
        System.out.println(getJavaTypeName("UserApp", true));
        System.out.println(getJavaTypeName("user_app", true));
        System.out.println(getVariableName("USERAPP", false));
        System.out.println(getVariableName("UserApp", false));
        System.out.println(getVariableName("user_app", false));
        System.out.println(getJavaTypeName("USERAPP", false));
        System.out.println(getJavaTypeName("UserApp", false));
        System.out.println(getJavaTypeName("user_app", false));
    }
}
