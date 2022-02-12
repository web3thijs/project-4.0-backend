package fact.it.backend.controller;

import fact.it.backend.exception.ResourceNotFoundException;
import fact.it.backend.model.*;
import fact.it.backend.repository.OrganizationRepository;
import fact.it.backend.util.JwtUtils;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
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
    public ResponseEntity<?> findAll(@RequestParam(required = false, defaultValue = "0") Integer page, @RequestParam(required = false, defaultValue = "organization_name")String sort, @RequestParam(required = false)String order, @RequestParam(required = false, defaultValue = "%%") String naam){
                if(order != null && order.equals("desc")){
                    Pageable requestedPageWithSortDesc = PageRequest.of(page, 9, Sort.by(sort).descending());
                    JSONObject organizations = organizationRepository.filterOrganizations(naam, requestedPageWithSortDesc);
                    return ResponseEntity.ok(organizations);
                }
                else{
                    Pageable requestedPageWithSort = PageRequest.of(page, 9, Sort.by(sort).ascending());
                    JSONObject organizations = organizationRepository.filterOrganizations(naam, requestedPageWithSort);
                    return ResponseEntity.ok(organizations);
                }
        }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable long id) throws ResourceNotFoundException {
        return ResponseEntity.ok(organizationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Organization not found for this id: " + id)));

    }

    @PutMapping
    public ResponseEntity<?> updateOrganization(@RequestHeader("Authorization") String tokenWithPrefix, @Valid @RequestBody Organization updatedOrganization) throws ResourceNotFoundException {
        String token = tokenWithPrefix.substring(7);
        Map<String, Object> claims = jwtUtils.extractAllClaims(token);
        String role = claims.get("role").toString();
        long user_id = Long.parseLong(claims.get("user_id").toString());

        if(role.contains("ADMIN") || (role.contains("ORGANIZATION") && updatedOrganization.getId() == user_id)){
            Organization retrievedOrganization = organizationRepository.findByRoleAndId(Role.ORGANIZATION, updatedOrganization.getId());

            if(retrievedOrganization != null){
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

            } else{
                throw new ResourceNotFoundException("Cannot update. Organization not found for this id: " + updatedOrganization.getId());
            }
            return ResponseEntity.ok(retrievedOrganization);

        } else {
            return new ResponseEntity<String>("Not authorized", HttpStatus.FORBIDDEN);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrganization(@RequestHeader("Authorization") String tokenWithPrefix, @PathVariable long id) throws ResourceNotFoundException {
        String token = tokenWithPrefix.substring(7);
        Map<String, Object> claims = jwtUtils.extractAllClaims(token);
        String role = claims.get("role").toString();

        if(role.contains("ADMIN")){
            Organization organization = organizationRepository.findByRoleAndId(Role.ORGANIZATION, id);

            if(organization != null){
                organizationRepository.delete(organization);
                return ResponseEntity.ok().build();
            } else{
                throw new ResourceNotFoundException("Cannot delete. Interaction not found for this id: " + id);
            }
        } else {
            return new ResponseEntity<String>("Forbidden", HttpStatus.FORBIDDEN);
        }
    }
}
