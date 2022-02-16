package by.unil2.itstep.testSring1.utilits;


/**
 * This class is locker for synchronized all thread
 */
public class MyLocker {



    private String title;

    private static MyLocker singleLocker;

    public static MyLocker getLocker(){
        if (singleLocker==null) singleLocker=new  MyLocker();
        return singleLocker;
        }

    private MyLocker(){
        title = "Locker for threads";
        }


}//class MyLocker
