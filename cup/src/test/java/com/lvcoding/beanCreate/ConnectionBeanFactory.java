package com.lvcoding.beanCreate;

import lombok.Data;
import org.springframework.beans.factory.FactoryBean;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * @author wuyanshen
 * @description 描述
 * @date 2020-07-06 2:24 下午
 */
@Data
public class ConnectionBeanFactory implements FactoryBean<Connection> {

    private String url;
    private String username;
    private String password;

    @Override
    public Connection getObject() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection(url, username, password);
        return conn;
    }

    @Override
    public Class<?> getObjectType() {
        return Connection.class;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }
}
