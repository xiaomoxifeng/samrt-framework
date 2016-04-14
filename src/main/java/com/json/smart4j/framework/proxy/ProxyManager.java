package com.json.smart4j.framework.proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.List;

/**
 * 代理管理器
 * Created by wuhao on 16/4/13.
 */
public class ProxyManager {
    public  static  <T> T createProxy(final Class<?> targetClass, final List<Proxy> proxyList){
        return  (T) Enhancer.create(targetClass, new MethodInterceptor() {
            @Override
            public Object intercept(Object tagetObject, Method targetMethod, Object[] methodParams, MethodProxy methodProxy) throws Throwable {
                return new ProxyChain(targetClass,tagetObject,targetMethod,methodProxy,methodParams,proxyList).doProxyChain();
            }
        });
    }
}
