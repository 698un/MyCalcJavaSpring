package by.unil2.itstep.testSring1.services;

import by.unil2.itstep.testSring1.controllers.webentity.ServerStatus;
import by.unil2.itstep.testSring1.dao.repository.ImageRepository;
import by.unil2.itstep.testSring1.dao.repository.VideoRepository;
import by.unil2.itstep.testSring1.utilits.CalcOptions;
import by.unil2.itstep.testSring1.utilits.loger.MyLogger;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@SpringBootTest
class SystemServiceTest {




    @Mock
    private MyLogger myLog;
    @Mock
    private CalcOptions calcOpt;
    @Mock
    private ClientService clientService;
    @Mock
    private ImageRepository imgRep;
    @Mock
    private VideoRepository videoRep;



    @InjectMocks
    private SystemService sysService;

    @Before
    public void init(){
        MockitoAnnotations.openMocks(this);
        sysService = new SystemService(clientService,calcOpt,myLog,
                                       imgRep,videoRep);
        }//init


    @Test
    void getPathApp() {

        when(calcOpt.getApplicationPath()).thenReturn(System.getProperty("user.dir"));

        SystemService sysService = new SystemService(clientService,calcOpt,myLog,
                                                     imgRep,videoRep);

        String getAppPath = sysService.getPathApp();
        assertEquals(getAppPath,System.getProperty("user.dir"));



        }

    @Test
    void resetCalculation() {


        SystemService sysService = new SystemService(clientService,calcOpt,myLog,
                                      imgRep,videoRep);


        try{

            sysService.resetCalculation();

            verify(calcOpt).reLoad();
            verify(imgRep).reset();
            verify(videoRep).reset();

            //if not error then assert
            assertEquals(1,1);
            } catch (Exception e){assertEquals(1,2);}


        }

    @Test
    void getServerStatus() {

        int clientCount = 10;
        int fps = 25;
        int imgWidth = 640;
        int imgHeight = 360;
        int imgAntialiasing = 5;

        try {
            when(clientService.getClientCount()).thenReturn(clientCount);
            when(calcOpt.getInt("fps")).thenReturn(fps);
            when(calcOpt.getInt("imageWidth")).thenReturn(imgWidth);
            when(calcOpt.getInt("imageHeight")).thenReturn(imgHeight);
            when(calcOpt.getInt("antialiasing")).thenReturn(imgAntialiasing);

            SystemService sysService = new SystemService(clientService,calcOpt,myLog,
                                                         imgRep,videoRep);

            ServerStatus ss = new ServerStatus();

            ss = sysService.getServerStatus();

            verify(calcOpt).getInt("fps");
            verify(calcOpt).getInt("imageWidth");
            verify(calcOpt).getInt("imageHeight");
            verify(calcOpt).getInt("antialiasing");
            verify(clientService).getClientCount();

            assertEquals(ss.getClientCount(),    clientCount);
            assertEquals(ss.getImgHeight(),      imgHeight);
            assertEquals(ss.getImgWidth(),       imgWidth);
            assertEquals(ss.getFps(),            fps);
            assertEquals(ss.getImgAntialiasing(),imgAntialiasing);



            } catch (Exception e) {assertEquals(1,2);}


    }


}