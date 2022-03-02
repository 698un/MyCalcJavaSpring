package by.unil2.itstep.testSring1.services;

import by.unil2.itstep.testSring1.dao.repository.ClientRepository;
import by.unil2.itstep.testSring1.utilits.CalcOptions;
import by.unil2.itstep.testSring1.utilits.loger.MyLogger;
import org.springframework.stereotype.Service;


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

        }//userExit








}//SystemService
