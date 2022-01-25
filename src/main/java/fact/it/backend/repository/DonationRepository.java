package fact.it.backend.repository;

import fact.it.backend.model.Donation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DonationRepository extends MongoRepository<Donation, String> {
    Page<Donation> findAll(Pageable pageable);
    Page<Donation> findDonationsByOrganizationId(String organizationId, Pageable pageable);
    Donation findDonationById(String id);
}
