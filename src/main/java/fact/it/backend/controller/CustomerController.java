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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;

@RequestMapping(path = "api/customers")
@RestController
public class CustomerController {
    @Autowired
    CustomerRepository customerRepository;

    @PostConstruct
    public void fillDB(){
        if(customerRepository.count() == 0){
            for(int i = 1; i < 4; i++){
                customerRepository.save(new Customer("test" + i + "@test.test", "password", "phoneNr", "address", "postalCode", "country", Role.CUSTOMER,"user" + i, "lastName", false));
            }
        }

        System.out.println("DB test customers: " + customerRepository.findAll().size());
    }

    @GetMapping("")
    public List<Customer> findAll(){
        return customerRepository.findByRole(Role.CUSTOMER);
    }
}
