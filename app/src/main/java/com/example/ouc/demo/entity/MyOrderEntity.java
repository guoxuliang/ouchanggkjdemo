package com.example.ouc.demo.entity;

import java.util.List;

public class MyOrderEntity {

    /**
     * data : [{"taskId":1,"id":37,"title":"魅力城市-1","taskid":1,"userId":3,"quantity":624,"gold":"0.2","type":"2","finishStatus":"1","integral":"0.22"},{"taskId":1,"id":37,"title":"魅力城市-1","taskid":1,"userId":3,"quantity":624,"gold":"0.2","type":"1","finishStatus":"1","integral":"0.02"},{"taskId":1,"id":37,"title":"魅力城市-1","taskid":1,"userId":3,"quantity":624,"gold":"0.2","type":"2","finishStatus":"1","integral":"0.02"},{"taskId":3,"id":40,"title":"魅力城市-3","taskid":3,"userId":3,"quantity":4611,"gold":"0.04","type":"1","finishStatus":"1","integral":"0.04"},{"taskId":4,"id":48,"title":"魅力城市-4","taskid":4,"userId":3,"quantity":1297,"gold":"0.05","type":"1","finishStatus":"1","integral":"0.06"},{"taskId":8,"id":39,"title":"策划大师","taskid":8,"userId":3,"quantity":996,"gold":"0.1","type":"1","finishStatus":"1","integral":"0.11"},{"taskId":8,"id":39,"title":"策划大师","taskid":8,"userId":3,"quantity":996,"gold":"0.1","type":"1","finishStatus":"1","integral":"0.01"},{"taskId":10,"id":38,"title":"广告机宣传","taskid":10,"userId":3,"quantity":1997,"gold":"0.2","type":"1","finishStatus":"1","integral":"0.22"},{"taskId":10,"id":38,"title":"广告机宣传","taskid":10,"userId":3,"quantity":1997,"gold":"0.2","type":"2","finishStatus":"1","integral":"0.02"},{"taskId":10,"id":38,"title":"广告机宣传","taskid":10,"userId":3,"quantity":1997,"gold":"0.2","type":"2","finishStatus":"1","integral":"0.02"},{"taskId":11,"id":56,"title":"广告机宣传2","taskid":11,"userId":3,"quantity":1300,"gold":"0.2","type":"2","finishStatus":"1","integral":"0.02"},{"taskId":11,"id":56,"title":"广告机宣传2","taskid":11,"userId":3,"quantity":1300,"gold":"0.2","type":"2","finishStatus":"1","integral":"0.02"}]
     * code : 200
     * success : true
     * execute : true
     * msg : 查询用户已进账成功
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
         * id : 37
         * title : 魅力城市-1
         * taskid : 1
         * userId : 3
         * quantity : 624
         * gold : 0.2
         * type : 2
         * finishStatus : 1
         * integral : 0.22
         */

        private int taskId;
        private int id;
        private String title;
        private int taskid;
        private int userId;
        private int quantity;
        private String gold;
        private String type;
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
