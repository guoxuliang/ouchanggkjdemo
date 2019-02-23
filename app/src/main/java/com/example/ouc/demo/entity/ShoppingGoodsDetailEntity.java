package com.example.ouc.demo.entity;

public class ShoppingGoodsDetailEntity {

    /**
     * data : {"id":"CLD265301","bannertwo":"http://kgj.ockeji.com/shop/comment/CLD265301/1548228127075700.jpg","price":100,"bannerone":"http://kgj.ockeji.com/shop/comment/CLD265301/1548228126245280.jpg","buy_count":0,"isCollection":false,"goodsinfo":"<p style=\"text-align: center;width:100%;height:100%\"><img src=\"http://kgj.ockeji.com/upload/kgj/task/contentFile/1541839342234436.jpg\" style=\"max-width:100%;\"><br><\/p>","name":"vkpopoijpl[l[pl[pkopkop","number":9000,"bannerthree":"http://kgj.ockeji.com/shop/comment/CLD265301/1548228127613638.jpg","DICT_DETAIL_NAME":"美甲"}
     * code : 200
     * execute : false
     * success : true
     * msg : 查询商品成功
     */

    private DataBean data;
    private int code;
    private boolean execute;
    private boolean success;
    private String msg;

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

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class DataBean {
        /**
         * id : CLD265301
         * bannertwo : http://kgj.ockeji.com/shop/comment/CLD265301/1548228127075700.jpg
         * price : 100
         * bannerone : http://kgj.ockeji.com/shop/comment/CLD265301/1548228126245280.jpg
         * buy_count : 0
         * isCollection : false
         * goodsinfo : <p style="text-align: center;width:100%;height:100%"><img src="http://kgj.ockeji.com/upload/kgj/task/contentFile/1541839342234436.jpg" style="max-width:100%;"><br></p>
         * name : vkpopoijpl[l[pl[pkopkop
         * number : 9000
         * bannerthree : http://kgj.ockeji.com/shop/comment/CLD265301/1548228127613638.jpg
         * DICT_DETAIL_NAME : 美甲
         */

        private String id;
        private String bannertwo;
        private int price;
        private String bannerone;
        private int buy_count;
        private boolean isCollection;
        private String goodsinfo;
        private String name;
        private int number;
        private String bannerthree;
        private String DICT_DETAIL_NAME;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getBannertwo() {
            return bannertwo;
        }

        public void setBannertwo(String bannertwo) {
            this.bannertwo = bannertwo;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public String getBannerone() {
            return bannerone;
        }

        public void setBannerone(String bannerone) {
            this.bannerone = bannerone;
        }

        public int getBuy_count() {
            return buy_count;
        }

        public void setBuy_count(int buy_count) {
            this.buy_count = buy_count;
        }

        public boolean getIsCollection() {
            return isCollection;
        }

        public void setIsCollection(boolean isCollection) {
            this.isCollection = isCollection;
        }

        public String getGoodsinfo() {
            return goodsinfo;
        }

        public void setGoodsinfo(String goodsinfo) {
            this.goodsinfo = goodsinfo;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public String getBannerthree() {
            return bannerthree;
        }

        public void setBannerthree(String bannerthree) {
            this.bannerthree = bannerthree;
        }

        public String getDICT_DETAIL_NAME() {
            return DICT_DETAIL_NAME;
        }

        public void setDICT_DETAIL_NAME(String DICT_DETAIL_NAME) {
            this.DICT_DETAIL_NAME = DICT_DETAIL_NAME;
        }
    }
}
