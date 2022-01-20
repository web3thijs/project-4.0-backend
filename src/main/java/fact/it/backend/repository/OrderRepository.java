package fact.it.backend.repository;

import fact.it.backend.model.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends MongoRepository<Order, String> {
    List<Order> findAll();
    List<Order> findOrdersByCustomerId(String customerId);
    Order findOrderById(String id);
}
