package fact.it.backend.controller;

import fact.it.backend.model.Donation;
import fact.it.backend.repository.DonationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(path = "api/donations")
@RestController
public class DonationController {
    @Autowired
    DonationRepository donationRepository;

    @GetMapping("")
    public List<Donation> findAll() { return donationRepository.findAll();}
}
