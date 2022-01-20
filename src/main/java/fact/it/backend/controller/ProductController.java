package fact.it.backend.controller;

import fact.it.backend.model.Category;
import fact.it.backend.model.Organization;
import fact.it.backend.model.Product;
import fact.it.backend.model.Role;
import fact.it.backend.repository.CategoryRepository;
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
    CategoryRepository categoryRepository;

    @PostConstruct
    public void fillDB(){
        if(productRepository.count() == 0){
            Category category1 = new Category("61e7e7ba38d73c28cb36c3eb", "shirts");
            Category category2 = new Category("61e7e7ba38d73c28cb36c3ec", "pants");
            Organization organization1 = new Organization("supporters@wwf.be", "wwf123", "+3223400920", "Emile Jacqmainlaan 90", "1000", "Belgium", Role.ORGANIZATION, "WWF", "BE0408656248", "BE0408656248", "Het World Wide Fund for Nature – waarvan de Nederlandse tak Wereld Natuur Fonds heet en de Amerikaanse World Wildlife Fund – is een wereldwijd opererende organisatie voor bescherming van de natuur", "+3223400920", "supporters@wwf.be");
            productRepository.save(new Product("61e6c2c183f852129f4ffff3", category1, organization1 , "T-shirt", 13.99, "Plain T-shirt", true, "Google.com"));
            productRepository.save(new Product("61e6c2c183f852129f4ffff4", category2, organization1, "Jeans", 23.99, "Plain Jeans", true, "Google.com"));
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

        retrievedProduct.setCategory(updatedProduct.getCategory());
        retrievedProduct.setOrganization(updatedProduct.getOrganization());
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
