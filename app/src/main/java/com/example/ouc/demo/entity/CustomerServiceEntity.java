package com.example.ouc.demo.entity;

public class CustomerServiceEntity {

    /**
     * data : {"data":{"id":4,"content":"<p><img src=\"http://kgj.ockeji.com/upload/kgj/task/contentFile/1539855752579884.jpg\" style=\"max-width:100%;\"><br><\/p>","status":"1","endTime":1546099200000,"startTime":1538841600000,"createTime":1539855781000,"title":"西安欧畅科技","phone":null,"quantity":8942,"cover":"http://kgj.ockeji.com/upload/kgj/task/imgFile/1539855781732845.jpg","amount":9665,"gold":0.1,"video":"http://kgj.ockeji.com/upload/kgj/task/videoFile/1539855781734374.mp4","publisher":"王雅","is_recomm":"0","shopinfo":"http://kgj.ockeji.com/upload/kgj/task/shopFile/592415218057261531.jpg","is_top":"0"},"personNo":"MS963217"}
     * msg : 查询任务信息成功
     * execute : true
     * success : true
     * code : 200
     */

    private DataBeanX data;
    private String msg;
    private boolean execute;
    private boolean success;
    private int code;

    public DataBeanX getData() {
        return data;
    }

    public void setData(DataBeanX data) {
        this.data = data;
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

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public static class DataBeanX {
        /**
         * data : {"id":4,"content":"<p><img src=\"http://kgj.ockeji.com/upload/kgj/task/contentFile/1539855752579884.jpg\" style=\"max-width:100%;\"><br><\/p>","status":"1","endTime":1546099200000,"startTime":1538841600000,"createTime":1539855781000,"title":"西安欧畅科技","phone":null,"quantity":8942,"cover":"http://kgj.ockeji.com/upload/kgj/task/imgFile/1539855781732845.jpg","amount":9665,"gold":0.1,"video":"http://kgj.ockeji.com/upload/kgj/task/videoFile/1539855781734374.mp4","publisher":"王雅","is_recomm":"0","shopinfo":"http://kgj.ockeji.com/upload/kgj/task/shopFile/592415218057261531.jpg","is_top":"0"}
         * personNo : MS963217
         */

        private DataBean data;
        private String personNo;

        public DataBean getData() {
            return data;
        }

        public void setData(DataBean data) {
            this.data = data;
        }

        public String getPersonNo() {
            return personNo;
        }

        public void setPersonNo(String personNo) {
            this.personNo = personNo;
        }

        public static class DataBean {
            /**
             * id : 4
             * content : <p><img src="http://kgj.ockeji.com/upload/kgj/task/contentFile/1539855752579884.jpg" style="max-width:100%;"><br></p>
             * status : 1
             * endTime : 1546099200000
             * startTime : 1538841600000
             * createTime : 1539855781000
             * title : 西安欧畅科技
             * phone : null
             * quantity : 8942
             * cover : http://kgj.ockeji.com/upload/kgj/task/imgFile/1539855781732845.jpg
             * amount : 9665
             * gold : 0.1
             * video : http://kgj.ockeji.com/upload/kgj/task/videoFile/1539855781734374.mp4
             * publisher : 王雅
             * is_recomm : 0
             * shopinfo : http://kgj.ockeji.com/upload/kgj/task/shopFile/592415218057261531.jpg
             * is_top : 0
             */

            private int id;
            private String content;
            private String status;
            private long endTime;
            private long startTime;
            private long createTime;
            private String title;
            private Object phone;
            private int quantity;
            private String cover;
            private int amount;
            private double gold;
            private String video;
            private String publisher;
            private String is_recomm;
            private String shopinfo;
            private String is_top;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public long getEndTime() {
                return endTime;
            }

            public void setEndTime(long endTime) {
                this.endTime = endTime;
            }

            public long getStartTime() {
                return startTime;
            }

            public void setStartTime(long startTime) {
                this.startTime = startTime;
            }

            public long getCreateTime() {
                return createTime;
            }

            public void setCreateTime(long createTime) {
                this.createTime = createTime;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public Object getPhone() {
                return phone;
            }

            public void setPhone(Object phone) {
                this.phone = phone;
            }

            public int getQuantity() {
                return quantity;
            }

            public void setQuantity(int quantity) {
                this.quantity = quantity;
            }

            public String getCover() {
                return cover;
            }

            public void setCover(String cover) {
                this.cover = cover;
            }

            public int getAmount() {
                return amount;
            }

            public void setAmount(int amount) {
                this.amount = amount;
            }

            public double getGold() {
                return gold;
            }

            public void setGold(double gold) {
                this.gold = gold;
            }

            public String getVideo() {
                return video;
            }

            public void setVideo(String video) {
                this.video = video;
            }

            public String getPublisher() {
                return publisher;
            }

            public void setPublisher(String publisher) {
                this.publisher = publisher;
            }

            public String getIs_recomm() {
                return is_recomm;
            }

            public void setIs_recomm(String is_recomm) {
                this.is_recomm = is_recomm;
            }

            public String getShopinfo() {
                return shopinfo;
            }

            public void setShopinfo(String shopinfo) {
                this.shopinfo = shopinfo;
            }

            public String getIs_top() {
                return is_top;
            }

            public void setIs_top(String is_top) {
                this.is_top = is_top;
            }
        }
    }
}
