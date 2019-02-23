package com.example.ouc.demo.entity;

import java.util.List;

public class OrderListEntity {


    /**
     * data : [{"phone":"13333333333","memberid":3,"createdate":1547516555000,"orderno":"481155bfed894d00be80e668a6686c12","shopname":"Yeelight小米生态链皓石智能LED吸顶灯Pro星空灰版客厅卧室长方形吸顶灯支持语音控制智能联动【三室一厅】","type":"2","info":"蓝*1","id":43,"username":"王雅","shopid":"CLD709405","bannerone":"http://kgj.ockeji.com//shop/comment/CLD705239/1548468927280111.jpg","deliverystatus":"0","address":"内蒙古呼和浩特市新城区gghfhon","paystatus":"1","name":"admin","money":12,"tradestatus":"0","delivename":"syk","updatedate":1550199819000},{"phone":"13333333333","memberid":3,"createdate":1547516467000,"orderno":"d215180871a346f3bbc1b137193eac18","shopname":"Yeelight小米生态链皓石智能LED吸顶灯Pro星空灰版客厅卧室长方形吸顶灯支持语音控制智能联动【三室一厅】","type":"2","info":"黑*1","id":42,"username":"王雅","shopid":"CLD709405","bannerone":"http://kgj.ockeji.com//shop/comment/CLD705239/1548468927280111.jpg","deliverystatus":"0","address":"内蒙古呼和浩特市新城区gghfhon","paystatus":"1","name":"admin","money":12,"tradestatus":"0","delivename":"syk","updatedate":1550199819000},{"memberid":3,"createdate":1545358813000,"orderno":"0eeaafcb496a4d2e897de6f363bcecf6","shopname":"Yeelight小米生态链皓石智能LED吸顶灯Pro星空灰版客厅卧室长方形吸顶灯支持语音控制智能联动【三室一厅】","type":"1","info":"红*1","id":39,"num":1,"shopid":"CLD709405","bannerone":"http://kgj.ockeji.com//shop/comment/CLD705239/1548468927280111.jpg","deliverystatus":"1","paystatus":"1","name":"美甲","money":10,"tradestatus":"1","updatedate":1550199819000,"courseid":55},{"memberid":3,"createdate":1545187737000,"orderno":"201812191048498091295974185","shopname":"Yeelight小米生态链皓石智能LED吸顶灯Pro星空灰版客厅卧室长方形吸顶灯支持语音控制智能联动【三室一厅】","type":"1","info":"白*1","id":30,"num":1,"shopid":"CLD709405","bannerone":"http://kgj.ockeji.com//shop/comment/CLD705239/1548468927280111.jpg","deliverystatus":"1","paystatus":"1","name":"测试支付","money":0,"tradestatus":"1","updatedate":1550199819000,"courseid":56}]
     * code : 200
     * success : true
     * msg : 获取用户订单成功
     * execute : false
     */

    private int code;
    private boolean success;
    private String msg;
    private boolean execute;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * phone : 13333333333
         * memberid : 3
         * createdate : 1547516555000
         * orderno : 481155bfed894d00be80e668a6686c12
         * shopname : Yeelight小米生态链皓石智能LED吸顶灯Pro星空灰版客厅卧室长方形吸顶灯支持语音控制智能联动【三室一厅】
         * type : 2
         * info : 蓝*1
         * id : 43
         * username : 王雅
         * shopid : CLD709405
         * bannerone : http://kgj.ockeji.com//shop/comment/CLD705239/1548468927280111.jpg
         * deliverystatus : 0
         * address : 内蒙古呼和浩特市新城区gghfhon
         * paystatus : 1
         * name : admin
         * money : 12
         * tradestatus : 0
         * delivename : syk
         * updatedate : 1550199819000
         * num : 1
         * courseid : 55
         */

        private String phone;
        private int memberid;
        private long createdate;
        private String orderno;
        private String shopname;
        private String type;
        private String info;
        private int id;
        private String username;
        private String shopid;
        private String bannerone;
        private String deliverystatus;
        private String address;
        private String paystatus;
        private String name;
        private int money;
        private String tradestatus;
        private String delivename;
        private long updatedate;
        private int num;
        private int courseid;

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public int getMemberid() {
            return memberid;
        }

        public void setMemberid(int memberid) {
            this.memberid = memberid;
        }

        public long getCreatedate() {
            return createdate;
        }

        public void setCreatedate(long createdate) {
            this.createdate = createdate;
        }

        public String getOrderno() {
            return orderno;
        }

        public void setOrderno(String orderno) {
            this.orderno = orderno;
        }

        public String getShopname() {
            return shopname;
        }

        public void setShopname(String shopname) {
            this.shopname = shopname;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getShopid() {
            return shopid;
        }

        public void setShopid(String shopid) {
            this.shopid = shopid;
        }

        public String getBannerone() {
            return bannerone;
        }

        public void setBannerone(String bannerone) {
            this.bannerone = bannerone;
        }

        public String getDeliverystatus() {
            return deliverystatus;
        }

        public void setDeliverystatus(String deliverystatus) {
            this.deliverystatus = deliverystatus;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getPaystatus() {
            return paystatus;
        }

        public void setPaystatus(String paystatus) {
            this.paystatus = paystatus;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getMoney() {
            return money;
        }

        public void setMoney(int money) {
            this.money = money;
        }

        public String getTradestatus() {
            return tradestatus;
        }

        public void setTradestatus(String tradestatus) {
            this.tradestatus = tradestatus;
        }

        public String getDelivename() {
            return delivename;
        }

        public void setDelivename(String delivename) {
            this.delivename = delivename;
        }

        public long getUpdatedate() {
            return updatedate;
        }

        public void setUpdatedate(long updatedate) {
            this.updatedate = updatedate;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public int getCourseid() {
            return courseid;
        }

        public void setCourseid(int courseid) {
            this.courseid = courseid;
        }
    }
}
