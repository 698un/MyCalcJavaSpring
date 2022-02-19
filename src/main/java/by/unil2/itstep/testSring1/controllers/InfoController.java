package by.unil2.itstep.testSring1.controllers;


import by.unil2.itstep.testSring1.controllers.webentity.ServerStatus;
import by.unil2.itstep.testSring1.dao.repository.ClientRepositoryHM;
import by.unil2.itstep.testSring1.services.ClientService;
import by.unil2.itstep.testSring1.services.ProductService;
import by.unil2.itstep.testSring1.services.SystemService;
import by.unil2.itstep.testSring1.utilits.CalcOptions;
import by.unil2.itstep.testSring1.utilits.loger.LogState;
import by.unil2.itstep.testSring1.utilits.loger.MyLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletResponse;

@Controller
public class InfoController {

    private final SystemService sysService;
    private final ClientService clientService;
    private final MyLogger myLog;
    private final CalcOptions calcOpt;

    //constructor
    public InfoController(SystemService sysService,
                          ClientService clientService,
                          MyLogger inpMyLogger,
                          CalcOptions inpCalcOptions){
        this.sysService = sysService;
        this.clientService = clientService;
        this.myLog = inpMyLogger;
        this.calcOpt = inpCalcOptions;
        }



    @GetMapping("/server/status")
    public ResponseEntity<?> getServerStatus(HttpServletResponse response) {

        //object of response

        ServerStatus answerStatus = new ServerStatus();

        int clientCount;

        try {

            answerStatus.setClientCount(5);
            //answerStatus.setClientCount(clientService.getClientCount());


            } catch (Exception e) {
            return ResponseEntity.badRequest().body("status not reading!!!");
            }

        /*





            StringBuffer sb1 = new StringBuffer("");

            sb1.append("{");
            sb1.append("\"clientCount\":" +     String.valueOf(clientCount));

            sb1.append(",");
            sb1.append("\"imgHeight\":" +       CalcOptions.getOptions().getStr("imageHeight"));

            sb1.append(",");
            sb1.append("\"imgWidth\":" +        CalcOptions.getOptions().getStr("imageWidth"));

            sb1.append(",");
            sb1.append("\"imgAntialiasing\":" + CalcOptions.getOptions().getStr("antialiasing"));

            sb1.append(",");
            sb1.append("\"fps\":" +             CalcOptions.getOptions().getStr("fps"));

            sb1.append("}");

        System.out.println("===="+sb1.toString());


        //MyLogger.getLogger().log(LogState.DEBUG,"Client ask server status...");
        response.setContentType("text/plain");
        return ResponseEntity.ok().body(sb1.toString());
        */


        answerStatus.setFps(            calcOpt.getInt("fps"));
        answerStatus.setImgWidth(       calcOpt.getInt("imageWidth"));
        answerStatus.setImgHeight(      calcOpt.getInt("imageHeight"));
        answerStatus.setImgAntialiasing(calcOpt.getInt("antialiasing"));

        response.setContentType("application/json");
        return ResponseEntity.ok().body(answerStatus);






        }//getServerStatus



    }//InfoController



