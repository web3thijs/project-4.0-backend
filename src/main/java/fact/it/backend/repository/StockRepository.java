package fact.it.backend.repository;

import fact.it.backend.model.Stock;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockRepository extends MongoRepository<Stock, ObjectId> {
    List<Stock> findAll();
    List<Stock> findStocksByProductId(ObjectId productId);
    Stock findStockById(ObjectId id);
}
