package by.unil2.itstep.testSring1.controllers;


import by.unil2.itstep.testSring1.dao.repository.ClientRepositoryHM;
import by.unil2.itstep.testSring1.services.ClientService;
import by.unil2.itstep.testSring1.services.ProductService;
import by.unil2.itstep.testSring1.services.SystemService;
import by.unil2.itstep.testSring1.utilits.CalcOptions;
import by.unil2.itstep.testSring1.utilits.loger.LogState;
import by.unil2.itstep.testSring1.utilits.loger.MyLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletResponse;

@Controller
public class InfoController {


    Logger log = LoggerFactory.getLogger(ClientController.class);

    private final SystemService sysService;
    private final ClientService clientService;

    //constructor
    public InfoController(SystemService sysService,ClientService clientService){
        this.sysService = sysService;
        this.clientService = clientService;
        }



    @GetMapping("/server/status")
    public ResponseEntity<?> getServerStatus(HttpServletResponse response) {

        int clientCount;

        try {
            clientCount = clientService.getClientCount();
            } catch (Exception e) {
            return ResponseEntity.badRequest().body("status not reading!!!");
            }




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


        //MyLogger.getLogger().log(LogState.DEBUG,"Client ask server status...");
        response.setContentType("text/plane");
        return ResponseEntity.ok().body(sb1.toString());
        }//getServerStatus



    }//InfoController



