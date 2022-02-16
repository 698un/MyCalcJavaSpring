package by.unil2.itstep.testSring1.dao.repository;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import by.unil2.itstep.testSring1.dao.model.Client;
//import by.itstep.mySite.utilits.CalcOptions;


public class ClientRepositoryHM implements iClientRepository{

    private long timeDeletingOldClients = 0L;

    private ConcurrentHashMap<String,Client> clientList;

    @Override
    public void deleteClientByKey(String inpKey)throws Exception{

        System.out.println("Deleted :"+inpKey);


        if (clientList.get(inpKey)==null)  {
                            System.out.println("NOT CONTAINT");
                            throw new Exception("UNKNOW CLIENT");
                            }
        clientList.remove(inpKey);
        }


    public void updateLastTimeConnect(String clientKey){

        Client client = clientList.get(clientKey);
        if (client ==null) return;


        client.updateLastTime();
        clientList.replace(clientKey,client);//update client in HashMap
        //System.out.println("update connect for "+clientKey);

        }//updateLastTimeConnect


    @Override
    public String newClient(){
        Client newClient=new Client();
        //System.out.println("newClientKey:"+newClient.getKey());
        clientList.put(newClient.getKey(),newClient);
        return newClient.getKey();
        }//new Client

    @Override
    public int getClientCount() throws Exception{

        try {
            if (System.currentTimeMillis() > this.timeDeletingOldClients) {
                //deleting deprecate clientKeys
                this.clearOldClients();
                //update time  for next deleting not active clients
                this.timeDeletingOldClients = System.currentTimeMillis() + 10000;//CalcOptions.getOptions().getClientClearTime();
                }

            return clientList.size();

            } catch (Exception e){throw new Exception(e.getMessage());}


        }

    /**
     * this method clear not active clients
     * every 10 second
     * while clients request ServerStatus
     *
     */

    private void clearOldClients() throws Exception{

       try {
           long now = System.currentTimeMillis();


           //............
           System.out.println("Clear_OLD_CLIENTS");

           //Client client1;
//           String[] keyList = (String[]) clientList.keySet().toArray();
         //  System.out.println(keyList.length);


           List<String> keys = new ArrayList<String>(clientList.keySet());
           for (int i = 0; i < keys.size(); i++) {
               String key = keys.get(i);
               Client client1 = clientList.get(key);
               if (client1 != null) {
                   if (client1.getLastTimeConnect() + 1000/*CalcOptions.getOptions().getClientLifeTime()*/ < now) clientList.remove(key);
                   }//if client not null


           }//next i


      } catch(Exception e){
           //e.printStackTrace();
           throw new Exception("ERROR in ClearClient");
            }


        }//Clear old Clients

    @Override
    public boolean inRepository(String inpKey){

        if ("0987654321"/*CalcOptions.getOptions().getCurrentRootKey()*/.equals((inpKey))) return true;


        if (clientList.get(inpKey)==null)  return false;
        return true;

        }//inRepository


//==============================SYSTEM METHODS

    //System Methods
    private static ClientRepositoryHM singleRep;


    public static ClientRepositoryHM getRepository(){
        if (singleRep==null) singleRep = new ClientRepositoryHM();
        return singleRep;
        }

    private ClientRepositoryHM(){
        clientList =  new ConcurrentHashMap<String,Client>();


        }



}//clientRepository
