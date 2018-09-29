package com.example.ouc.demo.entity;

public class ShareSuccessfulNoticeEntity {

    /**
     * data : {"id":1,"userId":1,"recevieTime":1536978081000,"finishTime":1536978333000,"finishStatus":"1","watchTime":1537511502000,"watchNum":125,"shareTime":1538123962142,"shareNum":4,"taskId":1}
     * code : 200
     * msg : 更新用户分享时间，次数成功
     * execute : true
     * success : true
     */

    private DataBean data;
    private int code;
    private String msg;
    private boolean execute;
    private boolean success;

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

    public static class DataBean {
        /**
         * id : 1
         * userId : 1
         * recevieTime : 1536978081000
         * finishTime : 1536978333000
         * finishStatus : 1
         * watchTime : 1537511502000
         * watchNum : 125
         * shareTime : 1538123962142
         * shareNum : 4
         * taskId : 1
         */

        private int id;
        private int userId;
        private long recevieTime;
        private long finishTime;
        private String finishStatus;
        private long watchTime;
        private int watchNum;
        private long shareTime;
        private int shareNum;
        private int taskId;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public long getRecevieTime() {
            return recevieTime;
        }

        public void setRecevieTime(long recevieTime) {
            this.recevieTime = recevieTime;
        }

        public long getFinishTime() {
            return finishTime;
        }

        public void setFinishTime(long finishTime) {
            this.finishTime = finishTime;
        }

        public String getFinishStatus() {
            return finishStatus;
        }

        public void setFinishStatus(String finishStatus) {
            this.finishStatus = finishStatus;
        }

        public long getWatchTime() {
            return watchTime;
        }

        public void setWatchTime(long watchTime) {
            this.watchTime = watchTime;
        }

        public int getWatchNum() {
            return watchNum;
        }

        public void setWatchNum(int watchNum) {
            this.watchNum = watchNum;
        }

        public long getShareTime() {
            return shareTime;
        }

        public void setShareTime(long shareTime) {
            this.shareTime = shareTime;
        }

        public int getShareNum() {
            return shareNum;
        }

        public void setShareNum(int shareNum) {
            this.shareNum = shareNum;
        }

        public int getTaskId() {
            return taskId;
        }

        public void setTaskId(int taskId) {
            this.taskId = taskId;
        }
    }
}
