package fact.it.backend.controller;

import fact.it.backend.model.*;
import fact.it.backend.repository.CategoryRepository;
import fact.it.backend.repository.ProductRepository;
import fact.it.backend.util.JwtUtils;
import org.apache.coyote.Response;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

@RequestMapping(path = "api/products")
@RestController
public class ProductController {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @GetMapping("")
    public Page<Product> findAll(@RequestParam int page, @RequestParam(required = false) String sort, @RequestParam(required = false) String order){
        if(sort != null){
            if(order != null){
                Pageable requestedPageWithSortDesc = PageRequest.of(page, 8, Sort.by(sort).descending());
                Page<Product> products = productRepository.findAll(requestedPageWithSortDesc);
                return products;
            }
            else{
                Pageable requestedPageWithSort = PageRequest.of(page, 8, Sort.by(sort).ascending());
                Page<Product> products = productRepository.findAll(requestedPageWithSort);
                return products;
            }
        }else{
            Pageable requestedPage = PageRequest.of(page, 8, Sort.by("name").ascending());
            Page<Product> products = productRepository.findAll(requestedPage);
            return products;
        }
    }

    @GetMapping("/organization/{organizationId}")
    public Page<Product> findProductsByOrganizationId(@PathVariable String organizationId, @RequestParam int page, @RequestParam(required = false) String sort){
        if(sort != null){
            Pageable requestedPageWithSort = PageRequest.of(page, 8, Sort.by(sort).ascending());
            Page<Product> productsByOrganization = productRepository.findProductsByOrganizationId(organizationId, requestedPageWithSort);
            return productsByOrganization;
        }else{
            Pageable requestedPage = PageRequest.of(page, 8);
            Page<Product> productsByOrganization = productRepository.findProductsByOrganizationId(organizationId, requestedPage);
            return productsByOrganization;
        }
    }

    @GetMapping("/{id}")
    public Product find(@PathVariable String id){

        return productRepository.findProductById(id);
    }

    @PostMapping("")
    public ResponseEntity<?> addProduct(@RequestHeader("Authorization") String tokenWithPrefix, @RequestBody Product product){
        String token = tokenWithPrefix.substring(7);
        Map<String, Object> claims = jwtUtils.extractAllClaims(token);
        String role = claims.get("role").toString();
        String user_id = claims.get("user_id").toString();

        if(role.contains("ADMIN") || (role.contains("ORGANIZATION") && product.getOrganization().getId().contains(user_id))){
            productRepository.save(product);
            return ResponseEntity.ok(product);
        } else {
            return new ResponseEntity<String>("Forbidden", HttpStatus.FORBIDDEN);
        }
    }

    @PutMapping("")
    public ResponseEntity<?> updateProduct(@RequestHeader("Authorization") String tokenWithPrefix, @RequestBody Product updatedProduct){
        String token = tokenWithPrefix.substring(7);
        Map<String, Object> claims = jwtUtils.extractAllClaims(token);
        String role = claims.get("role").toString();
        String user_id = claims.get("user_id").toString();

        if(role.contains("ADMIN") || (role.contains("ORGANIZATION") && updatedProduct.getOrganization().getId().contains(user_id))){
            Product retrievedProduct = productRepository.findProductById(updatedProduct.getId());

            retrievedProduct.setCategory(updatedProduct.getCategory());
            retrievedProduct.setOrganization(updatedProduct.getOrganization());
            retrievedProduct.setName(updatedProduct.getName());
            retrievedProduct.setPrice(updatedProduct.getPrice());
            retrievedProduct.setDescription(updatedProduct.getDescription());
            retrievedProduct.setActive(updatedProduct.getActive());
            retrievedProduct.setImageUrl(updatedProduct.getImageUrl());

            productRepository.save(retrievedProduct);
            return ResponseEntity.ok(retrievedProduct);
        } else {
            return new ResponseEntity<String>("Forbidden", HttpStatus.FORBIDDEN);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteProduct(@RequestHeader("Authorization") String tokenWithPrefix, @PathVariable String id){
        String token = tokenWithPrefix.substring(7);
        Map<String, Object> claims = jwtUtils.extractAllClaims(token);
        String role = claims.get("role").toString();

        if(role.contains("ADMIN")){
            Product product = productRepository.findProductById(id);

            if(product != null){
                productRepository.delete(product);
                return ResponseEntity.ok().build();
            } else{
                return ResponseEntity.notFound().build();
            }
        } else {
            return new ResponseEntity<String>("Forbidden", HttpStatus.FORBIDDEN);
        }
    }
}