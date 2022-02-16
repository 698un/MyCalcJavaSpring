package by.unil2.itstep.testSring1.dao.repository;


import by.unil2.itstep.testSring1.dao.model.Client;

public interface iClientRepository {

    void deleteClientByKey(String inpKey) throws Exception;
    void updateLastTimeConnect(String clientKey);
    String newClient();
    int getClientCount() throws Exception;
    //void clearOldClients();
    boolean inRepository(String inpKey);


    }
