package by.unil2.itstep.testSring1.controllers;

import by.unil2.itstep.testSring1.controllers.webentity.ErrorMessage;
import by.unil2.itstep.testSring1.exceptions.AccessException;
import by.unil2.itstep.testSring1.services.ClientService;
import by.unil2.itstep.testSring1.utilits.CalcOptions;
import by.unil2.itstep.testSring1.utilits.loger.MyLogger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;





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
    public ResponseEntity<String> postClientKey(){

        String newClientKey;//Object for ClientKey
        myLog.info("New Client ask clientKey");

        //get client key from service for New Client
        try {
            newClientKey = clientService.getNewClientKey();

            //add headers with Cookie
            HttpHeaders headers = new HttpHeaders();
            headers.add("Set-Cookie","ClientKey="+newClientKey);
            return ResponseEntity.ok().headers(headers).build();

            } catch(Exception e) {
              myLog.error("Get New ClientKey"+e.getMessage());
              return ResponseEntity.status(500).body("Internal Error");
              }


        }//Post /clientKey


    @PostMapping("/rootkey")
    public ResponseEntity<?> postRootKey(@RequestBody String adminPassword){

        myLog.info(" New Client ask RootKey");

        //get root key from config
         try {

            String newRootKey = clientService.getNewRootKey(adminPassword);//get RootKey from Service

            //add headers with Cookie
            HttpHeaders headers = new HttpHeaders();
            headers.add("Set-Cookie","ClientKey="+newRootKey);
            return ResponseEntity.ok().headers(headers).build();

            } catch (AccessException e) {
                    return ResponseEntity.ok().body(new ErrorMessage(e.getMessage()));//send errorMessage as AccessException

            } catch (Exception e){
              return new ResponseEntity<Error>(HttpStatus.INTERNAL_SERVER_ERROR);
              }

        }//Post /clientKey


    @PostMapping("/api/exit")
    public ResponseEntity<String> apiExit(@CookieValue(value="ClientKey") String clientKey){

        myLog.debug("command_api/exit");
        clientService.userExit(clientKey);
        return ResponseEntity.ok().body("exit");
        }//Post /api/exit


    }//ClientController
