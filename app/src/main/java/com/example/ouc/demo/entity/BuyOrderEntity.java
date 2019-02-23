package com.example.ouc.demo.entity;

import java.util.List;

public class BuyOrderEntity {


    /**
     * code : 0
     * appOrderList : [{"address":"陕西省西安市未央区大明宫万达广场","name":"罗马仕SW20移动电源20000毫安手机充电宝Type-C双向快充智能数显苹果/安卓通用 白色","id":null,"type":"2","info":null,"username":"张三丰","tradestatus":"0","deliverystatus":"0","electronicmoney":null,"maxelectronicmoney":null,"delivename":"wang","phone":"18191224567","createdate":1550480317618,"shopid":"CLD705239","num":1,"courseid":null,"memberid":99,"updatedate":1550480317618,"outtradeno":null,"orderno":"32d28d47f15e4e7b8ee086a72a057067","money":239,"paystatus":"2","integral":null,"remark":null,"imgGoods":"http://kgj.ockeji.com//shop/comment/CLD705239/1548468927280111.jpg","imgCourse":null},{"address":"山西省太原市小店区e阿哥","name":"Yeelight小米生态链皓石智能LED吸顶灯Pro星空灰版客厅卧室长方形吸顶灯支持语音控制智能联动【三室一厅】","id":null,"type":"2","info":null,"username":"张三丰","tradestatus":"0","deliverystatus":"0","electronicmoney":null,"maxelectronicmoney":null,"delivename":"王雅","phone":"15534008596","createdate":1550480317644,"shopid":"CLD709405","num":1,"courseid":null,"memberid":99,"updatedate":1550480317644,"outtradeno":null,"orderno":"cdb3712a66c54db698a94757ce71f1a1","money":239,"paystatus":"2","integral":null,"remark":null,"imgGoods":"http://kgj.ockeji.com//shop/comment/CLD705239/1548468927280111.jpg","imgCourse":null}]
     * msg : 下单成功
     * success : true
     * sumPrice : 478.0
     */

    private int code;
    private String msg;
    private boolean success;
    private double sumPrice;
    private List<AppOrderListBean> appOrderList;

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

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public double getSumPrice() {
        return sumPrice;
    }

    public void setSumPrice(double sumPrice) {
        this.sumPrice = sumPrice;
    }

    public List<AppOrderListBean> getAppOrderList() {
        return appOrderList;
    }

    public void setAppOrderList(List<AppOrderListBean> appOrderList) {
        this.appOrderList = appOrderList;
    }

    public static class AppOrderListBean {
        /**
         * address : 陕西省西安市未央区大明宫万达广场
         * name : 罗马仕SW20移动电源20000毫安手机充电宝Type-C双向快充智能数显苹果/安卓通用 白色
         * id : null
         * type : 2
         * info : null
         * username : 张三丰
         * tradestatus : 0
         * deliverystatus : 0
         * electronicmoney : null
         * maxelectronicmoney : null
         * delivename : wang
         * phone : 18191224567
         * createdate : 1550480317618
         * shopid : CLD705239
         * num : 1
         * courseid : null
         * memberid : 99
         * updatedate : 1550480317618
         * outtradeno : null
         * orderno : 32d28d47f15e4e7b8ee086a72a057067
         * money : 239.0
         * paystatus : 2
         * integral : null
         * remark : null
         * imgGoods : http://kgj.ockeji.com//shop/comment/CLD705239/1548468927280111.jpg
         * imgCourse : null
         */

        private String address;
        private String name;
        private Object id;
        private String type;
        private Object info;
        private String username;
        private String tradestatus;
        private String deliverystatus;
        private Object electronicmoney;
        private Object maxelectronicmoney;
        private String delivename;
        private String phone;
        private long createdate;
        private String shopid;
        private int num;
        private Object courseid;
        private int memberid;
        private long updatedate;
        private Object outtradeno;
        private String orderno;
        private double money;
        private String paystatus;
        private Object integral;
        private Object remark;
        private String imgGoods;
        private Object imgCourse;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Object getId() {
            return id;
        }

        public void setId(Object id) {
            this.id = id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public Object getInfo() {
            return info;
        }

        public void setInfo(Object info) {
            this.info = info;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getTradestatus() {
            return tradestatus;
        }

        public void setTradestatus(String tradestatus) {
            this.tradestatus = tradestatus;
        }

        public String getDeliverystatus() {
            return deliverystatus;
        }

        public void setDeliverystatus(String deliverystatus) {
            this.deliverystatus = deliverystatus;
        }

        public Object getElectronicmoney() {
            return electronicmoney;
        }

        public void setElectronicmoney(Object electronicmoney) {
            this.electronicmoney = electronicmoney;
        }

        public Object getMaxelectronicmoney() {
            return maxelectronicmoney;
        }

        public void setMaxelectronicmoney(Object maxelectronicmoney) {
            this.maxelectronicmoney = maxelectronicmoney;
        }

        public String getDelivename() {
            return delivename;
        }

        public void setDelivename(String delivename) {
            this.delivename = delivename;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public long getCreatedate() {
            return createdate;
        }

        public void setCreatedate(long createdate) {
            this.createdate = createdate;
        }

        public String getShopid() {
            return shopid;
        }

        public void setShopid(String shopid) {
            this.shopid = shopid;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public Object getCourseid() {
            return courseid;
        }

        public void setCourseid(Object courseid) {
            this.courseid = courseid;
        }

        public int getMemberid() {
            return memberid;
        }

        public void setMemberid(int memberid) {
            this.memberid = memberid;
        }

        public long getUpdatedate() {
            return updatedate;
        }

        public void setUpdatedate(long updatedate) {
            this.updatedate = updatedate;
        }

        public Object getOuttradeno() {
            return outtradeno;
        }

        public void setOuttradeno(Object outtradeno) {
            this.outtradeno = outtradeno;
        }

        public String getOrderno() {
            return orderno;
        }

        public void setOrderno(String orderno) {
            this.orderno = orderno;
        }

        public double getMoney() {
            return money;
        }

        public void setMoney(double money) {
            this.money = money;
        }

        public String getPaystatus() {
            return paystatus;
        }

        public void setPaystatus(String paystatus) {
            this.paystatus = paystatus;
        }

        public Object getIntegral() {
            return integral;
        }

        public void setIntegral(Object integral) {
            this.integral = integral;
        }

        public Object getRemark() {
            return remark;
        }

        public void setRemark(Object remark) {
            this.remark = remark;
        }

        public String getImgGoods() {
            return imgGoods;
        }

        public void setImgGoods(String imgGoods) {
            this.imgGoods = imgGoods;
        }

        public Object getImgCourse() {
            return imgCourse;
        }

        public void setImgCourse(Object imgCourse) {
            this.imgCourse = imgCourse;
        }
    }
}
