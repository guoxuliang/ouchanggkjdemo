package com.example.ouc.demo.entity;

import com.google.gson.annotations.SerializedName;

public class UpgradeEntity {

    /**
     * msg : 支付成功
     * code : 0
     * data : null
     * pageData : {"appid":"wxa8d1d04e3aba2b5e","noncestr":"dS6Sh51l0lPfwou7","package":"Sign=WXPay","partnerid":"1519511821","prepayid":"wx131349258340028282f0a4c03167734590","sign":"7DF5A7002C77A10C67C634468EC596A6","timestamp":"1544680165"}
     */

    private String msg;
    private int code;
    private Object data;
    private PageDataBean pageData;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public PageDataBean getPageData() {
        return pageData;
    }

    public void setPageData(PageDataBean pageData) {
        this.pageData = pageData;
    }

    public static class PageDataBean {
        /**
         * appid : wxa8d1d04e3aba2b5e
         * noncestr : dS6Sh51l0lPfwou7
         * package : Sign=WXPay
         * partnerid : 1519511821
         * prepayid : wx131349258340028282f0a4c03167734590
         * sign : 7DF5A7002C77A10C67C634468EC596A6
         * timestamp : 1544680165
         */

        private String appid;
        private String noncestr;
        @SerializedName("package")
        private String packageX;
        private String partnerid;
        private String prepayid;
        private String sign;
        private String timestamp;

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public String getNoncestr() {
            return noncestr;
        }

        public void setNoncestr(String noncestr) {
            this.noncestr = noncestr;
        }

        public String getPackageX() {
            return packageX;
        }

        public void setPackageX(String packageX) {
            this.packageX = packageX;
        }

        public String getPartnerid() {
            return partnerid;
        }

        public void setPartnerid(String partnerid) {
            this.partnerid = partnerid;
        }

        public String getPrepayid() {
            return prepayid;
        }

        public void setPrepayid(String prepayid) {
            this.prepayid = prepayid;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }
    }
}
