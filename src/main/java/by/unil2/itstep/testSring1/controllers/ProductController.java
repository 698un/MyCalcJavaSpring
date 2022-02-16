package by.unil2.itstep.testSring1.controllers;


import by.unil2.itstep.testSring1.services.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class ProductController {

    private final ProductService productService;

    Logger log = LoggerFactory.getLogger(ClientController.class);

    //constructor
    public ProductController(ProductService productService){
        this.productService = productService;
        }

    @GetMapping("/products")
    public String products(){
        return "products";
        }




    }
