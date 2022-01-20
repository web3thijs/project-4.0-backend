package fact.it.backend.repository;

import fact.it.backend.model.OrderDetail;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailRepository extends MongoRepository<OrderDetail, String> {
    List<OrderDetail> findAll();
    List<OrderDetail> findOrderDetailsByOrderId(String orderId);
    OrderDetail findOrderDetailById(String id);
}
