package fact.it.backend.repository;

import fact.it.backend.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends MongoRepository<Product, String>,ProductCustomRepository
 {
    Page<Product> findProductsByOrganizationId(String organizationId, Pageable pageable);
    Product findProductById(String id);
}
