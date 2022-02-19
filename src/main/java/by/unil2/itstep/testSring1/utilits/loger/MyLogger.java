/**
 * 2022-01-28  Correct filename of log file
 */
package by.unil2.itstep.testSring1.utilits.loger;


import by.unil2.itstep.testSring1.utilits.CalcOptions;
import by.unil2.itstep.testSring1.utilits.MyLocker;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Reader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;


@Component
public class MyLogger {


    //Injected CalcOptions
    private final CalcOptions calcOpt;

    //constructor
    public MyLogger(CalcOptions inpCalcOptions){
        this.calcOpt = inpCalcOptions;    //Injected CalcOptions
        this.reset();//reDefined parameters
        }

    private String folderPath;
    private String nowFilePath; //path to folder "log/date"
    private LogState recordLevel = LogState.ALL;
    private boolean logConsole  = false;

    public void reset(){
         this.folderPath = System.getProperty("user.dir")+
                           File.separator +
                           "log" +
                           File.separator;

         try{
             this.logConsole =calcOpt.getBoolean("logConsole");
             this.recordLevel = LogState.valueOf(calcOpt.getStr("logLevel"));
             } catch (Exception e) {         System.out.println("LogLevel not read");}

         }//reset


    private long dayNow=-1L;//date in stringToInt format(YYYYMMDD), value inposible that necesalary reCalculate in first time
   // private long dayNowTemp;//value for verify


    /**
     * method set minimal level for event that write on file
     * @param state
     */
    public void setRecordLevel(LogState state){
        this.recordLevel = state;
        }


    /**
     * All kids of situation
     * @param logMessage
     */
    public void info (String logMessage){ this.log(new Exception().getStackTrace()[1].getClassName(),LogState.INFO, logMessage); }
    public void trace(String logMessage){ this.log(new Exception().getStackTrace()[1].getClassName(),LogState.TRACE,logMessage); }
    public void all  (String logMessage){ this.log(new Exception().getStackTrace()[1].getClassName(),LogState.ALL,  logMessage); }
    public void debug(String logMessage){ this.log(new Exception().getStackTrace()[1].getClassName(),LogState.DEBUG,logMessage); }
    public void error(String logMessage){ this.log(new Exception().getStackTrace()[1].getClassName(),LogState.ERROR,logMessage); }
    public void fatal(String logMessage){ this.log(new Exception().getStackTrace()[1].getClassName(),LogState.FATAL,logMessage); }
    public void warn (String logMessage){ this.log(new Exception().getStackTrace()[1].getClassName(),LogState.WARN, logMessage); }


    /**
     * This method construction record for write to display or to file
     * and send this record on screen and logFile
     */
    public void log(String callerClassName,
                    LogState logState,
                    String message ){

        //exit if level if hightes of recordLevel
        if (logState.ordinal()>recordLevel.ordinal()) return;

        //defined name of caller Class
        int indexDot = callerClassName.lastIndexOf(".");
        if  (indexDot>=-1) {
            callerClassName = callerClassName.substring(indexDot+1);
        }

        //construction log record
        String recordString = "["+logState+"]\t"+
                LocalDate.now()+" "+
                LocalTime.now()+"\t"+
                callerClassName+"\t"+
                message;


        //send log record to file and console
        if (this.logConsole) System.out.println(recordString);
        writeRecordToFile(recordString);

        }//log


    /**
     * This method write o log file massage
     * @param record
     */
    public void writeRecordToFile(String record){

        //verify filename to records of log
        this.fileNameChange();

        //synchronize access to log file
        synchronized (MyLocker.getLocker()) {

            try {
                FileWriter writer = new FileWriter(nowFilePath, true);
                BufferedWriter bufferWriter = new BufferedWriter(writer);
                bufferWriter.write(record);
                bufferWriter.write("\n");
                bufferWriter.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }//synchronized
    }//writeRecord

    /**
     * This method reDefined dateFileName
     */
    private void fileNameChange(){

        //defined current date in long representation "YYYYMMDD"
        long dayNowTemp = LocalDate.now().getYear()*10000+
                     LocalDate.now().getMonthValue()*100+
                     LocalDate.now().getDayOfMonth();

        //if date not change then exit from method
        if (dayNowTemp==dayNow) return;


        dayNow = dayNowTemp;
        String dateString= String.valueOf(dayNow);

        //Create fileName for "YYYY-MM-DD.log"
        String fileName = dateString.substring(0,4)+
                          "-"+
                          dateString.substring(4,6)+
                          "-"+
                          dateString.substring(6)+
                          ".log";

        //construct filname for logFile
        nowFilePath=folderPath +fileName;

        //search file on the disc
        //create new file of log if this file is not exist
        File fileLog = new File(nowFilePath);
        if (!fileLog.exists()) {
            try{fileLog.createNewFile();}catch (Exception e){e.printStackTrace();};
            }

        }//fileNameChange



    //system methods========================================================
    //private static MyLogger singleLogger;

    //private constructor


    /*
    private MyLogger() {
        //defined folder for logs;
        folderPath = System.getProperty("user.dir")+
                File.separator +
                "log" +
                File.separator;

        this.logConsole =CalcOptions.getOptions().getBoolean("logConsole");

        //FATAL,ERROR,WARN,INFO,DEBUG,TRACE,ALL
        this.recordLevel = LogState.valueOf(CalcOptions.getOptions().getStr("logLevel"));



    } //Constructor
*/


/*
    public static MyLogger getLogger(){
//        if (singleLogger==null) singleLogger = new MyLogger();

        //verify actual filName for log file by date
        singleLogger.fileNameChange();

        return singleLogger;
    }


 */

}//class loger