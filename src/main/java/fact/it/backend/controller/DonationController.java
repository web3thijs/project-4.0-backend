package fact.it.backend.controller;

import fact.it.backend.model.Donation;
import fact.it.backend.model.Order;
import fact.it.backend.model.Product;
import fact.it.backend.repository.DonationRepository;
import fact.it.backend.repository.OrderRepository;
import fact.it.backend.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    private JwtUtils jwtUtils;

    @GetMapping("/order/{orderId}")
    public ResponseEntity<?> findDonationsByCustomerId(@RequestHeader("Authorization") String tokenWithPrefix, @PathVariable String orderId, @RequestParam(required = false, defaultValue = "0") int page) {
        String token = tokenWithPrefix.substring(7);
        Map<String, Object> claims = jwtUtils.extractAllClaims(token);
        String role = claims.get("role").toString();
        String user_id = claims.get("user_id").toString();
        Order order = orderRepository.findOrderById(orderId);

        if(role.contains("ADMIN") || (role.contains("CUSTOMER") && order.getCustomer().getId().contains(user_id))){
            Pageable requestedPage = PageRequest.of(page, 8);
            Page<Donation> donationsByCustomerIdAndOrderId = donationRepository.findDonationsByOrderId(orderId, requestedPage);
            return ResponseEntity.ok(donationsByCustomerIdAndOrderId);
        } else {
            return new ResponseEntity<String>("Forbidden", HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping
    public ResponseEntity<?> addDonation(@RequestHeader("Authorization") String tokenWithPrefix, @RequestBody Donation donation) {
        String token = tokenWithPrefix.substring(7);
        Map<String, Object> claims = jwtUtils.extractAllClaims(token);
        String role = claims.get("role").toString();
        String user_id = claims.get("user_id").toString();

        if(role.contains("ADMIN") || (role.contains("CUSTOMER") && donation.getOrder().getCustomer().getId().contains(user_id))){
            donationRepository.save(donation);
            return ResponseEntity.ok(donation);
        } else {
            return new ResponseEntity<String>("Forbidden", HttpStatus.FORBIDDEN);
        }
    }

    @PutMapping
    public ResponseEntity<?> updateDonation(@RequestHeader("Authorization") String tokenWithPrefix, @RequestBody Donation updatedDonation) {
        String token = tokenWithPrefix.substring(7);
        Map<String, Object> claims = jwtUtils.extractAllClaims(token);
        String role = claims.get("role").toString();
        String user_id = claims.get("user_id").toString();
        Donation retrievedDonation = donationRepository.findDonationById(updatedDonation.getId());

        if(role.contains("ADMIN") || (role.contains("CUSTOMER") && retrievedDonation.getOrder().getCustomer().getId().contains(user_id))){
            retrievedDonation.setOrder(updatedDonation.getOrder());
            retrievedDonation.setOrganization(updatedDonation.getOrganization());
            retrievedDonation.setAmount(updatedDonation.getAmount());

            donationRepository.save(retrievedDonation);

            return ResponseEntity.ok(retrievedDonation);
        } else {
            return new ResponseEntity<String>("Forbidden", HttpStatus.FORBIDDEN);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteDonation(@RequestHeader("Authorization") String tokenWithPrefix, @PathVariable String id) {
        String token = tokenWithPrefix.substring(7);
        Map<String, Object> claims = jwtUtils.extractAllClaims(token);
        String role = claims.get("role").toString();
        String user_id = claims.get("user_id").toString();
        Donation donation = donationRepository.findDonationById(id);

        if(role.contains("ADMIN") || (role.contains("CUSTOMER") && donation.getOrder().getCustomer().getId().contains(user_id))){

            if (donation != null) {
                donationRepository.delete(donation);
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return new ResponseEntity<String>("Forbidden", HttpStatus.FORBIDDEN);
        }
    }
}