package fact.it.backend.repository;

import fact.it.backend.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Page<Order> findAll(Pageable pageable);
    List<Order> findOrdersByCustomerId(long customerId);
    Order findOrderById(long id);
}
