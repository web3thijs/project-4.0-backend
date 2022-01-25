package fact.it.backend.controller;

import fact.it.backend.model.*;
import fact.it.backend.repository.CustomerRepository;
import fact.it.backend.repository.ProductRepository;
import fact.it.backend.repository.CustomerRepository;
import fact.it.backend.util.JwtUtils;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@RequestMapping(path = "api/customers")
@RestController
public class CustomerController {
    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtils jwtUtils;

    @GetMapping("")
    public ResponseEntity<?> findAll(@RequestHeader("Authorization") String tokenWithPrefix, @RequestParam int page, @RequestParam(required = false) String sort, @RequestParam(required = false) String order){

        String token = tokenWithPrefix.substring(7);
        Map<String, Object> claims = jwtUtils.extractAllClaims(token);
        String role = claims.get("role").toString();
        if(role.contains("ADMIN")){
            if(sort != null){
                if(order != null && order.equals("desc")){
                    Pageable requestedPageWithSortDesc = PageRequest.of(page, 8, Sort.by(sort).descending());
                    Page<Customer> customers = customerRepository.findByRole(Role.CUSTOMER, requestedPageWithSortDesc);
                    return ResponseEntity.ok(customers);
                }
                else{
                    Pageable requestedPageWithSort = PageRequest.of(page, 8, Sort.by(sort).ascending());
                    Page<Customer> customers = customerRepository.findByRole(Role.CUSTOMER, requestedPageWithSort);
                    return ResponseEntity.ok(customers);
                }
            }else{
                Pageable requestedPage = PageRequest.of(page, 8, Sort.by("name").ascending());
                Page<Customer> customers = customerRepository.findByRole(Role.CUSTOMER, requestedPage);
                return ResponseEntity.ok(customers);
            }
        } else {
            return new ResponseEntity<String>("Forbidden", HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@RequestHeader("Authorization") String tokenWithPrefix, @PathVariable String id){
        String token = tokenWithPrefix.substring(7);
        Map<String, Object> claims = jwtUtils.extractAllClaims(token);
        String role = claims.get("role").toString();
        String user_id = claims.get("user_id").toString();

        if(role.contains("ADMIN") || (role.contains("CUSTOMER") && id.contains(user_id))){
            return ResponseEntity.ok(customerRepository.findByRoleAndId(Role.CUSTOMER, id));
        } else {
            return new ResponseEntity<String>("Forbidden", HttpStatus.FORBIDDEN);
        }
    }

    @PutMapping
    public ResponseEntity<?> updateCustomer(@RequestHeader("Authorization") String tokenWithPrefix, @RequestBody Customer updatedCustomer){
        String token = tokenWithPrefix.substring(7);
        Map<String, Object> claims = jwtUtils.extractAllClaims(token);
        String role = claims.get("role").toString();
        String user_id = claims.get("user_id").toString();

        if(role.contains("ADMIN") || (role.contains("CUSTOMER") && updatedCustomer.getId().contains(user_id))){
            Customer retrievedCustomer = customerRepository.findByRoleAndId(Role.CUSTOMER, updatedCustomer.getId());

            retrievedCustomer.setEmail(updatedCustomer.getEmail());
            retrievedCustomer.setPassword(passwordEncoder.encode(updatedCustomer.getPassword()));
            retrievedCustomer.setPhoneNr(updatedCustomer.getPhoneNr());
            retrievedCustomer.setAddress(updatedCustomer.getAddress());
            retrievedCustomer.setPostalCode(updatedCustomer.getPostalCode());
            retrievedCustomer.setCountry(updatedCustomer.getCountry());
            retrievedCustomer.setRole(updatedCustomer.getRole());
            retrievedCustomer.setFirstName(updatedCustomer.getFirstName());
            retrievedCustomer.setLastName(updatedCustomer.getLastName());

            customerRepository.save(retrievedCustomer);

            return ResponseEntity.ok(retrievedCustomer);
        } else {
            return new ResponseEntity<String>("Forbidden", HttpStatus.FORBIDDEN);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCustomer(@RequestHeader("Authorization") String tokenWithPrefix, @PathVariable String id){
        String token = tokenWithPrefix.substring(7);
        Map<String, Object> claims = jwtUtils.extractAllClaims(token);
        String role = claims.get("role").toString();

        if(role.contains("ADMIN")){
            Customer customer = customerRepository.findByRoleAndId(Role.CUSTOMER, id);

            if(customer != null){
                customerRepository.delete(customer);
                return ResponseEntity.ok().build();
            } else{
                return ResponseEntity.notFound().build();
            }
        } else {
            return new ResponseEntity<String>("Forbidden", HttpStatus.FORBIDDEN);
        }
    }
}
