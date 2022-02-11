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
import javax.persistence.Query;
import java.util.List;

@Repository
public class CustomProductRepositoryImpl implements CustomProductRepository {

    private final EntityManager entityManager;

    @Autowired
    public CustomProductRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager.getEntityManagerFactory().createEntityManager();
    }
    @Override
    public JSONArray filterProductsOrganizationId(long organizationId, Pageable pageable){
        StringBuilder sb = new StringBuilder();
        JSONArray array = new JSONArray();
        JSONObject json = new JSONObject();

        sb.append("SELECT * FROM product WHERE organization_id = ").append(organizationId);

        String sort = pageable.getSort().toString();
        String sortFixed = sort.replace(":", "");
        sb.append(" Order By ").append(sortFixed);

        int pageNumber = pageable.getPageNumber();
        int pageSize = pageable.getPageSize();
        Query q = entityManager.createNativeQuery(sb.toString(), Product.class);
        int totalPages = q.getResultList().size()/9;
        if (q.getResultList().size() % 9 != 0) totalPages++;
        q.setFirstResult(pageNumber * pageSize);
        q.setMaxResults(pageSize);
        json.put("content", q.getResultList());
        json.put("totalPages", totalPages);
        array.add(json);
        return array;
    }
    @Override
    public JSONArray filterProductsBasedOnKeywords(long categorie, long vzw, long prijsgt, long prijslt, Pageable pageable) {
        StringBuilder sb = new StringBuilder();
        JSONArray array = new JSONArray();
        JSONObject json = new JSONObject();

        if (categorie != 0 && vzw != 0) {
            sb.append("SELECT * FROM product WHERE category_id = ").append(categorie).append(" AND organization_id = ").append(vzw).append(" AND price >= ").append(prijsgt).append(" AND price <= ").append(prijslt);
        } else if (categorie != 0) {
            sb.append("SELECT * FROM product WHERE category_id = ").append(categorie).append(" AND price >= ").append(prijsgt).append(" AND price <= ").append(prijslt);
        } else if (vzw != 0){
            sb.append("SELECT * FROM product WHERE organization_id = ").append(vzw).append(" AND price >= ").append(prijsgt).append(" AND price <= ").append(prijslt);
        } else {
            sb.append("SELECT * FROM product ").append("WHERE price >= ").append(prijsgt).append(" AND price <= ").append(prijslt);
        }
        String sort = pageable.getSort().toString();
        String sortFixed = sort.replace(":", "");
        sb.append(" Order By ").append(sortFixed);

        int pageNumber = pageable.getPageNumber();
        int pageSize = pageable.getPageSize();
        Query q = entityManager.createNativeQuery(sb.toString(), Product.class);
        int totalPages = q.getResultList().size()/9;
        if (q.getResultList().size() % 9 != 0) totalPages++;
        q.setFirstResult(pageNumber * pageSize);
        q.setMaxResults(pageSize);
        json.put("content", q.getResultList());
        json.put("totalPages", totalPages);
        array.add(json);

        return array;
    }

}
