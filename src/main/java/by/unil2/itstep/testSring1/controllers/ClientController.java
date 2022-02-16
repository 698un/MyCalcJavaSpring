package by.unil2.itstep.testSring1.controllers;

import by.unil2.itstep.testSring1.dao.model.Client;
import by.unil2.itstep.testSring1.services.ClientService;
import by.unil2.itstep.testSring1.services.ProductService;
import by.unil2.itstep.testSring1.utilits.CalcOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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


    Logger log = LoggerFactory.getLogger(ClientController.class);




    private final ClientService clientService;

    //constructor
    public ClientController(ClientService clientService){
        this.clientService = clientService;
    }


    @PostMapping("/clientkey")
    public ResponseEntity<String> postClientKey(HttpServletResponse response){


        String newClientKey;
        log.info("post clientKey");
        System.out.println("CLIENTKEY");

        //get client key from service

        try {
            newClientKey = clientService.getNewClientKey();
            } catch(Exception e) {

            log.error(e.getMessage());
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
