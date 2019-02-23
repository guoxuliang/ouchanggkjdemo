package com.example.ouc.demo.entity;

import java.util.List;

public class AllCommentsEntity {

    /**
     * data : [{"commentinfo":"梵蒂冈的双方各","id":36,"point":3,"username":"王雅","goodsid":"43","createdate":1550284807000,"userid":99,"orderno":"481155bfed894d00be80e668a6686c12","info":"蓝*1"},{"imgone":"http://kgj.ockeji.com//shop/comment/fsdfs/1550281248176641.jpg","id":35,"goodsid":"fsdfs","createdate":1550281248000,"userid":3},{"id":34,"goodsid":"fsdfs","createdate":1550281164000,"userid":3},{"commentinfo":"fdsfds","imgone":"http://kgj.ockeji.com//shop/comment/1/155022363072915.jpg","id":33,"imgthree":"http://kgj.ockeji.com//shop/comment/1/155022363172547.jpg","point":3.2,"username":"sdfsda","goodsid":"1","imgtwo":"http://kgj.ockeji.com//shop/comment/1/1550223631326588.jpg","createdate":1550223632000,"userid":3,"orderno":"dasdfsaf"},{"imgone":"https://www.baidu.com/img/bd_logo1.png","id":2,"imgthree":"https://www.baidu.com/img/bd_logo1.png","point":4,"goodsid":"CLD265302","imgtwo":"https://www.baidu.com/img/bd_logo1.png","createdate":1547004185000,"orderno":"201812191048498091295974185","info":"白*1"},{"imgone":"https://www.baidu.com/img/bd_logo1.png","id":1,"imgthree":"https://www.baidu.com/img/bd_logo1.png","point":0.5,"goodsid":"CLD265301","imgtwo":"https://www.baidu.com/img/bd_logo1.png","createdate":1547004183000,"orderno":"201812191048498091295974185","info":"白*1"}]
     * code : 200
     * msg : 获取评论成功
     * execute : false
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
         * commentinfo : 梵蒂冈的双方各
         * id : 36
         * point : 3
         * username : 王雅
         * goodsid : 43
         * createdate : 1550284807000
         * userid : 99
         * orderno : 481155bfed894d00be80e668a6686c12
         * info : 蓝*1
         * imgone : http://kgj.ockeji.com//shop/comment/fsdfs/1550281248176641.jpg
         * imgthree : http://kgj.ockeji.com//shop/comment/1/155022363172547.jpg
         * imgtwo : http://kgj.ockeji.com//shop/comment/1/1550223631326588.jpg
         */

        private String commentinfo;
        private int id;
        private int point;
        private String username;
        private String goodsid;
        private long createdate;
        private int userid;
        private String orderno;
        private String info;
        private String imgone;
        private String imgthree;
        private String imgtwo;

        public String getCommentinfo() {
            return commentinfo;
        }

        public void setCommentinfo(String commentinfo) {
            this.commentinfo = commentinfo;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getPoint() {
            return point;
        }

        public void setPoint(int point) {
            this.point = point;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getGoodsid() {
            return goodsid;
        }

        public void setGoodsid(String goodsid) {
            this.goodsid = goodsid;
        }

        public long getCreatedate() {
            return createdate;
        }

        public void setCreatedate(long createdate) {
            this.createdate = createdate;
        }

        public int getUserid() {
            return userid;
        }

        public void setUserid(int userid) {
            this.userid = userid;
        }

        public String getOrderno() {
            return orderno;
        }

        public void setOrderno(String orderno) {
            this.orderno = orderno;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }

        public String getImgone() {
            return imgone;
        }

        public void setImgone(String imgone) {
            this.imgone = imgone;
        }

        public String getImgthree() {
            return imgthree;
        }

        public void setImgthree(String imgthree) {
            this.imgthree = imgthree;
        }

        public String getImgtwo() {
            return imgtwo;
        }

        public void setImgtwo(String imgtwo) {
            this.imgtwo = imgtwo;
        }
    }
}
