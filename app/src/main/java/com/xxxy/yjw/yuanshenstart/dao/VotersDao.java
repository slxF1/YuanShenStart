package com.xxxy.yjw.yuanshenstart.dao;

import com.xxxy.yjw.yuanshenstart.model.Content;
import com.xxxy.yjw.yuanshenstart.model.Polls;
import com.xxxy.yjw.yuanshenstart.model.Users;
import com.xxxy.yjw.yuanshenstart.model.Voters;
import com.xxxy.yjw.yuanshenstart.utils.DBUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VotersDao {
    public List<Voters> getAll() {
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        List<Voters> votersList = new ArrayList<>();

        try {
            conn = DBUtils.getConn();
            StringBuilder sqlstr = new StringBuilder();
            sqlstr.append("select v.voters_id,v.voters_flag," +
                    " p.polls_id,p.polls_title,p.polls_time,p.content_id," +
                    " u.users_id,u.users_uid,u.users_uname from voters v " +
                    " join users u on v.users_id = u.users_id " +
                    " join polls p on v.polls_id = p.polls_id;");
            pstm = conn.prepareStatement(sqlstr.toString());
            rs = pstm.executeQuery();
            while (rs.next()) {
                Voters voters = new Voters();
                voters.setVoters_id(rs.getInt(1));
                voters.setVoters_flag(rs.getInt(2));
                Polls polls = new Polls();
                polls.setPollsid(rs.getInt(3));
                polls.setPolls_title(rs.getString(4));
                polls.setPolls_time(rs.getString(5));
                Content content = new Content();
                content.setContentid(rs.getInt(6));
                polls.setContent_id(content);
                Users users = new Users();
                users.setUserid(rs.getInt(7));
                users.setUseruid(rs.getInt(8));
                users.setUserName(rs.getString(9));
                voters.setPolls_id(polls);
                voters.setUsers_id(users);
                votersList.add(voters);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return votersList;
    }

    //删除
    public int delete(int voters_id) {
        int i = 0;
        Connection conn = null;
        PreparedStatement pstm = null;
        try {
            conn = DBUtils.getConn();
            StringBuilder sqlstr = new StringBuilder();
            sqlstr.append("delete from voters where voters_id = ?;");
            pstm = conn.prepareStatement(sqlstr.toString());
            pstm.setInt(1, voters_id);
            i = pstm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return i;
    }

    //增加
    public int add(Voters voters) {
        int i = 0;
        Connection conn = null;
        PreparedStatement pstm = null;
        try {
            conn = DBUtils.getConn();
            StringBuilder sqlstr = new StringBuilder();
            sqlstr.append("insert into voters values(null,?,?,?);");
            pstm = conn.prepareStatement(sqlstr.toString());
            pstm.setInt(1, voters.getUsers_id().getUserid());
            pstm.setInt(2, voters.getPolls_id().getPollsid());
            pstm.setInt(3, voters.getVoters_flag());
            i = pstm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return i;

    }

    //统计是否投票
    public Map<String, Integer> count(int voters_flag0, int voters_flag1) {
        int i0 = 0;
        int i1 = 0;
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        PreparedStatement pstm1 = null;
        ResultSet rs1 = null;
        Map<String, Integer> map = new HashMap<>();
        try {
            conn = DBUtils.getConn();
            StringBuilder sqlstr = new StringBuilder();
            StringBuilder sqlstr1 = new StringBuilder();
            sqlstr.append("select count(*) from voters where voters_flag = ?;");
            sqlstr1.append("select count(*) from voters where voters_flag != ?;");
            pstm = conn.prepareStatement(sqlstr.toString());
            pstm.setInt(1, voters_flag0);
            pstm1 = conn.prepareStatement(sqlstr1.toString());
            pstm1.setInt(1, voters_flag1);
            rs = pstm.executeQuery();
            rs1 = pstm1.executeQuery();
            while (rs.next()) {
                i0 = rs.getInt(1);
                map.put("0", i0);
            }
            while (rs1.next()) {
                i1 = rs1.getInt(1);
                map.put("1", i1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return map;
    }

    //查询人名根据是否投票
    public Map<String, List<String>> findNameByVote(int voters_flag) {
        Map<String, List<String>> map = new HashMap<>();
        List<String> list = new ArrayList<>();
        List<String> list1 = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        PreparedStatement pstm1 = null;
        ResultSet rs1 = null;
        try {
            conn = DBUtils.getConn();
            StringBuilder sqlstr = new StringBuilder();
            StringBuilder sqlstr1 = new StringBuilder();
            sqlstr.append("select u.users_uname from users u " +
                    " join voters v on v.users_id = u.users_id " +
                    " where v.voters_flag = ?;");
            pstm = conn.prepareStatement(sqlstr.toString());
            pstm.setInt(1, voters_flag);
            sqlstr1.append("select u.users_uname from users u " +
                    " join voters v on v.users_id = u.users_id " +
                    " where v.voters_flag != ?;");
            pstm = conn.prepareStatement(sqlstr1.toString());
            pstm1.setInt(1, voters_flag);
            rs = pstm.executeQuery();
            rs1 = pstm1.executeQuery();
            while (rs.next()) {
                list.add(rs.getString(1));
                map.put("0", list);
            }
            while (rs1.next()) {
                list.add(rs.getString(1));
                map.put("1", list1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return map;
    }

}
