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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
                    Pageable requestedPageWithSortDesc = PageRequest.of(page, 9, Sort.by(sort).descending());
                    Page<Organization> organizations = organizationRepository.findByRole(Role.ORGANIZATION, requestedPageWithSortDesc);
                    return ResponseEntity.ok(organizations);
                }
                else{
                    Pageable requestedPageWithSort = PageRequest.of(page, 9, Sort.by(sort).ascending());
                    Page<Organization> organizations = organizationRepository.findByRole(Role.ORGANIZATION, requestedPageWithSort);
                    return ResponseEntity.ok(organizations);
                }
        }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable long id){
        return ResponseEntity.ok(organizationRepository.findByRoleAndId(Role.ORGANIZATION, id));
    }

    @PutMapping
    public ResponseEntity<?> updateOrganization(@RequestHeader("Authorization") String tokenWithPrefix, @RequestBody Organization updatedOrganization){
        String token = tokenWithPrefix.substring(7);
        Map<String, Object> claims = jwtUtils.extractAllClaims(token);
        String role = claims.get("role").toString();
        long user_id = Long.parseLong(claims.get("user_id").toString());

        if(role.contains("ADMIN") || (role.contains("CUSTOMER") && updatedOrganization.getId() == user_id)){
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
    public ResponseEntity<?> deleteOrganization(@RequestHeader("Authorization") String tokenWithPrefix, @PathVariable long id){
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


    @PostConstruct
    public void fillDatabase(){
        String password = new BCryptPasswordEncoder().encode("Password123");

        organizationRepository.save(new Organization("supporters@wwf.be", password, "+3223400920", "Emile Jacqmainlaan 90", "1000", "Belgium", Role.ORGANIZATION,"WWF", "BE0408656248", "BE0408656248", "Sinds de oprichting in 1966", "Onze slogan ‘Together Possible!’", "WWF zet zich in", "+3223400920", "supporters@wwf.be", "https://adfinitas-statics-cdn.s3.eu-west-3.amazonaws.com/wwf/defisc-20/logo.jpg"));
        organizationRepository.save(new Organization("info@bkks.be", password, "+3211192819", "Diesterstraat 67A bus 001", "3800", "Belgium", Role.ORGANIZATION,"Belgisch KinderKanker Steunfonds", "BE0835627680", "0835627680", "Het Belgisch Kinder Kanker", "Het Belgisch Kinder", "Zelfsn dan financiële steun om deze moeilijke", "+3223400920", "supporters@wwf.be", "https://belgischkinderkankersteunfonds.be/wp-content/uploads/2020/02/cropped-logo-bkks-1.png"));
        organizationRepository.save(new Organization("welkom@bzn.be", password, "032012210", "Herentalsebaan 74", "2100", "Belgium", Role.ORGANIZATION,"Bond Zonder Naam", "BE0469514642", "BE0469514642", "De Bond zonder Naam (BZN) is een Vlaamse", "Bond zonder Naam schreef voor de", "Bond zonder Naam wil zoveel", "+3223400920", "supporters@wwf.be", "https://www.bzn.be/graphics/default-socialmedia.jpg"));
        organizationRepository.save(new Organization("maite@think-pink.be", password, "+32475406602", "Researchdreef 12", "1070", "Belgium", Role.ORGANIZATION,"Think Pink", "0810893274", "BE0810893274", "Think Pink is de nationale",
                "ondersteunen van zorg- en nazorgprojecten", "Borstkankanker steunen.", "+32475406602", "info@think-pink.be", "https://upload.wikimedia.org/wikipedia/commons/4/4b/Logo_think-pink.jpg"));
        organizationRepository.save(new Organization("info@damiaanactie.be", password, "+3224225911", "Leopold II-laan 263", "1081", "Belgium", Role.ORGANIZATION,"Damiaanactie", "0406694670", "BE05000000007575", "Damiaanactie is een Belgische medische non-profitorganisatie die zich inzet voor mensen met lepra, tuberculose en andere ziektes die vooral de kwetsbaarste bevolkingsgroepen treffen.", "Om lepra, tbc", "Bij Damiaanactie", "+3224225911", "info@damiaanactie.be", "https://damiaanactie.be/wp-content/uploads/2019/10/RGB-LOGO-DA-NL-transparant.png"));
        organizationRepository.save(new Organization("info@rodekruis.be", password, "+3215443322", "Motstraat 40", "2800", "Belgium", Role.ORGANIZATION,"Rode Kruis Vlaanderen", "2154897956", "BE0455024129", "Wij zijn", "Hulp", "Bij een ramp ", "+3215443322", "info@rodekruis.be", "https://www.rodekruis.be/img/logo.svg?1557833551"));
    }
}
