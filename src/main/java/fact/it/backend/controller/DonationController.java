package fact.it.backend.controller;

import fact.it.backend.exception.ResourceNotFoundException;
import fact.it.backend.model.Donation;
import fact.it.backend.model.Order;
import fact.it.backend.model.Organization;
import fact.it.backend.repository.DonationRepository;
import fact.it.backend.repository.OrderRepository;
import fact.it.backend.repository.OrganizationRepository;
import fact.it.backend.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RequestMapping(path = "api/donations")
@RestController
public class DonationController {
    @Autowired
    DonationRepository donationRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrganizationRepository organizationRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @PostConstruct
    public void fillDatabase(){
        donationRepository.save(new Donation(2.5, orderRepository.findOrderById(3), organizationRepository.findOrganizationById(7)));
        donationRepository.save(new Donation(1.5, orderRepository.findOrderById(1), organizationRepository.findOrganizationById(7)));
        donationRepository.save(new Donation(15, orderRepository.findOrderById(3), organizationRepository.findOrganizationById(10)));
        donationRepository.save(new Donation(1, orderRepository.findOrderById(1), organizationRepository.findOrganizationById(11)));
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<?> findDonationsByCustomerId(@RequestHeader("Authorization") String tokenWithPrefix, @PathVariable long orderId) throws ResourceNotFoundException {
        String token = tokenWithPrefix.substring(7);
        Map<String, Object> claims = jwtUtils.extractAllClaims(token);
        String role = claims.get("role").toString();
        long user_id = Long.parseLong(claims.get("user_id").toString());
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found for this id: " + orderId));

        if(role.contains("ADMIN") || (role.contains("CUSTOMER") && order.getCustomer().getId() == user_id)){
            List<Donation> donationsByCustomerIdAndOrderId = donationRepository.findDonationsByOrderId(orderId);
            return ResponseEntity.ok(donationsByCustomerIdAndOrderId);
        } else {
            return new ResponseEntity<String>("Not authorized", HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/organization/{organizationId}")
    public ResponseEntity<?> findDonationsByOrganizationId(@RequestHeader("Authorization") String tokenWithPrefix, @PathVariable long organizationId) throws ResourceNotFoundException {
        String token = tokenWithPrefix.substring(7);
        Map<String, Object> claims = jwtUtils.extractAllClaims(token);
        String role = claims.get("role").toString();
        long user_id = Long.parseLong(claims.get("user_id").toString());
        Organization organization = organizationRepository.findById(organizationId)
                .orElseThrow(() -> new ResourceNotFoundException("Organization not found for this id: " + organizationId));

        if(role.contains("ADMIN") || (role.contains("ORGANIZATION") && organization.getId() == user_id)){
            List<Donation> donationsByOrganizationId = donationRepository.findDonationsByOrganizationId(organizationId);
            return ResponseEntity.ok(donationsByOrganizationId);
        } else {
            return new ResponseEntity<String>("Not authorized", HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping
    public ResponseEntity<?> addDonation(@RequestHeader("Authorization") String tokenWithPrefix, @Valid @RequestBody Donation donation) {
        String token = tokenWithPrefix.substring(7);
        Map<String, Object> claims = jwtUtils.extractAllClaims(token);
        String role = claims.get("role").toString();
        long user_id = Long.parseLong(claims.get("user_id").toString());

        if(role.contains("ADMIN") || (role.contains("CUSTOMER") && donation.getOrder().getCustomer().getId() == user_id)){
            donationRepository.save(donation);
            return ResponseEntity.ok(donation);
        } else {
            return new ResponseEntity<String>("Not authorized", HttpStatus.FORBIDDEN);
        }
    }

    @PutMapping
    public ResponseEntity<?> updateDonation(@RequestHeader("Authorization") String tokenWithPrefix, @Valid @RequestBody Donation updatedDonation) throws ResourceNotFoundException {
        String token = tokenWithPrefix.substring(7);
        Map<String, Object> claims = jwtUtils.extractAllClaims(token);
        String role = claims.get("role").toString();
        long user_id = Long.parseLong(claims.get("user_id").toString());
        Donation retrievedDonation = donationRepository.findById(updatedDonation.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Cannot update. Donation not found for this id: " + updatedDonation.getId()));

        if(role.contains("ADMIN") || (role.contains("CUSTOMER") && retrievedDonation.getOrder().getCustomer().getId() == user_id)){
            retrievedDonation.setOrder(orderRepository.findOrderById(updatedDonation.getOrder().getId()));
            retrievedDonation.setOrganization(organizationRepository.findOrganizationById(updatedDonation.getOrganization().getId()));
            retrievedDonation.setAmount(updatedDonation.getAmount());

            donationRepository.save(retrievedDonation);

            return ResponseEntity.ok(retrievedDonation);
        } else {
            return new ResponseEntity<String>("Not authorized", HttpStatus.FORBIDDEN);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteDonation(@RequestHeader("Authorization") String tokenWithPrefix, @PathVariable long id) throws ResourceNotFoundException {
        String token = tokenWithPrefix.substring(7);
        Map<String, Object> claims = jwtUtils.extractAllClaims(token);
        String role = claims.get("role").toString();
        long user_id = Long.parseLong(claims.get("user_id").toString());
        Donation donation = donationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot delete. Donation not found for this id: " + id));

        if(role.contains("ADMIN") || (role.contains("CUSTOMER") && donation.getOrder().getCustomer().getId() == user_id)){
                donationRepository.delete(donation);
                return ResponseEntity.ok().build();
        } else {
            return new ResponseEntity<String>("Forbidden", HttpStatus.FORBIDDEN);
        }
    }
}