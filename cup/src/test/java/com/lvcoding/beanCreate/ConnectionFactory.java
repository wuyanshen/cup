package com.lvcoding.beanCreate;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author wuyanshen
 * @discription 描述
 * @date 2020-07-06 3:24 下午
 */
public class ConnectionFactory {

    public Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cup?useSSL=false", "root", "root");
        return conn;
    }
}
