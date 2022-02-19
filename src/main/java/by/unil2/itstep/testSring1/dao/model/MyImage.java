package by.unil2.itstep.testSring1.dao.model;




import by.unil2.itstep.testSring1.dao.model.enums.StatusPixelLine;
import by.unil2.itstep.testSring1.dao.model.enums.ImageStatus;
import by.unil2.itstep.testSring1.dao.repository.MyImageSave;
import by.unil2.itstep.testSring1.exceptions.CalcException;
import by.unil2.itstep.testSring1.utilits.MyLocker;
import by.unil2.itstep.testSring1.utilits.loger.MyLogger;



public class MyImage{

    private static final PixelLine nullPixelLine = new PixelLine(-1, -1);

    private int width;
    private int height;
    public PixelLine[] pixelLine;
    private int completeMinIndex;//index of minimal complette line
    private int frameNum;
    private int completteLineCount;
    private ImageStatus processStatus;

    //Getters and Setters====================================================================
    public int getFrameNum() {
        return this.frameNum;
    }
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

    //=============CONSTRUCTOR=====================================================
    public MyImage(int inpWidth, int inpHeight, int inpFrameNum) {
        this.frameNum = inpFrameNum;
        this.width = inpWidth;
        this.height = inpHeight;
        this.pixelLine = new PixelLine[inpHeight];
        this.completeMinIndex = -1;//noLine is complette
        this.processStatus = ImageStatus.CALC_PROCESS;//image is not complette
        this.completteLineCount = 0;
        }//MyImage



    /**this method search next uncalculate line in Image
     *
     * @param clientKey
     * @return  this pixelLine or null if not found
     */
    public PixelLine getEmptyPixelLine(String clientKey) {

        boolean thisIsComplette = true;//предполагаем что изображжени просчитано

        //начиная с минимально посчитанной ищем свободную
        //for (int i=this.completeMinIndex+1;i<this.height;i++) {

        for (int i = 0; i < this.height; i++) {
            //If pixelLine not exist then return it index
            if (pixelLine[i] == null) {
                    pixelLine[i] = new PixelLine(this.frameNum, i);
                    pixelLine[i].setClientKey(clientKey);
                    return pixelLine[i];
                    }

            //if duration of calculate pixelLine very long
            if (pixelLine[i] != null &&
                    pixelLine[i].getStatus() != StatusPixelLine.COMPLETTE &&
                    pixelLine[i].getDT() < System.currentTimeMillis()) {
                pixelLine[i] = new PixelLine(this.frameNum, i);
                pixelLine[i].setClientKey(clientKey);
                return pixelLine[i];
            }

            //if any pixelLine is notcomplette then check this Is unComplette
            if (pixelLine[i].getStatus() != StatusPixelLine.COMPLETTE) thisIsComplette = false;
        }//next i(pixelLine)

        //signed completted image
        if (this.processStatus==ImageStatus.CALC_PROCESS &&
            thisIsComplette) this.processStatus = ImageStatus.CALC_COMPLETTE;

        return null;
        }//getEmptyPixelLine


    public Long flushComplettePixelLine(PixelLine completteLine) throws Exception {

        boolean error = false;

        int lineNum = completteLine.getLineNumber();

        if (completteLine.getByteArray().length < this.width * 3) error = true;

        //length if responseString
        int postLength=completteLine.getByteArray().length;


        //Verifing correct size of the array
        if (error==true) {
            synchronized(MyLocker.getLocker()) {
                pixelLine[lineNum] = null;
                }//synchronized
            throw new CalcException("InCorrect line");
             }


        //verify clientKey
        if (pixelLine[lineNum].getClientKey().equals(completteLine.getClientKey())) {

                  long time = System.currentTimeMillis() - pixelLine[lineNum].getBT();

                  pixelLine[lineNum] = completteLine;
                  this.completteLineCount++;

                  if (completteLineCount == this.height &&
                      this.processStatus==ImageStatus.CALC_PROCESS) this.processStatus = ImageStatus.CALC_COMPLETTE; //mark as calculateComplette

                  return (Long)time;

            }//IF EQAULS CLIENT




        throw new CalcException("InCorrect format");

    }//flushComplettePixelLine






}//MyImage
