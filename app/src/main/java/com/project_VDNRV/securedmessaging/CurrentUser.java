package com.project_VDNRV.securedmessaging;

public class CurrentUser {


    String codeName,data,sec_que1,sec_que2,sec_que3,mes,key;

    public void setMes(String mes) {
        this.mes = mes;
    }

    public void setKey(String key) {
        this.key = key;
    }
    public String getKey() {
        return key;
    }
    public void setSec_que1(String sec_que1) {
        this.sec_que1 = sec_que1;
    }
    public String getMes() {
        return mes;
    }
    public void setSec_que2(String sec_que2) {
        this.sec_que2 = sec_que2;
    }
    public void setSec_que3(String sec_que3) {
        this.sec_que3 = sec_que3;
    }
    public String getSec_que2() {
        return sec_que2;
    }
    public String getSec_que3() {
        return sec_que3;
    }
    public String getSec_que1() {
        return sec_que1;
    }
    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }
    public String getCodeName()
    {
        return this.codeName;
    }
    public String getData() {
        return data;
    }
    public void setData(String data) {
        this.data = data;
    }
    private static final CurrentUser currentuser = new CurrentUser();
    public static CurrentUser getInstance() {return currentuser;}
}
