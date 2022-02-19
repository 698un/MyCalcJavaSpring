package by.unil2.itstep.testSring1.dao.repository;

/**
 * This class is launcher for thread imageSave
 */


import by.unil2.itstep.testSring1.dao.model.MyImage;
import by.unil2.itstep.testSring1.utilits.CalcOptions;
import by.unil2.itstep.testSring1.dao.repository.MyImageSave;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class ImageSaveComponent {

    private final CalcOptions calcOpt;

    public ImageSaveComponent(CalcOptions inpCalcOptions){
        this.calcOpt = inpCalcOptions;
        }//constructor


    public void saveLaunch(MyImage myImg){

        //Create folder for resultat of images if not exists
        String resImageFolderPath = calcOpt.getStr("applicationPath")+
                                    File.separator+
                                    calcOpt.getStr("imageResultatFolder");

        //create folder if not exist
        new File(resImageFolderPath).mkdirs();

        //Path to tamplate image
        String templatePath = calcOpt.getApplicationPath()+
                              File.separator+
                              "null.png";

        //create thread for save
        MyImageSave imgSaver = new MyImageSave(myImg,
                                               resImageFolderPath,
                                               templatePath);
        //launch process
        imgSaver.start();

        }//saveLaunchThread




}
