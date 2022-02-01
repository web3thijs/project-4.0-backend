package fact.it.backend.controller;

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

    @PostConstruct
    public void fillDatabase(){
        reviewRepository.save(new Review( 5, "Zeer leuke sleutelhanger", "Hangt heel mooi aan mijn sleutelbundel. Lief en zacht!", customerRepository.findById(1)));
        reviewRepository.save(new Review(3.5, "Mooi", "", customerRepository.findById(2)));
        reviewRepository.save(new Review( 5, "Zeer leuke knuffel", "", customerRepository.findById(1)));
        reviewRepository.save(new Review(3.5, "Mooi", "", customerRepository.findById(2)));
        reviewRepository.save(new Review(3.5, "De beer", "Beetje lelijk maar heeft een groot hart", customerRepository.findById(2)));
        reviewRepository.save(new Review(4.5, "Schrijft goed!", "", customerRepository.findById(3)));
        reviewRepository.save(new Review(5, "Stevige kop.", "Perfect om de ochtend mee te beginnen, warmte blijft goed binnen de koffiekop.", customerRepository.findById(3)));
    }

    @GetMapping
    public Page<Review> findAll(@RequestParam(required = false, defaultValue = "0") Integer page){
        Pageable requestedPage = PageRequest.of(page, 9);
        return reviewRepository.findAll(requestedPage);
    }

    @GetMapping("/{id}")
    public Review findById(@PathVariable long id){
        return reviewRepository.findReviewById(id);
    }

    @PostMapping
    public ResponseEntity<?> addReview(@RequestHeader("Authorization") String tokenWithPrefix, @RequestBody Review review){
        String token = tokenWithPrefix.substring(7);
        Map<String, Object> claims = jwtUtils.extractAllClaims(token);
        String role = claims.get("role").toString();
        long user_id = Long.parseLong(claims.get("user_id").toString());

        if(role.contains("ADMIN") || (role.contains("CUSTOMER") && review.getCustomer().getId() == user_id)){
            reviewRepository.save(review);
            return ResponseEntity.ok(review);
        } else {
            return new ResponseEntity<String>("Forbidden", HttpStatus.FORBIDDEN);
        }
    }

    @PutMapping
    public ResponseEntity<?> updateReview(@RequestHeader("Authorization") String tokenWithPrefix, @RequestBody Review updatedReview){
        String token = tokenWithPrefix.substring(7);
        Map<String, Object> claims = jwtUtils.extractAllClaims(token);
        String role = claims.get("role").toString();
        long user_id = Long.parseLong(claims.get("user_id").toString());

        if(role.contains("ADMIN") || (role.contains("CUSTOMER") && updatedReview.getCustomer().getId() == user_id)){
            Review retrievedReview = reviewRepository.findReviewById(updatedReview.getId());

            retrievedReview.setScore(updatedReview.getScore());
            retrievedReview.setTitle(updatedReview.getTitle());
            retrievedReview.setText(updatedReview.getText());

            reviewRepository.save(retrievedReview);

            return ResponseEntity.ok(retrievedReview);
        } else {
            return new ResponseEntity<String>("Forbidden", HttpStatus.FORBIDDEN);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteReview(@RequestHeader("Authorization") String tokenWithPrefix, @PathVariable long id){
        String token = tokenWithPrefix.substring(7);
        Map<String, Object> claims = jwtUtils.extractAllClaims(token);
        String role = claims.get("role").toString();

        if(role.contains("ADMIN")){
            Review review = reviewRepository.findReviewById(id);

            if(review != null){
                reviewRepository.delete(review);
                return ResponseEntity.ok().build();
            }else{
                return ResponseEntity.notFound().build();
            }
        } else {
            return new ResponseEntity<String>("Forbidden", HttpStatus.FORBIDDEN);
        }
    }
}
