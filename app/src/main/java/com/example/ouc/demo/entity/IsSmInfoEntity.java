package com.example.ouc.demo.entity;

public class IsSmInfoEntity {

    /**
     * data : {"name":"买了佛冷","id":5,"type":"1","level":"3","endTime":1541520000000,"username":"GHJ","password":"e10adc3949ba59abbe56e057f20f883e","status":"1","createTime":1540915200000,"ways":"0","openId":null,"personNo":"xt000000","is_real":"1","equipment":"http://kgj.ockeji.com/upload/kgj/user/equipment/1541657662073764.jpg","email":"123@qq.com","headImg":"http://kgj.ockeji.com/upload/kgj/user/HeadImg/1541657973323682.jpg","cardNumber":"61012219681023123X","cardConImg":"http://kgj.ockeji.com/upload/kgj/user/cardConImg/1541657662072333.jpg","commendNo":"MS615049","referee":null,"is_login":"1","equipmentID":"40167e25f2b73842","cardFaceImg":"http://kgj.ockeji.com/upload/kgj/user/cardFaceImg/1541657661889439.jpg","mobilePhone":"18191757870","beginTime":1541606400000}
     * msg : 用户已经实名认证通过，无需再次实名
     * execute : true
     * code : 200
     * success : true
     */

    private DataBean data;
    private String msg;
    private boolean execute;
    private int code;
    private boolean success;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
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

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public static class DataBean {
        /**
         * name : 买了佛冷
         * id : 5
         * type : 1
         * level : 3
         * endTime : 1541520000000
         * username : GHJ
         * password : e10adc3949ba59abbe56e057f20f883e
         * status : 1
         * createTime : 1540915200000
         * ways : 0
         * openId : null
         * personNo : xt000000
         * is_real : 1
         * equipment : http://kgj.ockeji.com/upload/kgj/user/equipment/1541657662073764.jpg
         * email : 123@qq.com
         * headImg : http://kgj.ockeji.com/upload/kgj/user/HeadImg/1541657973323682.jpg
         * cardNumber : 61012219681023123X
         * cardConImg : http://kgj.ockeji.com/upload/kgj/user/cardConImg/1541657662072333.jpg
         * commendNo : MS615049
         * referee : null
         * is_login : 1
         * equipmentID : 40167e25f2b73842
         * cardFaceImg : http://kgj.ockeji.com/upload/kgj/user/cardFaceImg/1541657661889439.jpg
         * mobilePhone : 18191757870
         * beginTime : 1541606400000
         */

        private String name;
        private int id;
        private String type;
        private String level;
        private long endTime;
        private String username;
        private String password;
        private String status;
        private long createTime;
        private String ways;
        private Object openId;
        private String personNo;
        private String is_real;
        private String equipment;
        private String email;
        private String headImg;
        private String cardNumber;
        private String cardConImg;
        private String commendNo;
        private Object referee;
        private String is_login;
        private String equipmentID;
        private String cardFaceImg;
        private String mobilePhone;
        private long beginTime;

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

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public long getEndTime() {
            return endTime;
        }

        public void setEndTime(long endTime) {
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

        public String getPersonNo() {
            return personNo;
        }

        public void setPersonNo(String personNo) {
            this.personNo = personNo;
        }

        public String getIs_real() {
            return is_real;
        }

        public void setIs_real(String is_real) {
            this.is_real = is_real;
        }

        public String getEquipment() {
            return equipment;
        }

        public void setEquipment(String equipment) {
            this.equipment = equipment;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
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

        public Object getReferee() {
            return referee;
        }

        public void setReferee(Object referee) {
            this.referee = referee;
        }

        public String getIs_login() {
            return is_login;
        }

        public void setIs_login(String is_login) {
            this.is_login = is_login;
        }

        public String getEquipmentID() {
            return equipmentID;
        }

        public void setEquipmentID(String equipmentID) {
            this.equipmentID = equipmentID;
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

        public long getBeginTime() {
            return beginTime;
        }

        public void setBeginTime(long beginTime) {
            this.beginTime = beginTime;
        }
    }
}
