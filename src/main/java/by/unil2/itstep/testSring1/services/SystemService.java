package by.unil2.itstep.testSring1.services;

import by.unil2.itstep.testSring1.dao.repository.ClientRepositoryHM;
import by.unil2.itstep.testSring1.models.Product;
import by.unil2.itstep.testSring1.utilits.CalcOptions;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SystemService {



    //method return appPath
    public String getPathApp(){
        return CalcOptions.getOptions().getStr("applicationPath");
        }//getAppPath


    //method return appPath
    public String getServerStatus() throws Exception{

        try {
            int clientCount = ClientRepositoryHM.getRepository().getClientCount();


            int imageWidth =   CalcOptions.getOptions().getInt("imageWidth");
            int imageHeight =  CalcOptions.getOptions().getInt("imageHeight");
            int antialiasing = CalcOptions.getOptions().getInt("antialiasing");
            int fps = CalcOptions.getOptions().getInt("fps");

            } catch (Exception e) {throw new Exception(e.getMessage());}

        return CalcOptions.getOptions().getStr("applicationPath");
        }//getAppPath





}//SystemService
