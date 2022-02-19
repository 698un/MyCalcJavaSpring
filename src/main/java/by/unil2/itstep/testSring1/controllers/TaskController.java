package by.unil2.itstep.testSring1.controllers;


import by.unil2.itstep.testSring1.controllers.webentity.NewTask;
import by.unil2.itstep.testSring1.controllers.webentity.ServerStatus;
import by.unil2.itstep.testSring1.exceptions.AccessException;
import by.unil2.itstep.testSring1.services.ClientService;
import by.unil2.itstep.testSring1.services.SystemService;
import by.unil2.itstep.testSring1.services.TaskService;
import by.unil2.itstep.testSring1.utilits.CalcOptions;
import by.unil2.itstep.testSring1.utilits.loger.MyLogger;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletResponse;

@Controller
public class TaskController {

    private final TaskService taskService;
    private final MyLogger myLog;
    private final ClientService clientService;


    //constructor
    public TaskController(TaskService inpTaskService,
                          MyLogger inpMyLogger,
                          ClientService inpClientService){

        this.taskService =    inpTaskService;
        this.myLog =          inpMyLogger;
        this.clientService =  inpClientService;
    }


    @GetMapping("/newtask")
    public ResponseEntity<?> getNewTask(@CookieValue(value="ClientKey") String clientKey){

        try{
            NewTask newTaskForClient = taskService.getNextTask(clientKey);
            return ResponseEntity.ok().body(newTaskForClient);

        } catch (AccessException e) {
            //return new ResponseEntity<Error>(HttpStatus.ACCEPTED);
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return new ResponseEntity<Error>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
    }//getNewTask


    @PostMapping("/newtask")
    public ResponseEntity<?> getNewTask(@CookieValue(value="ClientKey") String clientKey){

        try{
            NewTask newTaskForClient = taskService.getNextTask(clientKey);
            return ResponseEntity.ok().body(newTaskForClient);

        } catch (AccessException e) {
            //return new ResponseEntity<Error>(HttpStatus.ACCEPTED);
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return new ResponseEntity<Error>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }//getNewTask







}//InfoController



