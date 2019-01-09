package com.example.ouc.demo.entity;

import java.util.List;

public class GetPhoneNumberEntity {


    /**
     * data : [{"phone":"19928751892"},{"phone":"19928751897"},{"phone":"19928752086"},{"phone":"19928751859"},{"phone":"19928751779"}]
     * msg : 查询手机号信息成功
     * execute : true
     * success : true
     * code : 200
     */

    private String msg;
    private boolean execute;
    private boolean success;
    private int code;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * phone : 19928751892
         */

        private String phone;

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }
    }
}
