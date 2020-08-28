package com.yfy.app.login;

public class TermId {

    /**
     * result : true
     * error_code :

     */

    private String result;
    private String error_code;
    /**

     * id : 1
     * isnow : 1
     */

    private TermBean term;

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


    public TermBean getTerm() {
        return term;
    }

    public void setTerm(TermBean term) {
        this.term = term;
    }

    public static class TermBean {

        /**
         * datemax : 2017/1/1
         * datemin : 2017/6/1
         * name : 16－17下期
         * id : 48
         */
        private String datemax;
        private String datemin;
        private String name;
        private String id;

        public void setDatemax(String datemax) {
            this.datemax = datemax;
        }

        public void setDatemin(String datemin) {
            this.datemin = datemin;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getDatemax() {
            return datemax;
        }

        public String getDatemin() {
            return datemin;
        }

        public String getName() {
            return name;
        }

        public String getId() {
            return id;
        }
    }
}
