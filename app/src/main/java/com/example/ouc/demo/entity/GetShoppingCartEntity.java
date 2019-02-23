package com.example.ouc.demo.entity;

import java.util.List;

public class GetShoppingCartEntity {

    /**
     * data : [{"id":16,"num":1,"shopid":"CLD265302","price":200,"bannerone":"http://kgj.ockeji.com/shop/comment/CLD265301/1548228126245280.jpg","memberid":99,"name":"全面屏手机","createdate":1548399118000,"updatedate":1548399118000},{"id":17,"num":1,"shopid":"CLD265303","price":300,"bannerone":"http://kgj.ockeji.com/shop/comment/CLD265301/1548228126245280.jpg","memberid":99,"name":"vivo Z1极光特别版 新一代全面屏AI双摄手机 4GB+64GB 移动联通电信全网通4G手机","createdate":1548399285000,"updatedate":1548399285000},{"id":18,"num":1,"shopid":"CLD265303","price":300,"bannerone":"http://kgj.ockeji.com/shop/comment/CLD265301/1548228126245280.jpg","memberid":99,"name":"vivo Z1极光特别版 新一代全面屏AI双摄手机 4GB+64GB 移动联通电信全网通4G手机","createdate":1548470549000,"updatedate":1548470549000}]
     * success : true
     * code : 200
     * msg : 获取成功
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
         * id : 16
         * num : 1
         * shopid : CLD265302
         * price : 200
         * bannerone : http://kgj.ockeji.com/shop/comment/CLD265301/1548228126245280.jpg
         * memberid : 99
         * name : 全面屏手机
         * createdate : 1548399118000
         * updatedate : 1548399118000
         */

        private int id;
        private int num;
        private String shopid;
        private double price;
        private String bannerone;

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }

        private int memberid;
        private String name;
        private String info;
        private long createdate;
        private long updatedate;
        public boolean isChoosed;

        public boolean isChoosed() {
            return isChoosed;
        }

        public void setChoosed(boolean choosed) {
            isChoosed = choosed;
        }

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

        public String getShopid() {
            return shopid;
        }

        public void setShopid(String shopid) {
            this.shopid = shopid;
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

        public int getMemberid() {
            return memberid;
        }

        public void setMemberid(int memberid) {
            this.memberid = memberid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public long getCreatedate() {
            return createdate;
        }

        public void setCreatedate(long createdate) {
            this.createdate = createdate;
        }

        public long getUpdatedate() {
            return updatedate;
        }

        public void setUpdatedate(long updatedate) {
            this.updatedate = updatedate;
        }
    }
}
