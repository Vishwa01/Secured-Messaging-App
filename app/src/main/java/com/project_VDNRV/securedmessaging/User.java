package com.project_VDNRV.securedmessaging;

public class User {
    private String name,codeName,password,email;
    public User()
    {
        this.name=null;
        this.email=null;
        this.password = null;
        this.codeName = null;

    }

    public User(String name,String codeName,String email,String password)
    {
        this.name = name;
        this.codeName= codeName;
        this.password= password;
        this.email= email;
    }


    public  String getName()
    {
        return this.name;
    }
    public String getEmail(){return this.email;}
    public String getCodeName() {return this.codeName;}
    public void  setEmail(String email) { this.email = email;}
    public void setName(String nm) { this.name = nm; }
    public void setCodeName(String codeName) {this.codeName= codeName;}
    public void setPassword(String password) { this.password = password; }
}
