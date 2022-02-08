package fact.it.backend.repository;

import fact.it.backend.model.Donation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DonationRepository extends JpaRepository<Donation, Long> {
    Page<Donation> findAll(Pageable pageable);
    List<Donation> findDonationsByOrderId(long orderId);
    List<Donation> findDonationsByOrganizationId(long organizationId);
    Donation findDonationById(long id);
    Donation findDonationByOrderIdAndOrganizationId(long order_id, Long organization_id);
    List<Donation>  findDonationsByOrderIdAndOrganizationId(long order_id, Long organization_id);
}
