package fact.it.backend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import fact.it.backend.model.*;
import fact.it.backend.repository.CategoryRepository;
import fact.it.backend.repository.UserRepository;
import fact.it.backend.service.UserService;
import fact.it.backend.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RequestMapping(path = "/api")
@RestController
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register/customer")
    private ResponseEntity<?> registerCustomer(@RequestBody Customer retrievedCustomer){
        Customer customer = new Customer();

        customer.setCountry(retrievedCustomer.getCountry());
        customer.setPassword(passwordEncoder.encode(retrievedCustomer.getPassword()));
        customer.setEmail(retrievedCustomer.getEmail());
        customer.setPhoneNr(retrievedCustomer.getPhoneNr());
        customer.setAddress(retrievedCustomer.getAddress());
        customer.setPostalCode(retrievedCustomer.getPostalCode());
        customer.setRole(Role.CUSTOMER);
        customer.setFirstName(retrievedCustomer.getFirstName());
        customer.setLastName(retrievedCustomer.getLastName());

        try{
            userRepository.save(customer);
        } catch (Exception e){
            return ResponseEntity.ok(new AuthResponse("Error during registration for customer " + retrievedCustomer.getEmail()));
        }

        return ResponseEntity.ok(new AuthResponse("Succesful registration for customer " + retrievedCustomer.getEmail()));
    }

    @PostMapping("/register/organization")
    private ResponseEntity<?> registerCustomer(@RequestBody Organization retrievedOrganization){
        Organization organization = new Organization();

        organization.setEmail(retrievedOrganization.getEmail());
        organization.setPassword(passwordEncoder.encode(retrievedOrganization.getPassword()));
        organization.setPhoneNr(retrievedOrganization.getPhoneNr());
        organization.setAddress(retrievedOrganization.getAddress());
        organization.setPostalCode(retrievedOrganization.getPostalCode());
        organization.setCountry(retrievedOrganization.getCountry());
        organization.setRole(Role.ORGANIZATION);
        organization.setOrganizationName(retrievedOrganization.getOrganizationName());
        organization.setCompanyRegistrationNr(retrievedOrganization.getCompanyRegistrationNr());
        organization.setVatNr(retrievedOrganization.getVatNr());
        organization.setWho(retrievedOrganization.getWho());
        organization.setWhat(retrievedOrganization.getWhat());
        organization.setHelp(retrievedOrganization.getHelp());
        organization.setSupportEmail(retrievedOrganization.getSupportEmail());
        organization.setSupportPhoneNr(retrievedOrganization.getSupportPhoneNr());
        organization.setImageUrl(retrievedOrganization.getImageUrl());

        try{
            userRepository.save(organization);
        } catch (Exception e){
            return ResponseEntity.ok(new AuthResponse("Error during registration for organization " + organization.getEmail()));
        }

        return ResponseEntity.ok(new AuthResponse("Succesful registration for organization " + organization.getEmail()));
    }

    @PostMapping("/authenticate")
    private ResponseEntity<?> authenticateClient(@RequestBody AuthRequest authRequest) throws JsonProcessingException {
        String email = authRequest.getEmail();
        String password = authRequest.getPassword();

        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Error");
        }

        UserDetails retrievedUser = userService.loadUserByUsername(email);
        User retrievedUser2 = userRepository.findUserByEmail(email);

        String generatedToken = jwtUtils.generateToken(retrievedUser, retrievedUser2.getRole(), retrievedUser2.getId());

        HashMap<String, Object> map = new HashMap<>();
        map.put("token", generatedToken);
        map.put("id", retrievedUser2.getId());
        map.put("email", email);

        return new ResponseEntity<Object>(map, HttpStatus.OK);
    }
}
