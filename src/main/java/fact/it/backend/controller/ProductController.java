package fact.it.backend.controller;

import fact.it.backend.exception.ResourceNotFoundException;
import fact.it.backend.model.*;
import fact.it.backend.repository.CategoryRepository;
import fact.it.backend.repository.OrganizationRepository;
import fact.it.backend.repository.ProductRepository;
import fact.it.backend.util.JwtUtils;
import org.apache.coyote.Response;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
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
    public ResponseEntity<?> findAll(@RequestParam(required = false, defaultValue = "0") Integer page, @RequestParam(required = false, defaultValue = "name") String sort, @RequestParam(required = false) String order, @RequestParam(required = false, defaultValue = "0")long categorie, @RequestParam(required = false, defaultValue = "0") long vzw, @RequestParam(required = false, defaultValue = "0")long prijsgt, @RequestParam(required = false, defaultValue = "999999999999")long prijslt, @RequestParam(required = false, defaultValue = "%%") String naam){

        if(order != null && order.equals("desc")){
                Pageable requestedPageWithSortDesc = PageRequest.of(page, 8, Sort.by(sort).descending());
                JSONObject products = productRepository.filterProductsBasedOnKeywords(categorie, vzw, prijsgt, prijslt, naam, requestedPageWithSortDesc);
                return ResponseEntity.ok(products);
            }
            else{
                Pageable requestedPageWithSort = PageRequest.of(page, 8, Sort.by(sort).ascending());
                JSONObject products =  productRepository.filterProductsBasedOnKeywords(categorie, vzw, prijsgt, prijslt, naam, requestedPageWithSort);
                return ResponseEntity.ok(products);
            }
        }
    @GetMapping("/organization/{organizationId}")
    public JSONObject findProductsByOrganizationId(@PathVariable long organizationId, @RequestParam(required = false, defaultValue = "0") int page, @RequestParam(required = false, defaultValue = "name") String sort, @RequestParam(required = false)String order) throws ResourceNotFoundException {
        organizationRepository.findById(organizationId)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find products. Organization not found for this id: " + organizationId));
        if(order != null && order.equals("desc")){
                Pageable requestedPageWithSortDesc = PageRequest.of(page, 8, Sort.by(sort).descending());
                JSONObject products = productRepository.filterProductsOrganizationId(organizationId,requestedPageWithSortDesc);
                return products;
            }
            else{
                Pageable requestedPageWithSort = PageRequest.of(page, 8, Sort.by(sort).ascending());
                JSONObject products = productRepository.filterProductsOrganizationId(organizationId,requestedPageWithSort);
                return products;
            }
        }

    @GetMapping("/{id}")
    public ResponseEntity<?> find(@PathVariable long id) throws ResourceNotFoundException {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found for this id: " + id));
        return ResponseEntity.ok().body(product);
    }

    @PostMapping
    public ResponseEntity<?> addProduct(@RequestHeader("Authorization") String tokenWithPrefix, @Valid @RequestBody Product product){
        String token = tokenWithPrefix.substring(7);
        Map<String, Object> claims = jwtUtils.extractAllClaims(token);
        String role = claims.get("role").toString();
        long user_id = Long.parseLong(claims.get("user_id").toString());

        if(role.contains("ADMIN") || (role.contains("ORGANIZATION") && product.getOrganization().getId() == user_id)){
            productRepository.save(product);
            return ResponseEntity.ok(product);
        } else {
            return new ResponseEntity<String>("Not authorized", HttpStatus.FORBIDDEN);
        }
    }

    @PutMapping
    public ResponseEntity<?> updateProduct(@RequestHeader("Authorization") String tokenWithPrefix, @Valid @RequestBody Product updatedProduct) throws ResourceNotFoundException {
        String token = tokenWithPrefix.substring(7);
        Map<String, Object> claims = jwtUtils.extractAllClaims(token);
        String role = claims.get("role").toString();
        long user_id = Long.parseLong(claims.get("user_id").toString());

        if(role.contains("ADMIN") || (role.contains("ORGANIZATION") && updatedProduct.getOrganization().getId() == user_id)){
            Product retrievedProduct = productRepository.findById(updatedProduct.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Cannot update. Product not found for this id: " + updatedProduct.getId()));

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
            return new ResponseEntity<String>("Not authorized", HttpStatus.FORBIDDEN);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteProduct(@RequestHeader("Authorization") String tokenWithPrefix, @PathVariable long id) throws ResourceNotFoundException {
        String token = tokenWithPrefix.substring(7);
        Map<String, Object> claims = jwtUtils.extractAllClaims(token);
        String role = claims.get("role").toString();

        if(role.contains("ADMIN")){
            Product product = productRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Cannot update. category not found for this id: " + id));

                productRepository.delete(product);
                return ResponseEntity.ok().build();

        } else {
            return new ResponseEntity<String>("Not authorized", HttpStatus.FORBIDDEN);
        }
    }
}