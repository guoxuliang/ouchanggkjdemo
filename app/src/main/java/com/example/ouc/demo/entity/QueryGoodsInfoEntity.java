package com.example.ouc.demo.entity;

public class QueryGoodsInfoEntity {

    /**
     * data : {"cover":"http://kgj.ockeji.com/upload/kgj/task/imgFile/1541839415020525.jpg","title":"魅力美睫","price":100,"quantity":"10000"}
     * code : 200
     * success : true
     * msg : 查询商品信息成功
     * execute : true
     */

    private DataBean data;
    private int code;
    private boolean success;
    private String msg;
    private boolean execute;

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

    public static class DataBean {
        /**
         * cover : http://kgj.ockeji.com/upload/kgj/task/imgFile/1541839415020525.jpg
         * title : 魅力美睫
         * price : 100
         * quantity : 10000
         */

        private String cover;
        private String title;
        private double price;
        private String quantity;

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

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public String getQuantity() {
            return quantity;
        }

        public void setQuantity(String quantity) {
            this.quantity = quantity;
        }
    }
}
