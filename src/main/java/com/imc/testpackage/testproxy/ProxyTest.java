package com.imc.testpackage.testproxy;

/**
 * @author luoly
 * @date 2019/5/22 10:04
 * @description 2种代理方式，jdk动态代理只能对实现接口的类生成代理；CGlib是针对类实现代理，对指定的类生成一个子类，并覆盖其中的方法，因此不能代理final修改的类
 */
public class ProxyTest {
    public static void main(String[] args) {
        UserManager userManager = (UserManager)new CGLibProxy().createProxyObject(new UserManagerImpl());
        System.out.println("CGLibProxy：");
        userManager.addUser("tom", "root");
        System.out.println("JDKProxy：");
        JDKProxy jdkProxy = new JDKProxy();
        UserManager userManagerJDK = (UserManager)jdkProxy.newProxy(new UserManagerImpl());
        userManagerJDK.addUser("tom", "root");
    }
}
