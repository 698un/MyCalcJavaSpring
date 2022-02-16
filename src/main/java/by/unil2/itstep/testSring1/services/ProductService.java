package by.unil2.itstep.testSring1.services;


import by.unil2.itstep.testSring1.models.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    private List<Product> products = new ArrayList<>();
    private long ID=0;

    //FirstStatic method for init objects
    {
        Product p1 =new Product(++ID,  "PS5", "simple_descr", 1000,"Gorod", "tomas");
        products.add(p1);

        Product p2 =new Product(++ID, "xBox", "Nosimple_descr", 2000, "stadt", "jon");
        products.add(p2);
        }//static





    public List<Product> getList() {return products;}

    public void saveProduct(Product inpProduct){
        inpProduct.setId(++ID);
        products.add(inpProduct);
        }



    public void deleteProduct(Long inpId){
        products.removeIf(product->product.getId().equals(inpId));

        }


}//class ProductService
