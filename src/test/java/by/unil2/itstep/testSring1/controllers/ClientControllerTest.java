package by.unil2.itstep.testSring1.controllers;

import by.unil2.itstep.testSring1.controllers.webpages.ClientPathController;
import by.unil2.itstep.testSring1.services.ClientService;
import by.unil2.itstep.testSring1.utilits.CalcOptions;
import by.unil2.itstep.testSring1.utilits.loger.MyLogger;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletResponse;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@SpringBootTest
class ClientControllerTest {


    @Mock
    private CalcOptions calcOpt;

    @Mock
    private ClientService clientService;

    @Mock
    private MyLogger myLog;


    @InjectMocks
    private ClientController clientController;


    @Before
    public void init(){
        MockitoAnnotations.openMocks(this);
        clientController = new ClientController(clientService,calcOpt,myLog);

    }


    /**
     * Test Generate new ClientKey and sending this clientKey to browser
     */
    @Test
    void postClientKey() {

        //dummy clientKey for test
        when(clientService.getNewClientKey()).thenReturn("testClientKey");

        //Build response for test
        ResponseEntity<String> respStr =  clientController.postClientKey();

        //extract Headers in String representation
        String cookieFromResponse = respStr.getHeaders().toString();

        //example header
        //[Set-Cookie:"ClientKey=testClientKey"]

        //defined position clientKey in header
        int index = cookieFromResponse.indexOf("testClientKey");
        assertNotEquals(index,-1);
        }


    /**
     * test verify admin enter with VALID password
     */
    @Test
    void postRootKey_validPassword() {

        String rootPassword = "masterkey";

        try {
            //dummy clientKey for test
            when(clientService.getNewRootKey(rootPassword)).thenReturn("testRootKey");

            //Build response for test
            ResponseEntity<?> respStr =  clientController.postRootKey("masterkey");

            //extract Headers in String representation
            String cookieFromResponse = respStr.getHeaders().toString();

            System.out.println(cookieFromResponse);

            //example header
            //[Set-Cookie:"ClientKey=testClientKey"]

            //defined position clientKey in header
            int index = cookieFromResponse.indexOf("testRootKey");
            assertNotEquals(index,-1);

            } catch(Exception e) {}


    }//postRootKey


    /**
     * Test  Wron Root Password
     */
    @Test
    void postRootKey_invalidPassword() {

        String rootPassword = "wrongPassword";

        try {
            //dummy clientKey for test
            when(clientService.getNewRootKey(rootPassword)).thenReturn("testRootKey");

            //Build response for test
            ResponseEntity<?> respStr =  clientController.postRootKey("masterkey");

            //extract Headers in String representation
            String cookieFromResponse = respStr.getHeaders().toString();

            System.out.println(cookieFromResponse);

            //example header
            //[Set-Cookie:"ClientKey=testClientKey"]

            //defined position clientKey in header
            int index = cookieFromResponse.indexOf("testRootKey");
            assertEquals(index,-1);

        } catch(Exception e) {}


    }//postRootKey


    /**
     * This test not actual because apiExit is void method

    @Test
    void apiExit() {

    }
    */
}