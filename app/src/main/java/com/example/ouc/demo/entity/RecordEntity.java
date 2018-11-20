package com.example.ouc.demo.entity;

import java.util.List;

public class RecordEntity {
    /**
     * data : [{"dealtime":"2018-11-12 13:51:24","money":10,"banknumber":"18191757870","mobilephone":"18191757870","id":1,"type":"1","userid":5,"cashtime":1541988680000,"username":"威震天","status":"2"},{"dealtime":"2018-11-12 13:51:28","money":10,"banknumber":"18191757870","mobilephone":"18191757870","id":2,"type":"1","userid":5,"cashtime":1541988680000,"username":"威震天","status":"2"},{"dealtime":"2018-11-12 16:03:42","alipayaccopunt":"","banknumber":"6212260509002977304","bankaddress":"西安","type":"1","userid":5,"money":100,"mobilephone":"17629278565","id":3,"bankname":"招商银行","cashtime":1542009801000,"username":"王雅","status":"1"},{"alipayaccopunt":"","money":1333,"banknumber":"6212260509002977304","bankaddress":"提督街","mobilephone":"17629278565","id":4,"bankname":"u贵凤凰城","type":"1","userid":5,"cashtime":1542009889000,"username":"归于","status":"1"},{"alipayaccopunt":"","money":133669,"banknumber":"4246582372525","bankaddress":"丰富吃过几次","mobilephone":"17629278565","id":5,"bankname":"惩罚军服","type":"1","userid":5,"cashtime":1542009914000,"username":"-哥防风衣","status":"1"},{"money":10000000000,"banknumber":"5897565656726456455","bankaddress":"唱歌","mobilephone":"18191757870","id":6,"bankname":"效果更好","type":"1","userid":5,"cashtime":1542012289000,"username":"唱歌接触过","status":"1"},{"alipayaccopunt":"18191757870","money":10,"mobilephone":"18191757870","id":7,"type":"1","userid":5,"cashtime":1542013351000,"username":"威震天","status":"1"},{"alipayaccopunt":"55558556","money":12,"mobilephone":"18191757870","id":8,"type":"2","userid":5,"cashtime":1542013705000,"username":"打个电话","status":"1"},{"alipayaccopunt":"124536","money":99,"mobilephone":"18191757870","id":9,"type":"2","userid":5,"cashtime":1542013764000,"username":"Hdb","status":"1"},{"money":1000,"banknumber":"5544546449464846484","bankaddress":"Chg","mobilephone":"18191757870","id":10,"bankname":"擦干净","type":"1","userid":5,"cashtime":1542013797000,"username":"Gh","status":"1"},{"money":125,"banknumber":"21457555","bankaddress":"-粗腿杨馥宇","mobilephone":"17629278565","id":11,"bankname":"就GV就","type":"1","userid":5,"cashtime":1542014006000,"username":"需要糖醋鱼","status":"1"},{"money":222,"banknumber":"1234567890007845123","bankaddress":"更新换代","mobilephone":"18191757870","id":12,"bankname":"不到好多","type":"1","userid":5,"cashtime":1542014500000,"username":"搞得活动","status":"1"},{"alipayaccopunt":"18191757870","money":33,"mobilephone":"18191757870","id":13,"type":"2","userid":5,"cashtime":1542014614000,"username":"回电话","status":"1"}]
     * success : true
     * code : 200
     * msg : 查看用户提现记录成功
     * execute : true
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
         * dealtime : 2018-11-12 13:51:24
         * money : 10
         * banknumber : 18191757870
         * mobilephone : 18191757870
         * id : 1
         * type : 1
         * userid : 5
         * cashtime : 1541988680000
         * username : 威震天
         * status : 2
         * alipayaccopunt :
         * bankaddress : 西安
         * bankname : 招商银行
         */

        private String dealtime;
        private double money;
        private String banknumber;
        private String mobilephone;
        private int id;
        private String type;
        private int userid;
        private String cashtime;
        private String username;
        private String status;
        private String alipayaccopunt;
        private String bankaddress;
        private String bankname;

        public String getDealtime() {
            return dealtime;
        }

        public void setDealtime(String dealtime) {
            this.dealtime = dealtime;
        }

        public double getMoney() {
            return money;
        }

        public void setMoney(double money) {
            this.money = money;
        }

        public String getBanknumber() {
            return banknumber;
        }

        public void setBanknumber(String banknumber) {
            this.banknumber = banknumber;
        }

        public String getMobilephone() {
            return mobilephone;
        }

