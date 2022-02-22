package by.unil2.itstep.testSring1.controllers;


import by.unil2.itstep.testSring1.controllers.webentity.ErrorMessage;
import by.unil2.itstep.testSring1.exceptions.VideoException;
import by.unil2.itstep.testSring1.services.ClientService;
import by.unil2.itstep.testSring1.services.VideoService;
import by.unil2.itstep.testSring1.utilits.CalcOptions;
import by.unil2.itstep.testSring1.utilits.loger.MyLogger;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.List;

@Controller
public class VideoController {

    private final VideoService videoService;
    private final MyLogger myLog;
    private final ClientService clientService;
    private final CalcOptions calcOpt;


    //constructor
    public VideoController(VideoService inpVideoService,
                          MyLogger inpMyLogger,
                          ClientService inpClientService,
                          CalcOptions inpCalcOptions){

        this.videoService =    inpVideoService;
        this.myLog =          inpMyLogger;
        this.clientService =  inpClientService;
        this.calcOpt =        inpCalcOptions;
        }

/*
            if (urlString.indexOf("/video/all") == 0) answer = ControlVideo.getVideoAll(netReq);

            if (urlString.indexOf("/video/file/") == 0) answer = ControlVideo.getVideoFile(netReq);
  */


    @PostMapping("/api/createvideo/{filename}")
    public ResponseEntity<?> postCreateVideo(@PathVariable(name="filename") String fileName,
                                             @CookieValue(value="ClientKey") String clientKey){

        //if not valid rootKey then send ERROR message
        if (!clientService.isRootKey(clientKey)) return ResponseEntity.ok().body(new ErrorMessage("Wrong root key"));

        try{
            videoService.createMP4(fileName);
            return ResponseEntity.ok().body("process of videoCreate is launch");

        } catch (Exception e) {
            myLog.error(e.getMessage());
            return ResponseEntity.ok().body(new ErrorMessage(e.getMessage()));
        }
    }//getNewTask


    @GetMapping("/video/all")
    public ResponseEntity<?> getVideoList(@CookieValue(value="ClientKey") String clientKey){

        //if not valid rootKey then send ERROR message
        if (!clientService.isRootKey(clientKey)) return ResponseEntity.ok().body(new ErrorMessage("Wrong root key"));

        try {
            List videoList= videoService.getVideoList();//send to service and get time of calculation
            return ResponseEntity.ok().body(videoList);
            } catch (Exception e) {
                myLog.error(e.getMessage());
                return new ResponseEntity<Error>(HttpStatus.INTERNAL_SERVER_ERROR);
                }
        }//getVideoList




}//VideoController



