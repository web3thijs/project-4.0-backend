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
    public Organization updateOrganization(@RequestBody Organization updatedOrganization){
        Organization retrievedOrganization= organizationRepository.findByRoleAndId(Role.ORGANIZATION, updatedOrganization.getId());

        retrievedOrganization.setEmail(updatedOrganization.getEmail());
        retrievedOrganization.setPassword(updatedOrganization.getPassword());
        retrievedOrganization.setPhoneNr(updatedOrganization.getPhoneNr());
        retrievedOrganization.setAddress(updatedOrganization.getAddress());
        retrievedOrganization.setPostalCode(updatedOrganization.getPostalCode());
        retrievedOrganization.setCountry(updatedOrganization.getCountry());
        retrievedOrganization.setRole(updatedOrganization.getRole());
        retrievedOrganization.setOrganizationName(updatedOrganization.getOrganizationName());
        retrievedOrganization.setCompanyRegistrationNr(updatedOrganization.getCompanyRegistrationNr());
        retrievedOrganization.setVatNr(updatedOrganization.getVatNr());
        retrievedOrganization.setWho(updatedOrganization.getWho());
        retrievedOrganization.setWhat(updatedOrganization.getWhat());
        retrievedOrganization.setHelp(updatedOrganization.getHelp());
        retrievedOrganization.setSupportPhoneNr(updatedOrganization.getSupportPhoneNr());
        retrievedOrganization.setSupportEmail(updatedOrganization.getSupportEmail());

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
