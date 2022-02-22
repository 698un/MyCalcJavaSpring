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





    @GetMapping("/{somepath1}client{somepath2}")
    public String  getSomeClientPath(@PathVariable (name="somepath1") String somePath1,
                                     @PathVariable (name="somepath2") String somePath2,
                                     @CookieValue(value="ClientKey") String clientKey){

        //if service answer as client key not containt in clientepository then relink to "access_denied.html"
        if (!clientService.clientIsRegistration(clientKey)) return "access_denied.html";
        return "/"+somePath1+"client"+somePath2;
        }//getSomePathClient




    @GetMapping("/admin{somepath}")
    public String  getSomeAdminPath(@PathVariable (name="somepath") String somePathStr,
                                     @CookieValue(value="ClientKey") String rootKey){

        //if service answer as client key not containt in clientepository then relink to "access_denied.html"
        if (!clientService.rootIsRegistration(rootKey)) return "access_denied.html";
        return "/admin"+somePathStr;
        }//getSomePathRoot




    @RequestMapping(value = "/video/file/{filename}", method = RequestMethod.GET)
    public ResponseEntity<?> getVideoFile(@PathVariable(name = "filename") String fileName,
                                          @CookieValue(value="ClientKey") String rootKey){

        //verify rootKey
        if (!clientService.isRootKey(rootKey)) return ResponseEntity.ok().body("access denied!");

        try {
            String fullPath = videoService.getVideoFullPath(fileName);
            File file = new File(fullPath);

            HttpHeaders respHeaders = new HttpHeaders();
            respHeaders.setContentType(MediaType.parseMediaType("video/mp4"));
            respHeaders.setContentLength(file.length());
            respHeaders.setContentDispositionFormData("attachment", fileName);

            InputStreamResource isr = new InputStreamResource(new FileInputStream(file));
            return new ResponseEntity<InputStreamResource>(isr, respHeaders, HttpStatus.OK);

        }catch (Exception e) {
            return ResponseEntity.ok().body(e.getMessage());
            }
        }//getVideoFile




}//clacc ClientPathController
