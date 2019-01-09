package com.example.ouc.demo.entity;

import java.util.List;

public class UpgradeMembersEntity {

    /**
     * data : [{"id":2,"descp":"1.购买超级会员者，可以开始享受消费返佣及自己做任务返佣。","price":499,"level":"超级会员","levelImg":"http://localhost:8080/oilcard/upload/level/levalImg/1543477326809792.jpg"},{"id":3,"descp":"1.购买白金会员者，可以获得白金以下等级的消费返佣及自己做任务返佣。","price":3999,"level":"白金会员","levelImg":"http://localhost:8080/oilcard/upload/level/levalImg/1543477326809792.jpg"},{"id":4,"descp":"1.购买VIP会员者，可以获得下级消费返佣及自己做任务返佣。","price":39999,"level":"VIP代理","levelImg":"http://localhost:8080/oilcard/upload/level/levalImg/1543477326809792.jpg"}]
     * code : 200
     * msg : 查询等级列表成功
     * execute : true
     * success : true
     */

    private int code;
    private String msg;
    private boolean execute;
    private boolean success;
    private List<DataBean> data;

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

    public static class DataBean {
        /**
         * id : 2
         * descp : 1.购买超级会员者，可以开始享受消费返佣及自己做任务返佣。
         * price : 499
         * level : 超级会员
         * levelImg : http://localhost:8080/oilcard/upload/level/levalImg/1543477326809792.jpg
         */

        private int id;
        private String descp;
        private int price;
        private String level;
        private String levelImg;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getDescp() {
            return descp;
        }

        public void setDescp(String descp) {
            this.descp = descp;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public String getLevelImg() {
            return levelImg;
        }

        public void setLevelImg(String levelImg) {
            this.levelImg = levelImg;
        }
    }
}
