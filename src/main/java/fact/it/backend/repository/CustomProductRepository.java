package fact.it.backend.repository;

import fact.it.backend.model.Product;
import org.json.simple.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomProductRepository {
    JSONObject filterProductsBasedOnKeywords(long categorie, long vzw, long prijsgt, long prijslt, String naam, Pageable pageable);
    JSONObject filterProductsOrganizationId(long organizationId, Pageable pageable);

    }
