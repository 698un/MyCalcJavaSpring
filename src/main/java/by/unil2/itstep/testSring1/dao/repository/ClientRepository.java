package by.unil2.itstep.testSring1.dao.repository;



import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import by.unil2.itstep.testSring1.dao.model.Client;
import by.unil2.itstep.testSring1.utilits.CalcOptions;
import by.unil2.itstep.testSring1.utilits.loger.MyLogger;
import org.springframework.stereotype.Component;

@Component
public class ClientRepository{

    //Injected Components
    private MyLogger myLog;
    private CalcOptions calcOpt;

    //fields of object
    private long timeDeletingOldClients = 0L;           //time of deleting deprecated Clients
    private ConcurrentHashMap<String,Client> clientList;//storage of Clients


    //constructor
    public ClientRepository(MyLogger inpMyLogger,
                            CalcOptions inpCalcOptions ){
        this.myLog   = inpMyLogger;   //injected loger
        this.calcOpt = inpCalcOptions;//injected CalcOptions
        clientList =  new ConcurrentHashMap<String,Client>();//create storage
        }

    public void reset(){


    }


    /**
     * Deleting Client from storage by it ClientKey
     * @param inpKey
     * @throws Exception
     */
    public void deleteClientByKey(String inpKey)throws Exception{

        myLog.info("Client "+inpKey+" deleting from calculation");
        if (clientList.get(inpKey)==null)  {
            myLog.warn("Client "+inpKey+" not CONTAINT");
            throw new Exception("UNKNOW CLIENT");
            }

        clientList.remove(inpKey);//delete from storage
        }//deleteClient by key

    /**
     * This method update timeConnect of client (mark as active)
     * @param clientKey
     */
    public void updateLastTimeConnect(String clientKey){

        Client client = clientList.get(clientKey);
        if (client ==null) return;//exit from method if Client by clientKey not found!

        client.updateLastTime();
        clientList.replace(clientKey,client);//update client in HashMap
        }//updateLastTimeConnect


    /**
     * This method add New Cliient to Storage with net ClientKey
     * @return
     */
    public String newClient(){
        Client newClient=new Client();                        //cerateNew Client
        newClient.setKey(calcOpt.getRandomKey(10));// set randomKey to client
        clientList.put(newClient.getKey(),newClient);         //add new client to storage
        return newClient.getKey();
        }//new Client



    public int getClientCount() throws Exception{

        //deleting old clients
        try {
            if (System.currentTimeMillis() > this.timeDeletingOldClients) {
                this.clearOldClients();//deleting deprecate clientKeys
                //update time  for next deleting not active clients
                this.timeDeletingOldClients = System.currentTimeMillis() + calcOpt.getInt("clientClearTime");
                }


            return clientList.size();

        } catch (Exception e){throw new Exception(e.getMessage());}


    }//getClientCount

    /**
     * this method clear not active clients
     * every 10 second
     * while clients request ServerStatus
     *
     */

    private void clearOldClients() throws Exception{

        try {
            long now = System.currentTimeMillis();//get current timeInMillis

            myLog.debug("clear old Clients...");

            List<String> keys = new ArrayList<String>(clientList.keySet());
            for (int i = 0; i < keys.size(); i++) {
                String key = keys.get(i);
                Client client1 = clientList.get(key);
                if (client1 != null) {
                    if (client1.getLastTimeConnect() + calcOpt.getInt("clientLifeTime") < now) clientList.remove(key);
                }//if client not null


            }//next i


        } catch(Exception e){
            myLog.error("error clientClear");
            throw new Exception("ERROR in ClearClient");
        }


    }//Clear old Clients

    /**
     * This method verify has clientkey in repository
     * @param inpKey
     * @return
     */
    public boolean inRepository(String inpKey){

        if (clientList.get(inpKey)==null)  return false;
        return true;

    }//inRepository





}//clientRepository


