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

        //if service answer as client key not containt in clientepository then relink to "access_denied.html"
        if (!clientService.clientIsRegistration(clientKey)) return "access_denied.html";
        return "/client"+somePathStr;
        }//getSomePathClient




    @GetMapping("/admin{somepath}")
    public String  getSomeAdminPath(@PathVariable (name="somepath") String somePathStr,
                                     @CookieValue(value="ClientKey") String rootKey){

        //if service answer as client key not containt in clientepository then relink to "access_denied.html"
        if (!clientService.rootIsRegistration(rootKey)) return "access_denied.html";
        return "/admin"+somePathStr;
        }//getSomePathRoot






}//clacc ClientPathController
