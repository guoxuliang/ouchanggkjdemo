package com.example.ouc.demo.entity;

import java.util.List;

public class MyOrderEntity {

    /**
     * data : [{"taskId":9,"id":132,"title":"美睫展示","taskid":9,"userId":68,"quantity":1289,"gold":"0.2","type":"1","recevieTime":"2018-11-08 11:22:11","finishStatus":"1","integral":"0.2"},{"taskId":8,"id":133,"title":"纹绣展示","taskid":8,"userId":68,"quantity":1293,"gold":"0.2","type":"1","recevieTime":"2018-11-08 11:27:11","finishStatus":"1","integral":"0.2"}]
     * success : true
     * msg : 查询用户已进账成功
     * execute : true
     * code : 200
     */

    private boolean success;
    private String msg;
    private boolean execute;
    private int code;
    private List<DataBean> data;

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

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * taskId : 9
         * id : 132
         * title : 美睫展示
         * taskid : 9
         * userId : 68
         * quantity : 1289
         * gold : 0.2
         * type : 1
         * recevieTime : 2018-11-08 11:22:11
         * finishStatus : 1
         * integral : 0.2
         */

        private int taskId;
        private int id;
        private String title;
        private int taskid;
        private int userId;
        private int quantity;
        private String gold;
        private String type;
        private String recevieTime;
        private String finishStatus;
        private String integral;

        public int getTaskId() {
            return taskId;
        }

        public void setTaskId(int taskId) {
            this.taskId = taskId;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getTaskid() {
            return taskid;
        }

        public void setTaskid(int taskid) {
            this.taskid = taskid;
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

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getRecevieTime() {
            return recevieTime;
        }

        public void setRecevieTime(String recevieTime) {
            this.recevieTime = recevieTime;
        }

        public String getFinishStatus() {
            return finishStatus;
        }

        public void setFinishStatus(String finishStatus) {
            this.finishStatus = finishStatus;
        }

        public String getIntegral() {
            return integral;
        }

        public void setIntegral(String integral) {
            this.integral = integral;
        }
    }
}
