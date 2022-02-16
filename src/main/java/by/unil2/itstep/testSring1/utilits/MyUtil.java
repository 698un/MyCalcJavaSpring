package by.itstep.mySite.utilits;


public class MyUtil {

    public static boolean equalsSB2(StringBuffer sb1,StringBuffer sb2){

        if (sb1.length()!=sb2.length()) return false;

        //if two StringBuffer is empty then TRUE
        if (sb1.length()==0) return true;

        //equal every symbol
        for (int i=0;i<sb1.length();i++){
            if (sb1.charAt(i)!=sb2.charAt(i)) return false;
            }//next i

        return true;
        }//equalsSB2


}
