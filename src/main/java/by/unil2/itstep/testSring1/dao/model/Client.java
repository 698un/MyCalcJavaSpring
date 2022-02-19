package by.unil2.itstep.testSring1.dao.model;
//import by.itstep.mySite.utilits.CalcOptions;

import by.unil2.itstep.testSring1.utilits.CalcOptions;

import java.util.Random;


public class Client {

    private String key;
    private int calcFrameNum;
    private int calcLineNum;
    private long lastTimeConnect;

    public String getKey(){
        return this.key;
        }
    public void  setKey(String inpClientKey){
        this.key=inpClientKey;
        }

    public Client(){
        this.key = null;
        this.lastTimeConnect = System.currentTimeMillis();
        }//constructor



    /**
     * This method rewrite lastTimeConnect as now
     */
    public void updateLastTime(){
        this.lastTimeConnect = System.currentTimeMillis();
        }
    public long getLastTimeConnect(){return this.lastTimeConnect;}



}//class Client
