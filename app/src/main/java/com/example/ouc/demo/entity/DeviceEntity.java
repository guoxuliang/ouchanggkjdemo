package com.example.ouc.demo.entity;

public class DeviceEntity {

    /**
     * data : {"equipmentID":"69c7879037eb41ca","is_login":"0"}
     * success : true
     * code : 200
     * msg : 获取设备ID，登录状态成功
     * execute : true
     */

    private DataBean data;
    private boolean success;
    private int code;
    private String msg;
    private boolean execute;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
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
         * equipmentID : 69c7879037eb41ca
         * is_login : 0
         */

        private String equipmentID;
        private String is_login;

        public String getEquipmentID() {
            return equipmentID;
        }

        public void setEquipmentID(String equipmentID) {
            this.equipmentID = equipmentID;
        }

        public String getIs_login() {
            return is_login;
        }

        public void setIs_login(String is_login) {
            this.is_login = is_login;
        }
    }
}
