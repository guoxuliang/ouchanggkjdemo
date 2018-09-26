package com.example.ouc.demo.entity;

import java.io.Serializable;

public class LoginEntity implements Serializable{


    /**
     * data : {"name":null,"id":14,"endTime":null,"level":"1","username":"QQ","status":"1","password":"e10adc3949ba59abbe56e057f20f883e","cardFaceImg":null,"mobilePhone":"0b3338a9e210fd39b4ca26a274bc7389","email":null,"createTime":1536991318000,"ways":"0","openId":null,"headImg":null,"cardNumber":null,"cardConImg":null,"commendNo":"MS658179","personNo":"XT000000","beginTime":null,"referee":null,"is_login":true}
     * code : 200
     * success : true
     * execute : true
     * msg : 登录系统成功!
     */

    private DataBean data;
    private int code;
    private boolean success;
    private boolean execute;
    private String msg;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

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

    public static class DataBean {
        /**
         * name : null
         * id : 14
         * endTime : null
         * level : 1
         * username : QQ
         * status : 1
         * password : e10adc3949ba59abbe56e057f20f883e
         * cardFaceImg : null
         * mobilePhone : 0b3338a9e210fd39b4ca26a274bc7389
         * email : null
         * createTime : 1536991318000
         * ways : 0
         * openId : null
         * headImg : null
         * cardNumber : null
         * cardConImg : null
         * commendNo : MS658179
         * personNo : XT000000
         * beginTime : null
         * referee : null
         * is_login : true
         */

        private Object name;
        private int id;
        private Object endTime;
        private String level;
        private String username;
        private String status;
        private String password;
        private Object cardFaceImg;
        private String mobilePhone;
        private Object email;
        private long createTime;
        private String ways;
        private Object openId;
        private Object headImg;
        private Object cardNumber;
        private Object cardConImg;
        private String commendNo;
        private String personNo;
        private Object beginTime;
        private Object referee;
        private boolean is_login;

        public Object getName() {
            return name;
        }

        public void setName(Object name) {
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public Object getEndTime() {
            return endTime;
        }

        public void setEndTime(Object endTime) {
            this.endTime = endTime;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public Object getCardFaceImg() {
            return cardFaceImg;
        }

        public void setCardFaceImg(Object cardFaceImg) {
            this.cardFaceImg = cardFaceImg;
        }

        public String getMobilePhone() {
            return mobilePhone;
        }

        public void setMobilePhone(String mobilePhone) {
            this.mobilePhone = mobilePhone;
        }

        public Object getEmail() {
            return email;
        }

        public void setEmail(Object email) {
            this.email = email;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public String getWays() {
            return ways;
        }

        public void setWays(String ways) {
            this.ways = ways;
        }

        public Object getOpenId() {
            return openId;
        }

        public void setOpenId(Object openId) {
            this.openId = openId;
        }

        public Object getHeadImg() {
            return headImg;
        }

        public void setHeadImg(Object headImg) {
            this.headImg = headImg;
        }

        public Object getCardNumber() {
            return cardNumber;
        }

        public void setCardNumber(Object cardNumber) {
            this.cardNumber = cardNumber;
        }

        public Object getCardConImg() {
            return cardConImg;
        }

        public void setCardConImg(Object cardConImg) {
            this.cardConImg = cardConImg;
        }

        public String getCommendNo() {
            return commendNo;
        }

        public void setCommendNo(String commendNo) {
            this.commendNo = commendNo;
        }

        public String getPersonNo() {
            return personNo;
        }

        public void setPersonNo(String personNo) {
            this.personNo = personNo;
        }

        public Object getBeginTime() {
            return beginTime;
        }

        public void setBeginTime(Object beginTime) {
            this.beginTime = beginTime;
        }

        public Object getReferee() {
            return referee;
        }

        public void setReferee(Object referee) {
            this.referee = referee;
        }

        public boolean isIs_login() {
            return is_login;
        }

        public void setIs_login(boolean is_login) {
            this.is_login = is_login;
        }
    }
}
