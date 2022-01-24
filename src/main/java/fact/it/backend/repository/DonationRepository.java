package fact.it.backend.repository;

import fact.it.backend.model.Donation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DonationRepository extends MongoRepository<Donation, String> {
    List<Donation> findAll();
    List<Donation> findDonationsByOrganizationId(String organizationId);
    Donation findDonationById(String id);
}
