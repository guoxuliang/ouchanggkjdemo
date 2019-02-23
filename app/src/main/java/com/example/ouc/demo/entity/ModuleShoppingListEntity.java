package com.example.ouc.demo.entity;

import java.util.List;

public class ModuleShoppingListEntity {

    /**
     * data : [{"id":"CLD239604","logo":"/ftpResources/shopbrand/admin/1546500794929986.png","brandname":"耐克","price":400,"bannerone":"http://kgj.ockeji.com//shop/comment/CLD239604/1548398097541246.jpg","buy_count":0,"name":"vsdfnsakl","number":100}]
     * success : true
     * code : 200
     * execute : true
     * msg : 获取该类别下所有商品成功
     */

    private boolean success;
    private int code;
    private boolean execute;
    private String msg;
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

    public boolean isExecute() {
        return execute;
    }

    public void setExecute(boolean execute) {
        this.execute = execute;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : CLD239604
         * logo : /ftpResources/shopbrand/admin/1546500794929986.png
         * brandname : 耐克
         * price : 400
         * bannerone : http://kgj.ockeji.com//shop/comment/CLD239604/1548398097541246.jpg
         * buy_count : 0
         * name : vsdfnsakl
         * number : 100
         */

        private String id;
        private String logo;
        private String brandname;
        private int price;
        private String bannerone;
        private int buy_count;
        private String name;
        private int number;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getBrandname() {
            return brandname;
        }

        public void setBrandname(String brandname) {
            this.brandname = brandname;
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
    }
}
