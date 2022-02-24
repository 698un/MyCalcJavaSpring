package by.unil2.itstep.testSring1.controllers;


import by.unil2.itstep.testSring1.controllers.webentity.ErrorMessage;
import by.unil2.itstep.testSring1.controllers.webentity.NewTask;
import by.unil2.itstep.testSring1.controllers.webentity.ResultatReport;
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
public class TaskController {

    private final TaskService taskService;
    private final MyLogger myLog;
    private final ClientService clientService;
    private final CalcOptions calcOpt;
    private final SystemService sysService;


    //constructor
    public TaskController(TaskService inpTaskService,
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


    @GetMapping("/newtask")
    public ResponseEntity<?> getNewTask(@CookieValue(value="ClientKey") String clientKey){


       if (!clientService.clientIsRegistration(clientKey)) return ResponseEntity.ok().body(new ErrorMessage("invalid clientkey"));


        try{
            NewTask newTaskForClient = taskService.getNextTask(clientKey);
            return ResponseEntity.ok().body(newTaskForClient);

        } catch (AccessException e) {
            myLog.error(e.getMessage());
            return ResponseEntity.ok().body(new ErrorMessage(e.getMessage()));

        } catch (Exception e) {
            myLog.error(e.getMessage());
            return ResponseEntity.ok().body(new ErrorMessage(e.getMessage()));
            }
    }//getNewTask


    //29-886-1850




    @PostMapping("/resultat/{sceneKey}/{frameNum}/{lineNum}")
    public ResponseEntity<?> postResultat(@PathVariable (name="sceneKey") String sceneKey,
                                          @PathVariable (name="frameNum") int frameNum,
                                          @PathVariable (name="lineNum") int lineNum,
                                          @RequestBody String pixArrayStr,
                                          @CookieValue(value="ClientKey") String clientKey){

        //SCENEKEY
        if (!taskService.getSceneKey().equals(sceneKey)) {
            myLog.error("invalid sceneKey");
            return ResponseEntity.ok().body(new ErrorMessage("invalid scenekey"));
            }

        //SEQURITY
        if (!clientService.clientIsRegistration(clientKey)) return ResponseEntity.ok().body(new ErrorMessage("invalid clientkey"));




        //representatoin resultat in object
        PixelLine resPixelLine = new PixelLine(frameNum,lineNum);  //create object of resultat
        resPixelLine.setByteArray(getArrayFromString(pixArrayStr));//set pixelArray from body of request
        resPixelLine.setClientKey(clientKey);                      //mark as clientKey

        try {
            long duration = taskService.postCompletteTask(resPixelLine);//send to service and get time of calculation
            ResultatReport resReport = new ResultatReport(duration);
            return ResponseEntity.ok().body(resReport);

            } catch (IncorrectFormatException e) {
                myLog.error(e.getMessage());
                return ResponseEntity.ok().body(new ErrorMessage(e.getMessage()));

            } catch (AccessException e) {
                myLog.error(e.getMessage());
                return ResponseEntity.ok().body(new ErrorMessage(e.getMessage()));

            } catch (CalcException e) {
                myLog.error(e.getMessage());
                return ResponseEntity.ok().body(new ErrorMessage(e.getMessage()));

            } catch (Exception e) {
                myLog.error(e.getMessage());
                return new ResponseEntity<Error>(HttpStatus.INTERNAL_SERVER_ERROR);
            }

    }//postResultat





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



