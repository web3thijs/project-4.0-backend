package fact.it.backend.controller;

import fact.it.backend.exception.ResourceNotFoundException;
import fact.it.backend.model.*;
import fact.it.backend.repository.CustomerRepository;
import fact.it.backend.repository.OrganizationRepository;
import fact.it.backend.repository.ReviewRepository;
import fact.it.backend.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "api/reviews")
public class ReviewController {

    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @GetMapping
    public ResponseEntity<?> findAll(@RequestParam(required = false, defaultValue = "0") Integer page){
        Pageable requestedPage = PageRequest.of(page, 8);
        Page<Review> reviews = reviewRepository.findAll(requestedPage);

        return ResponseEntity.ok().body(reviews);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable long id) throws ResourceNotFoundException {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found for this id: " + id));
        return ResponseEntity.ok().body(review);
    }

    @PostMapping
    public ResponseEntity<?> addReview(@RequestHeader("Authorization") String tokenWithPrefix, @Valid @RequestBody Review review){
        String token = tokenWithPrefix.substring(7);
        Map<String, Object> claims = jwtUtils.extractAllClaims(token);
        String role = claims.get("role").toString();
        long user_id = Long.parseLong(claims.get("user_id").toString());

        if(role.contains("ADMIN") || (role.contains("CUSTOMER") && review.getCustomer().getId() == user_id)){
            reviewRepository.save(review);
            return ResponseEntity.ok(review);
        } else {
            return new ResponseEntity<String>("Not authorized", HttpStatus.FORBIDDEN);
        }
    }

    @PutMapping
    public ResponseEntity<?> updateReview(@RequestHeader("Authorization") String tokenWithPrefix, @Valid @RequestBody Review updatedReview) throws ResourceNotFoundException {
        String token = tokenWithPrefix.substring(7);
        Map<String, Object> claims = jwtUtils.extractAllClaims(token);
        String role = claims.get("role").toString();
        long user_id = Long.parseLong(claims.get("user_id").toString());

        if(role.contains("ADMIN") || (role.contains("CUSTOMER") && updatedReview.getCustomer().getId() == user_id)){
            Review retrievedReview = reviewRepository.findById(updatedReview.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Cannot update. Review not found for this id: " + updatedReview.getId()));

            retrievedReview.setScore(updatedReview.getScore());
            retrievedReview.setTitle(updatedReview.getTitle());
            retrievedReview.setText(updatedReview.getText());

            reviewRepository.save(retrievedReview);

            return ResponseEntity.ok(retrievedReview);
        } else {
            return new ResponseEntity<String>("Not authorized", HttpStatus.FORBIDDEN);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteReview(@RequestHeader("Authorization") String tokenWithPrefix, @PathVariable long id) throws ResourceNotFoundException {
        String token = tokenWithPrefix.substring(7);
        Map<String, Object> claims = jwtUtils.extractAllClaims(token);
        String role = claims.get("role").toString();

        if(role.contains("ADMIN")){
            Review review = reviewRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Cannot delete. Review not found for this id: " + id));

                reviewRepository.delete(review);
                return ResponseEntity.ok().build();
        } else {
            return new ResponseEntity<String>("Not authorized", HttpStatus.FORBIDDEN);
        }
    }
}
