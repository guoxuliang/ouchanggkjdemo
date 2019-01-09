package com.example.ouc.demo.entity;

import java.util.List;

public class UserScrrollEntity {


    /**
     * data : [{"createTime":"2018-12-27","username":"ggg","price":100,"name":"王雅","shopname":"魅力美睫"},{"createTime":"2018-12-27","username":"ggg","price":100,"name":"王雅","shopname":"魅力美睫"}]
     * success : true
     * msg : 查询用户购买记录成功
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
         * createTime : 2018-12-27
         * username : ggg
         * price : 100
         * name : 王雅
         * shopname : 魅力美睫
         */

        private String createTime;
        private String username;
        private int price;
        private String name;
        private String shopname;

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getShopname() {
            return shopname;
        }

        public void setShopname(String shopname) {
            this.shopname = shopname;
        }
    }
}
