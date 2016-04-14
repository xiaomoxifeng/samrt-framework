package com.json.smart4j.framework.proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * 切面代理
 * Created by wuhao on 16/4/13.
 * 在重写父类方法的时候,可以把方法改成final,防止子类再次覆盖
 */
public abstract class AspectProxy implements Proxy {
    private static final Logger logger = LoggerFactory.getLogger(AspectProxy.class);

    @Override
    public final Object doProxy(ProxyChain proxyChain) throws Throwable {
        Object result = null;
        Class<?> cls = proxyChain.getTargetClass();
        Method method = proxyChain.getTargetMethod();
        Object[] params = proxyChain.getMethodParams();
        begin();
        try {
            if(intercepter(cls,method,params)){
                before(cls,method,params);
                result = proxyChain.doProxyChain();
                after(cls,method,params);
            }
        } catch (Exception e) {
            logger.error("proxy failure", e);
            error(cls, method, params, e);
            throw e;
        } finally {
            end();
        }
        return result;
    }

    public void after(Class<?> cls, Method method, Object[] params) throws Throwable{

    }

    public void before(Class<?> cls, Method method, Object[] params) throws Throwable{

    }

    public boolean intercepter(Class<?> cls, Method method, Object[] params) throws Throwable {
        return true;
    }

    public void end() {
    }

    public void error(Class<?> cls, Method method, Object[] params, Exception e) {

    }

    public void begin() {

    }
}
