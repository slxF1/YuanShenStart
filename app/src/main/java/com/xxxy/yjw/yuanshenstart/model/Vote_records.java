package com.xxxy.yjw.yuanshenstart.model;

public class Vote_records {
    private int vote_records_id;
    private Polls polls_id;
    private Users users_id;

    public Vote_records() {
    }

    public int getVote_records_id() {
        return vote_records_id;
    }

    public void setVote_records_id(int vote_records_id) {
        this.vote_records_id = vote_records_id;
    }

    public Polls getPolls_id() {
        return polls_id;
    }

    public void setPolls_id(Polls polls_id) {
        this.polls_id = polls_id;
    }

    public Users getUsers_id() {
        return users_id;
    }

    public void setUsers_id(Users users_id) {
        this.users_id = users_id;
    }

    @Override
    public String toString() {
        return "Vote_records{" +
                "vote_records_id=" + vote_records_id +
                ", polls_id=" + polls_id +
                ", users_id=" + users_id +
                '}';
    }
}
