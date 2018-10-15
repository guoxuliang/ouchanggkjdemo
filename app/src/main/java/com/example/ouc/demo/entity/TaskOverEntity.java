package com.example.ouc.demo.entity;

public class TaskOverEntity {

    /**
     * data : {"id":null,"getTime":1539136686543,"integral":110,"usertaskid":9}
     * execute : true
     * code : 200
     * success : true
     * msg : 任务完成积分添加成功
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
         * getTime : 1539136686543
         * integral : 110.0
         * usertaskid : 9
         */

        private Object id;
        private long getTime;
        private double integral;
        private int usertaskid;

        public Object getId() {
            return id;
        }

        public void setId(Object id) {
            this.id = id;
        }

        public long getGetTime() {
            return getTime;
        }

        public void setGetTime(long getTime) {
            this.getTime = getTime;
        }

        public double getIntegral() {
            return integral;
        }

        public void setIntegral(double integral) {
            this.integral = integral;
        }

        public int getUsertaskid() {
            return usertaskid;
        }

        public void setUsertaskid(int usertaskid) {
            this.usertaskid = usertaskid;
        }
    }
}
