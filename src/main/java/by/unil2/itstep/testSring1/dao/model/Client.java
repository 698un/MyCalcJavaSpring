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

    public Client(){

        this.key = CalcOptions.getOptions().getRandomKey(10);
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
