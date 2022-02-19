package by.unil2.itstep.testSring1.utilits;


 import by.unil2.itstep.testSring1.exceptions.AccessException;
 import by.unil2.itstep.testSring1.utilits.loger.LogState;
 import org.springframework.stereotype.Component;

 import java.io.File;
 import java.io.IOException;
 import java.util.HashMap;
 import java.util.Map;
 import java.util.Random;
 import java.util.Scanner;
 import java.util.concurrent.ConcurrentHashMap;

@Component
public class CalcOptions {


    //private ConcurrentHashMap<String,String> optList;

    private volatile Map<String,String>  optListStr;


    //paths properties
    private String applicationPath;
    public String getApplicationPath(){return this.applicationPath;}



    public CalcOptions (){
        optListStr =     new ConcurrentHashMap<String,String>();
        this.reLoad();//read config from from file
        this.rootKey = getRandomKey(10);
        }//constructor



    public String getStr(String name){     return optListStr.get(name);     }

    public boolean getBoolean(String name){
        //get string value
        String strValue = optListStr.get(name);
        //convert this string to boolean
        Boolean booleanValue = Boolean.valueOf(strValue);
        return booleanValue;
        }

    public int    getInt(String name){
        String strValue = optListStr.get(name);
        int intValue = Integer.valueOf(strValue);
        return intValue;
        }





    /**
     * This method generate random key
     * @param lengthOfKey
     * @return
     */
    public String getRandomKey(int lengthOfKey){

       if (lengthOfKey<3) lengthOfKey = 3;
       Random rnd = new Random();
       StringBuilder sb1=new StringBuilder("");
       for (int i=0;i<lengthOfKey;i++) sb1.append((char)('a'+rnd.nextInt('z'-'a')));
       return sb1.toString();
       }//getRandomKey

    private String rootKey;

    public String getCurrentRootKey(){
        return this.rootKey;

        }

    /**
     * This method compares rootPassword and input passsord
     * @param inpPassword
     * @return  rootKey (new created key for root)
     * @return  "none" if password not equals
     */
    public String getNewRootKey(String inpPassword)throws AccessException{
        String rootPassword = this.getStr("rootPassword");//get rootPassword from config
        if (rootPassword.equals(inpPassword)) {
                    this.rootKey = getRandomKey(10);
                    return this.rootKey;
                    }
        throw new AccessException("Invalid login or password!");
        }//rootPasswordEquals

    private void reLoad(){

        //Read options from file
        this.applicationPath= System.getProperty("user.dir");


        File fileConfig = new File(applicationPath+ File.separator+"config.ini");


        try {
            Scanner scanner = new Scanner(fileConfig);

            String keyStr;//key of property
            String valStr;//value of property
            String lineString;
            //построчно считываем файл
            scanner.useDelimiter(System.getProperty("line.separator"));
            scanner.useDelimiter("\n");
            while (scanner.hasNext()) {

                lineString = scanner.next();
                int limitIndex = lineString.indexOf("=");

                keyStr  = lineString.substring(0,limitIndex);
                valStr  = lineString.substring(limitIndex+1).trim();


                optListStr.put(keyStr, valStr);


                }//next Line

            scanner.close();
        } catch (IOException e) {
           e.printStackTrace();}

        System.out.println("Read complette." );

        }






}//CalcOptions
