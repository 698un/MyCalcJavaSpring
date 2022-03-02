package by.unil2.itstep.testSring1.dao.repository;

import by.unil2.itstep.testSring1.utilits.CalcOptions;
import by.unil2.itstep.testSring1.utilits.loger.MyLogger;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import java.io.File;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class VideoRepositoryTest {





    @Mock
    private MyLogger myLog;
    @Mock
    private CalcOptions calcOpt;


    @InjectMocks
    private VideoRepository videoRepository;

    String appPathTemp;

    @Before
    public void init(){


        MockitoAnnotations.openMocks(this);
        videoRepository  = new VideoRepository(calcOpt);

        }//init

    @After
    public void theEnd(){


    }




    @Test
    void reset() throws Exception {

        //temporary folder for testing
        when(calcOpt.getStr("videoResultatFolder")).thenReturn("temp");

        //projectFolder
        when(calcOpt.getApplicationPath()).thenReturn(System.getProperty("user.dir"));

        String fullTempPath = calcOpt.getApplicationPath()+
                              File.separator+
                              "temp"+File.separator;

        //deleting old files in TEMP Path
        try {
            deleteAllFilesInFolder(fullTempPath);
            }catch (Exception e) {}

        VideoRepository videoRep = new VideoRepository(calcOpt);

        //verify videoMark in fileSystem
        File fileMark = new File(fullTempPath+MyVideoSave.completteFileName);
        assertEquals(true,fileMark.exists());


        }

    @Test
    void fileIsExist() {

        //temporary folder for testing
        when(calcOpt.getStr("videoResultatFolder")).thenReturn("temp");

        //projectFolder
        when(calcOpt.getApplicationPath()).thenReturn(System.getProperty("user.dir"));

        String fullTempPath = calcOpt.getApplicationPath()+
                File.separator+
                "temp"+File.separator;

        //deleting old files in TEMP Path
        try {
            deleteAllFilesInFolder(fullTempPath);
        }catch (Exception e) {}

        VideoRepository videoRep = new VideoRepository(calcOpt);

        Boolean fileIsExist = videoRep.fileIsExist(MyVideoSave.completteFileName);
        assertEquals(fileIsExist,true);

        }//fileIsExist_test



    @Test
    void getVideoList() throws IOException {

        //temporary folder for testing
        when(calcOpt.getStr("videoResultatFolder")).thenReturn("temp");

        //projectFolder
        when(calcOpt.getApplicationPath()).thenReturn(System.getProperty("user.dir"));

        String fullTempPath = calcOpt.getApplicationPath()+
                File.separator+
                "temp"+File.separator;

        //deleting old files in TEMP Path
        try {
            deleteAllFilesInFolder(fullTempPath);
            }catch (Exception e) {}

        //create several dummyVideoFiles
        new File(fullTempPath+File.separator+"video1.mp4").createNewFile();
        new File(fullTempPath+File.separator+"video2.mp4").createNewFile();
        new File(fullTempPath+File.separator+"video3.mp4").createNewFile();

        //createTestObject
        VideoRepository videoRep = new VideoRepository(calcOpt);

        //get list of videoFiles in String representation
        String videoListStr = videoRep.getVideoList().toString();

        assertNotEquals(videoListStr.indexOf("video1.mp4"),-1);
        assertNotEquals(videoListStr.indexOf("video2.mp4"),-1);
        assertNotEquals(videoListStr.indexOf("video3.mp4"),-1);


        }

    @Test
    void getVideoFullPath() throws Exception {

        //temporary folder for testing
        when(calcOpt.getStr("videoResultatFolder")).thenReturn("temp");

        //projectFolder
        when(calcOpt.getApplicationPath()).thenReturn(System.getProperty("user.dir"));

        String fullTempPath = calcOpt.getApplicationPath()+
                File.separator+
                "temp"+File.separator;

        //deleting old files in TEMP Path
        try {
            deleteAllFilesInFolder(fullTempPath);
        }catch (Exception e) {}

        //create several dummyVideoFiles
        new File(fullTempPath+File.separator+"video1.mp4").createNewFile();
        new File(fullTempPath+File.separator+"video2.mp4").createNewFile();
        new File(fullTempPath+File.separator+"video3.mp4").createNewFile();

        //createTestObject
        VideoRepository videoRep = new VideoRepository(calcOpt);

        //get list of videoFiles in String representation
        String videoListStr = videoRep.getVideoList().toString();

        String fullVideoPath1 = videoRep.getVideoFullPath("video1.mp4");
        String fullVideoPath2 = videoRep.getVideoFullPath("video2.mp4");
        String fullVideoPath3 = videoRep.getVideoFullPath("video3.mp4");

        assertNotEquals(fullVideoPath1,calcOpt.getApplicationPath()+File.separator+"video1.mp4");
        assertNotEquals(fullVideoPath2,calcOpt.getApplicationPath()+File.separator+"video2.mp4");
        assertNotEquals(fullVideoPath3,calcOpt.getApplicationPath()+File.separator+"video3.mp4");

        }





    /**
     * This method deleting all files from currentFolder
     * @throws Exception
     */
    private void deleteAllFilesInFolder(String currentFolder) throws Exception{

        String folderPath = currentFolder;

        try {
            //deleting in cicle all files in directory
            for (File myFile : new File(folderPath).listFiles())
                if (myFile.isFile()) myFile.delete();
        } catch (Exception e) {throw new Exception(e.getMessage());}

        }//deleteAllImages



}