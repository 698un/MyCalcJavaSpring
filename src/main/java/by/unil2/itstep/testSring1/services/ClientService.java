package by.unil2.itstep.testSring1.services;

import by.unil2.itstep.testSring1.dao.model.Client;
import by.unil2.itstep.testSring1.dao.repository.ClientRepository;
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
    private final ClientRepository clientRep;

    //constructor
    public ClientService(ClientRepository inpClientRepository,
                         CalcOptions inpCalcOptions,
                         MyLogger inpMyLogger){
        this.clientRep = inpClientRepository;
        this.calcOpt = inpCalcOptions;
        this.myLog = inpMyLogger;
        }//constructor

    //method return new clientKey for new client
    public String getNewClientKey(){
        String newClientKey = clientRep.newClient();
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

    /**
     * This method verify rootKey
     * @param inpRootKey
     * @return  true or false
     */
    public boolean isRootKey(String inpRootKey){
        if (calcOpt.getCurrentRootKey().equals(inpRootKey)) return true;
        return false;
        }


    public int getClientCount()throws Exception{

        try{
            return clientRep.getClientCount();
            } catch (Exception e){ throw new Exception(e.getMessage());}
        }//getClientCount

    /**
     * this method verify clientKey
     * @param clientKey
     * @return true if clientKey is correct
     * @return false if clientKey is NOT correct
     */
    public Boolean clientIsRegistration(String clientKey){
        if (clientRep.inRepository(clientKey)) return true;
        return false;
        }//clientIsRegistration


    /**
     * this method verify rootKey
     * @param rootKey
     * @return true if rootKey is correct
     * @return false if rootKey is NOT correct
     */
    public Boolean rootIsRegistration(String rootKey){
        if (calcOpt.getCurrentRootKey().equals(rootKey)) return true;
        return false;
        }//clientIsRegistration


    public void userExit(String clientKey){

        try {
            clientRep.deleteClientByKey(clientKey);
            myLog.info("Client "+clientKey+" leave of calculation");
            } catch (Exception e) {}

        try {
            if (calcOpt.getCurrentRootKey().equals(clientKey)) {
                    calcOpt.getNewRootKey(calcOpt.getStr("rootPassword"));// Change rootKey to random
                    myLog.info("root is exit");
                    }
            } catch (Exception e){}







        }








}//SystemService
