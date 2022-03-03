package by.unil2.itstep.testSring1.services;

import by.unil2.itstep.testSring1.controllers.webentity.NewTask;
import by.unil2.itstep.testSring1.dao.model.PixelLine;
import by.unil2.itstep.testSring1.dao.repository.ClientRepository;
import by.unil2.itstep.testSring1.dao.repository.ImageRepository;
import by.unil2.itstep.testSring1.utilits.CalcOptions;
import by.unil2.itstep.testSring1.utilits.loger.MyLogger;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.boot.test.context.SpringBootTest;
import static org.mockito.Mockito.when;

@SpringBootTest
class TaskServiceTest {


    @Mock
    private MyLogger myLog;
    @Mock
    private CalcOptions calcOpt;
    @Mock
    private ClientRepository clientRep;
    @Mock
    private ImageRepository imgRep;

    @InjectMocks
    private TaskService taskService;

    @Before
    public void init(){
        MockitoAnnotations.openMocks(this);
        taskService = new TaskService(calcOpt,myLog,clientRep,imgRep);
        }//init


    @Test
    void reset() {
       when(calcOpt.getInt("lineLifeTime")).thenReturn(10);
       TaskService taskService = new TaskService(calcOpt,myLog,clientRep,imgRep);

       assertEquals(10,taskService.getLineLifeTime());
       }



    @Test
    void getSceneKey() {
        when(imgRep.getSceneKey()).thenReturn("1234567890");
        TaskService taskService = new TaskService(calcOpt,myLog,clientRep,imgRep);

        assertEquals("1234567890",taskService.getSceneKey());

        }


    @Test
    void getNextTask() throws Exception {

        //entity for controll
        String clientKey = "1234567890";
        PixelLine getPixelLine = new PixelLine(1,1,clientKey);

        //innner behavior
        when(clientRep.inRepository(clientKey)).thenReturn(true);
        when(imgRep.getEmptyPixelLine(clientKey)).thenReturn(getPixelLine);
        when(imgRep.getSceneKey()).thenReturn("0987654321");


        TaskService taskService = new TaskService(calcOpt,myLog,clientRep,imgRep);
        NewTask testTask = taskService.getNextTask(clientKey);

        assertEquals(testTask.getFrame(),1);
        assertEquals(testTask.getLine(),1);

        }

    @Test
    void postCompletteTask() throws Exception {

        int imgWidth = 640;

        //entity for controll
        String clientKey = "1234567890";
        short[] pixelArray = new short[imgWidth*3];
        for (int i=0;i<imgWidth*3;i++) pixelArray[i] = 1;

        PixelLine postPixelLine = new PixelLine(1,1,clientKey);
        postPixelLine.setByteArray(pixelArray);

        //innner behavior
        when(clientRep.inRepository(clientKey)).thenReturn(true);
        when(imgRep.getSceneKey()).thenReturn("0987654321");
        when(imgRep.insertComplettePixelLine(postPixelLine)).thenReturn(100L);

        TaskService taskService = new TaskService(calcOpt,myLog,clientRep,imgRep);
        Long duration = taskService.postCompletteTask(postPixelLine);

        assertEquals(duration,100L);

        }//postCompletteTask _ TEST
}