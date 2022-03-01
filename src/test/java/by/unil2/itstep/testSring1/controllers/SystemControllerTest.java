package by.unil2.itstep.testSring1.controllers;

import by.unil2.itstep.testSring1.services.ClientService;
import by.unil2.itstep.testSring1.services.SystemService;
import by.unil2.itstep.testSring1.utilits.CalcOptions;
import by.unil2.itstep.testSring1.utilits.loger.MyLogger;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class SystemControllerTest {


    /*
    private final MyLogger myLog;
    private final ClientService clientService;
    private final SystemService sysService;
    */


    @Mock
    private SystemService sysService;

    @Mock
    private ClientService clientService;

    @Mock
    private MyLogger myLog;


    @InjectMocks
    private SystemController sysController;


    @Before
    public void init(){
        MockitoAnnotations.openMocks(this);
        sysController = new SystemController(clientService,sysService,myLog);
        }

   /*

    @GetMapping("/server/status")
    public ResponseEntity<?> getServerStatus(HttpServletResponse response) {

        //object of response
        try {
            ServerStatus answerStatus = sysService.getServerStatus();//new ServerStatus();
            myLog.info("client ask ServerStatus");
            return ResponseEntity.ok().body(answerStatus);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Status not reading.");
        }


        }//getServerStatus

    */



    @Test
    void getServerStatus() {





    }

    @Test
    void postApiReset() {
    }




}