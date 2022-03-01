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

    /*
    private final CalcOptions calcOpt;
    private final ClientService clientService;
    private final MyLogger myLog;
    */


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


    @Test
    void postClientKey() {


        //String myClientKey;
        //prepare
        String getUrl;// = "{somefile}";
        String myClientKey = "1234567890";

        ResponseEntity<String> respStr;
        HttpServletResponse HttpServlResp = null;

        //respStr =  clientController.postClientKey(HttpServlResp);

      //  respStr =  clientController.postClientKey(HttpServletResponse);// HttpServlResp);




        }







    @Test
    void postRootKey() {
    }

    @Test
    void apiExit() {
    }
}