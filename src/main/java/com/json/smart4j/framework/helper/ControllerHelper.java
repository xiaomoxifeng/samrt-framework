package com.json.smart4j.framework.helper;

import com.json.smart4j.framework.annotation.Action;
import com.json.smart4j.framework.bean.Handle;
import com.json.smart4j.framework.bean.Request;
import com.json.smart4j.framework.util.ArrayUtil;
import com.json.smart4j.framework.util.CollectionUtil;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 控制器助手类
 * Created by wuhao on 16/3/23.
 */
public class ControllerHelper {
    private  static final Map<Request,Handle> ACTION_MAP = new HashMap<Request,Handle>();
    static {
        Set<Class<?>> controllerClassSet = ClassHelper.getControllerClassSet();
        if(CollectionUtil.isNotEmpty(controllerClassSet)){
            for(Class<?> controllerClass :controllerClassSet){
                Method[] methods = controllerClass.getDeclaredMethods();
                if(ArrayUtil.isNotEmpty(methods)){
                    for (Method method :methods){
                        if(method.isAnnotationPresent(Action.class)){
                            Action action = method.getAnnotation(Action.class);
                            String mapping = action.value();
                            //验证URL映射规则
                            if(mapping.matches("\\w+:/\\w*")){
                                String[] array = mapping.split(":");
                                if(ArrayUtil.isNotEmpty(array)&&array.length ==2){
                                    String requestMethod =array[0];
                                    String requestPath =array[1];
                                    Request request = new Request(requestMethod,requestPath);
                                    Handle handle = new Handle(controllerClass,method);
                                    ACTION_MAP.put(request,handle);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 获取Handler
     * @param requestMethod
     * @param requestPath
     * @return
     */
    public static  Handle getHandler(String requestMethod,String requestPath){
        Request request = new Request(requestMethod,requestPath);
        return ACTION_MAP.get(request);
    }
}
