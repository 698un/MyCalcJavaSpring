package by.unil2.itstep.testSring1.services;

import by.unil2.itstep.testSring1.dao.model.Client;
import by.unil2.itstep.testSring1.dao.repository.ClientRepositoryHM;
import by.unil2.itstep.testSring1.models.Product;
import by.unil2.itstep.testSring1.utilits.CalcOptions;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
public class ClientService {


    //method return new clientKey for new client
    public String getNewClientKey(){

        Client newClient = new Client();

        String newClientKey = ClientRepositoryHM.getRepository().newClient();
        return newClientKey;
        }//getNewClientKey


    public int getClientCount()throws Exception{

        try{
            return ClientRepositoryHM.getRepository().getClientCount();
            } catch (Exception e){ throw new Exception(e.getMessage());}
        }//getClientCount






}//SystemService
