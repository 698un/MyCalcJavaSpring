package by.unil2.itstep.testSring1.dao.repository;

import by.unil2.itstep.testSring1.dao.model.PixelLine;
import by.unil2.itstep.testSring1.dao.model.enums.StatusPixelLine;
import by.unil2.itstep.testSring1.utilits.CalcOptions;
import by.unil2.itstep.testSring1.utilits.loger.MyLogger;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@SpringBootTest
class ImageRepositoryTest {



    @Mock
    private MyLogger myLog;
    @Mock
    private CalcOptions calcOpt;


    @InjectMocks
    private ImageRepository imgRepository;

    @Before
    public void init(){


        MockitoAnnotations.openMocks(this);


        when(calcOpt.getInt("imageWidth")).thenReturn(640);
        when(calcOpt.getInt("imageHeight")).thenReturn(480);
        when(calcOpt.getInt("antialiasing" )).thenReturn(5);
        when(calcOpt.getInt("imageInBuffer")).thenReturn(10);
        when(calcOpt.getInt("lineLifeTime")).thenReturn(1000);
        when(calcOpt.getStr("imageResultatFolder")).thenReturn(System.getProperty("user.dir"));


        imgRepository  = new ImageRepository(calcOpt,myLog);

        System.out.println("width="+imgRepository.getImageWidth());
        }//init



    @Test
    void getImageWidth() {

        int width = 640;
        when(calcOpt.getInt("imageWidth")).thenReturn(width);
        when(calcOpt.getInt("imageHeight")).thenReturn(480);
        when(calcOpt.getInt("antialiasing" )).thenReturn(5);
        when(calcOpt.getInt("imageInBuffer")).thenReturn(10);
        when(calcOpt.getInt("lineLifeTime")).thenReturn(1000);
        when(calcOpt.getStr("imageResultatFolder")).thenReturn(System.getProperty("user.dir"));


        imgRepository  = new ImageRepository(calcOpt,myLog);

        int getImageWidth = imgRepository.getImageWidth();
        assertEquals(getImageWidth,width);
        }


    @Test
    void getImageHeight() {
        int height = 360;

        when(calcOpt.getInt("imageWidth")).thenReturn(640);
        when(calcOpt.getInt("imageHeight")).thenReturn(height);
        when(calcOpt.getInt("antialiasing" )).thenReturn(5);
        when(calcOpt.getInt("imageInBuffer")).thenReturn(10);
        when(calcOpt.getInt("lineLifeTime")).thenReturn(1000);
        when(calcOpt.getStr("imageResultatFolder")).thenReturn(System.getProperty("user.dir"));


        imgRepository  = new ImageRepository(calcOpt,myLog);

        int getImageHeight = imgRepository.getImageHeight();
        assertEquals(getImageHeight,height);

        }

    @Test
    void getImageAntialiasing() {
        int antiAliasing = 5;

        when(calcOpt.getInt("imageWidth")).thenReturn(640);
        when(calcOpt.getInt("imageHeight")).thenReturn(360);
        when(calcOpt.getInt("antialiasing" )).thenReturn(antiAliasing);
        when(calcOpt.getInt("imageInBuffer")).thenReturn(10);
        when(calcOpt.getInt("lineLifeTime")).thenReturn(1000);
        when(calcOpt.getStr("imageResultatFolder")).thenReturn(System.getProperty("user.dir"));


        imgRepository  = new ImageRepository(calcOpt,myLog);

        int getAntialiasing = imgRepository.getImageAntialiasing();
        assertEquals(getAntialiasing,antiAliasing);

        }


    @Test
    void getEmptyPixelLine() throws Exception {

        String clientKey = "1234567890";

        when(calcOpt.getInt("imageWidth")).thenReturn(640);
        when(calcOpt.getInt("imageHeight")).thenReturn(360);
        when(calcOpt.getInt("antialiasing" )).thenReturn(5);
        when(calcOpt.getInt("imageInBuffer")).thenReturn(10);
        when(calcOpt.getInt("lineLifeTime")).thenReturn(1000);
        when(calcOpt.getStr("imageResultatFolder")).thenReturn(System.getProperty("user.dir"));

        imgRepository  = new ImageRepository(calcOpt,myLog);

        PixelLine pixLine = imgRepository.getEmptyPixelLine(clientKey);

        assertEquals(StatusPixelLine.PROCESS,pixLine.getStatus());
        }

    @Test
    void insertComplettePixelLine() throws Exception {

        int width = 640;
        String clientKey = "1234567890";

        when(calcOpt.getInt("imageWidth")).thenReturn(width);
        when(calcOpt.getInt("imageHeight")).thenReturn(360);
        when(calcOpt.getInt("antialiasing" )).thenReturn(5);
        when(calcOpt.getInt("imageInBuffer")).thenReturn(10);
        when(calcOpt.getInt("lineLifeTime")).thenReturn(1000);
        when(calcOpt.getStr("imageResultatFolder")).thenReturn(System.getProperty("user.dir"));


        imgRepository  = new ImageRepository(calcOpt,myLog);

        //get pixleLine from repository to calculation
        PixelLine pixLine = imgRepository.getEmptyPixelLine(clientKey);

        //crete resultat of calculation
        short[] pixelArray = new short[width*3];
        for(int i=0;i<width;i++) pixelArray[i]=(short)i;

        //set array of pixels  to pixleLine
        pixLine.setByteArray(pixelArray);

        //flush complette pixelline

        try {
            imgRepository.insertComplettePixelLine(pixLine);
            assertEquals(1,1);
              } catch (Exception e) {assertEquals(1,2);}


        }
}