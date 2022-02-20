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


    private int imgWidth;
    private int imgHeight;
    private int imgAntialiasing;//глубина антиальясинга
    private String sceneKey;
    private String imageResultatFolder;


    public void reset(){

        myLog.info(" Init applicationPath: "+calcOpt.getApplicationPath());

        //create Folder for images



        try {
            this.deleteAllImages();
        } catch (Exception e){
            System.out.println("DELETE IMAGES ERROR");
        };

        //set options from config
        this.imageResultatFolder=calcOpt.getApplicationPath()+
                                 File.separator+
                                 calcOpt.getStr("imageResultatFolder");//get folder of images from config

        this.imgWidth=           calcOpt.getInt("imageWidth" );
        this.imgHeight=          calcOpt.getInt("imageHeight");
        this.imgAntialiasing =   calcOpt.getInt("antialiasing" );

        this.imgCountInBuffer =calcOpt.getInt("imageInBuffer");//ImageInBufferCount();
        this.imgInBuffer = new MyImage[imgCountInBuffer];//create buffer for several images

        //create object of images in buffer
        for (int i=0;i<imgCountInBuffer;i++){
            imgInBuffer[i] = new MyImage(this.imgWidth,
                                         this.imgHeight,
                                         i,         //mark all imageObject in buffer as frameIndex
                                         calcOpt.getInt("lineLifeTime"),  //set lineLifeTime for every pixelLine in this image
                                         this.imageResultatFolder
                                         );



            newFrameNum = imgCountInBuffer;//defined next frameIndex as last frameIndex in buffer+1
        }//Next i

        //generated sceneKey
        this.sceneKey = getNewKey();
    }//reset



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
    private int sourceIndex=0;

    public PixelLine getEmptyPixelLine(String clientKey) throws Exception {

        PixelLine currentPixelLine = null;

        clearCompletteImagesFromBuffer();//

        //Search free linePixel in buffer of the images

        //sourceIndex=sourceIndex%imgCountInBuffer;

        //sourceIndex=(sourceIndex+1)%imgCountInBuffer;

        //int i=sourceIndex;
        int i;
        for (int j = sourceIndex; j < sourceIndex+imgCountInBuffer; j++) {

            i=j%imgCountInBuffer;

            //Loked image because search by all pixelLine in current image
            synchronized (MyLocker.getLocker()) {
                //search only in uncomplette images
                if (imgInBuffer[i].getProcesstatus() == ImageStatus.CALC_PROCESS)
                    currentPixelLine = imgInBuffer[i].getEmptyPixelLine(clientKey);
            }//synchronized


            //if free pixelLine  is exist then return it
            //synchronized (MyLocker.getLocker()) {
            if (currentPixelLine != null) return currentPixelLine;

        }//next i

        //if not empty pixelLine then exception
        throw new CalcException("Calculate is END");



    }

    /**
     * this method deleted from bufer saved images
     */
    private void clearCompletteImagesFromBuffer(){


        //search completted images and erase it
        for (int i = 0; i < imgCountInBuffer; i++) {
            //action for save correct
            if (imgInBuffer[i].getProcesstatus() == ImageStatus.SAVE_COMPLETTE ||
                imgInBuffer[i].getProcesstatus() == ImageStatus.SAVE_ERROR) {
                myLog.info("image " + imgInBuffer[i].getFrameNum() + " "+ imgInBuffer[i].getProcesstatus());
                deleteOneImageFromBuffer(i);
                return;
                }

            }//next i

        }//clearCompletteImagesFromBuffer(){



    private void deleteOneImageFromBuffer(int index){

           synchronized (MyLocker.getLocker()) {

            /*
            imgInBuffer[index] = new MyImage(this.imgWidth,
                                             this.imgHeight,
                                             this.newFrameNum,
                                             calcOpt.getInt("lineLifeTime"),  //set lineLifeTime for every pixelLine in this image
                                             this.imageResultatFolder
                                             );
            */

            //Remark image as nextFrame
            imgInBuffer[index].setFrameNum(newFrameNum);
            imgInBuffer[index].clear();
            imgInBuffer[index].setProcesstatus(ImageStatus.CALC_PROCESS);

            System.out.println("imgInBuffer:");
            for (int i=0;i<imgCountInBuffer;i++){
                System.out.println(imgInBuffer[i].getFrameNum());
                }


            }//Synchronized


        newFrameNum++;
        myLog.trace("CreateImage "+newFrameNum+" FOR CALCULATION");

        sourceIndex++;
        sourceIndex=sourceIndex%imgCountInBuffer;



    }//deleteOneImageFromBuffer




    /**
     * This method search image that pixelLine sending
     *
     * @param complettePixelLine
     * @return time in millis
     * @throws Exception
     */
    public Long insertComplettePixelLine(PixelLine complettePixelLine) throws Exception {

        //define image in buffer
        int frameNumber = complettePixelLine.getFrameNumber();
        MyImage currentImage = getImageByFrameNumber(frameNumber);

        //exception if image not found or not CALC_PROCESS
        if (currentImage == null ||
            currentImage.getProcesstatus()!=ImageStatus.CALC_PROCESS) {

            myLog.warn("image for flush resultat not found");
            throw new CalcException("This line not actual");
            }


        //flush to found image line of resultat
        try {
            return currentImage.flushComplettePixelLine(complettePixelLine);
            } catch (Exception e) {
                myLog.error("not flush resultat "+e.getMessage());
                throw e;
                }

      }//insertComplettePixelLine

    private MyImage getImageByFrameNumber(int inpFrame) {
        synchronized (MyLocker.getLocker()) {
            for (int i = 0; i < imgCountInBuffer; i++)
                if (imgInBuffer[i].getFrameNum() == inpFrame &&
                    imgInBuffer[i].getProcesstatus()==ImageStatus.CALC_PROCESS) return imgInBuffer[i];
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