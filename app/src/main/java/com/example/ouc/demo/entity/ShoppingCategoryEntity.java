package com.example.ouc.demo.entity;

import java.util.List;

public class ShoppingCategoryEntity {

    /**
     * data : [{"DICT_DETAIL_ORDER":1,"DICT_DETALL_CODE":"1012_1001","DICT_DETAIL_ID":"1012_1001","DICT_ID":"1012","DICT_DETAIL_NAME":"美甲"},{"DICT_DETAIL_ORDER":2,"DICT_DETALL_CODE":"1012_1002","DICT_DETAIL_ID":"1012_1002","DICT_ID":"1012","DICT_DETAIL_NAME":"美妆"},{"DICT_DETAIL_ORDER":3,"DICT_DETALL_CODE":"1012_1003","DICT_DETAIL_ID":"1012_1003","DICT_ID":"1012","DICT_DETAIL_NAME":"纹绣"},{"DICT_DETAIL_ORDER":4,"DICT_DETALL_CODE":"1012_1004","DICT_DETAIL_ID":"1012_1004","DICT_ID":"1012","DICT_DETAIL_NAME":"美睫"},{"DICT_DETAIL_ORDER":5,"DICT_DETALL_CODE":"1012_1005","DICT_DETAIL_ID":"1012_1005","DICT_ID":"1012","DICT_DETAIL_NAME":"套装批发"},{"DICT_DETAIL_ORDER":6,"DICT_DETALL_CODE":"1012_1006","DICT_DETAIL_ID":"1012_1006","DICT_ID":"1012","DICT_DETAIL_NAME":"笔刷"},{"DICT_DETAIL_ORDER":7,"DICT_DETALL_CODE":"1012_1007","DICT_DETAIL_ID":"1012_1007","DICT_ID":"1012","DICT_DETAIL_NAME":"工具耗材"},{"DICT_DETAIL_ORDER":8,"DICT_DETALL_CODE":"1012_1008","DICT_DETAIL_ID":"1012_1008","DICT_ID":"1012","DICT_DETAIL_NAME":"美甲灯"},{"DICT_DETAIL_ORDER":9,"DICT_DETALL_CODE":"1012_1009","DICT_DETAIL_ID":"1012_1009","DICT_ID":"1012","DICT_DETAIL_NAME":"手足护理"}]
     * msg : 获取商品类别成功
     * execute : true
     * code : 200
     * success : true
     */

    private String msg;
    private boolean execute;
    private int code;
    private boolean success;
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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * DICT_DETAIL_ORDER : 1
         * DICT_DETALL_CODE : 1012_1001
         * DICT_DETAIL_ID : 1012_1001
         * DICT_ID : 1012
         * DICT_DETAIL_NAME : 美甲
         */

        private int DICT_DETAIL_ORDER;
        private String DICT_DETALL_CODE;
        private String DICT_DETAIL_ID;
        private String DICT_ID;
        private String DICT_DETAIL_NAME;

        public String getDetail_img() {
            return detail_img;
        }

        public void setDetail_img(String detail_img) {
            this.detail_img = detail_img;
        }

        private String detail_img;

        public int getDICT_DETAIL_ORDER() {
            return DICT_DETAIL_ORDER;
        }

        public void setDICT_DETAIL_ORDER(int DICT_DETAIL_ORDER) {
            this.DICT_DETAIL_ORDER = DICT_DETAIL_ORDER;
        }

        public String getDICT_DETALL_CODE() {
            return DICT_DETALL_CODE;
        }

        public void setDICT_DETALL_CODE(String DICT_DETALL_CODE) {
            this.DICT_DETALL_CODE = DICT_DETALL_CODE;
        }

        public String getDICT_DETAIL_ID() {
            return DICT_DETAIL_ID;
        }

        public void setDICT_DETAIL_ID(String DICT_DETAIL_ID) {
            this.DICT_DETAIL_ID = DICT_DETAIL_ID;
        }

        public String getDICT_ID() {
            return DICT_ID;
        }

        public void setDICT_ID(String DICT_ID) {
            this.DICT_ID = DICT_ID;
        }

        public String getDICT_DETAIL_NAME() {
            return DICT_DETAIL_NAME;
        }

        public void setDICT_DETAIL_NAME(String DICT_DETAIL_NAME) {
            this.DICT_DETAIL_NAME = DICT_DETAIL_NAME;
        }
    }
}
