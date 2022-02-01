package fact.it.backend.repository;

import fact.it.backend.model.Stock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {
    Page<Stock> findAll(Pageable pageable);
    List<Stock> findStocksByProductId(long productId);
    Stock findStockById(long id);
}
