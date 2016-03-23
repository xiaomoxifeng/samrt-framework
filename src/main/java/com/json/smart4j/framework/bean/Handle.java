package com.json.smart4j.framework.bean;

import java.lang.reflect.Method;

/**
 * 封装Action信息
 * Created by wuhao on 16/3/23.
 */
public class Handle {
    /**
     * Controller 类
     */
    private Class<?> controllerClass;

    /**
     * Action 方法
     */
    private Method actionMethod;
    public Handle(Class<?> controllerClass, Method actionMethod) {
        this.controllerClass = controllerClass;
        this.actionMethod = actionMethod;
    }
    public Method getActionMethod() {
        return actionMethod;
    }

    public Class<?> getControllerClass() {
        return controllerClass;
    }
}
