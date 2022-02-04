package fact.it.backend.controller;

import fact.it.backend.model.*;
import fact.it.backend.repository.CategoryRepository;
import fact.it.backend.repository.OrganizationRepository;
import fact.it.backend.repository.ProductRepository;
import fact.it.backend.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "api/products")
public class ProductController {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    OrganizationRepository organizationRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @GetMapping
    public Page<Product> findAll(@RequestParam(required = false, defaultValue = "0") Integer page, @RequestParam(required = false, defaultValue = "name") String sort, @RequestParam(required = false) String order, @RequestParam(required = false)String categorie, @RequestParam(required = false) String vzw, @RequestParam(required = false)Double prijsgt, @RequestParam(required = false)Double prijslt ){
            if(order != null && order.equals("desc")){
                Pageable requestedPageWithSortDesc = PageRequest.of(page, 9, Sort.by(sort).descending());
                Page<Product> products = productRepository.findAll(requestedPageWithSortDesc);
                return products;
            }
            else{
                Pageable requestedPageWithSort = PageRequest.of(page, 9, Sort.by(sort).ascending());
                Page<Product> products = productRepository.findAll(requestedPageWithSort);
                return products;
            }
        }
    @GetMapping("/organization/{organizationId}")
    public Page<Product> findProductsByOrganizationId(@PathVariable long organizationId, @RequestParam(required = false, defaultValue = "0") int page, @RequestParam(required = false, defaultValue = "name") String sort, @RequestParam(required = false)String order){
            if(order != null && order.equals("desc")){
                Pageable requestedPageWithSortDesc = PageRequest.of(page, 9, Sort.by(sort).descending());
                Page<Product> products = productRepository.findProductsByOrganizationId(organizationId,requestedPageWithSortDesc);
                return products;
            }
            else{
                Pageable requestedPageWithSort = PageRequest.of(page, 9, Sort.by(sort).ascending());
                Page<Product> products = productRepository.findProductsByOrganizationId(organizationId,requestedPageWithSort);
                return products;
            }
        }

    @GetMapping("/{id}")
    public Product find(@PathVariable long id){

        return productRepository.findProductById(id);
    }

    @PostMapping
    public ResponseEntity<?> addProduct(@RequestHeader("Authorization") String tokenWithPrefix, @RequestBody Product product){
        String token = tokenWithPrefix.substring(7);
        Map<String, Object> claims = jwtUtils.extractAllClaims(token);
        String role = claims.get("role").toString();
        long user_id = Long.parseLong(claims.get("user_id").toString());

        if(role.contains("ADMIN") || (role.contains("ORGANIZATION") && product.getOrganization().getId() == user_id)){
            productRepository.save(product);
            return ResponseEntity.ok(product);
        } else {
            return new ResponseEntity<String>("Forbidden", HttpStatus.FORBIDDEN);
        }
    }

    @PutMapping
    public ResponseEntity<?> updateProduct(@RequestHeader("Authorization") String tokenWithPrefix, @RequestBody Product updatedProduct){
        String token = tokenWithPrefix.substring(7);
        Map<String, Object> claims = jwtUtils.extractAllClaims(token);
        String role = claims.get("role").toString();
        long user_id = Long.parseLong(claims.get("user_id").toString());

        if(role.contains("ADMIN") || (role.contains("ORGANIZATION") && updatedProduct.getOrganization().getId() == user_id)){
            Product retrievedProduct = productRepository.findProductById(updatedProduct.getId());

            retrievedProduct.setCategory(categoryRepository.findCategoryById(updatedProduct.getCategory().getId()));
            retrievedProduct.setOrganization(organizationRepository.findOrganizationById(updatedProduct.getOrganization().getId()));
            retrievedProduct.setName(updatedProduct.getName());
            retrievedProduct.setPrice(updatedProduct.getPrice());
            retrievedProduct.setDescription(updatedProduct.getDescription());
            retrievedProduct.setActive(updatedProduct.isActive());
            retrievedProduct.setImageUrl(updatedProduct.getImageUrl());

            productRepository.save(retrievedProduct);
            return ResponseEntity.ok(retrievedProduct);
        } else {
            return new ResponseEntity<String>("Forbidden", HttpStatus.FORBIDDEN);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteProduct(@RequestHeader("Authorization") String tokenWithPrefix, @PathVariable long id){
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