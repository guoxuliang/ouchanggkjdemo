package com.example.ouc.demo.entity;

import java.util.List;

public class RecommendedEntity {


    /**
     * data : [{"cover":"http://kgj.ockeji.com/upload/1537423654042411.jpg","gold":100,"timelong":"0:0:32","quantity":1298,"browsevolume":"1563482","id":1,"video":"http://kgj.ockeji.com/video/1537423654046470.mp4","title":"魅力城市-1","content":"斯蒂芬森"},{"cover":"http://kgj.ockeji.com/upload/1537423654042411.jpg","gold":100,"timelong":"0:0:17","quantity":1300,"browsevolume":"0","id":4,"video":"http://kgj.ockeji.com/video/1536974390479278.mp4","title":"魅力城市-4","content":"斯蒂芬森"}]
     * code : 200
     * msg : 查询任务成功
     * execute : true
     * success : true
     */

    private int code;
    private String msg;
    private boolean execute;
    private boolean success;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * cover : http://kgj.ockeji.com/upload/1537423654042411.jpg
         * gold : 100
         * timelong : 0:0:32
         * quantity : 1298
         * browsevolume : 1563482
         * id : 1
         * video : http://kgj.ockeji.com/video/1537423654046470.mp4
         * title : 魅力城市-1
         * content : 斯蒂芬森
         */

        private String cover;
        private int gold;
        private String timelong;
        private int quantity;
        private String browsevolume;
        private int id;
        private String video;
        private String title;
        private String content;

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public int getGold() {
            return gold;
        }

        public void setGold(int gold) {
            this.gold = gold;
        }

        public String getTimelong() {
            return timelong;
        }

        public void setTimelong(String timelong) {
            this.timelong = timelong;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public String getBrowsevolume() {
            return browsevolume;
        }

        public void setBrowsevolume(String browsevolume) {
            this.browsevolume = browsevolume;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
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
    }
}
