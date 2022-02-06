package fact.it.backend.repository;

import fact.it.backend.model.Customer;
import fact.it.backend.model.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Page<Customer> findByRole(Role role, Pageable pageable);
    List<Customer> findByRole(Role role);
    Customer findByRoleAndId(Role role, long id);
    Customer findById(long id);

    Customer findCustomerById(Long customerId);
}
