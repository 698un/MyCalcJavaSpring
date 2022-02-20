package by.unil2.itstep.testSring1.dao.factory;


import by.unil2.itstep.testSring1.dao.model.MyImage;
import by.unil2.itstep.testSring1.utilits.CalcOptions;
import org.springframework.stereotype.Component;

@Component
public class MyImageFactory {

    private CalcOptions calcOpt;

    public MyImageFactory(CalcOptions inpCalcOptions){
        this.calcOpt = inpCalcOptions;
        }


        /*
    public MyImage getNewMyImage(int inpWidth,
                         int inpHeight,
                         int inpFrameNum){
        MyImage resMyImage = new MyImage(inpWidth,inpHeight,inpFrameNum);
        return resMyImage;
        }
*/

}
