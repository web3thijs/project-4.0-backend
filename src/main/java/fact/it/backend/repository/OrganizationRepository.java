package fact.it.backend.repository;

import fact.it.backend.model.Customer;
import fact.it.backend.model.Organization;
import fact.it.backend.model.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrganizationRepository extends MongoRepository<Organization, String> {
    Page<Organization> findByRole(Role role, Pageable pageable);
    List<Organization> findByRole(Role role);
    Organization findByRoleAndId(Role role, String id);
}
