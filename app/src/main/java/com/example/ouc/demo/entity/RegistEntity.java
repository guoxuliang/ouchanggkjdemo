package com.example.ouc.demo.entity;

public class RegistEntity {

    /**
     * data : {"name":null,"id":null,"endTime":null,"level":"1","username":"haha","status":"1","password":"14e1b600b1fd579f47433b88e8d85291","cardFaceImg":null,"mobilePhone":"18191757872","email":null,"createTime":1536977893045,"ways":"0","openId":null,"headImg":null,"cardNumber":null,"cardConImg":null,"commendNo":"MS317492","personNo":"XT000000","beginTime":null,"referee":null}
     * code : 200
     * success : true
     * execute : true
     * msg : 会员增加成功！
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
         * id : null
         * endTime : null
         * level : 1
         * username : haha
         * status : 1
         * password : 14e1b600b1fd579f47433b88e8d85291
         * cardFaceImg : null
         * mobilePhone : 18191757872
         * email : null
         * createTime : 1536977893045
         * ways : 0
         * openId : null
         * headImg : null
         * cardNumber : null
         * cardConImg : null
         * commendNo : MS317492
         * personNo : XT000000
         * beginTime : null
         * referee : null
         */

        private Object name;
        private Object id;
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

        public Object getName() {
            return name;
        }

        public void setName(Object name) {
            this.name = name;
        }

        public Object getId() {
            return id;
        }

        public void setId(Object id) {
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
    }
}
