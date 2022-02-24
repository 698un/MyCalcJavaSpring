package by.unil2.itstep.testSring1.controllers.webpages;


import by.unil2.itstep.testSring1.controllers.webentity.ErrorMessage;
import by.unil2.itstep.testSring1.dao.repository.ClientRepository;
import by.unil2.itstep.testSring1.exceptions.VideoException;
import by.unil2.itstep.testSring1.services.ClientService;
import by.unil2.itstep.testSring1.services.VideoService;
import by.unil2.itstep.testSring1.utilits.CalcOptions;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@Controller
public class ClientPathController {

    private final ClientService clientService;
    private final VideoService videoService;
    private final CalcOptions calcOpt;

    //constructor
    public ClientPathController(ClientService inpClientService,
                                VideoService inpVideoService,
                                CalcOptions inpCalcOptions){
        this.clientService = inpClientService;
        this.videoService =  inpVideoService;
        this.calcOpt =       inpCalcOptions;
        }



    @GetMapping("/client/{someFile}")
    public String  getSomeClientFile(@PathVariable (name="someFile") String someFile,
                                     @CookieValue(value="ClientKey") String clientKey){
        return clientPathResult("/client/"+someFile,clientKey);
        }//getSomePathClient

    @GetMapping("/client/{somefolder}/{somefile}")
    public String  getSomeClientFolderFile(@PathVariable (name="somefolder") String someFolder,
                                           @PathVariable (name="somefile") String someFile,
                                           @CookieValue(value="ClientKey") String clientKey){
        return clientPathResult("/client/"+someFolder+"/"+someFile,clientKey);
        }//getSomePathClient


    private String clientPathResult(String path,String clientKey){

        //path.replaceAll("/",File.separator);

        //if service answer as client key not containt in clientepository then relink to "access_denied.html"
        if (!clientService.clientIsRegistration(clientKey)) return "access_denied.html";
        return path;
        }





}//clacc ClientPathController
