package fact.it.backend.repository;

import fact.it.backend.model.Stock;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockRepository extends MongoRepository<Stock, String> {
    List<Stock> findAll();
    List<Stock> findStocksByProductId(String productId);
    Stock findStockById(String id);
}
