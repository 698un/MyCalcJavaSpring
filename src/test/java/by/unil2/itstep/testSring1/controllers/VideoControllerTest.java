package by.unil2.itstep.testSring1.controllers;


import by.unil2.itstep.testSring1.services.ClientService;
import by.unil2.itstep.testSring1.services.VideoService;
import by.unil2.itstep.testSring1.utilits.CalcOptions;
import by.unil2.itstep.testSring1.utilits.loger.MyLogger;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import java.util.ArrayList;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class VideoControllerTest {


    /*
    private final VideoService videoService;
    private final MyLogger myLog;
    private final ClientService clientService;
    private final CalcOptions calcOpt;
     */



    @Mock
    private VideoService videoService;
    @Mock
    private ClientService clientService;
    @Mock
    private MyLogger myLog;
    @Mock
    private CalcOptions calcOpt;


    @InjectMocks
    private VideoController videoController;

    @Before
    public void init(){
        MockitoAnnotations.openMocks(this);

        videoController = new VideoController(videoService,myLog,clientService,calcOpt);
        }//init




    @Test
    void postCreateVideo() throws Exception {

        String fileName = "videofile.mp4";
        String rootKey = "1234567890";
        ResponseEntity<?> response;

        //behavior
        when(clientService.isRootKey(rootKey)).thenReturn(true);

        response = videoController.postCreateVideo(fileName,rootKey);

        String bodyStr = response.getBody().toString();
        System.out.println(bodyStr);
        int index = bodyStr.indexOf("is launch");
        assertNotEquals(index,-1);
        }





    @Test
    void getVideoList() throws Exception {

        ResponseEntity<?> response;
        String rootKey = "1234567890";

        //create test list of files
        ArrayList<String> videoFileList = new ArrayList();
        for(int i=0;i<5;i++) videoFileList.add("file_"+String.valueOf(i)+".mp4");

        //behavior
        when(videoService.getVideoList()).thenReturn(videoFileList);
        when(clientService.isRootKey(rootKey)).thenReturn(true);


        response = videoController.getVideoList(rootKey);

        String bodyString = response.getBody().toString();
        //System.out.println(bodyString);

        int index = bodyString.indexOf("file_1.mp4");

        assertNotEquals(bodyString.indexOf("file_0.mp4"),-1);
        assertNotEquals(bodyString.indexOf("file_1.mp4"),-1);
        assertNotEquals(bodyString.indexOf("file_2.mp4"),-1);
        assertNotEquals(bodyString.indexOf("file_3.mp4"),-1);
        assertNotEquals(bodyString.indexOf("file_4.mp4"),-1);

        }




}