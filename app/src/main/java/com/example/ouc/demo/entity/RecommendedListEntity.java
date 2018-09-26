package com.example.ouc.demo.entity;

import java.util.List;

public class RecommendedListEntity {

    /**
     * data : [{"id":1,"cover":"kgj.ockeji.com/upload/1536974390478658.jpg","title":"魅力城市-1","browsevolume":"1563480","quantity":16460,"gold":100,"video":"kgj.ockeji.com/video/1536974390479278.mp4"},{"id":8,"cover":"kgj.ockeji.com/upload/1536974390478658.jpg","title":"魅力城市-4","browsevolume":"0","quantity":1300,"gold":120,"video":"kgj.ockeji.com/video/1536974390479278.mp4"},{"id":17,"cover":"kgj.ockeji.com/upload/1536974390478658.jpg","title":"魅力城市-3","browsevolume":"0","quantity":1300,"gold":120,"video":"kgj.ockeji.com/video/1536974390479278.mp4"},{"id":20,"cover":"kgj.ockeji.com/upload/1536974390478658.jpg","title":"魅力城市-2","browsevolume":"0","quantity":1300,"gold":120,"video":"kgj.ockeji.com/video/1536974390479278.mp4"}]
     * code : null
     * success : true
     * msg : 查询任务成功
     * execute : true
     */

    private Object code;
    private boolean success;
    private String msg;
    private boolean execute;
    private List<DataBean> data;

    public Object getCode() {
        return code;
    }

    public void setCode(Object code) {
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
         * id : 1
         * cover : kgj.ockeji.com/upload/1536974390478658.jpg
         * title : 魅力城市-1
         * browsevolume : 1563480
         * quantity : 16460
         * gold : 100
         * video : kgj.ockeji.com/video/1536974390479278.mp4
         */

        private int id;
        private String cover;
        private String title;
        private String browsevolume;
        private int quantity;
        private int gold;
        private String video;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getBrowsevolume() {
            return browsevolume;
        }

        public void setBrowsevolume(String browsevolume) {
            this.browsevolume = browsevolume;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public int getGold() {
            return gold;
        }

        public void setGold(int gold) {
            this.gold = gold;
        }

        public String getVideo() {
            return video;
        }

        public void setVideo(String video) {
            this.video = video;
        }
    }
}
