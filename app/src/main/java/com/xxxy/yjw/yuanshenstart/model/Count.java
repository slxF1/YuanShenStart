package com.xxxy.yjw.yuanshenstart.model;

public class Count {
    private String count0;
    private String count1;

    public Count() {
    }

    public String getCount0() {
        return count0;
    }

    public void setCount0(String count0) {
        this.count0 = count0;
    }

    public String getCount1() {
        return count1;
    }

    public void setCount1(String count1) {
        this.count1 = count1;
    }

    @Override
    public String toString() {
        return "Count{" +
                "count0='" + count0 + '\'' +
                ", count1='" + count1 + '\'' +
                '}';
    }
}
