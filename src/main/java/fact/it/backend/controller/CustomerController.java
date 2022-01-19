package fact.it.backend.controller;

import fact.it.backend.model.Customer;
import fact.it.backend.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
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
                customerRepository.save(new Customer("FirstName" + i, "LastName" + i, "test" + i + "@test.test", "hash", "+324000000", "testaddress" + i, "2400", "Country" + i, false));
            }
        }
    }

    @GetMapping("")
    public List<Customer> findAll() { return customerRepository.findAll(); }

    @PostMapping("")
    public Customer addCustomer(@RequestBody Customer customer){
        customerRepository.save(customer);
        return customer;
    }
}
