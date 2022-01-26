package fact.it.backend.repository;

import fact.it.backend.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductCustomRepository {
    public List<Product> findProductsByProperties(String categorie, String vzw, Double prijsgt, Double prijslt, Pageable page);
}

