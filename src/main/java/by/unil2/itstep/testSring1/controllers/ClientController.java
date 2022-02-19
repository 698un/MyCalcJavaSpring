package by.unil2.itstep.testSring1.controllers;

import by.unil2.itstep.testSring1.dao.model.Client;
import by.unil2.itstep.testSring1.services.ClientService;
import by.unil2.itstep.testSring1.services.ProductService;
import by.unil2.itstep.testSring1.utilits.CalcOptions;
import by.unil2.itstep.testSring1.utilits.loger.MyLogger;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * This class control request as client connections
 */

@Controller
public class ClientController {

    //Logger log = LoggerFactory.getLogger(this.getClass());

    private final CalcOptions calcOpt;
    private final ClientService clientService;
    private final MyLogger myLog;



    //constructor
    public ClientController(ClientService inpClientService,
                            CalcOptions inpCalcOptions,
                            MyLogger inpMyLogger){
        this.clientService = inpClientService;
        this.calcOpt = inpCalcOptions;
        this.myLog = inpMyLogger;
        }//constructor






    @PostMapping("/clientkey")
    public ResponseEntity<String> postClientKey(HttpServletResponse response){

        String newClientKey;
        myLog.info("client ask clientKey");


        System.out.println(calcOpt.getStr("applicationPath"));

        //get client key from service

        try {
            newClientKey = clientService.getNewClientKey();
            } catch(Exception e) {

            myLog.error(e.getMessage());
            return ResponseEntity.status(401).body("not Autorized");

            }




        ///create cookie

        Cookie cookie = new Cookie("ClientKey",newClientKey);

        //add cookie to response
        response.addCookie(cookie);

        response.setContentType("text/plain");// mark response as string
        return ResponseEntity.ok().body(newClientKey);
        }//Post /clientKey


    @PostMapping("/rootkey")
    public ResponseEntity<?> postRootKey(HttpServletResponse response,
                                         @RequestBody String adminPassword){

        System.out.println("ROOT_KEY");

        //get client key from service
        String newClientKey = CalcOptions.getOptions().getNewRootKey(adminPassword);

        ///create cookie
        Cookie cookie = new Cookie("ClientKey",newClientKey);

        //add cookie to response
        response.addCookie(cookie);

        response.setContentType("text/plain");// mark response as string
        return ResponseEntity.ok().body(newClientKey);
        }//Post /clientKey






}
