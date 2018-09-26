package com.example.ouc.demo.entity;

public class CheckUpdataEntity {

    /**
     * data : {"appname":"广告机","severVersion":1,"serverFlag":"1","lastForce":"1","updateUrl":"http://192.168.1.103:8080/36Kr.apk","updateInfo":"V1.1版本更新，享受更好体验！"}
     * success : true
     * msg : 请更新最新版本
     * execute : true
     * code : 200
     */

    private DataBean data;
    private boolean success;
    private String msg;
    private boolean execute;
    private int code;

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

    public static class DataBean {
        /**
         * appname : 广告机
         * severVersion : 1
         * serverFlag : 1
         * lastForce : 1
         * updateUrl : http://192.168.1.103:8080/36Kr.apk
         * updateInfo : V1.1版本更新，享受更好体验！
         */

        private String appname;
        private int severVersion;
        private String serverFlag;
        private String lastForce;
        private String updateUrl;
        private String updateInfo;

        public String getAppname() {
            return appname;
        }

        public void setAppname(String appname) {
            this.appname = appname;
        }

        public int getSeverVersion() {
            return severVersion;
        }

        public void setSeverVersion(int severVersion) {
            this.severVersion = severVersion;
        }

        public String getServerFlag() {
            return serverFlag;
        }

        public void setServerFlag(String serverFlag) {
            this.serverFlag = serverFlag;
        }

        public String getLastForce() {
            return lastForce;
        }

        public void setLastForce(String lastForce) {
            this.lastForce = lastForce;
        }

        public String getUpdateUrl() {
            return updateUrl;
        }

        public void setUpdateUrl(String updateUrl) {
            this.updateUrl = updateUrl;
        }

        public String getUpdateInfo() {
            return updateInfo;
        }

        public void setUpdateInfo(String updateInfo) {
            this.updateInfo = updateInfo;
        }
    }
}
