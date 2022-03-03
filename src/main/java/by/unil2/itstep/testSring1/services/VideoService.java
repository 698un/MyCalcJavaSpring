package by.unil2.itstep.testSring1.services;

import by.unil2.itstep.testSring1.dao.repository.ClientRepository;
import by.unil2.itstep.testSring1.dao.repository.VideoRepository;
import by.unil2.itstep.testSring1.utilits.CalcOptions;
import by.unil2.itstep.testSring1.utilits.loger.MyLogger;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;


@Service
public class VideoService {

    private final CalcOptions      calcOpt;
    private final MyLogger         myLog;
    private final ClientRepository clientRep;
    private final VideoRepository videoRep;


    //constructor
    public VideoService(CalcOptions inpCalcOptions,
                       MyLogger inpMyLogger,
                       ClientRepository inpClientRepository,
                       VideoRepository inpVideoRepository){

        this.calcOpt =  inpCalcOptions;
        this.myLog   =  inpMyLogger;
        this.clientRep= inpClientRepository;
        this.videoRep =   inpVideoRepository;

        //this.reset();
        }








    public void createMP4(String fileName)throws Exception {

        String lowFileName = fileName.toLowerCase();//NECESalary
        //append ext for filename if not exist
        if (lowFileName.indexOf(".mp4")<0) fileName+=".mp4";



        try {
            videoRep.createNewVideo(fileName);
            } catch (Exception e) {throw new Exception(e.getMessage());}


        }//createMP4


    public boolean fileIsExist(String fileName){


        return videoRep.fileIsExist(fileName);
        }


    /**
     * get list of complette videofiles
     * @param none
     * @return list<String>
     * @throws Exception
     */

    public ArrayList getVideoList()throws Exception {
        try {
            return videoRep.getVideoList();
            } catch (Exception e) {throw new Exception(e.getMessage());}

        }//getVideoList

    public String getVideoFullPath(String fileName) throws Exception{

        try{
            return videoRep.getVideoFullPath(fileName);
            } catch (Exception e) {throw e;}

        }//getFileObject






}//VideoService