        public void setMobilephone(String mobilephone) {
            this.mobilephone = mobilephone;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public int getUserid() {
            return userid;
        }

        public void setUserid(int userid) {
            this.userid = userid;
        }

        public String getCashtime() {
            return cashtime;
        }

        public void setCashtime(String cashtime) {
            this.cashtime = cashtime;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getAlipayaccopunt() {
            return alipayaccopunt;
        }

        public void setAlipayaccopunt(String alipayaccopunt) {
            this.alipayaccopunt = alipayaccopunt;
        }

        public String getBankaddress() {
            return bankaddress;
        }

        public void setBankaddress(String bankaddress) {
            this.bankaddress = bankaddress;
        }

        public String getBankname() {
            return bankname;
        }

        public void setBankname(String bankname) {
            this.bankname = bankname;
        }
    }
//
//    /**
//     * data : [{"id":1,"type":"1","username":"威震天","status":"2","cashtime":1541988680000,"dealtime":null,"banknumber":"18191757870","bankaddress":null,"bankname":null,"userid":5,"mobilephone":"18191757870","money":10,"alipayaccopunt":null},{"id":2,"type":"1","username":"威震天","status":"2","cashtime":1541988680000,"dealtime":null,"banknumber":"18191757870","bankaddress":null,"bankname":null,"userid":5,"mobilephone":"18191757870","money":10,"alipayaccopunt":null}]
//     * code : 200
//     * msg : 查看用户提现记录成功
//     * execute : true
//     * success : true
//     */
//
//    private int code;
//    private String msg;
//    private boolean execute;
//    private boolean success;
//    private List<DataBean> data;
//
//    public int getCode() {
//        return code;
//    }
//
//    public void setCode(int code) {
//        this.code = code;
//    }
//
//    public String getMsg() {
//        return msg;
//    }
//
//    public void setMsg(String msg) {
//        this.msg = msg;
//    }
//
//    public boolean isExecute() {
//        return execute;
//    }
//
//    public void setExecute(boolean execute) {
//        this.execute = execute;
//    }
//
//    public boolean isSuccess() {
//        return success;
//    }
//
//    public void setSuccess(boolean success) {
//        this.success = success;
//    }
//
//    public List<DataBean> getData() {
//        return data;
//    }
//
//    public void setData(List<DataBean> data) {
//        this.data = data;
//    }
//
//    public static class DataBean {
//        /**
//         * id : 1
//         * type : 1
//         * username : 威震天
//         * status : 2
//         * cashtime : 1541988680000
//         * dealtime : null
//         * banknumber : 18191757870
//         * bankaddress : null
//         * bankname : null
//         * userid : 5
//         * mobilephone : 18191757870
//         * money : 10
//         * alipayaccopunt : null
//         */
//
//        private int id;
//        private String type;
//        private String username;
//        private String status;
//        private long cashtime;
//        private Object dealtime;
//        private String banknumber;
//        private Object bankaddress;
//        private Object bankname;
//        private int userid;
//        private String mobilephone;
//        private int money;
//        private Object alipayaccopunt;
//
//        public int getId() {
//            return id;
//        }
//
//        public void setId(int id) {
//            this.id = id;
//        }
//
//        public String getType() {
//            return type;
//        }
//
//        public void setType(String type) {
//            this.type = type;
//        }
//
//        public String getUsername() {
//            return username;
//        }
//
//        public void setUsername(String username) {
//            this.username = username;
//        }
//
//        public String getStatus() {
//            return status;
//        }
//
//        public void setStatus(String status) {
//            this.status = status;
//        }
//
//        public long getCashtime() {
//            return cashtime;
//        }
//
//        public void setCashtime(long cashtime) {
//            this.cashtime = cashtime;
//        }
//
//        public Object getDealtime() {
//            return dealtime;
//        }
//
//        public void setDealtime(Object dealtime) {
//            this.dealtime = dealtime;
//        }
//
//        public String getBanknumber() {
//            return banknumber;
//        }
//
//        public void setBanknumber(String banknumber) {
//            this.banknumber = banknumber;
//        }
//
//        public Object getBankaddress() {
//            return bankaddress;
//        }
//
//        public void setBankaddress(Object bankaddress) {
//            this.bankaddress = bankaddress;
//        }
//
//        public Object getBankname() {
//            return bankname;
//        }
//
//        public void setBankname(Object bankname) {
//            this.bankname = bankname;
//        }
//
//        public int getUserid() {
//            return userid;
//        }
//
//        public void setUserid(int userid) {
//            this.userid = userid;
//        }
//
//        public String getMobilephone() {
//            return mobilephone;
//        }
//
//        public void setMobilephone(String mobilephone) {
//            this.mobilephone = mobilephone;
//        }
//
//        public int getMoney() {
//            return money;
//        }
//
//        public void setMoney(int money) {
//            this.money = money;
//        }
//
//        public Object getAlipayaccopunt() {
//            return alipayaccopunt;
//        }
//
//        public void setAlipayaccopunt(Object alipayaccopunt) {
//            this.alipayaccopunt = alipayaccopunt;
//        }
//    }


}
