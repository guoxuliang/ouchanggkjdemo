package com.example.ouc.demo.entity;

import java.util.List;

public class MyOrderEntity {


    /**
     * data : [{"quantity":1300,"video":"http://kgj.ockeji.com/video/1536974390479278.mp4","title":"魅力城市-2","content":"斯蒂芬森","is_top":"1","cover":"http://kgj.ockeji.com/upload/1537423654042411.jpg","gold":100,"createTime":1536805429000,"integral":240,"publisher":"www","startTime":1537804800000,"id":2,"endTime":1537977600000,"is_recomm":"1","status":"1"},{"quantity":1300,"video":"http://kgj.ockeji.com/video/1537423654046470.mp4","title":"魅力城市-3","content":"斯蒂芬森","cover":"http://kgj.ockeji.com/upload/1537423654042411.jpg","gold":100,"createTime":1536974363000,"integral":250,"publisher":"www","startTime":1537804800000,"id":3,"endTime":1537977600000,"is_recomm":"1","status":"1"}]
     * code : 200
     * success : true
     * msg : 用户查询未完成任务成功
     * execute : true
     */

    private int code;
    private boolean success;
    private String msg;
    private boolean execute;
    private List<DataBean> data;

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

    public boolean isExecute() {
        return execute;
    }

    public void setExecute(boolean execute) {
        this.execute = execute;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * quantity : 1300
         * video : http://kgj.ockeji.com/video/1536974390479278.mp4
         * title : 魅力城市-2
         * content : 斯蒂芬森
         * is_top : 1
         * cover : http://kgj.ockeji.com/upload/1537423654042411.jpg
         * gold : 100
         * createTime : 1536805429000
         * integral : 240
         * publisher : www
         * startTime : 1537804800000
         * id : 2
         * endTime : 1537977600000
         * is_recomm : 1
         * status : 1
         */

        private int quantity;
        private String video;
        private String title;
        private String content;
        private String is_top;
        private String cover;
        private double gold;
        private long createTime;
        private int integral;
        private String publisher;
        private long startTime;
        private int id;
        private long endTime;
        private String is_recomm;
        private String status;

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public String getVideo() {
            return video;
        }

        public void setVideo(String video) {
            this.video = video;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getIs_top() {
            return is_top;
        }

        public void setIs_top(String is_top) {
            this.is_top = is_top;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public double getGold() {
            return gold;
        }

        public void setGold(double gold) {
            this.gold = gold;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public int getIntegral() {
            return integral;
        }

        public void setIntegral(int integral) {
            this.integral = integral;
        }

        public String getPublisher() {
            return publisher;
        }

        public void setPublisher(String publisher) {
            this.publisher = publisher;
        }

        public long getStartTime() {
            return startTime;
        }

        public void setStartTime(long startTime) {
            this.startTime = startTime;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public long getEndTime() {
            return endTime;
        }

        public void setEndTime(long endTime) {
            this.endTime = endTime;
        }

        public String getIs_recomm() {
            return is_recomm;
        }

        public void setIs_recomm(String is_recomm) {
            this.is_recomm = is_recomm;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
