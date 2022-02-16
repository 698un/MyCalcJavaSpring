package by.unil2.itstep.testSring1.controllers.webpages;


import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller

public class adminController {


    @GetMapping()
    public String getPageAdmin(HttpRequest request){

        //String url = request.getURI();


       return null;

    }//getPageAdmin

}
