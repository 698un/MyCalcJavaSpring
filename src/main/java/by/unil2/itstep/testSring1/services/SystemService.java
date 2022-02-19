package by.unil2.itstep.testSring1.services;

import by.unil2.itstep.testSring1.controllers.webentity.ServerStatus;
import by.unil2.itstep.testSring1.dao.repository.ClientRepository;
import by.unil2.itstep.testSring1.exceptions.AccessException;
import by.unil2.itstep.testSring1.models.Product;
import by.unil2.itstep.testSring1.utilits.CalcOptions;
import by.unil2.itstep.testSring1.utilits.loger.MyLogger;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SystemService {

    private final CalcOptions calcOpt;
    private final MyLogger myLog;
    private final ClientService clientService;


    //constructor
    public SystemService(ClientService inpClientService,
                         CalcOptions inpCalcOptions,
                         MyLogger inpMyLogger){
        this.clientService = inpClientService;
        this.calcOpt = inpCalcOptions;
        this.myLog = inpMyLogger;
        }//constructor


    //method return appPath
    public String getPathApp(){
        return calcOpt.getStr("applicationPath");
        }//getAppPath



    public ServerStatus getServerStatus() throws Exception{

        ServerStatus answerStatus = new ServerStatus();
        //object of response
        try {

            answerStatus.setClientCount(clientService.getClientCount());
            answerStatus.setFps(              calcOpt.getInt("fps"));
            answerStatus.setImgWidth(         calcOpt.getInt("imageWidth"));
            answerStatus.setImgHeight(        calcOpt.getInt("imageHeight"));
            answerStatus.setImgAntialiasing(  calcOpt.getInt("antialiasing"));

            } catch (Exception e) {
                myLog.error("Server status not reading. "+e.getMessage());
                throw new Exception(e.getMessage());
                }

        return answerStatus;
        }//getAppPath





}//SystemService
