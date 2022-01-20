package fact.it.backend.controller;

import fact.it.backend.model.*;
import fact.it.backend.repository.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;

@RequestMapping(path = "api/organizations")
@RestController
public class OrganizationController {
    @Autowired
    OrganizationRepository organizationRepository;

    @PostConstruct
    public void fillDB(){
        if(organizationRepository.count() == 3){
            organizationRepository.save(new Organization("supporters@wwf.be", "wwf123", "+3223400920", "Emile Jacqmainlaan 90", "1000", "Belgium", Role.ORGANIZATION, "WWF", "BE0408656248", "BE0408656248", "Het World Wide Fund for Nature – waarvan de Nederlandse tak Wereld Natuur Fonds heet en de Amerikaanse World Wildlife Fund – is een wereldwijd opererende organisatie voor bescherming van de natuur", "+3223400920", "supporters@wwf.be"));
        }

        System.out.println("DB test organizations: " + organizationRepository.findAll().size());
    }

    @GetMapping("")
    public List<Organization> findAll(){
        return organizationRepository.findByRole(Role.ORGANIZATION);
    }

    @GetMapping("/{id}")
    public Organization findById(@PathVariable String id){
        return organizationRepository.findByRoleAndId(Role.ORGANIZATION, id);
    }

    @PostMapping("")
    public Organization addOrganization(@RequestBody Organization organization) {
        organizationRepository.save(organization);
        return organization;
    }

    @PutMapping
    public Organization updateOrganization(@RequestBody Organization updatedOrganizationr){
        Organization retrievedOrganization= organizationRepository.findByRoleAndId(Role.ORGANIZATION, updatedOrganizationr.getId());

        retrievedOrganization.setEmail(updatedOrganizationr.getEmail());
        retrievedOrganization.setPassword(updatedOrganizationr.getPassword());
        retrievedOrganization.setPhoneNr(updatedOrganizationr.getPhoneNr());
        retrievedOrganization.setAddress(updatedOrganizationr.getAddress());
        retrievedOrganization.setPostalCode(updatedOrganizationr.getPostalCode());
        retrievedOrganization.setCountry(updatedOrganizationr.getCountry());
        retrievedOrganization.setRole(updatedOrganizationr.getRole());
        retrievedOrganization.setOrganizationName(updatedOrganizationr.getOrganizationName());
        retrievedOrganization.setCompanyRegistrationNr(updatedOrganizationr.getCompanyRegistrationNr());
        retrievedOrganization.setVatNr(updatedOrganizationr.getVatNr());
        retrievedOrganization.setAbout(updatedOrganizationr.getAbout());
        retrievedOrganization.setSupportPhoneNr(updatedOrganizationr.getSupportPhoneNr());
        retrievedOrganization.setSupportEmail(updatedOrganizationr.getSupportEmail());

        organizationRepository.save(retrievedOrganization);

        return retrievedOrganization;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteOrganization(@PathVariable String id){
        Organization organization = organizationRepository.findByRoleAndId(Role.ORGANIZATION, id);

        if(organization != null){
            organizationRepository.delete(organization);
            return ResponseEntity.ok().build();
        } else{
            return ResponseEntity.notFound().build();
        }
    }
}
