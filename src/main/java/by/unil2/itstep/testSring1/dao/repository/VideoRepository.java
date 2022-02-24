package by.unil2.itstep.testSring1.dao.repository;

//import by.unil2.itstep.testSring1.dao.model.VideoFile;
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
        reset();
        }


    private String videoFolder;

    public void reset(){

        videoFolder =calcOpt.getApplicationPath()+
                     File.separator+
                     calcOpt.getStr("videoResultatFolder");

        //create videoFolder if not exist
        new File(videoFolder).mkdir();

        //set marker in fileSystem as VideoCreate ENABLE
        MyVideoSave.videoCreateSetEnabled(this.videoFolder);
        }

    public Boolean fileIsExist(String fileName){



        return false;
        }




    /**
     * This method start the coding of videoFile
      * @return filename
     */
    public void createNewVideo(String fileName) throws Exception{

        //if process of create of videoFile is NOT ENABLED then exit with errorMessage
        if (!MyVideoSave.videoCreateIsEnabled(this.videoFolder)) throw new AccessException("Process is not Complette");

        //defined folder of images
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
    public String getVideoFullPath(String fileName) throws Exception{

        String fullFilePath = calcOpt.getApplicationPath()+
                              File.separator+
                              calcOpt.getStr("videoResultatFolder")+
                              File.separator+
                              fileName;

        System.out.println("fullFilePath:"+fullFilePath);

        File videoFileObject = new File(fullFilePath);

        if (videoFileObject.exists()==false) throw new VideoException("File not found");

        if (videoFileObject.isFile()==false) throw new VideoException("incorrect File name");

        return fullFilePath;
        //return videoFileObject;
        }




}
