package com.example.ouc.demo.entity;

public class UserInfoEntity {

    /**
     * data : {"name":null,"id":14,"level":null,"createTime":1536830197000,"endTime":null,"username":"纳斯达克了","password":"123456","status":"1","ways":null,"openId":null,"email":null,"headImg":null,"cardNumber":null,"cardFaceImg":null,"cardConImg":null,"mobilePhone":"41515","commendNo":"MS82179","personNo":"XT000000","beginTime":null,"referee":null,"is_login":true}
     * code : 200
     * msg : 更新个人信息成功
     * execute : true
     * success : true
     */

    private DataBean data;
    private int code;
    private String msg;
    private boolean execute;
    private boolean success;

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

    public static class DataBean {
        /**
         * name : null
         * id : 14
         * level : null
         * createTime : 1536830197000
         * endTime : null
         * username : 纳斯达克了
         * password : 123456
         * status : 1
         * ways : null
         * openId : null
         * email : null
         * headImg : null
         * cardNumber : null
         * cardFaceImg : null
         * cardConImg : null
         * mobilePhone : 41515
         * commendNo : MS82179
         * personNo : XT000000
         * beginTime : null
         * referee : null
         * is_login : true
         */

        private Object name;
        private int id;
        private Object level;
        private long createTime;
        private Object endTime;
        private String username;
        private String password;
        private String status;
        private Object ways;
        private Object openId;
        private Object email;
        private Object headImg;
        private Object cardNumber;
        private Object cardFaceImg;
        private Object cardConImg;
        private String mobilePhone;
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

        public Object getLevel() {
            return level;
        }

        public void setLevel(Object level) {
            this.level = level;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public Object getEndTime() {
            return endTime;
        }

        public void setEndTime(Object endTime) {
            this.endTime = endTime;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public Object getWays() {
            return ways;
        }

        public void setWays(Object ways) {
            this.ways = ways;
        }

        public Object getOpenId() {
            return openId;
        }

        public void setOpenId(Object openId) {
            this.openId = openId;
        }

        public Object getEmail() {
            return email;
        }

        public void setEmail(Object email) {
            this.email = email;
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

        public Object getCardFaceImg() {
            return cardFaceImg;
        }

        public void setCardFaceImg(Object cardFaceImg) {
            this.cardFaceImg = cardFaceImg;
        }

        public Object getCardConImg() {
            return cardConImg;
        }

        public void setCardConImg(Object cardConImg) {
            this.cardConImg = cardConImg;
        }

        public String getMobilePhone() {
            return mobilePhone;
        }

        public void setMobilePhone(String mobilePhone) {
            this.mobilePhone = mobilePhone;
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
