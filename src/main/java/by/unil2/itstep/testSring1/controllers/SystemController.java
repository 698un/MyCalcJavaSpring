package by.unil2.itstep.testSring1.controllers;


import by.unil2.itstep.testSring1.controllers.webentity.ErrorMessage;
import by.unil2.itstep.testSring1.controllers.webentity.NewTask;
import by.unil2.itstep.testSring1.controllers.webentity.ResultatReport;
import by.unil2.itstep.testSring1.controllers.webentity.ServerStatus;
import by.unil2.itstep.testSring1.dao.model.PixelLine;
import by.unil2.itstep.testSring1.exceptions.AccessException;
import by.unil2.itstep.testSring1.exceptions.CalcException;
import by.unil2.itstep.testSring1.exceptions.IncorrectFormatException;
import by.unil2.itstep.testSring1.services.ClientService;
import by.unil2.itstep.testSring1.services.SystemService;
import by.unil2.itstep.testSring1.services.TaskService;
import by.unil2.itstep.testSring1.utilits.CalcOptions;
import by.unil2.itstep.testSring1.utilits.loger.MyLogger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@Controller
public class SystemController {

    private final TaskService taskService;
    private final MyLogger myLog;
    private final ClientService clientService;
    private final CalcOptions calcOpt;
    private final SystemService sysService;


    //constructor
    public SystemController(TaskService inpTaskService,
                          ClientService inpClientService,
                          SystemService inpSystemService,
                          MyLogger inpMyLogger,
                          CalcOptions inpCalcOptions){

        this.taskService =    inpTaskService;
        this.myLog =          inpMyLogger;
        this.clientService =  inpClientService;
        this.calcOpt =        inpCalcOptions;
        this.sysService =     inpSystemService;
    }



    @GetMapping("/server/status")
    public ResponseEntity<?> getServerStatus(HttpServletResponse response) {

        //object of response
        try {
            ServerStatus answerStatus = sysService.getServerStatus();//new ServerStatus();
            myLog.info("client ask ServerStatus");
            return ResponseEntity.ok().body(answerStatus);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Status not reading.");
        }
    }//getServerStatus


    @PostMapping("/api/reset")
    public ResponseEntity<?> postApiReset(HttpServletResponse response,
                                          @CookieValue(value="ClientKey") String rootKey){

        //if not root then return error_message
        if (!clientService.isRootKey(rootKey)) return ResponseEntity.ok().body(new ErrorMessage("not right for this command"));

        myLog.debug("requestcommand_api/reset");
        sysService.resetCalculation();

        return ResponseEntity.ok().body("reset_is_ok");





    }//Post /api/exit









    /**
     * This method converter string from body of request to array of numbers
     * @param inpString
     * @return short[]  - array of numbers
     */
    private short[] getArrayFromString(String inpString) {

        //converted bodyString to number Array
        int byteCountInLine = inpString.length();//count number id body
        int pixelCountInLine =byteCountInLine/3/3;

        short[] pixelArray = new short[byteCountInLine/3];
        int index;

        StringBuffer byteOneStr = new StringBuffer("");
        short byteOneInt;

        //преобразоывание строки цифр в массив int
        for (int i=0;i<pixelCountInLine;i++) {

            //перебор каналов
            for (int ri = 0; ri < 3; ri++) {
                index = i * 9 + ri*3;
                byteOneStr.setLength(0);
                byteOneInt =(short)(
                        (inpString.charAt( index + 0) - '0') * 100 +
                                (inpString.charAt( index + 1) - '0') * 10 +
                                (inpString.charAt( index + 2) - '0')
                );
                pixelArray[i*3+ri] = byteOneInt;
            }//next ri
        }//next i

        return pixelArray;
    }//getArrayFromString










}//InfoController



