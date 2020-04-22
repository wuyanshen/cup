package com.lvcoding.service.impl;

import com.lvcoding.entity.SysUser;
import lombok.SneakyThrows;
import org.assertj.core.util.Lists;
import org.springframework.util.PatternMatchUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author wuyanshen
 * @date 2020-04-12 10:10 上午
 * @discription 反射
 */
public class ReflectTest {
    public static void main(String[] args) {
//        test1();
//        test2();
//        test3();
//        test4();
//        test5();
        test6();
    }

    /**
     * 实例化对象
     *
     * @param
     * @return void
     * @date 2020/4/12 11:08 上午
     * @author wuyanshen
     */
    @SneakyThrows
    public static void test1(){
        SysUser sysUser = (SysUser)Class.forName("com.lvcoding.entity.SysUser").newInstance();
        sysUser.setUsername("小红");
        System.out.println(sysUser);
    }

    /**
     * 获取类加载器的名称->AppClassLoader
     *
     * @param
     * @return void
     * @date 2020/4/12 11:08 上午
     * @author wuyanshen
     */
    @SneakyThrows
    public static void test2(){
        Class<?> clazz = Class.forName("com.lvcoding.entity.SysUser");
        String classLoaderName = clazz.getClassLoader().getClass().getName();
        System.out.println(classLoaderName);
    }

    /**
     * 获取成员变量，成员方法，构造方法
     *
     * @param
     * @return void
     * @date 2020/4/12 11:09 上午
     * @author wuyanshen
     */
    @SneakyThrows
    public static void test3(){
        Class<?> clazz = Class.forName("com.lvcoding.entity.SysUser");
        //成员变量
        Field[] declaredFields = clazz.getDeclaredFields();
        //只能获取public的成员变量
        Field[] fields = clazz.getFields();
        //成员方法
        Method[] declaredMethods = clazz.getDeclaredMethods();
        //构造函数
        Constructor<?>[] declaredConstructors = clazz.getDeclaredConstructors();
        Stream.of(declaredConstructors).forEach(System.out::println);
    }

    /**
     * 操作get,set方法
     *
     * @param
     * @return void
     * @date 2020/4/12 11:12 上午
     * @author wuyanshen
     */
    @SneakyThrows
    public static void test4(){
        Class<?> clazz = Class.forName("com.lvcoding.entity.SysUser");
        Object obj = clazz.newInstance();
        Field username = clazz.getDeclaredField("username");
        username.setAccessible(true);
        username.set(obj,"小明");
        System.out.println(username.get(obj));
    }

    /**
     * 操作方法
     *
     * @param
     * @return void
     * @date 2020/4/12 11:14 上午
     * @author wuyanshen
     */
    @SneakyThrows
    public static void test5(){
        Class<?> clazz = Class.forName("com.lvcoding.entity.SysUser");
        Object obj = clazz.newInstance();
        Method setUsername = clazz.getDeclaredMethod("setUsername", String.class);
        Method getUsername = clazz.getDeclaredMethod("getUsername");
        setUsername.invoke(obj,"测试");
        getUsername.invoke(obj);
        System.out.println(getUsername.invoke(obj));
    }

    public static void test6(){
        String str = null;
        List<String> list = Arrays.asList("sys:user:view","sys:user:add","","sys:user:delete");
        boolean f = list.stream().filter(StringUtils::hasText).anyMatch(s -> PatternMatchUtils.simpleMatch("sys:user:view1",s));
        System.out.println(f);
    }
}
