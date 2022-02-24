package by.unil2.itstep.testSring1.controllers;

import by.unil2.itstep.testSring1.controllers.webentity.ErrorMessage;
import by.unil2.itstep.testSring1.dao.model.Client;
import by.unil2.itstep.testSring1.exceptions.AccessException;
import by.unil2.itstep.testSring1.services.ClientService;
import by.unil2.itstep.testSring1.services.ProductService;
import by.unil2.itstep.testSring1.utilits.CalcOptions;
import by.unil2.itstep.testSring1.utilits.loger.MyLogger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
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

        String newClientKey;//Object for ClientKey
        myLog.info("New Client ask clientKey");

        //get client key from service for New Client
        try {
            newClientKey = clientService.getNewClientKey();
            Cookie cookie = new Cookie("ClientKey",newClientKey);//create cookie
            response.addCookie(cookie);                                //add cookie to response
            response.setContentType("text/plain");// mark response as string
            return ResponseEntity.ok().body(newClientKey);

            } catch(Exception e) {
              myLog.error("Get New ClientKey"+e.getMessage());
              return ResponseEntity.status(500).body("Internal Error");
              }


        }//Post /clientKey


    @PostMapping("/rootkey")
    public ResponseEntity<?> postRootKey(HttpServletResponse response,
                                         @RequestBody String adminPassword){
        myLog.info(" New Client ask RootKey");

        //get root key from config
        try {

            String newRootKey = clientService.getNewRootKey(adminPassword);//get RootKey from Service
            Cookie cookie = new Cookie("ClientKey",newRootKey);      //create cookie for Root
            response.addCookie(cookie);                                    //add cookie to response
            response.setContentType("text/plain");                         // mark response as string

            return ResponseEntity.ok().body(newRootKey);

            } catch (AccessException e) {
                    return ResponseEntity.ok().body(new ErrorMessage(e.getMessage()));//send errorMessage as AccessException

            } catch (Exception e){
              return new ResponseEntity<Error>(HttpStatus.INTERNAL_SERVER_ERROR);
              }

        }//Post /clientKey


    @PostMapping("/api/exit")
    public ResponseEntity<String> apiExit(HttpServletResponse response,
                                          @CookieValue(value="ClientKey") String clientKey){

        myLog.debug("command_api/exit");
        clientService.userExit(clientKey);
        return ResponseEntity.ok().body("exit");
        }//Post /api/exit


    }//ClientController
