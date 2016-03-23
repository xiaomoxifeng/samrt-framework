package com.json.smart4j.framework.bean;

import com.json.smart4j.framework.util.CastUtil;

import java.util.Map;

/**
 * Created by wuhao on 16/3/23.
 */
public class Param {
    private Map<String,Object> paramMap;

    public Param(Map<String, Object> paramMap) {
        this.paramMap = paramMap;
    }
    public long getLong(String name){
        return CastUtil.castLong(paramMap.get(name));
    }
    public Map<String,Object> getMap(){
        return paramMap;
    }
}
