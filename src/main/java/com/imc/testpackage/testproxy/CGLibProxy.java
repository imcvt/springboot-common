package com.imc.testpackage.testproxy;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author luoly
 * @date 2019/5/22 09:54
 * @description
 */
public class CGLibProxy implements MethodInterceptor {

    // CGlib需要代理的目标对象
    private Object targetObject;

    public Object createProxyObject(Object obj) {
        this.targetObject = obj;
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(obj.getClass());
        enhancer.setCallback(this);
        Object proxyObj = enhancer.create();
        return proxyObj;
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        Object obj = null;
        // 过滤方法
        if ("addUser".equals(method.getName())) {
            // 检查权限
            checkPopedom();
        }
        obj = method.invoke(targetObject, objects);
        return obj;
    }

    private void checkPopedom() {
        System.out.println("检查权限：checkPopedom()!");
    }
}
