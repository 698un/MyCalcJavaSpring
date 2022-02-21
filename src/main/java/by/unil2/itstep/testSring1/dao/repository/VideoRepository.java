package by.unil2.itstep.testSring1.dao.repository;

import by.unil2.itstep.testSring1.dao.model.VideoFile;
import by.unil2.itstep.testSring1.exceptions.AccessException;
import by.unil2.itstep.testSring1.exceptions.VideoException;
import by.unil2.itstep.testSring1.utilits.CalcOptions;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

@Component
public class VideoRepository {

    private CalcOptions calcOpt;//Injects components

    //constructor
    public VideoRepository(CalcOptions inpCalcOptions){
        this.calcOpt = inpCalcOptions;

        }


    private String videoFolder;

    public void reset(){
        videoSetComplette();//Создание файла присзнака что можно склеивать
        videoFolder =calcOpt.getApplicationPath()+
                     File.separator+
                     calcOpt.getStr("imageResultatFolder");

        //create videoFolder if not exist
        new File(videoFolder).mkdir();
        }

    public Boolean fileIsExist(String fileName){



        return false;
        }




    /**
     * This method start the coding of videoFile
      * @return filename
     */
    public void createNewVideo(String fileName) throws Exception{

        //if process is not complete
        if (!videoIsComplette()) throw new AccessException("Process is not Complette");


        String videoFolder = calcOpt.getApplicationPath()+
                             File.separator+
                             calcOpt.getStr("videoResultatFolder");

        String imageFolder = calcOpt.getApplicationPath()+
                             File.separator+
                             calcOpt.getStr("imageResultatFolder");


        try {

            MyVideoSave saveThread = new MyVideoSave(fileName,
                                                     videoFolder,
                                                     imageFolder,
                                                     calcOpt.getStr("ffmpegPath"),
                                                     calcOpt.getInt("fps")
                                                     );



            saveThread.start();

            } catch (Exception e){throw new Exception(e.getMessage());}

        //return videoFileName;
        }



    /**
     * This method defined complette process of video create or not
      * @return TRUE if not complette
     */
    public boolean videoIsComplette(){
       // System.out.println("COMPLETTE:"+getFileComplettePath());
        File fileComplette = new File(getFileComplettePath());
        if (fileComplette.exists()) return true;
        return false;
        }

    /**
     * This method set marker as video is complette
     */
    public void videoSetComplette(){
        // System.out.println("COMPLETTE:"+getFileComplettePath());
        File fileComplette = new File(getFileComplettePath());
        try {
            fileComplette.createNewFile();
            } catch (IOException e){}
        }//videoSetComplette


    /**
     * This method set marker as process create of video is running
     */
    public void videoSetUnComplette(){
        // System.out.println("COMPLETTE:"+getFileComplettePath());
        File fileComplette = new File(getFileComplettePath());
        try {
            fileComplette.delete();
            } catch (Exception e){}
        }//videoSetUnComplette



    /**
     * This method generate path to completteFile
      * @return
     */
    public String getFileComplettePath(){

        return calcOpt.getApplicationPath()+
               File.separator+
               calcOpt.getStr("videoResultatFolder")+
               File.separator+
               "complette.inf";
        }//getFileComplettePath


    /**
     * This method search completting videoFiles
     * @return ArrayList of filenames
     */
    public ArrayList<String> getVideoList(){

        //Path to folder of videoFiles
        String path = calcOpt.getApplicationPath()+
                      File.separator+
                      calcOpt.getStr("videoResultatFolder")+
                      File.separator;

        File dir = new File(path); //path указывает на директорию
        ArrayList<String> lst = new ArrayList<>();
        String fileName;

        for ( File file : dir.listFiles() ){

            if ( file.isFile() ){
                fileName = file.getName();
                if (fileName.indexOf(".mp4")>-1) lst.add(file.getName());
                }//if isFile
            }//next file

        return lst;

        }//getVideoList

    /**
     * This method return File object as videoFile by name
      * @param fileName
     * @return
     */
    public File getVideo(String fileName) throws Exception{

        String fullFilePath = calcOpt.getApplicationPath()+
                              File.separator+
                              calcOpt.getStr("videoResultatFolder")+
                              fileName;

        File videoFileObject = new File(fullFilePath);

        if (videoFileObject.exists()==false) throw new VideoException("File not found");

        if (videoFileObject.isFile()==false) throw new VideoException("incorrect File name");

        return videoFileObject;
        }




}
