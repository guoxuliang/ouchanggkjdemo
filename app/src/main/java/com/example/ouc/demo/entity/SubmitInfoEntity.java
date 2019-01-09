package com.example.ouc.demo.entity;

public class SubmitInfoEntity {

    /**
     * data : {"address":"陕西省西安市新城区五路口民安大厦A座746","name":"账单","id":141,"type":"2","createTime":1544866498321,"mobilephone":"13820080828","orderid":null,"userid":"99","phone":"19928751962","idCard":null}
     * msg : 手机卡绑定用户成功
     * execute : true
     * success : true
     * code : 200
     */

    private DataBean data;
    private String msg;
    private boolean execute;
    private boolean success;
    private int code;

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

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public static class DataBean {
        /**
         * address : 陕西省西安市新城区五路口民安大厦A座746
         * name : 账单
         * id : 141
         * type : 2
         * createTime : 1544866498321
         * mobilephone : 13820080828
         * orderid : null
         * userid : 99
         * phone : 19928751962
         * idCard : null
         */

        private String address;
        private String name;
        private int id;
        private String type;
        private long createTime;
        private String mobilephone;
        private Object orderid;
        private String userid;
        private String phone;
        private Object idCard;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

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

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public String getMobilephone() {
            return mobilephone;
        }

        public void setMobilephone(String mobilephone) {
            this.mobilephone = mobilephone;
        }

        public Object getOrderid() {
            return orderid;
        }

        public void setOrderid(Object orderid) {
            this.orderid = orderid;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public Object getIdCard() {
            return idCard;
        }

        public void setIdCard(Object idCard) {
            this.idCard = idCard;
        }
    }
}
