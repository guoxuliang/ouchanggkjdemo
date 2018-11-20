package com.example.ouc.demo.entity;

import java.util.List;

public class ShareSumEntity {

    /**
     * data : [{"money":1,"createTime":1542007843000,"title":"魅力城市-2"},{"money":32,"createTime":1542007856000,"title":"魅力城市-4"},{"money":43,"createTime":1542007871000,"title":"魅力城市-3"}]
     * code : 200
     * msg : 查询用户返佣记录成功
     * execute : true
     * success : true
     */

    private int code;
    private String msg;
    private boolean execute;
    private boolean success;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isExecute() {
        return execute;
    }

    public void setExecute(boolean execute) {
        this.execute = execute;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * money : 1.0
         * createTime : 1542007843000
         * title : 魅力城市-2
         */

        private double money;
        private String createTime;
        private String title;

        public double getMoney() {
            return money;
        }

        public void setMoney(double money) {
            this.money = money;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
