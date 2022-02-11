package fact.it.backend.repository;

import fact.it.backend.model.Product;
import org.json.simple.JSONArray;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomProductRepository {
    JSONArray filterProductsBasedOnKeywords(long categorie, long vzw, long prijsgt, long prijslt, Pageable pageable);
    JSONArray filterProductsOrganizationId(long organizationId, Pageable pageable);

}
