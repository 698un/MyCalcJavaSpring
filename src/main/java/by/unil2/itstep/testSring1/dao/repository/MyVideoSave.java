package by.unil2.itstep.testSring1.dao.repository;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class MyVideoSave extends Thread{

    public static String completteFileName =  "enablevideocreate.inf";

    String fileName;
    String videoFolder;
    String imageFolder;
    String appCodecPath;
    int fps;

    public MyVideoSave(String inpFileName,
                       String inpVideoFolder,
                       String inpImageFolder,
                       String inpAppCodecPath,
                       int inpFps){

        this.fileName=      inpFileName;
        this.videoFolder =  inpVideoFolder;
        this.imageFolder=   inpImageFolder;
        this.appCodecPath = inpAppCodecPath;
        this.fps =          inpFps;
        }//constructor


    @Override
    public void run(){


        try{

            //Create Command File in videoFolder
            String batPath = createBatFile(this.videoFolder+File.separator+fileName);

            //Run creating command file
            launchBatFile(batPath);


            } catch (Exception e) {

            // При открытии и сохранении файлов, может произойти неожиданный случай.
            // И на этот случай у нас try catch
            e.printStackTrace();

            //MyLogger.getLogger().log(LogState.ERROR,"not started video create!!!");

        }

    }//saveToDisc





    /**
     * Method launch .bat file for launch ather application for
     * @param bathPath
     * @throws Exception
     */
    private void launchBatFile(String bathPath)throws Exception{

        //bat file locate in resImageFolder
        String workDir = this.imageFolder+
                         File.separator;

        try {
            Runtime.getRuntime().exec("cmd /c start "+bathPath,null,new File(workDir));

             } catch (IOException e) {

                    throw new Exception(e.getMessage()) ;
                        }

        //mark in fileSystem that process is started
        videoCreateSetDisabled(this.videoFolder);

        }//launchBatFile



    /**
     * This method defined complette process of video create or not
     * @return TRUE if not complette
     */
    public static boolean videoCreateIsEnabled(String videoFolder){
        File fileComplette = new File(videoFolder+File.separator+completteFileName);
        if (fileComplette.exists()) return true;
        return false;
        }


    /**
     * This method check "videoComplette" in fileSystem
     * @Param = folder of video files
     */
    public static void videoCreateSetEnabled(String videoFolder){
        File fileComplette = new File(videoFolder+File.separator+completteFileName);
        try {
            fileComplette.createNewFile();
        } catch (IOException e){}
        }//videoSetComplette


    /**
     * This method set marker as process create of video is running
     * @Param = folder of video files
     */
    public static void videoCreateSetDisabled(String videoFolder){
        File fileComplette = new File(videoFolder+File.separator+completteFileName);
        try {
            fileComplette.delete();
        } catch (Exception e){}
        }//videoSetUnComplette





    /**
     * This methos create bat file to launch codec from ather application
     * @param fullVideoPath
     * @return
     * @throws IOException
     */
    private String createBatFile(String fullVideoPath)throws IOException {

        String batFullPath = this.imageFolder+
                             File.separator+
                             "create.bat";

        //create first Command line in bat file
        String data1 =this.appCodecPath+
                     " -r "+
                     this.fps+
                     " -y -i \"%%10d.png\" "+
                     fullVideoPath+
                     "\n";

        //create second Command line in bat file
        String data2 = "NUL>  "+
                      videoFolder+File.separator+completteFileName+
                      "\n";

        String data3 = "exit";

        System.out.println("bat_data:"+data1);
        System.out.println("bat_data:"+data2);
        System.out.println("bat_data:"+data3);


        System.out.println("bat_path:"+batFullPath);



        File batFile = new File(batFullPath);

        FileWriter fr = null;
        try {
            fr = new FileWriter(batFile);
            fr.write(data1);
            fr.write(data2);
            fr.write(data3);


        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                fr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return batFullPath;

    }//createBatFile


}//MyImage
