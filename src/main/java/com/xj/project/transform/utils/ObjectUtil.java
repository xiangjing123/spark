package com.xj.project.transform.utils;

/**
 * ces
 *
 * @author xiangjing
 * @date 2018/4/2
 * @company 天极云智
 */
public class ObjectUtil {

    public static boolean isNull(Object obj){
        return obj == null;
    }

    public static boolean isNotNull(Object obj){
        return !isNull(obj);
    }
}
