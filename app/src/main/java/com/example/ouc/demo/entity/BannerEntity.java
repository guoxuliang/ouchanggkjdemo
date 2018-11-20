package com.example.ouc.demo.entity;

import java.util.List;

public class BannerEntity {


    /**
     * data : [{"id":7,"bannername":"7","createtime":1539592973000,"is_top":null,"headimg":"http://kgj.ockeji.com/upload/kgj/banner/6.jpg","bannerdescribe":"畅联达轮播图","url":"http://kgj.ockeji.com/html/adv/detaile.jsp"},{"id":5,"bannername":"5","createtime":1539593344000,"is_top":null,"headimg":"http://kgj.ockeji.com/upload/kgj/banner/1.jpg","bannerdescribe":"畅联达轮播图","url":"http://kgj.ockeji.com/html/adv/detaile.jsp"},{"id":4,"bannername":"4","createtime":1539593344000,"is_top":null,"headimg":"http://kgj.ockeji.com/upload/kgj/banner/2.jpg","bannerdescribe":"畅联达轮播图","url":"http://kgj.ockeji.com/html/adv/detaile.jsp"},{"id":3,"bannername":"3","createtime":1539593344000,"is_top":null,"headimg":"http://kgj.ockeji.com/upload/kgj/banner/3.jpg","bannerdescribe":"畅联达轮播图","url":"http://kgj.ockeji.com/html/adv/detaile.jsp"},{"id":2,"bannername":"2","createtime":1539593344000,"is_top":null,"headimg":"http://kgj.ockeji.com/upload/kgj/banner/4.jpg","bannerdescribe":"畅联达轮播图","url":"http://kgj.ockeji.com/html/adv/detaile.jsp"},{"id":1,"bannername":"1","createtime":1539593344000,"is_top":null,"headimg":"http://kgj.ockeji.com/upload/kgj/banner/5.jpg","bannerdescribe":"畅联达轮播图","url":"https://kgj.ockeji.com/html/adv/detaile.jsp"},{"id":6,"bannername":"6","createtime":1539593344000,"is_top":null,"headimg":"http://kgj.ockeji.com/upload/kgj/banner/7.jpg","bannerdescribe":"畅联达轮播图","url":"http://kgj.ockeji.com/html/adv/detaile.jsp"}]
     * success : true
     * msg : 获取首轮播图成功
     * execute : true
     * code : 200
     */

    private boolean success;
    private String msg;
    private boolean execute;
    private int code;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 7
         * bannername : 7
         * createtime : 1539592973000
         * is_top : null
         * headimg : http://kgj.ockeji.com/upload/kgj/banner/6.jpg
         * bannerdescribe : 畅联达轮播图
         * url : http://kgj.ockeji.com/html/adv/detaile.jsp
         */

        private int id;
        private String bannername;
        private long createtime;
        private Object is_top;
        private String headimg;
        private String bannerdescribe;
        private String url;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getBannername() {
            return bannername;
        }

        public void setBannername(String bannername) {
            this.bannername = bannername;
        }

        public long getCreatetime() {
            return createtime;
        }

        public void setCreatetime(long createtime) {
            this.createtime = createtime;
        }

        public Object getIs_top() {
            return is_top;
        }

        public void setIs_top(Object is_top) {
            this.is_top = is_top;
        }

        public String getHeadimg() {
            return headimg;
        }

        public void setHeadimg(String headimg) {
            this.headimg = headimg;
        }

        public String getBannerdescribe() {
            return bannerdescribe;
        }

        public void setBannerdescribe(String bannerdescribe) {
            this.bannerdescribe = bannerdescribe;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
