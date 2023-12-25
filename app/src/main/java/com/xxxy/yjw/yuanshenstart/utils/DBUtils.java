package com.xxxy.yjw.yuanshenstart.utils;

import android.util.Log;
import java.sql.Connection;
import java.sql.DriverManager;

/**
 * function： 数据库工具类，连接数据库用
 */
public class DBUtils {
    private static final String TAG = "mysql-party-JDBCUtils";
    private static String driver = "com.mysql.jdbc.Driver";// MySql驱动
    private static String dbName = "yuanshenstart";// 数据库名称
    private static String user = "root";// 用户名
    private static String password = "hsp";// 密码
    public static Connection getConn(){
        Connection connection = null;
        try{
            Class.forName(driver);// 动态加载类
            String ip = "192.168.196.1";// 写成本机地址，不能写成localhost，
            // 尝试建立到给定数据库URL的连接
            connection = DriverManager.getConnection("jdbc:mysql://" + ip + ":3306/" + dbName, user, password);
//            Log.d("ning","数据库连接成功！");
        }catch (Exception e){
            e.printStackTrace();
//            Log.d("ning","数据库连接失败！");
        }
        return connection;
    }
}