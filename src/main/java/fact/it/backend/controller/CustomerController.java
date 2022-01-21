package fact.it.backend.controller;

import fact.it.backend.model.Customer;
import fact.it.backend.model.Product;
import fact.it.backend.model.Role;
import fact.it.backend.model.User;
import fact.it.backend.repository.CustomerRepository;
import fact.it.backend.repository.ProductRepository;
import fact.it.backend.repository.CustomerRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;

@RequestMapping(path = "api/customers")
@RestController
public class CustomerController {
    @Autowired
    CustomerRepository customerRepository;

    @GetMapping("")
    public List<Customer> findAll(){
        return customerRepository.findByRole(Role.CUSTOMER);
    }

    @GetMapping("/{id}")
    public Customer findById(@PathVariable String id){
        return customerRepository.findByRoleAndId(Role.CUSTOMER, id);
    }

    @PostMapping("")
    public Customer addCustomer(@RequestBody Customer customer) {
        customerRepository.save(customer);
        return customer;
    }

    @PutMapping
    public Customer updateCustomer(@RequestBody Customer updatedCustomer){
        Customer retrievedCustomer = customerRepository.findByRoleAndId(Role.CUSTOMER, updatedCustomer.getId());

        retrievedCustomer.setEmail(updatedCustomer.getEmail());
        retrievedCustomer.setPassword(updatedCustomer.getPassword());
        retrievedCustomer.setPhoneNr(updatedCustomer.getPhoneNr());
        retrievedCustomer.setAddress(updatedCustomer.getAddress());
        retrievedCustomer.setPostalCode(updatedCustomer.getPostalCode());
        retrievedCustomer.setCountry(updatedCustomer.getCountry());
        retrievedCustomer.setRole(updatedCustomer.getRole());
        retrievedCustomer.setFirstName(updatedCustomer.getFirstName());
        retrievedCustomer.setLastName(updatedCustomer.getLastName());
        retrievedCustomer.setAdmin(updatedCustomer.isAdmin());

        customerRepository.save(retrievedCustomer);

        return retrievedCustomer;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteCustomer(@PathVariable String id){
        Customer customer = customerRepository.findByRoleAndId(Role.CUSTOMER, id);

        if(customer != null){
            customerRepository.delete(customer);
            return ResponseEntity.ok().build();
        } else{
            return ResponseEntity.notFound().build();
        }
    }
}
