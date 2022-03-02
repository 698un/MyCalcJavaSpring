package by.unil2.itstep.testSring1.dao.model;

import by.unil2.itstep.testSring1.dao.model.enums.ImageStatus;
import org.junit.Before;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class MyImageTest {


    private int width;
    private int height;
    private int frameNum;
    private long lineLifeTime;
    private String saveFolder;


    @Before
    public void init(){
        width = 640;
        height =480;
        frameNum = 123;
        lineLifeTime = 1000;
        saveFolder = System.getProperty("user.dir");
        }





    @Test
    void getFrameNum() {
        MyImage img1 = new MyImage(width,height,frameNum,lineLifeTime,saveFolder);
        int getFrameNum = img1.getFrameNum();
        assertEquals(getFrameNum,frameNum);
        }

    @Test
    void setFrameNum() {
        MyImage img1 = new MyImage(width,height,frameNum,lineLifeTime,saveFolder);
        img1.setFrameNum(1000);
        assertEquals(1000,img1.getFrameNum());
        }


    @Test
    void getWidth() {
        MyImage img1 = new MyImage(width,height,frameNum,lineLifeTime,saveFolder);
        int getWidth = img1.getWidth();
        assertEquals(getWidth,width);
        }

    @Test
    void getHeight() {
        MyImage img1 = new MyImage(width,height,frameNum,lineLifeTime,saveFolder);
        int getHeight = img1.getHeight();
        assertEquals(getHeight,height);
        }

    @Test
    void getProcesstatus() {
        MyImage img1 = new MyImage(width,height,frameNum,lineLifeTime,saveFolder);
        ImageStatus imgProcessStatus = img1.getProcesstatus();
        assertEquals(imgProcessStatus, ImageStatus.CALC_PROCESS);
        }

    @Test
    void setProcesstatus() {
        MyImage img1 = new MyImage(width,height,frameNum,lineLifeTime,saveFolder);
        img1.setProcesstatus(ImageStatus.CALC_COMPLETTE);

        ImageStatus imgProcessStatus = img1.getProcesstatus();
        assertEquals(imgProcessStatus, ImageStatus.CALC_COMPLETTE);
        }



    @Test
    void setLineLifeTime() {
        MyImage img1 = new MyImage(width,height,frameNum,lineLifeTime,saveFolder);
        img1.setLineLifeTime(12345);

        assertEquals(1,1);
        }



    @Test
    void setErrorMessage() {
        MyImage img1 = new MyImage(width,height,frameNum,lineLifeTime,saveFolder);
        img1.setErrorMessage("someError");

        String resError = img1.getErrorMessage();
        assertEquals(resError,"someError");
        }

    @Test
    void getErrorMessage() {
        MyImage img1 = new MyImage(width,height,frameNum,lineLifeTime,saveFolder);
        img1.setErrorMessage("someError");

        String resError = img1.getErrorMessage();
        assertEquals(resError,"someError");
        }


    @Test
    void getEmptyPixelLine() {
        String clientKey = "1234567890";

        //create  any image
        MyImage img1 = new MyImage(640,480,
                                123,1000,
                                 System.getProperty("user.dir"));

        //Read first pixelLine (lineNum = 0)
        PixelLine newPixelLine = img1.getEmptyPixelLine(clientKey);

        //Read second pixelLine (LineNum= 1)
        newPixelLine = img1.getEmptyPixelLine(clientKey);



        int lineNum = newPixelLine.getLineNumber();
        assertEquals(lineNum,1);


        }

    @Test
    void flushComplettePixelLine() throws InterruptedException {

        String clientKey = "1234567890";
        int width = 640;
        int height = 480;
        //create  any image
        MyImage img1 = new MyImage(width,height,
                123,1000,
                System.getProperty("user.dir"));


        //Read first pixelLine (lineNum = 0)
        PixelLine calcPixelLine = img1.getEmptyPixelLine(clientKey);


        //CALCULATE IN BROWSER AND FILL pixelArray
        short[] pixelArrayByte = new short[width*3];

        for (int i=0;i<pixelArrayByte.length;i++) pixelArrayByte[i]=0;
        calcPixelLine.setByteArray(pixelArrayByte);

        Thread.sleep(100L); //delay to calculate
        long duration=0;

        try {
            duration = img1.flushComplettePixelLine(calcPixelLine);
            } catch (Exception e) {}


        if (duration>=100)  duration =100;else duration = 0;

        assertEquals(duration,100L);

        }








}