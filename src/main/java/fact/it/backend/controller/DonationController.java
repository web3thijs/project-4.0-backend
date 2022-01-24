package fact.it.backend.controller;

import fact.it.backend.model.Donation;
import fact.it.backend.repository.DonationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(path = "api/donations")
@RestController
public class DonationController {
    @Autowired
    DonationRepository donationRepository;

    @GetMapping("")
    public List<Donation> findAll() { return donationRepository.findAll();}

    @GetMapping("/organization/{organizationId}")
    public List<Donation> findDonationsByOrganizationId(@PathVariable String organizationId){
        return donationRepository.findDonationsByOrganizationId(organizationId);
    }

    @PostMapping("")
    public Donation addDonation(@RequestBody Donation donation){
        donationRepository.save(donation);
        return donation;
    }

    @PutMapping("")
    public Donation updateDonation(@RequestBody Donation updatedDonation){
        Donation retrievedDonation = donationRepository.findDonationById(updatedDonation.getId());

        retrievedDonation.setProduct(updatedDonation.getProduct());
        retrievedDonation.setOrganization(updatedDonation.getOrganization());
        retrievedDonation.setAmount(updatedDonation.getAmount());

        donationRepository.save(retrievedDonation);

        return retrievedDonation;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteDonation(@PathVariable String id){
        Donation donation = donationRepository.findDonationById(id);

        if(donation != null){
            donationRepository.delete(donation);
            return ResponseEntity.ok().build();
        }   else{
            return ResponseEntity.notFound().build();
        }
    }
}
