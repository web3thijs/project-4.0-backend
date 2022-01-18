package fact.it.backend.repository;

import fact.it.backend.model.Product;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {
    List<Product> findAll();
    List<Product> findProductsByOrganizationId(ObjectId organizationId);
    Product findProductById(ObjectId id);
}
