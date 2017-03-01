package com.touchstone.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
/**
 * 用于在非spring管理类中使用spring 的bean
 * @author zhuwenhong on 2016/12/30.
 */
public class SpringUtils {

    /**
     * 当前IOC
     */
    private static ApplicationContext applicationContext;

    /**
     * 设置当前上下文环境
     * @param context ApplicationContext
     */
    public static void setApplicationContext(ApplicationContext context)
            throws BeansException {
        applicationContext = context;
    }

    /**
     * 从当前IOC获取bean
     * @param id bean的id
     * @return Object
     */
    public static Object getObject(String id) {
        Object object = null;
        object = applicationContext.getBean(id);
        return object;
    }

}