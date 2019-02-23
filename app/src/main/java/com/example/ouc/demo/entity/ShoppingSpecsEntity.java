package com.example.ouc.demo.entity;

import java.util.List;

public class ShoppingSpecsEntity {


    /**
     * data : [{"id":5,"num":1,"price":100,"productid":"CLD265301","name":"【华为荣耀9X】全面屏手机屏占比高达98%超智能学生手机【绝地求生m416】电动连发水弹枪儿童玩具aug九五式突击步m416电动连发水弹吃鸡玩具突击步枪儿童98K","img":"http://kgj.ockeji.com//shop/productAttr/CLD265302/1548382700745104.jpg","value":"红","attrid":1},{"id":6,"num":2,"price":119,"productid":"CLD265301","name":"【华为荣耀9X】全面屏手机屏占比高达98%超智能学生手机【绝地求生m416】电动连发水弹枪儿童玩具aug九五式突击步m416电动连发水弹吃鸡玩具突击步枪儿童98K","img":"http://kgj.ockeji.com//shop/productAttr/CLD265303/1548382732053209.jpg","value":"白","attrid":1},{"id":7,"num":3,"price":199,"productid":"CLD265301","name":"【华为荣耀9X】全面屏手机屏占比高达98%超智能学生手机【绝地求生m416】电动连发水弹枪儿童玩具aug九五式突击步m416电动连发水弹吃鸡玩具突击步枪儿童98K","img":"http://kgj.ockeji.com//shop/productAttr/CLD265302/1548382715448856.jpg","value":"黑","attrid":1},{"id":8,"num":4,"price":123,"productid":"CLD265301","name":"【华为荣耀9X】全面屏手机屏占比高达98%超智能学生手机【绝地求生m416】电动连发水弹枪儿童玩具aug九五式突击步m416电动连发水弹吃鸡玩具突击步枪儿童98K","img":"http://kgj.ockeji.com//shop/productAttr/CLD265303/154838274537392.jpg","value":"绿","attrid":1}]
     * success : true
     * code : 200
     * msg : 获取商品属性成功。
     * execute : false
     */

    private boolean success;
    private int code;
    private String msg;
    private boolean execute;
    private List<DataBean> data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 5
         * num : 1
         * price : 100
         * productid : CLD265301
         * name : 【华为荣耀9X】全面屏手机屏占比高达98%超智能学生手机【绝地求生m416】电动连发水弹枪儿童玩具aug九五式突击步m416电动连发水弹吃鸡玩具突击步枪儿童98K
         * img : http://kgj.ockeji.com//shop/productAttr/CLD265302/1548382700745104.jpg
         * value : 红
         * attrid : 1
         */

        private int id;
        private int num;
        private double price;
        private String productid;
        private String name;
        private String img;
        private String value;
        private int attrid;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public String getProductid() {
            return productid;
        }

        public void setProductid(String productid) {
            this.productid = productid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public int getAttrid() {
            return attrid;
        }

        public void setAttrid(int attrid) {
            this.attrid = attrid;
        }
    }
}
