package fact.it.backend.repository;

import fact.it.backend.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends MongoRepository<Order, String> {
    Page<Order> findAll(Pageable pageable);
    Page<Order> findOrdersByCustomerId(String customerId, Pageable pageable);
    Order findOrderById(String id);
}
