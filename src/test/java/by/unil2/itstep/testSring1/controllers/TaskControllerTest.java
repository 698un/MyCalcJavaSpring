package by.unil2.itstep.testSring1.controllers;

import by.unil2.itstep.testSring1.controllers.webentity.NewTask;
import by.unil2.itstep.testSring1.dao.model.PixelLine;
import by.unil2.itstep.testSring1.services.ClientService;
import by.unil2.itstep.testSring1.services.SystemService;
import by.unil2.itstep.testSring1.services.TaskService;
import by.unil2.itstep.testSring1.utilits.CalcOptions;
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
public class TaskControllerTest {



    @Mock
    private SystemService systemService;
    @Mock
    private ClientService clientService;
    @Mock
    private MyLogger myLog;
    @Mock
    private TaskService taskService;
    @Mock
    private CalcOptions calcOpt;


    @InjectMocks
    private TaskController taskController;


    @Before
    public void init(){
        MockitoAnnotations.openMocks(this);

        taskController = new TaskController(taskService,clientService,
                                            systemService,myLog,calcOpt);
        }//init


    /**
     * Verify getNewTask  for valid CLIENTKEY
     * @throws Exception
     */
    @Test
    void getNewTask_validKey() throws Exception {

        String clientKey = "1234567890";
        //String invalidClientKey = "INVALIDKEY";
        ResponseEntity<?> resp;

        //create dummy task for client
        PixelLine pixLine = new PixelLine(1,1,clientKey);
        NewTask taskForClient = new NewTask(pixLine);

        //behavior if VALID CLIENTKEY
        when(clientService.clientIsRegistration(clientKey)).thenReturn(true);
        when(  taskService.getNextTask(clientKey)).thenReturn(taskForClient);


        resp = taskController.getNewTask(clientKey);

        String bodyString = resp.getBody().toString();
        int index = bodyString.indexOf("NewTask");
        assertNotEquals(index,-1);


        }//getNewTask _validClientKey

    /**
     * Verify getNewTask  for INvalid CLIENTKEY
     * @throws Exception
     */
    @Test
    void getNewTask_inValidKey() throws Exception {


        String invalidClientKey = "INVALIDKEY";
        ResponseEntity<?> resp;

        //behavior if INVALID CLIENTKEY
        when(clientService.clientIsRegistration(invalidClientKey)).thenReturn(false);

        resp = taskController.getNewTask(invalidClientKey);

        String bodyString = resp.getBody().toString();
        //System.out.println(bodyString);
        int index = bodyString.indexOf("ErrorMessage");
        assertNotEquals(index,-1);

        }//getNewTask _invalidClientKey




    @Test
    void postResultat() {

        String clientKey = "1234567890";
        String sceneKey = "12345678901234567890";
        int frameNum = 1;
        int lineNum = 1;
        String pixelArrayStr;
        int width = 640;

        ResponseEntity<?> response;


        //create Dummy PixelArrayString with length by width
        StringBuffer sb1 = new StringBuffer("");
        for (int i=0;i<width*3*3;i++) sb1.append("0");
        pixelArrayStr = sb1.toString();

        //behavior
        when(taskService.getSceneKey()).thenReturn(sceneKey);
        when(clientService.clientIsRegistration(clientKey)).thenReturn(true);

        response = taskController.postResultat(sceneKey,frameNum,lineNum,pixelArrayStr,clientKey);

        String bodyStr = response.getBody().toString();

        //verify return object of ResultatReport
        int index = bodyStr.indexOf("ResultatReport");

        assertNotEquals(index,-1);



        }//postResultat


    }
