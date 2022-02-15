package fact.it.backend.controller;

import fact.it.backend.exception.ResourceNotFoundException;
import fact.it.backend.model.*;
import fact.it.backend.repository.CustomerRepository;
import fact.it.backend.util.JwtUtils;
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
import javax.validation.Valid;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping(path = "api/customers")
public class CustomerController {
    @Autowired
    CustomerRepository customerRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtils jwtUtils;

    public CustomerController(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public ResponseEntity<?> findAll(@RequestHeader("Authorization") String tokenWithPrefix, @RequestParam(required = false, defaultValue = "0") Integer page, @RequestParam(required = false, defaultValue = "email") String sort, @RequestParam(required = false) String order){
        String token = tokenWithPrefix.substring(7);
        Map<String, Object> claims = jwtUtils.extractAllClaims(token);
        String role = claims.get("role").toString();
        if(role.contains("ADMIN")){
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

        } else {
            return new ResponseEntity<String>("Forbidden", HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@RequestHeader("Authorization") String tokenWithPrefix, @PathVariable long id) throws ResourceNotFoundException{
        String token = tokenWithPrefix.substring(7);
        Map<String, Object> claims = jwtUtils.extractAllClaims(token);
        String role = claims.get("role").toString();
        long user_id = Long.parseLong(claims.get("user_id").toString());

        if(role.contains("ADMIN") || (role.contains("CUSTOMER") && id == user_id)){
            Customer customer = customerRepository.findByRoleAndId(Role.CUSTOMER, id);
            if(customer != null){
                return ResponseEntity.ok(customer);
            }
            else{
                throw new ResourceNotFoundException("Customer not found for this id: " + id);
            }
        } else {
            return new ResponseEntity<String>("Forbidden", HttpStatus.FORBIDDEN);
        }
    }

    @PutMapping
    public ResponseEntity<?> updateCustomer(@RequestHeader("Authorization") String tokenWithPrefix, @Valid @RequestBody Customer updatedCustomer) throws ResourceNotFoundException {
        String token = tokenWithPrefix.substring(7);
        Map<String, Object> claims = jwtUtils.extractAllClaims(token);
        String role = claims.get("role").toString();
        long user_id = Long.parseLong(claims.get("user_id").toString());

        if(role.contains("ADMIN") || (role.contains("CUSTOMER") && updatedCustomer.getId() == user_id)){
            Customer retrievedCustomer = customerRepository.findByRoleAndId(Role.CUSTOMER, updatedCustomer.getId());
            if(retrievedCustomer != null){
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
            }
            else{
                throw new ResourceNotFoundException("Cannot update. Customer not found for this id: " + updatedCustomer.getId());
            }

        } else {
            return new ResponseEntity<String>("Not authorized", HttpStatus.FORBIDDEN);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCustomer(@RequestHeader("Authorization") String tokenWithPrefix, @PathVariable long id) throws ResourceNotFoundException {
        String token = tokenWithPrefix.substring(7);
        Map<String, Object> claims = jwtUtils.extractAllClaims(token);
        String role = claims.get("role").toString();

        if(role.contains("ADMIN")){
            Customer customer = customerRepository.findByRoleAndId(Role.CUSTOMER, id);

            if(customer != null){
                customerRepository.delete(customer);
                return ResponseEntity.ok().build();
            } else{
                throw new ResourceNotFoundException("Cannot delete. Customer not found for this id: " + id);
            }

        } else {
            return new ResponseEntity<String>("Not authorized", HttpStatus.FORBIDDEN);
        }
    }
}
