package by.unil2.itstep.testSring1.services;

import by.unil2.itstep.testSring1.dao.model.Client;
import by.unil2.itstep.testSring1.dao.repository.ClientRepositoryHM;
import by.unil2.itstep.testSring1.exceptions.AccessException;
import by.unil2.itstep.testSring1.models.Product;
import by.unil2.itstep.testSring1.utilits.CalcOptions;
import by.unil2.itstep.testSring1.utilits.loger.MyLogger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
public class ClientService {

    private final CalcOptions calcOpt;
    private final MyLogger myLog;
    private final ClientService clientService;


    //constructor
    public ClientService(ClientService inpClientService,
                         CalcOptions inpCalcOptions,
                         MyLogger inpMyLogger){
        this.clientService = inpClientService;
        this.calcOpt = inpCalcOptions;
        this.myLog = inpMyLogger;
        }//constructor




    //method return new clientKey for new client
    public String getNewClientKey(){

        String newClientKey = ClientRepositoryHM.getRepository().newClient();
        return newClientKey;
        }//getNewClientKey

    //method return new RootKey for user of ROOT
    public String getNewRootKey(String adminPassword) throws Exception {


        //get root key from config
        try {
            String newRootKey = calcOpt.getNewRootKey(adminPassword);
            return newRootKey;
        } catch (Exception e) {
            throw e;
            }
    }//getNewRootKey





    public int getClientCount()throws Exception{

        try{
            return ClientRepositoryHM.getRepository().getClientCount();
            } catch (Exception e){ throw new Exception(e.getMessage());}
        }//getClientCount






}//SystemService
