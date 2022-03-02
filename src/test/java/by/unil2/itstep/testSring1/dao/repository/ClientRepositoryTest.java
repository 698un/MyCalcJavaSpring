package by.unil2.itstep.testSring1.dao.repository;

import by.unil2.itstep.testSring1.utilits.CalcOptions;
import by.unil2.itstep.testSring1.utilits.loger.MyLogger;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.Random;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;



import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ClientRepositoryTest {

    @Mock
    private MyLogger myLog;
    @Mock
    private CalcOptions calcOpt;


    @InjectMocks
    private ClientRepository clientRepository;

    @Before
    public void init(){
        MockitoAnnotations.openMocks(this);

        clientRepository  = new ClientRepository(myLog,calcOpt);
        }//init


    @Test
    void deleteClientByKey() throws Exception {


        when(calcOpt.getRandomKey(10)).thenReturn(getRandomKey(10));
        when(calcOpt.getInt("clientClearTime")).thenReturn(20000);
        when(calcOpt.getInt("clientLifeTime")).thenReturn(10000);


        //verify add new client to repository
        String clientKey = clientRepository.newClient();
        //verify that repository containt one client
        assertEquals(1,clientRepository.getClientCount());

        //verify delete client from repository
        clientRepository.deleteClientByKey(clientKey);
        assertEquals(0,clientRepository.getClientCount());
        }

    @Test
    void updateLastTimeConnect() throws Exception {

        when(calcOpt.getRandomKey(10)).thenReturn(getRandomKey(10));
        when(calcOpt.getInt("clientClearTime")).thenReturn(200);
        when(calcOpt.getInt("clientLifeTime")).thenReturn(100);


        //verify add new client to repository
        String clientKey = clientRepository.newClient();
        //verify that repository containt one client
        assertEquals(1,clientRepository.getClientCount());

        //update lastTimeConnect

        Thread.sleep(50);
        //verify delete client from repository
        clientRepository.updateLastTimeConnect(clientKey);

        Thread.sleep(60);

        assertEquals(1,clientRepository.getClientCount());

        }


    @Test
    void newClient() {
        when(calcOpt.getRandomKey(10)).thenReturn(getRandomKey(10));
        when(calcOpt.getInt("clientClearTime")).thenReturn(20000);
        when(calcOpt.getInt("clientLifeTime")).thenReturn(10000);


        //verify add new client to repository
        String clientKey = clientRepository.newClient();

        //verify that repository containt one client
        assertEquals(true,clientRepository.inRepository(clientKey));
        }

    @Test
    void getClientCount() throws Exception {
        String clientKey;
        when(calcOpt.getRandomKey(10)).thenReturn(getRandomKey(10));
        when(calcOpt.getInt("clientClearTime")).thenReturn(20000);
        when(calcOpt.getInt("clientLifeTime")).thenReturn(10000);


        //verify add new client to repository
        clientKey = clientRepository.newClient();
        System.out.println(clientKey);
        assertEquals(1,clientRepository.getClientCount());


        //delete one
        clientRepository.deleteClientByKey(clientKey);
        assertEquals(0,clientRepository.getClientCount());


        }

    @Test
    void inRepository() {
        String clientKey;
        when(calcOpt.getRandomKey(10)).thenReturn(getRandomKey(10));
        when(calcOpt.getInt("clientClearTime")).thenReturn(20000);
        when(calcOpt.getInt("clientLifeTime")).thenReturn(10000);


        //verify add new client to repository
        clientKey = clientRepository.newClient();
        assertEquals(true,clientRepository.inRepository(clientKey));

        assertEquals(false,clientRepository.inRepository("randomkey"));

        }





    // ADD METHOD overide calcOptions.getRandomKey

    public String getRandomKey(int lengthOfKey){

        if (lengthOfKey<3) lengthOfKey = 3;
        Random rnd = new Random();
        StringBuilder sb1=new StringBuilder("");
        for (int i=0;i<lengthOfKey;i++) sb1.append((char)('a'+rnd.nextInt('z'-'a')));
        return sb1.toString();
        }//getRandomKey
}