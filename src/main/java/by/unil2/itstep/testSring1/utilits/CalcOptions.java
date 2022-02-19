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
    private volatile Map<String,Integer> optListInt;
    private volatile Map<String,Boolean> optListBoolean;

    private int imageInBufferCount;

    //control properties
    private int lineLifeTime;
    private int clientLifeTime;
    private int clientClearTime;

    //image properties
    private int fps;
    private int imageHeight;
    private int imageWidth;
    private int antialiasing;

    //paths properties
    private String imageResultatFolder;
    private String videoResultatFolder;
    private String commandFolder;


    public CalcOptions (){
        optListStr =     new ConcurrentHashMap<String,String>();
        optListInt =     new ConcurrentHashMap<String,Integer>();
        optListBoolean = new ConcurrentHashMap<String,Boolean>();
        this.reLoad();//read fields from file

        this.rootKey = getRandomKey(10);
        }//constructor




    public boolean getBoolean(String name){return optListBoolean.get(name); }
    public String getStr(String name){     return optListStr.get(name);     }

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
        String appPath = System.getProperty("user.dir");


        //add properties ApplicationPath
        this.optListStr.put("applicationPath",appPath);

        File fileConfig = new File(appPath+ File.separator+"config.ini");


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

                //add to String map
                optListStr.put(keyStr, valStr);
                //System.out.println(keyStr+" : "+valStr);

                }//next Line

            scanner.close();
        } catch (IOException e) {e.printStackTrace();}

        System.out.println("Read complette." );

        }


    //===========SYSTEM_METHODS==============================================






}//CalcOptions
