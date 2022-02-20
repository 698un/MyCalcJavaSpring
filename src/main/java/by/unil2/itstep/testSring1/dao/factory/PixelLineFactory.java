/**
 * This class is Spring Factory for create new PixelLine
 */

package by.unil2.itstep.testSring1.dao.factory;


import by.unil2.itstep.testSring1.dao.model.PixelLine;
import by.unil2.itstep.testSring1.utilits.CalcOptions;
import org.springframework.stereotype.Component;

@Component
public class PixelLineFactory {

    private CalcOptions calcOpt;//injected component

    public PixelLineFactory(CalcOptions inpCalcOptions){
        this.calcOpt = inpCalcOptions;
        }


    public PixelLine getNewPixelLine(int frameNum,
                                     int lineNum,
                                     String clientKey){

        PixelLine resPixelLine = new PixelLine(frameNum,lineNum);
        resPixelLine.setBT(System.currentTimeMillis());
        resPixelLine.setDT(System.currentTimeMillis()+calcOpt.getInt("lineLifeTime"));
        return resPixelLine;
        }//getNewPixelLine


}
