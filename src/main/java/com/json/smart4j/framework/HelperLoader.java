package com.json.smart4j.framework;

import com.json.smart4j.framework.helper.*;
import com.json.smart4j.framework.util.ClassUtil;

/**
 * 加载响应的Helper
 * Created by wuhao on 16/3/23.
 */
public final class HelperLoader {
    public static void init() {
        Class<?>[] classList = {
                ClassHelper.class,
                BeanHelper.class,
                AopHelper.class,
                IocHelper.class,
                ControllerHelper.class
        };
        for(Class<?> cls :classList){
            ClassUtil.loadClass(cls.getName());
        }
    }
}
