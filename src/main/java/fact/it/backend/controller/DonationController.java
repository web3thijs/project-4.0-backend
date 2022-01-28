package fact.it.backend.controller;

import fact.it.backend.model.Donation;
import fact.it.backend.model.Product;
import fact.it.backend.repository.DonationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/donations")
public class DonationController {
    @Autowired
    DonationRepository donationRepository;

    @GetMapping
    public Page<Donation> findAll(@RequestParam(required = false, defaultValue = "0") Integer page, @RequestParam(required = false, defaultValue = "organization.organizationName") String sort, @RequestParam(required = false) String order) {
        if (order != null && order.equals("desc")) {
            Pageable requestedPageWithSortDesc = PageRequest.of(page, 8, Sort.by(sort).descending());
            Page<Donation> donations = donationRepository.findAll(requestedPageWithSortDesc);
            return donations;
        } else {
            Pageable requestedPageWithSort = PageRequest.of(page, 8, Sort.by(sort).ascending());
            Page<Donation> donations = donationRepository.findAll(requestedPageWithSort);
            return donations;
        }
    }

    @GetMapping("/organization/{organizationId}")
    public Page<Donation> findDonationsByOrganizationId(@PathVariable String organizationId, @RequestParam int page) {
        Pageable requestedPage = PageRequest.of(page, 8);
        Page<Donation> donationsByOrganizationId = donationRepository.findDonationsByOrganizationId(organizationId, requestedPage);
        return donationsByOrganizationId;
    }

    @PostMapping
    public Donation addDonation(@RequestBody Donation donation) {
        donationRepository.save(donation);
        return donation;
    }

    @PutMapping
    public Donation updateDonation(@RequestBody Donation updatedDonation) {
        Donation retrievedDonation = donationRepository.findDonationById(updatedDonation.getId());

        retrievedDonation.setProduct(updatedDonation.getProduct());
        retrievedDonation.setOrganization(updatedDonation.getOrganization());
        retrievedDonation.setAmount(updatedDonation.getAmount());

        donationRepository.save(retrievedDonation);

        return retrievedDonation;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteDonation(@PathVariable String id) {
        Donation donation = donationRepository.findDonationById(id);

        if (donation != null) {
            donationRepository.delete(donation);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
