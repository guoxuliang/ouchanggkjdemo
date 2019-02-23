package com.example.ouc.demo.entity;

public class ShoppingPayEntity {


    /**
     * pageData : {"resultCode":"SUCCESS","errMsg":null,"timeStamp":"1550129581","packageValue":null,"partnerid":null,"appId":null,"nonceStr":"llEjWk5cQb6jUJVK","packageStr":null,"signType":"MD5","paySign":"1ECB5C493B72B61AA2859E17F27B741B","prepayid":"wx1415330790963036ae66bcd81808567236"}
     * code : 0
     * success : true
     * msg : 支付成功
     */

    private PageDataBean pageData;
    private int code;
    private boolean success;
    private String msg;

    public PageDataBean getPageData() {
        return pageData;
    }

    public void setPageData(PageDataBean pageData) {
        this.pageData = pageData;
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

    public static class PageDataBean {
        /**
         * resultCode : SUCCESS
         * errMsg : null
         * timeStamp : 1550129581
         * packageValue : null
         * partnerid : null
         * appId : null
         * nonceStr : llEjWk5cQb6jUJVK
         * packageStr : null
         * signType : MD5
         * paySign : 1ECB5C493B72B61AA2859E17F27B741B
         * prepayid : wx1415330790963036ae66bcd81808567236
         */

        private String resultCode;
        private Object errMsg;
        private String timeStamp;
        private Object packageValue;
        private Object partnerid;
        private Object appId;
        private String nonceStr;
        private Object packageStr;
        private String signType;
        private String paySign;
        private String prepayid;

        public String getResultCode() {
            return resultCode;
        }

        public void setResultCode(String resultCode) {
            this.resultCode = resultCode;
        }

        public Object getErrMsg() {
            return errMsg;
        }

        public void setErrMsg(Object errMsg) {
            this.errMsg = errMsg;
        }

        public String getTimeStamp() {
            return timeStamp;
        }

        public void setTimeStamp(String timeStamp) {
            this.timeStamp = timeStamp;
        }

        public Object getPackageValue() {
            return packageValue;
        }

        public void setPackageValue(Object packageValue) {
            this.packageValue = packageValue;
        }

        public Object getPartnerid() {
            return partnerid;
        }

        public void setPartnerid(Object partnerid) {
            this.partnerid = partnerid;
        }

        public Object getAppId() {
            return appId;
        }

        public void setAppId(Object appId) {
            this.appId = appId;
        }

        public String getNonceStr() {
            return nonceStr;
        }

        public void setNonceStr(String nonceStr) {
            this.nonceStr = nonceStr;
        }

        public Object getPackageStr() {
            return packageStr;
        }

        public void setPackageStr(Object packageStr) {
            this.packageStr = packageStr;
        }

        public String getSignType() {
            return signType;
        }

        public void setSignType(String signType) {
            this.signType = signType;
        }

        public String getPaySign() {
            return paySign;
        }

        public void setPaySign(String paySign) {
            this.paySign = paySign;
        }

        public String getPrepayid() {
            return prepayid;
        }

        public void setPrepayid(String prepayid) {
            this.prepayid = prepayid;
        }
    }
}
