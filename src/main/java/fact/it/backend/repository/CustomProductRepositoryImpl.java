package fact.it.backend.repository;

import fact.it.backend.model.Product;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class CustomProductRepositoryImpl implements CustomProductRepository {

    @PersistenceContext
    private final EntityManager entityManager;

    @Autowired
    public CustomProductRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager.getEntityManagerFactory().createEntityManager();
    }


    @Override
    public JSONObject filterProductsOrganizationId(long organizationId, Pageable pageable){
        StringBuilder sb = new StringBuilder();
        JSONObject json = new JSONObject();
        entityManager.clear();

        sb.append("SELECT * FROM product WHERE organization_id = ").append(organizationId);

        String sort = pageable.getSort().toString();
        String sortFixed = sort.replace(":", "");
        sb.append(" Order By ").append(sortFixed);

        int pageNumber = pageable.getPageNumber();
        int pageSize = pageable.getPageSize();
        Query q = entityManager.createNativeQuery(sb.toString(), Product.class);
        int totalPages = q.getResultList().size()/8;
        if (q.getResultList().size() % 8 != 0) totalPages++;
        q.setFirstResult(pageNumber * pageSize);
        q.setMaxResults(pageSize);
        json.put("content", q.getResultList());
        json.put("totalPages", totalPages);
        return json;
    }
    @Override
    public JSONObject filterProductsBasedOnKeywords(long categorie, long vzw, long prijsgt, long prijslt, String naam, Pageable pageable) {
        StringBuilder sb = new StringBuilder();
        JSONObject json = new JSONObject();
        entityManager.clear();


        if (categorie != 0 && vzw != 0) {
            sb.append("SELECT * FROM product WHERE category_id = ").append(categorie).append(" AND organization_id = ").append(vzw).append(" AND price >= ").append(prijsgt).append(" AND price <= ").append(prijslt).append(" AND name LIKE \"%").append(naam).append("%\"");
        } else if (categorie != 0) {
            sb.append("SELECT * FROM product WHERE category_id = ").append(categorie).append(" AND price >= ").append(prijsgt).append(" AND price <= ").append(prijslt).append(" AND name LIKE \"%").append(naam).append("%\"");
        } else if (vzw != 0){
            sb.append("SELECT * FROM product WHERE organization_id = ").append(vzw).append(" AND price >= ").append(prijsgt).append(" AND price <= ").append(prijslt).append(" AND name LIKE \"%").append(naam).append("%\"");
        } else {
            sb.append("SELECT * FROM product ").append("WHERE price >= ").append(prijsgt).append(" AND price <= ").append(prijslt).append(" AND name LIKE \"%").append(naam).append("%\"");
        }
        String sort = pageable.getSort().toString();
        String sortFixed = sort.replace(":", "");
        sb.append(" Order By ").append(sortFixed);

        int pageNumber = pageable.getPageNumber();
        int pageSize = pageable.getPageSize();
        Query q = entityManager.createNativeQuery(sb.toString(), Product.class);
        int totalPages = q.getResultList().size()/8;
        if (q.getResultList().size() % 8 != 0) totalPages++;
        q.setFirstResult(pageNumber * pageSize);
        q.setMaxResults(pageSize);
        json.put("content", q.getResultList());
        json.put("totalPages", totalPages);

        return json;
    }

}
