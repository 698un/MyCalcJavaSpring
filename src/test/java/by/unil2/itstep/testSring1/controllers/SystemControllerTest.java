package by.unil2.itstep.testSring1.controllers;

import by.unil2.itstep.testSring1.controllers.webentity.ServerStatus;
import by.unil2.itstep.testSring1.services.ClientService;
import by.unil2.itstep.testSring1.services.SystemService;
import by.unil2.itstep.testSring1.utilits.loger.MyLogger;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class SystemControllerTest {


    @Mock
    private SystemService systemService;

    @Mock
    private ClientService clientService;

    @Mock
    private MyLogger myLog;


    @InjectMocks
    private SystemController sysController;


    @Before
    public void init(){
        MockitoAnnotations.openMocks(this);
        sysController = new SystemController(clientService,systemService,myLog);
        }

    @Test
    void getServerStatus() throws Exception {

        //create DTO dummy object as serverStatus
        ServerStatus srvStatus = new ServerStatus();
        srvStatus.setClientCount(10);
        srvStatus.setImgWidth(640);
        srvStatus.setImgHeight(360);
        srvStatus.setFps(25);
        srvStatus.setImgAntialiasing(5);

        String bodyString = "null";

        when(systemService.getServerStatus()).thenReturn(srvStatus);
        ResponseEntity<?> resp = sysController.getServerStatus();

        bodyString = String.valueOf(resp.getBody().toString());
        System.out.println(bodyString);


        assertNotEquals(bodyString,"null");

        //verify ServerStatus in response
        int index = bodyString.indexOf("ServerStatus");
        assertNotEquals(index,-1);

        }//TEST_CONTROL_SERVER_STATUS


    @Test
    void postApiReset() {

        //ani  rootKey (from cookie)
        String rootKey = "1234567890";
        String invalidRootKey = "INVALIDKEY";
        String bodyString;

        when(clientService.isRootKey(rootKey)).thenReturn(true);
        when(clientService.isRootKey(invalidRootKey)).thenReturn(false);

        //verify VALID KEY
        ResponseEntity<?> resp = sysController.postApiReset(rootKey);
        bodyString = resp.getBody().toString();
        //System.out.println(bodyString);
        assertEquals("reset_is_ok",bodyString);

        //verify invalidKEY
        resp = sysController.postApiReset(invalidRootKey);
        bodyString = resp.getBody().toString();
        //System.out.println(bodyString);
        int index = bodyString.indexOf("ErrorMessage");
        assertNotEquals(index,-1);

        }




}