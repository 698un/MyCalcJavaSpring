package by.unil2.itstep.testSring1.controllers.webpages;


import by.unil2.itstep.testSring1.dao.repository.ClientRepository;
import by.unil2.itstep.testSring1.services.ClientService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class ClientPathController {

    private final ClientService clientService;

    //constructor
    public ClientPathController(ClientService inpClientService){
        this.clientService = inpClientService;
        }



    @GetMapping("/client{somepath}")
    public String  getSomeClientPath(@PathVariable (name="somepath") String somePathStr,
                                     @CookieValue(value="ClientKey") String clientKey){

        if (!clientService.clientIsRegistration(clientKey)) return "access_denied.html";

        return "/client"+somePathStr;
        }//getSomePathClient

    }//clacc ClientPathController
