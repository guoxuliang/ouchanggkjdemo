package com.example.ouc.demo.entity;

public class GetTaskEntity {

    /**
     * data : {"id":null,"taskId":1,"userId":1,"recevieTime":1539135666362,"finishTime":null,"finishStatus":"0","watchTime":null,"watchNum":0,"shareTime":null,"shareNum":0}
     * execute : true
     * code : 200
     * success : true
     * msg : 任务领取成功
     */

    private DataBean data;
    private boolean execute;
    private int code;
    private boolean success;
    private String msg;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
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

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class DataBean {
        /**
         * id : null
         * taskId : 1
         * userId : 1
         * recevieTime : 1539135666362
         * finishTime : null
         * finishStatus : 0
         * watchTime : null
         * watchNum : 0
         * shareTime : null
         * shareNum : 0
         */

        private Object id;
        private int taskId;
        private int userId;
        private long recevieTime;
        private Object finishTime;
        private String finishStatus;
        private Object watchTime;
        private int watchNum;
        private Object shareTime;
        private int shareNum;

        public Object getId() {
            return id;
        }

        public void setId(Object id) {
            this.id = id;
        }

        public int getTaskId() {
            return taskId;
        }

        public void setTaskId(int taskId) {
            this.taskId = taskId;
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

        public Object getFinishTime() {
            return finishTime;
        }

        public void setFinishTime(Object finishTime) {
            this.finishTime = finishTime;
        }

        public String getFinishStatus() {
            return finishStatus;
        }

        public void setFinishStatus(String finishStatus) {
            this.finishStatus = finishStatus;
        }

        public Object getWatchTime() {
            return watchTime;
        }

        public void setWatchTime(Object watchTime) {
            this.watchTime = watchTime;
        }

        public int getWatchNum() {
            return watchNum;
        }

        public void setWatchNum(int watchNum) {
            this.watchNum = watchNum;
        }

        public Object getShareTime() {
            return shareTime;
        }

        public void setShareTime(Object shareTime) {
            this.shareTime = shareTime;
        }

        public int getShareNum() {
            return shareNum;
        }

        public void setShareNum(int shareNum) {
            this.shareNum = shareNum;
        }
    }
}
