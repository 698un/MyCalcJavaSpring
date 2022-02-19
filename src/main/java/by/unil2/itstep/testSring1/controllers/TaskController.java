package by.unil2.itstep.testSring1.controllers;

import by.unil2.itstep.testSring1.dao.model.Client;
import by.unil2.itstep.testSring1.exceptions.AccessException;
import by.unil2.itstep.testSring1.services.ClientService;
import by.unil2.itstep.testSring1.services.ProductService;
import by.unil2.itstep.testSring1.utilits.CalcOptions;
import by.unil2.itstep.testSring1.utilits.loger.MyLogger;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.http.HttpStatus;
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
public class TaskController {

    private final CalcOptions calcOpt;
    private final ClientService clientService;
    private final MyLogger myLog;



    //constructor
    public TaskController(ClientService inpClientService,
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
        } catch(Exception e) {
            myLog.error("Get New ClientKey"+e.getMessage());
            return ResponseEntity.status(401).body("not Autorized");
        }

        Cookie cookie = new Cookie("ClientKey",newClientKey);//create cookie
        response.addCookie(cookie);                                //add cookie to response
        response.setContentType("text/plain");// mark response as string
        return ResponseEntity.ok().body(newClientKey);
    }//Post /clientKey


    @PostMapping("/rootkey")
    public ResponseEntity<?> postRootKey(HttpServletResponse response,
                                         @RequestBody String adminPassword){

        myLog.info(" New Client ask RootKey");

        //get root key from config
        try {
            String newRootKey = calcOpt.getNewRootKey(adminPassword);
            Cookie cookie = new Cookie("ClientKey", newRootKey);//create cookie for Root
            response.addCookie(cookie);                              //add cookie to response
            response.setContentType("text/plain");// mark response as string
            return ResponseEntity.ok().body(newRootKey);

            } catch (AccessException e){
              return new ResponseEntity<Error>(HttpStatus.ACCEPTED);
            } catch (Exception e){
              return new ResponseEntity<Error>(HttpStatus.INTERNAL_SERVER_ERROR);
              }


    }//Post /clientKey

}//ClientController
