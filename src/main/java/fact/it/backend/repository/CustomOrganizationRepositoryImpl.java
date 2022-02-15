package fact.it.backend.repository;

import fact.it.backend.model.Category;
import fact.it.backend.model.Organization;
import fact.it.backend.model.Product;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

public class CustomOrganizationRepositoryImpl implements CustomOrganizationRepository {
    @PersistenceContext
    private final EntityManager entityManager;

    @Autowired
    public CustomOrganizationRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager.getEntityManagerFactory().createEntityManager();
    }
    @Override
    public JSONObject filterOrganizations(String naam, Pageable pageable){
        StringBuilder sb = new StringBuilder();
        JSONObject json = new JSONObject();
        entityManager.clear();

        sb.append("SELECT * FROM app_users WHERE role = 1").append(" AND organization_name LIKE \"%").append(naam).append("%\"");

        String sort = pageable.getSort().toString();
        String sortFixed = sort.replace(":", "");
        sb.append(" Order By ").append(sortFixed);

        int pageNumber = pageable.getPageNumber();
        int pageSize = pageable.getPageSize();
        Query q = entityManager.createNativeQuery(sb.toString(), Organization.class);
        int totalPages = q.getResultList().size()/9;
        if (q.getResultList().size() % 9 != 0) totalPages++;
        q.setFirstResult(pageNumber * pageSize);
        q.setMaxResults(pageSize);
        json.put("content", q.getResultList());
        json.put("totalPages", totalPages);
        return json;
    }
}
