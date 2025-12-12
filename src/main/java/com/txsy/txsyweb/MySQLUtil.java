package com.txsy.txsyweb;

import java.sql.*;


public class MySQLUtil {
    private static final String DB_URL = "jdbc:mysql://172.18.64.254:3306/studentdb?useUnicode=yes&characterEncoding=UTF-8";
    private static final String USER = "root";
    private static final String PASSWORD = "mysql";
    static {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch(ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws  SQLException{
        return DriverManager.getConnection(DB_URL, USER, PASSWORD);
    }
    public static void close(Connection conn, Statement stmt, ResultSet rs){
        try {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
