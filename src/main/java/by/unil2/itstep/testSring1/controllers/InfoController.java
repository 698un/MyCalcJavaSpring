package by.unil2.itstep.testSring1.controllers;


import by.unil2.itstep.testSring1.controllers.webentity.ServerStatus;
import by.unil2.itstep.testSring1.services.ClientService;
import by.unil2.itstep.testSring1.services.SystemService;
import by.unil2.itstep.testSring1.utilits.CalcOptions;
import by.unil2.itstep.testSring1.utilits.loger.MyLogger;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletResponse;

@Controller
public class InfoController {

    private final SystemService sysService;
    private final MyLogger myLog;


    //constructor
    public InfoController(SystemService inpSystemService,
                          MyLogger inpMyLogger
                          ){

        this.sysService =    inpSystemService;
        this.myLog =         inpMyLogger;
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
    }//InfoController



