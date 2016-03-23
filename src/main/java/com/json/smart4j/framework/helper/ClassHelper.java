package com.json.smart4j.framework.helper;

import com.json.smart4j.framework.annotation.Controller;
import com.json.smart4j.framework.annotation.Service;
import com.json.smart4j.framework.util.ClassUtil;

import java.util.HashSet;
import java.util.Set;

/**
 * Class<T>在实例化的时候，T要替换成具体类
 * Class<?>它是个通配泛型，?可以代表任何类型
 * 类操作
 * Created by wuhao on 16/3/23.
 */
public class ClassHelper {
    private static final Set<Class<?>> CLASS_SET;

    static {
        String basePackage = ConfigHelper.getAppBasePackage();
        CLASS_SET = ClassUtil.getClassSet(basePackage);
    }

    /**
     * 获取应用包名下的所有类
     *
     * @return
     */
    public static Set<Class<?>> getClassSet() {
        return CLASS_SET;
    }

    public static Set<Class<?>> getServiceClassSet() {
        Set<Class<?>> classSet = new HashSet<Class<?>>();
        for (Class<?> cls : CLASS_SET) {
            //判断是否被所需的标签修饰
            if (cls.isAnnotationPresent(Service.class)) {
                classSet.add(cls);
            }
        }
        return classSet;
    }

    public static Set<Class<?>> getControllerClassSet() {
        Set<Class<?>> classSet = new HashSet<Class<?>>();
        for (Class<?> cls : CLASS_SET) {
            //判断是否被所需的标签修饰
            if (cls.isAnnotationPresent(Controller.class)) {
                classSet.add(cls);
            }
        }
        return classSet;
    }

    public static Set<Class<?>> getBeanClassSet() {
        Set<Class<?>> classSet = new HashSet<Class<?>>();
        classSet.addAll(getControllerClassSet());
        classSet.addAll(getServiceClassSet());
        return classSet;
    }
}
