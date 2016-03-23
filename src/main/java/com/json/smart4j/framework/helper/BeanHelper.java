package com.json.smart4j.framework.helper;

import com.json.smart4j.framework.util.ReflectionUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Bean助手类
 * Bean容器
 * Created by wuhao on 16/3/23.
 */
public final class BeanHelper {
    private static final Map<Class<?>,Object> BEAN_MAP = new HashMap<Class<?>,Object>();
    static {
        Set<Class<?> > beanClassSet =ClassHelper.getBeanClassSet();
        for (Class<?> beanClass :beanClassSet){
            Object obj = ReflectionUtil.newInstance(beanClass);
            BEAN_MAP.put(beanClass,obj);
        }
    }

    /**
     * 获取bean映射
     * @return
     */
    public static Map<Class<?>,Object> getBeanMap(){
        return BEAN_MAP;
    }
    @SuppressWarnings("unchecked")
    public static <T> T getBean(Class<T> cls){
        if (!BEAN_MAP.containsKey(cls)){
            throw new RuntimeException("can not get bean by class:"+cls);
        }
        return (T) BEAN_MAP.get(cls);
    }
}
