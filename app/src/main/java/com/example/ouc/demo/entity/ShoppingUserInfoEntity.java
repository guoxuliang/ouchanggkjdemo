package com.example.ouc.demo.entity;

public class ShoppingUserInfoEntity {

    /**
     * data : {"id":44,"detailed_address":"没看呢陈师傅说的耐腐蚀的","isdefault":"0","userid":99,"province":"陕西省","city":"西安市","district":"新城区","delivename":"风格","phone":"15212124545"}
     * code : 200
     * success : true
     * msg : 查询成功。
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
         * id : 44
         * detailed_address : 没看呢陈师傅说的耐腐蚀的
         * isdefault : 0
         * userid : 99
         * province : 陕西省
         * city : 西安市
         * district : 新城区
         * delivename : 风格
         * phone : 15212124545
         */

        private int id;
        private String detailed_address;
        private String isdefault;
        private int userid;
        private String province;
        private String city;
        private String district;
        private String delivename;
        private String phone;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getDetailed_address() {
            return detailed_address;
        }

        public void setDetailed_address(String detailed_address) {
            this.detailed_address = detailed_address;
        }

        public String getIsdefault() {
            return isdefault;
        }

        public void setIsdefault(String isdefault) {
            this.isdefault = isdefault;
        }

        public int getUserid() {
            return userid;
        }

        public void setUserid(int userid) {
            this.userid = userid;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getDistrict() {
            return district;
        }

        public void setDistrict(String district) {
            this.district = district;
        }

        public String getDelivename() {
            return delivename;
        }

        public void setDelivename(String delivename) {
            this.delivename = delivename;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }
    }
}
