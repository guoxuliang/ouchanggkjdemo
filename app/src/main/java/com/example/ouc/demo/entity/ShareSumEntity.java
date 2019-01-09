package com.example.ouc.demo.entity;

import java.util.List;

public class ShareSumEntity {


    /**
     * data : [{"money":"6000.0","createTime":"2018-11-24 09:30:07","title":"","type":"3"},{"money":"80.0","createTime":"2018-11-23 09:32:57","title":"","type":"3"},{"money":"80.0","createTime":"2018-11-22 16:33:20","title":"","type":"3"},{"money":"20.0","createTime":"2018-11-22 16:33:20","title":"","type":"3"}]
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
         * money : 6000.0
         * createTime : 2018-11-24 09:30:07
         * title :
         * type : 3
         */

        private String money;
        private String createTime;
        private String title;
        private String type;

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
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

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
