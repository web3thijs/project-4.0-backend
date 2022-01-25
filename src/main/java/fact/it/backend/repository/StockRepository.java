package fact.it.backend.repository;

import fact.it.backend.model.Stock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockRepository extends MongoRepository<Stock, String> {
    Page<Stock> findAll(Pageable pageable);
    List<Stock> findStocksByProductId(String productId);
    Stock findStockById(String id);
}
