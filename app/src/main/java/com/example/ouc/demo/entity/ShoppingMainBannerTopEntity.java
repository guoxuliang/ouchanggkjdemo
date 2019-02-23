package com.example.ouc.demo.entity;

import java.util.List;

public class ShoppingMainBannerTopEntity {


    /**
     * data : [{"id":2,"status":"1","img":"http://kgj.ockeji.com/upload/kgj/banner/bannerFile/1544767218957708.jpg","bannerurl":"http://baidu.com","bannername":"精品美甲","bannerdesc":"精品美甲"},{"id":3,"status":"1","img":"http://kgj.ockeji.com/shop/shopbanner/admin/1548399122673571.jpg","bannerurl":"http://baidu.com","bannername":"睫毛增长液","bannerdesc":"睫毛增长液"},{"id":4,"status":"1","img":"http://kgj.ockeji.com//shop/shopbanner/admin/1548490588919261.jpg","bannerurl":"http://baidu.com","bannername":"贝特优美睫毛增长液","bannerdesc":"贝特"}]
     * msg : 获取首轮播图成功
     * execute : true
     * code : 200
     * success : true
     */

    private String msg;
    private boolean execute;
    private int code;
    private boolean success;
    private List<DataBean> data;

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
         * id : 2
         * status : 1
         * img : http://kgj.ockeji.com/upload/kgj/banner/bannerFile/1544767218957708.jpg
         * bannerurl : http://baidu.com
         * bannername : 精品美甲
         * bannerdesc : 精品美甲
         */

        private int id;
        private String status;
        private String img;
        private String bannerurl;
        private String bannername;
        private String bannerdesc;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getBannerurl() {
            return bannerurl;
        }

        public void setBannerurl(String bannerurl) {
            this.bannerurl = bannerurl;
        }

        public String getBannername() {
            return bannername;
        }

        public void setBannername(String bannername) {
            this.bannername = bannername;
        }

        public String getBannerdesc() {
            return bannerdesc;
        }

        public void setBannerdesc(String bannerdesc) {
            this.bannerdesc = bannerdesc;
        }
    }
}
