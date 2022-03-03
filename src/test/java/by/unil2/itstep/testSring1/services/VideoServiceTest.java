package by.unil2.itstep.testSring1.services;

import by.unil2.itstep.testSring1.dao.repository.ClientRepository;
import by.unil2.itstep.testSring1.dao.repository.VideoRepository;
import by.unil2.itstep.testSring1.utilits.CalcOptions;
import by.unil2.itstep.testSring1.utilits.loger.MyLogger;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
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

import java.io.File;
import java.util.ArrayList;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;


@SpringBootTest
class VideoServiceTest {

    @Mock
    private MyLogger myLog;
    @Mock
    private CalcOptions calcOpt;
    @Mock
    private ClientRepository clientRep;
    @Mock
    private VideoRepository videoRep;

    @InjectMocks
    private VideoService videoService;

    @Before
    public void init(){
        MockitoAnnotations.openMocks(this);
        videoService = new VideoService(calcOpt,myLog,clientRep,videoRep);
        }//init



    @Test
    void createMP4() throws Exception {

        String fileName  = "videofile.mp4";
        when(videoRep.createNewVideo(fileName)).thenReturn(true);

        VideoService videoService = new VideoService(calcOpt,myLog,clientRep,videoRep);
        videoService.createMP4(fileName);

        verify(videoRep).createNewVideo(fileName);
        }//createMP4


    @Test
    void fileIsExist() {

        String fileName  = "videofile.mp4";
        when(videoRep.fileIsExist(fileName)).thenReturn(true);

        VideoService videoService = new VideoService(calcOpt,myLog,clientRep,videoRep);
        Boolean getFileIsExist =  videoService.fileIsExist(fileName);

        assertEquals(getFileIsExist,true);

        }


    @Test
    void getVideoList() throws Exception {

        ArrayList<String> fileList = new ArrayList();
        fileList.add("video1.mp4");
        fileList.add("video2.mp4");
        fileList.add("video3.mp4");

        when(videoRep.getVideoList()).thenReturn(fileList);

        VideoService videoService = new VideoService(calcOpt,myLog,clientRep,videoRep);
        String getFileListStr = videoService.getVideoList().toString();

        assertNotEquals(getFileListStr.indexOf("video1.mp4"),-1);
        assertNotEquals(getFileListStr.indexOf("video2.mp4"),-1);
        assertNotEquals(getFileListStr.indexOf("video3.mp4"),-1);

        }




    @Test
    void getVideoFullPath() throws Exception {

        String fileName  = "videofile.mp4";
        String folder = System.getProperty("user.dir");

        when(videoRep.getVideoFullPath(fileName)).thenReturn(folder+ File.separator+fileName);

        VideoService videoService = new VideoService(calcOpt,myLog,clientRep,videoRep);
        String  getFullPath = videoService.getVideoFullPath(fileName);

        assertEquals(getFullPath.indexOf(folder),0);
        assertEquals(getFullPath.indexOf(fileName),folder.length()+1);

        }//getVideoFullPath
}