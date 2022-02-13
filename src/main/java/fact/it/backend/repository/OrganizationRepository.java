package fact.it.backend.repository;

import fact.it.backend.model.Organization;
import fact.it.backend.model.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Long>, CustomOrganizationRepository {
    Page<Organization> findByRole(Role role, Pageable pageable);
    List<Organization> findByRole(Role role);
    Organization findByRoleAndId(Role role, long id);
    Organization findOrganizationById(long id);
}
