package com.lvcoding.beanFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class BeanFactory {

    private static Properties properties = new Properties();

    static {
        InputStream inputStream = BeanFactory.class.getClassLoader().getResourceAsStream("beanFactory.properties");
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Object getBean(String key) {
       String clazzName = properties.get(key).toString();
       Object obj = null;
        try {
            obj = Class.forName(clazzName).newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }
}
