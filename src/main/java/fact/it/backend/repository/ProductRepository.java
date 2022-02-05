package fact.it.backend.repository;

import fact.it.backend.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findAll(Pageable pageable);
    Page<Product> findProductsByOrganizationId(long organizationId, Pageable pageable);
    Page<Product> findProductsByCategoryIdAndOrganizationId(long categoryId, long organizationId, Pageable pageable);
    Product findProductById(long id);
}
