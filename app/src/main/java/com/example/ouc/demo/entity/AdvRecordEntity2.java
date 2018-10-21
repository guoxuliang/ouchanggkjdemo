package com.example.ouc.demo.entity;

import java.util.List;

public class AdvRecordEntity2 {

    /**
     * data : [{"taskId":1,"shareNum":2,"title":"魅力城市-1","userId":3,"quantity":624,"shareTime":"2018-10-17 10:39:10","gold":"0.2"},{"taskId":8,"shareNum":1,"title":"策划大师","userId":3,"quantity":996,"shareTime":"2018-10-17 10:15:10","gold":"0.1"},{"taskId":10,"shareNum":1,"title":"广告机宣传","userId":3,"quantity":1997,"shareTime":"2018-10-18 11:06:10","gold":"0.2"},{"taskId":11,"shareNum":2,"title":"广告机宣传2","userId":3,"quantity":1300,"shareTime":"2018-10-19 14:59:10","gold":"0.2"}]
     * code : 200
     * success : true
     * execute : true
     * msg : 查询会员分享广告记录成功
     */

    private int code;
    private boolean success;
    private boolean execute;
    private String msg;
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

    public boolean isExecute() {
        return execute;
    }

    public void setExecute(boolean execute) {
        this.execute = execute;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
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
         * shareNum : 2
         * title : 魅力城市-1
         * userId : 3
         * quantity : 624
         * shareTime : 2018-10-17 10:39:10
         * gold : 0.2
         */

        private int taskId;
        private int shareNum;
        private String title;
        private int userId;
        private int quantity;
        private String shareTime;
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

        public String getShareTime() {
            return shareTime;
        }

        public void setShareTime(String shareTime) {
            this.shareTime = shareTime;
        }

        public String getGold() {
            return gold;
        }

        public void setGold(String gold) {
            this.gold = gold;
        }
    }
}
