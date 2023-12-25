package com.xxxy.yjw.yuanshenstart.dao;

import android.util.Log;

import com.xxxy.yjw.yuanshenstart.model.Content;
import com.xxxy.yjw.yuanshenstart.model.Polls;
import com.xxxy.yjw.yuanshenstart.model.Users;
import com.xxxy.yjw.yuanshenstart.model.Vote_records;
import com.xxxy.yjw.yuanshenstart.utils.DBUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Vote_RecordsDao {

    public List<Vote_records> getAll() {
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        List<Vote_records> vote_recordsArrayList = new ArrayList<>();

        try {
            conn = DBUtils.getConn();
            StringBuilder sqlstr = new StringBuilder();
            sqlstr.append("select v.vote_records_id," +
                    " p.polls_id,p.polls_title,p.polls_time," +
                    " p.content_id," +
                    " u.users_id,u.users_uid,u.users_uname" +
                    " from vote_records v join polls p on v.polls_id = p.polls_id" +
                    " join users u on v.users_id = u.users_id ;");
            pstm = conn.prepareStatement(sqlstr.toString());
            rs = pstm.executeQuery();
            while (rs.next()) {
                Vote_records voteRecords = new Vote_records();
                voteRecords.setVote_records_id(rs.getInt(1));

                Polls polls = new Polls();
                polls.setPollsid(rs.getInt(2));
                polls.setPolls_title(rs.getString(3));
                polls.setPolls_time(rs.getString(4));
                voteRecords.setPolls_id(polls);

                Content content = new Content();
                content.setContentid(rs.getInt(5));
                polls.setContent_id(content);


                Users users = new Users();
                users.setUserid(rs.getInt(6));
                users.setUseruid(rs.getInt(7));
                users.setUserName(rs.getString(8));
                polls.setUsers_id(users);
                voteRecords.setUsers_id(users);

                vote_recordsArrayList.add(voteRecords);


            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vote_recordsArrayList;
    }

    // 增加投票记录
    public int add(Vote_records vote_records) {
        int i = 0;
        Connection conn = null;
        PreparedStatement pstm = null;
        try {
            conn = DBUtils.getConn();
            StringBuilder sqlstr = new StringBuilder();
            sqlstr.append("insert into vote_records " +
                    "values(null,?,?)");
            pstm = conn.prepareStatement(sqlstr.toString());
            pstm.setInt(2, vote_records.getPolls_id().getPollsid());
            pstm.setInt(3, vote_records.getUsers_id().getUserid());
            i = pstm.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return i;
    }

    // 删除投票记录
    public int del(int id) {
        int i = 0;
        Connection conn = null;
        PreparedStatement pstm = null;
        try {
            conn = DBUtils.getConn();
            StringBuilder sqlstr = new StringBuilder();
            sqlstr.append("delete from vote_records where vote_records = ?;");
            pstm = conn.prepareStatement(sqlstr.toString());
            pstm.setInt(1, id);
            i = pstm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return i;


    }

    //进度条查询
    public Map<String, List<String>> getProgress(String title) {
        Map<String, List<String>> map = new HashMap<>();
        List<String> list = new ArrayList<>();
        List<String> list1 = new ArrayList<>();
        List<String> list2 = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConn();
            StringBuilder sqlstr = new StringBuilder();
            sqlstr.append("select count(*),content.content_show,polls.polls_title" +
                    " from content,polls,vote_records" +
                    " where vote_records.polls_id = polls.polls_id" +
                    " and polls.content_id=content.content_id" +
                    " and polls.polls_title =?" +
                    " group by polls.users_id,vote)records.polls_id");
            pstm = conn.prepareStatement(sqlstr.toString());
            pstm.setString(1, title);
            rs = pstm.executeQuery();
            while (rs.next()) {
                list.add(rs.getString(1));
                list1.add(rs.getString(2));
                list2.add(rs.getString(3));
            }
            map.put("count(*)",list);
            map.put("content.content_show",list1);
            map.put("polls.polls_title",list2);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return map;

    }


}
