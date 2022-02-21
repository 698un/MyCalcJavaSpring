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

        this.reset();
        }

    public void reset(){

    }





    public void createMP4(String fileName)throws Exception {
        try {
            videoRep.createNewVideo(fileName);
            } catch (Exception e) {throw new Exception(e.getMessage());}

        }//createMP4


    /**
     * get list of complette videofiles
     * @param fileName
     * @return
     * @throws Exception
     */

    public ArrayList getVideoList()throws Exception {
        try {
            return VideoRepository.getRepository().getVideoList();
            } catch (Exception e) {throw new Exception(e.getMessage());}

        }//getVideoList

    public File getFileObject(String fileName) throws Exception{

        try{
            return VideoRepository.getRepository().getVideo(fileName);
            } catch (Exception e) {throw new Exception("file error");}

        }//getFileObject







}//VideoService