package com.example.ouc.demo.entity;

/**
 * Created by AYD on 2016/11/22.
 * <p>
 * 购物车
 */
public class ShoppingCartBean {

    private String money;//商品id
    private String shopid;//封面url
    private String type;//商品名称
    private String memberid;
    private String num;
    private String addressid;




    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }


//    public ShoppingCartBean(String money, String shopid, String type, String memberid,
//                            String num, String addressid) {
//        this.money = money;
//        this.shopid = shopid;
//        this.type = type;
//        this.memberid = memberid;
//        this.num = num;
//        this.addressid = addressid;
//
//    }

    public String getShopid() {
        return shopid;
    }

    public void setShopid(String shopid) {
        this.shopid = shopid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMemberid() {
        return memberid;
    }

    public void setMemberid(String memberid) {
        this.memberid = memberid;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getAddressid() {
        return addressid;
    }

    public void setAddressid(String addressid) {
        this.addressid = addressid;
    }
}
