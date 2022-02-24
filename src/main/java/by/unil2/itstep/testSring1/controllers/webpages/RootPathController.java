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
public class RootPathController {

    private final ClientService clientService;
    private final VideoService videoService;
    private final CalcOptions calcOpt;

    //constructor
    public RootPathController(ClientService inpClientService,
                                VideoService inpVideoService,
                                CalcOptions inpCalcOptions){
        this.clientService = inpClientService;
        this.videoService =  inpVideoService;
        this.calcOpt =       inpCalcOptions;
    }



    @GetMapping("/root/{someFile}")
    public String  getSomeRootFile(@PathVariable (name="someFile") String someFile,
                                     @CookieValue(value="ClientKey") String rootKey){
        return rootPathResult("/root/"+someFile,rootKey);
        }//getSomePathRoot

    @GetMapping("/root/{somefolder}/{somefile}")
    public String  getSomeRootFolderFile(@PathVariable (name="somefolder") String someFolder,
                                         @PathVariable (name="somefile") String someFile,
                                         @CookieValue(value="ClientKey") String rootKey){
        return rootPathResult("/root/"+someFolder+"/"+someFile,rootKey);
         }//getSomePathRoot


    private String rootPathResult(String path,String rootKey){

        //path.replaceAll("/",File.separator);

        //if service answer as client key not containt in clientepository then relink to "access_denied.html"
        if (!clientService.isRootKey(rootKey)) return "access_denied.html";
        return path;
        }//reLink to rootFolder


    /**
     * Contol as videoFiles for root
     * @param fileName
     * @param rootKey
     * @return
     */

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
