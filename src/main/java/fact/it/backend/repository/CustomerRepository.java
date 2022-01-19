package fact.it.backend.repository;

import fact.it.backend.model.Customer;
import fact.it.backend.model.Role;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends MongoRepository<Customer, String> {
    List<Customer> findByRole(Role role);
}
