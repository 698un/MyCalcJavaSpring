package by.unil2.itstep.testSring1.dao.model;




import by.unil2.itstep.testSring1.dao.model.enums.StatusPixelLine;
import by.unil2.itstep.testSring1.dao.model.enums.ImageStatus;
import by.unil2.itstep.testSring1.dao.repository.MyImageSave;
import by.unil2.itstep.testSring1.exceptions.AccessException;
import by.unil2.itstep.testSring1.exceptions.CalcException;
import by.unil2.itstep.testSring1.exceptions.IncorrectFormatException;
import by.unil2.itstep.testSring1.utilits.MyLocker;


public class MyImage{

    private static final PixelLine nullPixelLine = new PixelLine(-1, -1);

    private int width;
    private int height;
    public PixelLine[] pixelLine;
    private int completeMinIndex;//index of minimal complette line
    private int frameNum;
    private int completteLineCount;
    private ImageStatus processStatus;
    private long lineLifeTime;
    private String saveFolder;
    private String errorMessage;

    //Getters and Setters====================================================================
    public int getFrameNum() {
        return this.frameNum;
    }
    public void  setFrameNum(int inpFrameNum) {  this.frameNum = inpFrameNum; }

    public int getWidth() {
        return this.width;
    }
    public int getHeight() {
        return this.height;
    }
    public ImageStatus getProcesstatus() {
        return this.processStatus;
    }
    public void        setProcesstatus(ImageStatus inpImageStatus) {  this.processStatus = inpImageStatus;   }
    public void setLineLifeTime(long inpLineLifeTime){this.lineLifeTime=inpLineLifeTime;}

    public void setErrorMessage(String message){this.errorMessage = message;}//set errorMessage if save not complette
    public String getErrorMessage(){return this.errorMessage;}//set errorMessage if save not complette



    //=============CONSTRUCTOR=====================================================
    public MyImage(int inpWidth,
                   int inpHeight,
                   int inpFrameNum,
                   long inpLineLifeTime,
                   String inpSaveFolder) {

        this.frameNum =         inpFrameNum;
        this.width =            inpWidth;
        this.height =           inpHeight;
        this.completeMinIndex = -1;//noLine is complette
        this.processStatus =    ImageStatus.CALC_PROCESS;//image is not complette
        this.completteLineCount = 0;
        this.lineLifeTime =     inpLineLifeTime;
        this.saveFolder =       inpSaveFolder;//mark folder for MyImageSaverProcess

        clear();
        }//MyImage


    public void clear(){
        this.pixelLine = new PixelLine[this.height];
        for (int i=0;i<this.height;i++) pixelLine[i]=null;
        }


    /**this method search next uncalculate line in Image
     *
     * @param clientKey
     * @return  this pixelLine or null if not found
     */
    public PixelLine getEmptyPixelLine(String clientKey) {



        for (int i = 0; i < this.height; i++) {



            //If pixelLine not exist then return it
            if (pixelLine[i] == null) {
                    pixelLine[i] = new PixelLine(this.frameNum, i,clientKey);
                    pixelLine[i].setDT(pixelLine[i].getBT()+this.lineLifeTime);
                    return pixelLine[i];
                    }


            //if duration of calculate pixelLine very long
            //remark this pixelLine as currentClientKey
            //and return for calculation to current Client
            if (pixelLine[i].getStatus() != StatusPixelLine.COMPLETTE &&
                pixelLine[i].getDT() < System.currentTimeMillis()) {

                pixelLine[i] = new PixelLine(this.frameNum, i,clientKey);
                pixelLine[i].setDT(pixelLine[i].getBT()+this.lineLifeTime);
                return pixelLine[i];
                }

            }//next i(pixelLine)

        return null; //return null if in this image not contain unCalculated pixelLine

        }//getEmptyPixelLine





    public Long flushComplettePixelLine(PixelLine completteLine) throws Exception {

        //define line in image
        int lineNum = completteLine.getLineNumber();

        //verify length of pixelLine
        if (completteLine.getByteArray().length != this.width * 3) {




               throw new IncorrectFormatException(" length of pixelLine is NOT CORRECT..."+this.width+" <> "+completteLine.getByteArray().length/3 );
               }

        //verify clientKey
        if (!pixelLine[lineNum].getClientKey().equals(completteLine.getClientKey())) {
            throw new CalcException("this line for another client");
            }

        //defined duratio af all cicle
        long time = System.currentTimeMillis() - pixelLine[lineNum].getBT();

        //mark pixelLine as COMPLETTE because this pixelLine is correct
        completteLine.setStatus(StatusPixelLine.COMPLETTE);

        //injecter this correct picelLine in image
        pixelLine[lineNum] = completteLine;

        //if last PixelLine is empty then return duration and break
        if (pixelLine[this.height-1]==null) return (Long)time;

        //if last PixelLine is unComplette then return duration and break
        if (pixelLine[this.height-1].getStatus()==StatusPixelLine.PROCESS) return (Long)time;//break calculate in any line is null


        //Calculate complette line count:
        //calculate count o complette pixelline
        completteLineCount=0;
        for (int i=0;i<this.height;i++){
            if (pixelLine[i]==null) return (Long)time;//break calculate in any line is null
            if (pixelLine[i].getStatus()==StatusPixelLine.COMPLETTE) completteLineCount++;
            }//next line

        if (completteLineCount >= this.height &&
            this.processStatus==ImageStatus.CALC_PROCESS) {
                    this.processStatus = ImageStatus.CALC_COMPLETTE; //mark as calculateComplette

                    MyImageSave imgSaver = new MyImageSave(this,this.saveFolder);
                    imgSaver.start();
                    }




        return (Long)time;


    }//flushComplettePixelLine






}//MyImage
