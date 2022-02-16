package by.unil2.itstep.testSring1.models;

//import lombok.AllArgsConstructor;
//import lombok.Data;

import org.springframework.context.annotation.Bean;

//import javax.persistence.Entity;

//@Data
//@AllArgsConstructor

public class Product {
    Long id;
    private String title;
    private String description;
    private int price;
    private String city;
    private String author;

    public Product(Long inpId,
                   String inpTitle,
                   String inpDescription,
                   int inpPrice,
                   String inpCity,
                   String inpAuthor){

        this.id = inpId;
        this.title = inpTitle;
        this.description = inpDescription;
        this.price = inpPrice;
        this.city = inpCity;
        this.author = inpAuthor;

        }//Product

    public void setId(Long inpId){this.id = inpId;}
    public Long getId(){return this.id;}



    }
