package com.example.ouc.demo.entity;

import java.io.Serializable;

public class LoginEntity implements Serializable{


    /**
     * data : {"name":"郭","id":5,"endTime":1569772800000,"level":null,"username":"haha","status":"1","password":"e10adc3949ba59abbe56e057f20f883e","ways":"2","openId":null,"beginTime":1538236800000,"headImg":"http://kgj.ockeji.com/upload/kgj/user/HeadImg/1538290559956460.jpg","cardNumber":"1232546576","cardConImg":"http://kgj.ockeji.com/upload/kgj/user/cardConImg/1538292313996641.jpg","commendNo":"MS691080","personNo":"MS617908","referee":null,"is_login":true,"createTime":1533312000000,"email":"4539811@qq.com","cardFaceImg":"http://kgj.ockeji.com/upload/kgj/user/cardFaceImg/1538292313993864.jpg","mobilePhone":"18191757870"}
     * code : 200
     * success : true
     * msg : 登录系统成功!
     * execute : true
     */

    private DataBean data;
    private int code;
    private boolean success;
    private String msg;
    private boolean execute;

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

    public static class DataBean {
        /**
         * name : 郭
         * id : 5
         * endTime : 1569772800000
         * level : null
         * username : haha
         * status : 1
         * password : e10adc3949ba59abbe56e057f20f883e
         * ways : 2
         * openId : null
         * beginTime : 1538236800000
         * headImg : http://kgj.ockeji.com/upload/kgj/user/HeadImg/1538290559956460.jpg
         * cardNumber : 1232546576
         * cardConImg : http://kgj.ockeji.com/upload/kgj/user/cardConImg/1538292313996641.jpg
         * commendNo : MS691080
         * personNo : MS617908
         * referee : null
         * is_login : true
         * createTime : 1533312000000
         * email : 4539811@qq.com
         * cardFaceImg : http://kgj.ockeji.com/upload/kgj/user/cardFaceImg/1538292313993864.jpg
         * mobilePhone : 18191757870
         */

        private String name;
        private int id;
        private long endTime;
        private Object level;
        private String username;
        private String status;
        private String password;
        private String ways;
        private Object openId;
        private long beginTime;
        private String headImg;
        private String cardNumber;
        private String cardConImg;
        private String commendNo;
        private String personNo;
        private Object referee;
        private String is_login;
        private long createTime;
        private String email;
        private String cardFaceImg;
        private String mobilePhone;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public long getEndTime() {
            return endTime;
        }

        public void setEndTime(long endTime) {
            this.endTime = endTime;
        }

        public Object getLevel() {
            return level;
        }

        public void setLevel(Object level) {
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

        public long getBeginTime() {
            return beginTime;
        }

        public void setBeginTime(long beginTime) {
            this.beginTime = beginTime;
        }

        public String getHeadImg() {
            return headImg;
        }

        public void setHeadImg(String headImg) {
            this.headImg = headImg;
        }

        public String getCardNumber() {
            return cardNumber;
        }

        public void setCardNumber(String cardNumber) {
            this.cardNumber = cardNumber;
        }

        public String getCardConImg() {
            return cardConImg;
        }

        public void setCardConImg(String cardConImg) {
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

        public Object getReferee() {
            return referee;
        }

        public void setReferee(Object referee) {
            this.referee = referee;
        }

        public String isIs_login() {
            return is_login;
        }

        public void setIs_login(String is_login) {
            this.is_login = is_login;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getCardFaceImg() {
            return cardFaceImg;
        }

        public void setCardFaceImg(String cardFaceImg) {
            this.cardFaceImg = cardFaceImg;
        }

        public String getMobilePhone() {
            return mobilePhone;
        }

        public void setMobilePhone(String mobilePhone) {
            this.mobilePhone = mobilePhone;
        }
    }
}
