package fact.it.backend.repository;

import fact.it.backend.model.OrderDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
    Page<OrderDetail> findAll(Pageable pageable);
    List<OrderDetail> findOrderDetailsByOrderId(long orderId);
    OrderDetail findOrderDetailById(long id);
    OrderDetail findOrderDetailBySizeIdAndColorIdAndProductIdAndOrderId(Long size_id, long color_id, long product_id, long order_id);
    List<OrderDetail> findOrderDetailsBySizeIdAndColorIdAndProductIdAndOrderId(Long size_id, long color_id, long product_id, long order_id);
}
