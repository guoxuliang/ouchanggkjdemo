package com.example.ouc.demo.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

public class ShoppingListEntity implements Parcelable {


    /**
     * data : [{"name":"是大法官","id":"CLD265301","type":"101","number":9000,"level":"1012_1001","userid":"4b020b21-1252-11e9-a409-40167e25f343","bannerthree":"http://kgj.ockeji.com/shop/comment/CLD265301/1548228127613638.jpg","promoteprice":null,"promotestartdate":null,"promoteenddate":null,"isCollection":0,"isnew":0,"brand":1,"price":100,"bannerone":"http://kgj.ockeji.com/shop/comment/CLD265301/1548228126245280.jpg","bannertwo":"http://kgj.ockeji.com/shop/comment/CLD265301/1548228127075700.jpg","isdelete":0,"ishot":1,"isbest":0,"ispass":1,"buyCount":null,"createdate":1548207040000,"keybord":null,"ispromote":0,"indx":3,"goodsinfo":"<img src=\"/images/sjwt/syscontent/20190123093034_384.jpg\" alt=\"\" />"}]
     * msg : 获取成功。
     * execute : false
     * code : null
     * success : true
     */

    private String msg;
    private boolean execute;
    private int code;
    private boolean success;
    private List<DataBean> data;

    protected ShoppingListEntity(Parcel in) {
        msg = in.readString();
        execute = in.readByte() != 0;
        code = in.readInt();
        success = in.readByte() != 0;
    }

    public static final Creator<ShoppingListEntity> CREATOR = new Creator<ShoppingListEntity>() {
        @Override
        public ShoppingListEntity createFromParcel(Parcel in) {
            return new ShoppingListEntity(in);
        }

        @Override
        public ShoppingListEntity[] newArray(int size) {
            return new ShoppingListEntity[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(msg);
        parcel.writeByte((byte) (execute ? 1 : 0));
        parcel.writeInt(code);
        parcel.writeByte((byte) (success ? 1 : 0));
    }

    public static class DataBean {
        /**
         * name : 是大法官
         * id : CLD265301
         * type : 101
         * number : 9000
         * level : 1012_1001
         * userid : 4b020b21-1252-11e9-a409-40167e25f343
         * bannerthree : http://kgj.ockeji.com/shop/comment/CLD265301/1548228127613638.jpg
         * promoteprice : null
         * promotestartdate : null
         * promoteenddate : null
         * isCollection : 0
         * isnew : 0
         * brand : 1
         * price : 100
         * bannerone : http://kgj.ockeji.com/shop/comment/CLD265301/1548228126245280.jpg
         * bannertwo : http://kgj.ockeji.com/shop/comment/CLD265301/1548228127075700.jpg
         * isdelete : 0
         * ishot : 1
         * isbest : 0
         * ispass : 1
         * buyCount : null
         * createdate : 1548207040000
         * keybord : null
         * ispromote : 0
         * indx : 3
         * goodsinfo : <img src="/images/sjwt/syscontent/20190123093034_384.jpg" alt="" />
         */

        private String name;
        private String id;
        private String type;
        private int number;
        private String level;
        private String userid;
        private String bannerthree;
        private Object promoteprice;
        private Object promotestartdate;
        private Object promoteenddate;
        private int isCollection;
        private int isnew;
        private int brand;
        private double price;
        private String bannerone;
        private String bannertwo;
        private int isdelete;
        private int ishot;
        private int isbest;
        private int ispass;
        private Object buyCount;
        private long createdate;
        private Object keybord;
        private int ispromote;
        private int indx;
        private String goodsinfo;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public String getBannerthree() {
            return bannerthree;
        }

        public void setBannerthree(String bannerthree) {
            this.bannerthree = bannerthree;
        }

        public Object getPromoteprice() {
            return promoteprice;
        }

        public void setPromoteprice(Object promoteprice) {
            this.promoteprice = promoteprice;
        }

        public Object getPromotestartdate() {
            return promotestartdate;
        }

        public void setPromotestartdate(Object promotestartdate) {
            this.promotestartdate = promotestartdate;
        }

        public Object getPromoteenddate() {
            return promoteenddate;
        }

        public void setPromoteenddate(Object promoteenddate) {
            this.promoteenddate = promoteenddate;
        }

        public int getIsCollection() {
            return isCollection;
        }

        public void setIsCollection(int isCollection) {
            this.isCollection = isCollection;
        }

        public int getIsnew() {
            return isnew;
        }

        public void setIsnew(int isnew) {
            this.isnew = isnew;
        }

        public int getBrand() {
            return brand;
        }

        public void setBrand(int brand) {
            this.brand = brand;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public String getBannerone() {
            return bannerone;
        }

        public void setBannerone(String bannerone) {
            this.bannerone = bannerone;
        }

        public String getBannertwo() {
            return bannertwo;
        }

        public void setBannertwo(String bannertwo) {
            this.bannertwo = bannertwo;
        }

        public int getIsdelete() {
            return isdelete;
        }

        public void setIsdelete(int isdelete) {
            this.isdelete = isdelete;
        }

        public int getIshot() {
            return ishot;
        }

        public void setIshot(int ishot) {
            this.ishot = ishot;
        }

        public int getIsbest() {
            return isbest;
        }

        public void setIsbest(int isbest) {
            this.isbest = isbest;
        }

        public int getIspass() {
            return ispass;
        }

        public void setIspass(int ispass) {
            this.ispass = ispass;
        }

        public Object getBuyCount() {
            return buyCount;
        }

        public void setBuyCount(Object buyCount) {
            this.buyCount = buyCount;
        }

        public long getCreatedate() {
            return createdate;
        }

        public void setCreatedate(long createdate) {
            this.createdate = createdate;
        }

        public Object getKeybord() {
            return keybord;
        }

        public void setKeybord(Object keybord) {
            this.keybord = keybord;
        }

        public int getIspromote() {
            return ispromote;
        }

        public void setIspromote(int ispromote) {
            this.ispromote = ispromote;
        }

        public int getIndx() {
            return indx;
        }

        public void setIndx(int indx) {
            this.indx = indx;
        }

        public String getGoodsinfo() {
            return goodsinfo;
        }

        public void setGoodsinfo(String goodsinfo) {
            this.goodsinfo = goodsinfo;
        }
    }
}
