package com.example.ouc.demo.entity;

import java.util.List;

public class AddressListEntity {

    /**
     * data : [{"id":37,"isdefault":null,"userid":99,"province":"é\u0099\u0095è¥¿ç\u009c\u0081","city":"è¥¿å®\u0089å¸\u0082","district":"æ\u0096°å\u009f\u008eå\u008cº","delivename":null,"phone":null,"detailed_address":null}]
     * success : true
     * code : 200
     * execute : false
     * msg : 获取成功。
     */

    private boolean success;
    private int code;
    private boolean execute;
    private String msg;
    private List<DataBean> data;

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
         * id : 37
         * isdefault : null
         * userid : 99
         * province : éè¥¿ç
         * city : è¥¿å®å¸
         * district : æ°ååº
         * delivename : null
         * phone : null
         * detailed_address : null
         */

        private int id;
        private Object isdefault;
        private int userid;
        private String province;
        private String city;
        private String district;
        private Object delivename;
        private Object phone;
        private Object detailed_address;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public Object getIsdefault() {
            return isdefault;
        }

        public void setIsdefault(Object isdefault) {
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

        public Object getDelivename() {
            return delivename;
        }

        public void setDelivename(Object delivename) {
            this.delivename = delivename;
        }

        public Object getPhone() {
            return phone;
        }

        public void setPhone(Object phone) {
            this.phone = phone;
        }

        public Object getDetailed_address() {
            return detailed_address;
        }

        public void setDetailed_address(Object detailed_address) {
            this.detailed_address = detailed_address;
        }
    }
}
