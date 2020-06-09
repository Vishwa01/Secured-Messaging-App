package com.project_VDNRV.securedmessaging;
public class Message {

    public String MESSAGE,QUESTION1,QUESTION2,QUESTION3,ENCRYPTED,ANSWER1,ANSWER2,ANSWER3,KEY;
    public long END;
    public long  LEVEL;
    public boolean STATUS;
    public Message(String message,String QUESTION1,String ANSWER1,String QUESTION2 ,String ANSWER2, String QUESTION3,String ANSWER3, String ENCRYPTED,String KEY,long LEVEL)
    {
        this.MESSAGE = message;
        this.LEVEL = LEVEL;
        this.QUESTION1 = QUESTION1;
        this.ANSWER1 = ANSWER1;
        this.QUESTION2 = QUESTION2;
        this.ANSWER2 = ANSWER2;
        this.QUESTION3 = QUESTION3;
        this.ANSWER3 = ANSWER3;
        this.END = 0;
        this.ENCRYPTED= ENCRYPTED;
        this.STATUS = false;
        this.KEY = KEY;
    }
    public Message()
    {
        this.MESSAGE = null;
        this.QUESTION1 = null;
        this.ANSWER1 = null;
        this.QUESTION2 = null;
        this.ANSWER2 = null;
        this.QUESTION3 = null;
        this.ANSWER3 = null;
        this.END = 0;
        this.LEVEL =1;
        this.ENCRYPTED= null;
        this.STATUS = false;
        this.KEY = null;
    }
}
