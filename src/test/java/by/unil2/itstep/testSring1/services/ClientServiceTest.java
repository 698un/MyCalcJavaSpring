package by.unil2.itstep.testSring1.services;

import by.unil2.itstep.testSring1.dao.repository.ClientRepository;
import by.unil2.itstep.testSring1.utilits.CalcOptions;
import by.unil2.itstep.testSring1.utilits.loger.MyLogger;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;




@SpringBootTest
class ClientServiceTest {


    @Mock
    private MyLogger myLog;
    @Mock
    private CalcOptions calcOpt;
    @Mock
    private ClientRepository clientRep;


    @InjectMocks
    private ClientService clientService;


    @Before
    public void init(){
        MockitoAnnotations.openMocks(this);
        clientService  = new ClientService(clientRep,calcOpt,myLog);
        }//init




    @Test
    void getNewClientKey() {

        when(clientRep.newClient()).thenReturn("1234567890");

        ClientService clientservice =new ClientService(clientRep,calcOpt,myLog);

        String newClientKey = clientService.getNewClientKey();
        assertEquals(newClientKey,"1234567890");
        }


    @Test
    void getNewRootKey() throws Exception {

        String rootPassword = "masterkey";
        String invalidRootPassword = "wrongPassword";

        when(calcOpt.getNewRootKey(rootPassword)).thenReturn("1234567890");
        when(calcOpt.getNewRootKey(invalidRootPassword)).thenReturn("none");


        ClientService clientservice =new ClientService(clientRep,calcOpt,myLog);

        String newRootKey = clientService.getNewRootKey(rootPassword);
        assertEquals(newRootKey,"1234567890");

        String nullRootKey = clientService.getNewRootKey(invalidRootPassword);
        assertEquals(nullRootKey,"none");

        }


    @Test
    void isRootKey() throws Exception {

        String rootPassword = "masterkey";
        String invalidRootPassword = "wrongPassword";

        when(calcOpt.getNewRootKey(rootPassword)).thenReturn("1234567890");
        when(calcOpt.getCurrentRootKey()).thenReturn("1234567890");

        ClientService clientservice =new ClientService(clientRep,calcOpt,myLog);
        String newRootKey = clientService.getNewRootKey(rootPassword);
        assertEquals(true,clientService.isRootKey(newRootKey));
        }//is RootKey


    @Test
    void getClientCount() throws Exception {

        when(clientRep.getClientCount()).thenReturn(10);
        when(clientRep.newClient()).thenReturn("1234567890");

        ClientService clientservice =new ClientService(clientRep,calcOpt,myLog);

        int clientCount = clientService.getClientCount();
        assertEquals(10,clientCount);
        }



    @Test
    void clientIsRegistration() {

        String clientKey = "1234567890";
        String invalidClientKey = "0987654321";

        when(clientRep.inRepository(clientKey)).thenReturn(true);
        when(clientRep.inRepository(invalidClientKey)).thenReturn(false);

        ClientService clientService =new ClientService(clientRep,calcOpt,myLog);

        assertEquals(true,clientService.clientIsRegistration(clientKey));
        assertEquals(false,clientService.clientIsRegistration(invalidClientKey));

        }

}