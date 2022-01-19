package fact.it.backend.controller;

import fact.it.backend.model.Product;
import fact.it.backend.repository.ProductRepository;
import org.apache.coyote.Response;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;

@RequestMapping(path = "api/products")
@RestController
public class ProductController {

    @Autowired
    ProductRepository productRepository;

    @PostConstruct
    public void fillDB(){
        if(productRepository.count() == 0){
            productRepository.save(new Product(new ObjectId().toString(), new ObjectId().toString(), new ObjectId().toString(), "T-shirt", 13.99, "Plain T-shirt", true, "Google.com"));
            productRepository.save(new Product(new ObjectId().toString(), new ObjectId().toString(), new ObjectId().toString(), "Jeans", 23.99, "Plain Jeans", true, "Google.com"));
        }

        System.out.println("DB test products: " + productRepository.findAll().size() + " products.");
    }

    @GetMapping("")
    public List<Product> findAll(){
        return productRepository.findAll();
    }

    @GetMapping("/organization/{organizationId}")
    public List<Product> findProductsByOrganizationId(@PathVariable String organizationId){
        return productRepository.findProductsByOrganizationId(organizationId);
    }

    @GetMapping("/{id}")
    public Product find(@PathVariable String id){
        return productRepository.findProductById(id);
    }

    @PostMapping("")
    public Product addProduct(@RequestBody Product product){
        productRepository.save(product);
        return product;
    }

    @PutMapping("")
    public Product updateProduct(@RequestBody Product updatedProduct){
        Product retrievedProduct = productRepository.findProductById(updatedProduct.getId());

        retrievedProduct.setCategoryId(updatedProduct.getCategoryId());
        retrievedProduct.setOrganizationId(updatedProduct.getOrganizationId());
        retrievedProduct.setName(updatedProduct.getName());
        retrievedProduct.setPrice(updatedProduct.getPrice());
        retrievedProduct.setDescription(updatedProduct.getDescription());
        retrievedProduct.setActive(updatedProduct.getActive());
        retrievedProduct.setImageUrl(updatedProduct.getImageUrl());

        productRepository.save(retrievedProduct);

        return retrievedProduct;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteProduct(@PathVariable String id){
        Product product = productRepository.findProductById(id);

        if(product != null){
            productRepository.delete(product);
            return ResponseEntity.ok().build();
        } else{
            return ResponseEntity.notFound().build();
        }
    }
}
