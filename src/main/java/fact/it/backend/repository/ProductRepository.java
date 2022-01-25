package fact.it.backend.repository;

import fact.it.backend.model.Product;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {
    Page<Product> findAll(Pageable pageable);
    Page<Product> findProductsByOrganizationId(String organizationId, Pageable pageable);
    Page<Product> findProductsByPriceGreaterThan(Number price, Pageable pageable);
    Product findProductById(String id);
}
