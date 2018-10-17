package com.example.ouc.demo.entity;

import java.util.List;

public class AdvRecordEntity {


    /**
     * data : [{"taskId":1,"shareNum":0,"title":"魅力城市-1","watchtime":"2018-10-16 16:06:10","watchNum":1,"userId":3,"quantity":626,"gold":"0.2"},{"taskId":8,"shareNum":0,"title":"策划大师","watchtime":"2018-10-16 16:27:10","watchNum":1,"userId":3,"quantity":997,"gold":"0.1"},{"taskId":2,"shareNum":0,"title":"魅力城市-2","watchNum":0,"userId":3,"quantity":161,"gold":"0.03"}]
     * code : null
     * success : true
     * msg : 查询会员观看广告记录成功
     * execute : true
     */

    private int code;
    private boolean success;
    private String msg;
    private boolean execute;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * taskId : 1
         * shareNum : 0
         * title : 魅力城市-1
         * watchtime : 2018-10-16 16:06:10
         * watchNum : 1
         * userId : 3
         * quantity : 626
         * gold : 0.2
         */

        private int taskId;
        private int shareNum;
        private String title;
        private String watchtime;
        private int watchNum;
        private int userId;
        private int quantity;
        private String gold;

        public int getTaskId() {
            return taskId;
        }

        public void setTaskId(int taskId) {
            this.taskId = taskId;
        }

        public int getShareNum() {
            return shareNum;
        }

        public void setShareNum(int shareNum) {
            this.shareNum = shareNum;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getWatchtime() {
            return watchtime;
        }

        public void setWatchtime(String watchtime) {
            this.watchtime = watchtime;
        }

        public int getWatchNum() {
            return watchNum;
        }

        public void setWatchNum(int watchNum) {
            this.watchNum = watchNum;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public String getGold() {
            return gold;
        }

        public void setGold(String gold) {
            this.gold = gold;
        }
    }
}
