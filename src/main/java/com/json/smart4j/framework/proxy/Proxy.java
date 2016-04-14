package com.json.smart4j.framework.proxy;

/**
 * Created by wuhao on 16/4/12.
 */
public interface Proxy {
    Object doProxy(ProxyChain proxyChain)throws Throwable;
}
