package fact.it.backend.controller;

import fact.it.backend.model.*;
import fact.it.backend.repository.OrganizationRepository;
import fact.it.backend.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "api/organizations")
public class OrganizationController {
    @Autowired
    OrganizationRepository organizationRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtils jwtUtils;

    @GetMapping
    public ResponseEntity<?> findAll(@RequestParam(required = false, defaultValue = "0") Integer page, @RequestParam(required = false, defaultValue = "organizationName")String sort, @RequestParam(required = false)String order){
                if(order != null && order.equals("desc")){
                    Pageable requestedPageWithSortDesc = PageRequest.of(page, 8, Sort.by(sort).descending());
                    Page<Organization> organizations = organizationRepository.findByRole(Role.ORGANIZATION, requestedPageWithSortDesc);
                    return ResponseEntity.ok(organizations);
                }
                else{
                    Pageable requestedPageWithSort = PageRequest.of(page, 8, Sort.by(sort).ascending());
                    Page<Organization> organizations = organizationRepository.findByRole(Role.ORGANIZATION, requestedPageWithSort);
                    return ResponseEntity.ok(organizations);
                }
        }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable String id){
        return ResponseEntity.ok(organizationRepository.findByRoleAndId(Role.ORGANIZATION, id));
    }

    @PutMapping
    public ResponseEntity<?> updateOrganization(@RequestHeader("Authorization") String tokenWithPrefix, @RequestBody Organization updatedOrganization){
        String token = tokenWithPrefix.substring(7);
        Map<String, Object> claims = jwtUtils.extractAllClaims(token);
        String role = claims.get("role").toString();
        String user_id = claims.get("user_id").toString();

        if(role.contains("ADMIN") || (role.contains("CUSTOMER") && updatedOrganization.getId().contains(user_id))){
            Organization retrievedOrganization= organizationRepository.findByRoleAndId(Role.ORGANIZATION, updatedOrganization.getId());

            retrievedOrganization.setEmail(updatedOrganization.getEmail());
            retrievedOrganization.setPassword(passwordEncoder.encode(updatedOrganization.getPassword()));
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

            return ResponseEntity.ok(retrievedOrganization);
        } else {
            return new ResponseEntity<String>("Forbidden", HttpStatus.FORBIDDEN);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrganization(@RequestHeader("Authorization") String tokenWithPrefix, @PathVariable String id){
        String token = tokenWithPrefix.substring(7);
        Map<String, Object> claims = jwtUtils.extractAllClaims(token);
        String role = claims.get("role").toString();

        if(role.contains("ADMIN")){
            Organization organization = organizationRepository.findByRoleAndId(Role.ORGANIZATION, id);

            if(organization != null){
                organizationRepository.delete(organization);
                return ResponseEntity.ok().build();
            } else{
                return ResponseEntity.notFound().build();
            }
        } else {
            return new ResponseEntity<String>("Forbidden", HttpStatus.FORBIDDEN);
        }
    }
}
