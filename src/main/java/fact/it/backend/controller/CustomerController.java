package fact.it.backend.controller;

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
import java.util.Map;

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

    @PostConstruct
    public void fillDatabase(){
        String password = passwordEncoder.encode("Password123");

        customerRepository.save(new Customer("giannideherdt@gmail.com", password, "0479994529", "Belgium", "2200", "Kersstraat 17", Role.ADMIN, "Gianni" , "De Herdt"));
        customerRepository.save(new Customer("thijswouters@gmail.com", password, "0479954719", "Belgium", "1680", "Hoekstraat 165", Role.ADMIN, "Thijs" , "Wouters"));
        customerRepository.save(new Customer("jolienfoets@gmail.com", password, "0466544922", "Belgium", "1700", "Stepelaar 6A", Role.CUSTOMER, "Jolien" , "Foets"));
        customerRepository.save(new Customer("boblourdaux@gmail.com", password, "0495946569", "Belgium", "3040", "Sint-Schepersberg 45", Role.CUSTOMER, "Bob" , "Lourdaux"));
        customerRepository.save(new Customer("kevinmaes@gmail.com", password, "0476281912", "Belgium", "2260", "Lambertuslaan 42", Role.CUSTOMER, "Kevin" , "Maes"));
        customerRepository.save(new Customer("helderceyssens@gmail.com", password, "0476596168", "Belgium", "1540", "Koepel 186", Role.CUSTOMER, "Helder" , "Ceyssens"));
    }

    @GetMapping
    public ResponseEntity<?> findAll(@RequestHeader("Authorization") String tokenWithPrefix, @RequestParam(required = false, defaultValue = "0") Integer page, @RequestParam(required = false, defaultValue = "email") String sort, @RequestParam(required = false) String order){
        String token = tokenWithPrefix.substring(7);
        Map<String, Object> claims = jwtUtils.extractAllClaims(token);
        String role = claims.get("role").toString();
        if(role.contains("ADMIN")){
                if(order != null && order.equals("desc")){
                    Pageable requestedPageWithSortDesc = PageRequest.of(page, 9, Sort.by(sort).descending());
                    Page<Customer> customers = customerRepository.findByRole(Role.CUSTOMER, requestedPageWithSortDesc);
                    return ResponseEntity.ok(customers);
                }
                else{
                    Pageable requestedPageWithSort = PageRequest.of(page, 9, Sort.by(sort).ascending());
                    Page<Customer> customers = customerRepository.findByRole(Role.CUSTOMER, requestedPageWithSort);
                    return ResponseEntity.ok(customers);
                }

        } else {
            return new ResponseEntity<String>("Forbidden", HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@RequestHeader("Authorization") String tokenWithPrefix, @PathVariable long id){
        String token = tokenWithPrefix.substring(7);
        Map<String, Object> claims = jwtUtils.extractAllClaims(token);
        String role = claims.get("role").toString();
        long user_id = Long.parseLong(claims.get("user_id").toString());

        if(role.contains("ADMIN") || (role.contains("CUSTOMER") && id == user_id)){
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
        long user_id = Long.parseLong(claims.get("user_id").toString());

        if(role.contains("ADMIN") || (role.contains("CUSTOMER") && updatedCustomer.getId() == user_id)){
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
    public ResponseEntity<?> deleteCustomer(@RequestHeader("Authorization") String tokenWithPrefix, @PathVariable long id){
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
