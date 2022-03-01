package by.unil2.itstep.testSring1.controllers;


import by.unil2.itstep.testSring1.controllers.webentity.ErrorMessage;
import by.unil2.itstep.testSring1.controllers.webentity.ServerStatus;
import by.unil2.itstep.testSring1.services.ClientService;
import by.unil2.itstep.testSring1.services.SystemService;
import by.unil2.itstep.testSring1.utilits.loger.MyLogger;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;

@Controller
public class SystemController {


    private final MyLogger myLog;
    private final ClientService clientService;
    private final SystemService sysService;


    //constructor
    public SystemController(ClientService inpClientService,
                            SystemService inpSystemService,
                            MyLogger inpMyLogger){

        this.myLog =          inpMyLogger;
        this.clientService =  inpClientService;
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

        myLog.debug("requestcommand: api/reset");
        sysService.resetCalculation();

        return ResponseEntity.ok().body("reset_is_ok");


        }//Post /api/reset






}//InfoController



