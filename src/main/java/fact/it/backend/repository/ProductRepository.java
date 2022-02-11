package fact.it.backend.repository;

import fact.it.backend.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, CustomProductRepository {
    Page<Product> findProductsByOrganizationId(long organizationId, Pageable pageable);
    Page<Product> findProductsByCategoryIdAndOrganizationId(long categoryId, long organizationId, Pageable pageable);
    Product findProductById(long id);

}
