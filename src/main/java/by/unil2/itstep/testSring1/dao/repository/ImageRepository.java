package by.unil2.itstep.testSring1.dao.repository;

import by.unil2.itstep.testSring1.dao.model.PixelLine;
import by.unil2.itstep.testSring1.dao.model.enums.ImageStatus;
import by.unil2.itstep.testSring1.exceptions.AccessException;
import by.unil2.itstep.testSring1.exceptions.CalcException;
import by.unil2.itstep.testSring1.utilits.CalcOptions;
import by.unil2.itstep.testSring1.dao.model.MyImage;
import by.unil2.itstep.testSring1.utilits.MyLocker;
import by.unil2.itstep.testSring1.utilits.loger.MyLogger;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Random;

@Component
public class ImageRepository {


    private final CalcOptions calcOpt;
    private final MyLogger myLog;


    //constructor
    public ImageRepository(CalcOptions inpCalcOptions,
                            MyLogger inpMyLogger ) {
        this.calcOpt = inpCalcOptions;
        this.myLog   = inpMyLogger;
        this.reset();
    }



    public void reset(){

        try {
            this.deleteAllImages();
        } catch (Exception e){
            System.out.println("DELETE IMAGES ERROR");
        };

        //set options from config
        this.imgWidth=           calcOpt.getInt("imageWidth" );
        this.imgHeight=          calcOpt.getInt("imageHeight");
        this.imgAntialiasing =   calcOpt.getInt("antialiasing" );

        this.imgCountInBuffer =calcOpt.getInt("imageInBuffer");//ImageInBufferCount();
        this.imgInBuffer = new MyImage[imgCountInBuffer];//create buffer for several images

        //create object of images in buffer
        for (int i=0;i<imgCountInBuffer;i++){
            imgInBuffer[i] = new MyImage(this.imgWidth,
                    this.imgHeight,
                    i);//mark all imageObject in buffer as frameIndex

            newFrameNum = imgCountInBuffer;//defined next frameIndex as last frameIndex in buffer+1
        }//Next i

        //generated sceneKey
        this.sceneKey = getNewKey();
    }//reset

    private int imgWidth;
    private int imgHeight;
    private int imgAntialiasing;//глубина антиальясинга
    private String sceneKey;

    public int getImageWidth() {
        return this.imgWidth;
    }
    public int getImageHeight() {
        return this.imgHeight;
    }
    public int getImageAntialiasing() {
        return this.imgAntialiasing;
    }
    public String getSceneKey() {
        return this.sceneKey;
    }

    private MyImage[] imgInBuffer;
    private int imgCountInBuffer;
    private int newFrameNum;

    public PixelLine getEmptyPixelLine(String clientKey) {

        PixelLine currentPixelLine;
        int calcLineNumber;

        //Search free linePixel in buffer of the images
        for (int i = 0; i < imgCountInBuffer; i++) {

            synchronized (MyLocker.getLocker()) {
                currentPixelLine = imgInBuffer[i].getEmptyPixelLine(clientKey);
            }

            //if free pixelLine  is exist then signed thes pixel line
            //of clientKey and return it
            synchronized (MyLocker.getLocker()) {
                if (currentPixelLine != null) {
                    currentPixelLine.setClientKey(clientKey);//mark clientKey
                    return currentPixelLine;
                }//if getting pixelLine
            }


        }//next i


        //search completted images and erase it
        for (int i = 0; i < imgCountInBuffer; i++) {


            if (imgInBuffer[i].getProcesstatus()== ImageStatus.SAVE_COMPLETTE &&
                    imgInBuffer[i].getProcesstatus()== ImageStatus.SAVE_ERROR ) {

                //write log as flush image on disc

                switch (imgInBuffer[i].getProcesstatus()) {

                    case SAVE_COMPLETTE : myLog.trace("Flush image "+imgInBuffer[i].getFrameNum()+" on disc");
                        break;
                    case SAVE_ERROR:      myLog.trace("Flush image "+imgInBuffer[i].getFrameNum()+" error");
                        break;

                }//switch


                synchronized (MyLocker.getLocker()) {
                    int bufferSize = this.imgCountInBuffer;

                    for (int j = i; j < bufferSize - 1; j++) imgInBuffer[j] = imgInBuffer[j + 1];

                    imgInBuffer[bufferSize - 1] = new MyImage(this.imgWidth, this.imgHeight, this.newFrameNum);
                    newFrameNum++;
                    break;
                }//Synchronized


            }//if complette


        }//next i


        return new PixelLine(0, 0);

    }

    /**
     * This method search image that pixelLine sending
     *
     * @param complettePixelLine
     * @return time in millis
     * @throws Exception
     */
    public Long insertComplettePixelLine(PixelLine complettePixelLine) throws Exception {

        //define image from that getting the line
        int frameNumber = complettePixelLine.getFrameNumber();
        MyImage currentImage = getImageByFrameNumber(frameNumber);

        //exception if image not found
        if (currentImage == null) throw new CalcException("This line not actual");


        //flush in searched image line of resultat
        return currentImage.flushComplettePixelLine(complettePixelLine);

    }//insertComplettePixelLine

    private MyImage getImageByFrameNumber(int inpFrame) {
        synchronized (MyLocker.getLocker()) {
            for (int i = 0; i < imgCountInBuffer; i++)
                if (imgInBuffer[i].getFrameNum() == inpFrame) return imgInBuffer[i];
        }
        return null;
    }//getImageByFrameNumber


    private void deleteAllImages() throws Exception{

        String folderPath = System.getProperty("user.dir")+
                File.separator +
                calcOpt.getStr("imageResultatFolder")+
                File.separator;

        try {
            //deleting in cicle all files in directory
            for (File myFile : new File(folderPath).listFiles())
                if (myFile.isFile()) myFile.delete();
        } catch (Exception e) {throw new Exception(e.getMessage());}

        myLog.debug("Delete allImages");


    }//deleteAllImages




    private String getNewKey(){
        Random rnd = new Random();
        StringBuilder sb1=new StringBuilder("");
        for (int i=0;i<20;i++) sb1.append((char)('a'+rnd.nextInt('z'-'a')));
        return sb1.toString();
    }//getNewKey


}
