package com.oval.util;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public final class PropertiesUtil {

	private static Log log = LogFactory.getLog(PropertiesUtil.class);
    /**
     * 根据配置文件路径和key，获取配置参数
     * @param key
     * @param configPath
     * @return
     */
    public static String getString(String key,String configPath) {
        //如果配置文件唯一，可以在类中把configPath成名为常量，把ResourceBundle对象声明为全局静态变量
        ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(configPath);
        try {
            return RESOURCE_BUNDLE.getString(key);
        } catch (MissingResourceException e) {
            log.error("getString", e);
            return null;
        }
    }
    /**
     * 根据配置文件路径和key，获取配置参数（整型）
     * @param key
     * @param configPath
     * @return
     */
    public static int getInt(String key,String configPath) {
        return Integer.parseInt(getString(key,configPath));
    }
    /*
     * 测试
     */
    public static void main(String[] args) {
        System.out.println(getString("DIR_PREFIX","paras"));
    }
}
