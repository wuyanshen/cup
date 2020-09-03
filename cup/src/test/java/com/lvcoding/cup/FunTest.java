package com.lvcoding.cup;

import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * @Description 描述
 * @Date 2020-09-01 5:02 下午
 * @Author wuyanshen
 */
public class FunTest {

    public static Integer tenantId = 1;

    // 消费性接口
    @FunctionalInterface
    public interface MyFun<T>{
        void run(T tenantId);
    }

    // 功能性接口
    @FunctionalInterface
    public interface MyApply<T,R>{
        R apply(T tenantId);
    }

    public static void test(Integer tenantId, MyFun<Integer> myFun){
        final Integer yid = FunTest.tenantId;
        try {
            System.out.println("我执行了");
            FunTest.tenantId = tenantId;
            myFun.run(tenantId);
        }catch (Exception e){
            throw new RuntimeException("error");
        }
        finally {
            FunTest.tenantId = yid;
        }
    }

    public static void test2(Integer tenantId){
        try {
            System.out.println("我执行了2");
            FunTest.tenantId = tenantId;
        }catch (Exception e){
            throw new RuntimeException("error");
        }
        finally {
            FunTest.tenantId = 1;
        }
    }

    public static void test(Supplier<Integer> supplier, MyFun<Integer> func) {
        test(supplier.get(), func);
    }

    public static <T> T applyAs(Integer tenantId, MyApply<Integer,T> myApply){
        final Integer yid = FunTest.tenantId;
        try {
            System.out.println("我执行了2");
            FunTest.tenantId = tenantId;
            return myApply.apply(tenantId);
        }catch (Exception e){
            throw new RuntimeException("error");
        }
        finally {
            FunTest.tenantId = yid;
        }
    }

    public static void main(String[] args) {

        //String str = applyAs(66, id->{
        //    System.out.println("函数里面：" + FunTest.tenantId);
        //    return "hello";
        //});
        //System.out.println("str = " + str);
        //Supplier<Integer> supplier = () -> 33;
        //test(() -> 33,(id)->{
        //    System.out.println(id);
        //});
        //System.out.println("函数外面：" + FunTest.tenantId);

        test(2,(tenantId)-> {
            System.out.println("函数里面1：" + FunTest.tenantId);
            test(66,(id)-> {
                System.out.println("函数里面2：" + FunTest.tenantId);
                test(88,(id2)-> {
                    System.out.println("函数里面3：" + FunTest.tenantId);
                });
            });
            System.out.println("函数里面4：" + FunTest.tenantId);
        });
        System.out.println("函数外面：" + FunTest.tenantId);

        //Consumer<String> consumer = System.out::println;
        //consumer.accept("哈哈");


        //MyFun<String> myFun = System.out::println;
        //myFun.run("啦啦啦");
        //MyApply<String,String> myApply = str -> "hello " + str;
        //System.out.println(myApply.apply("world"));
        //
        //Predicate<String> predicate = "Hello"::equalsIgnoreCase;
        //System.out.println(predicate.test("hello1"));
    }
}
