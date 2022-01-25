package fact.it.backend.repository;

import fact.it.backend.model.OrderDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailRepository extends MongoRepository<OrderDetail, String> {
    Page<OrderDetail> findAll(Pageable pageable);
    List<OrderDetail> findOrderDetailsByOrderId(String orderId);
    OrderDetail findOrderDetailById(String id);
}
