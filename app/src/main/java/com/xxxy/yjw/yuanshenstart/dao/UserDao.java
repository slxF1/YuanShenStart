package com.xxxy.yjw.yuanshenstart.dao;

import com.xxxy.yjw.yuanshenstart.utils.DBUtils;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class UserDao {
    public int listAll(){
        int a=0;
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConn();
            StringBuilder sqlstr=new StringBuilder();
            sqlstr.append("SELECT * FROM users ");
            pstm = conn.prepareStatement(sqlstr.toString());
            rs=pstm.executeQuery();
            while (rs.next()){
                a=rs.getInt(1);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return a;
    }

}
