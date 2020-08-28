package com.yfy.app.login;

/**
 * Created by yfy1 on 2016/12/1.
 */
public class UserInfor {

    /**
     * id :
     * fxid : 0
     * headPic : null
     * name :
     * username :
     */

    private UserinfoBean userinfo;
    /**
     * userinfo : {"id":"","fxid":0,"headPic":null,"name":"","username":""}
     * session_key :
     * result : false

     */

    private String session_key;
    private String result;
    private String error_code;

    public UserinfoBean getUserinfo() {
        return userinfo;
    }

    public void setUserinfo(UserinfoBean userinfo) {
        this.userinfo = userinfo;
    }

    public String getSession_key() {
        return session_key;
    }

    public void setSession_key(String session_key) {
        this.session_key = session_key;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getError_code() {
        return error_code;
    }

    public void setError_code(String error_code) {
        this.error_code = error_code;
    }

    public static class UserinfoBean {
        private String id;
        private int fxid;
        private String headPic;
        private String name;
        private String username;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getFxid() {
            return fxid;
        }

        public void setFxid(int fxid) {
            this.fxid = fxid;
        }

        public String getHeadPic() {
            return headPic;
        }

        public void setHeadPic(String headPic) {
            this.headPic = headPic;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }
}
