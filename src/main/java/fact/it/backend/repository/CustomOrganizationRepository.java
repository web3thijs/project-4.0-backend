package fact.it.backend.repository;

import org.json.simple.JSONObject;
import org.springframework.data.domain.Pageable;

public interface CustomOrganizationRepository {
    JSONObject filterOrganizations(String naam, Pageable pageable);

}
