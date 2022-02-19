package by.unil2.itstep.testSring1.services;


import by.unil2.itstep.testSring1.controllers.webentity.NewTask;
import by.unil2.itstep.testSring1.dao.model.PixelLine;
import by.unil2.itstep.testSring1.dao.repository.ClientRepository;
import by.unil2.itstep.testSring1.dao.repository.ImageRepository;
import by.unil2.itstep.testSring1.exceptions.AccessException;
import by.unil2.itstep.testSring1.exceptions.CalcException;
import by.unil2.itstep.testSring1.utilits.CalcOptions;
import by.unil2.itstep.testSring1.utilits.MyLocker;
import by.unil2.itstep.testSring1.utilits.loger.LogState;
import by.unil2.itstep.testSring1.utilits.loger.MyLogger;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

    private final CalcOptions      calcOpt;
    private final MyLogger         myLog;
    private final ClientRepository clientRep;
    private final ImageRepository  imgRep;


    //constructor
    public TaskService(CalcOptions inpCalcOptions,
                       MyLogger inpMyLogger,
                       ClientRepository inpClientRepository,
                       ImageRepository inpImageRepository){

        this.calcOpt =  inpCalcOptions;
        this.myLog   =  inpMyLogger;
        this.clientRep= inpClientRepository;
        this.imgRep =   inpImageRepository;

        this.reset();
        }

    public void reset(){
        this.lineLifeTime = calcOpt.getInt("lineLifeTime");
        }


    private long lineLifeTime;


    public String getSceneKey(){
        return imgRep.getSceneKey();
        }//get SceneKey

    public NewTask getNextTask(String clientKey)throws AccessException {

        //if clientKey not containt in clientRepository
        //throw AccessException
        if (false==clientRep.inRepository(clientKey)) throw new AccessException("Not actual client");

        myLog.info("Client "+clientKey+" ask new task");

        //update lastTimeConnect for this client
        clientRep.updateLastTimeConnect(clientKey);

        //return emptyPixelLine from imageRepository
        PixelLine emptyPixelLine = imgRep.getEmptyPixelLine(clientKey);

        //create newTask for client;
        NewTask newTask =new  NewTask(emptyPixelLine);
        newTask.setSceneKey(imgRep.getSceneKey());//mark as SceneKey



        return newTask;

        }//get New Task (PixelLine

    /**
     * This method POST complette pixelLine to imageRepository
     * @param complettePixelLine
     * @return duration time from send to client to write resultat
     * @throws Exception
     */
    public Long postCompletteTask(PixelLine complettePixelLine) throws Exception{

        myLog.info("Client "+complettePixelLine.getClientKey()+
                  " send resultat:"+
                  "frame: "+complettePixelLine.getFrameNumber()+
                  "line: "+complettePixelLine.getFrameNumber()
                   );




        try {

            synchronized(MyLocker.getLocker()) {
                return imgRep.insertComplettePixelLine(complettePixelLine);
            }//synchronized

        } catch (Exception e) {
            myLog.warn(e.getMessage());
            throw new CalcException(e.getMessage());
        }
    }//get New Task (PixelLine




}//class TaskService
