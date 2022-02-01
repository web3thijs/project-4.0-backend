package fact.it.backend.controller;

import fact.it.backend.model.*;
import fact.it.backend.repository.CustomerRepository;
import fact.it.backend.repository.InteractionRepository;
import fact.it.backend.repository.ProductRepository;
import fact.it.backend.repository.ReviewRepository;
import fact.it.backend.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "api/interactions")
public class InteractionController {

    @Autowired
    InteractionRepository interactionRepository;

    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @PostConstruct
    public void fillDatabase(){
        interactionRepository.save(new Interaction(4, 3, 1, reviewRepository.findReviewById(1), productRepository.findProductById(1), customerRepository.findById(1)));
        interactionRepository.save(new Interaction(3, 2, 2, reviewRepository.findReviewById(3), productRepository.findProductById(1), customerRepository.findById(1)));
        interactionRepository.save(new Interaction(6, 0, 0, reviewRepository.findReviewById(2), productRepository.findProductById(2), customerRepository.findById(2)));
        interactionRepository.save(new Interaction(2, 1, 1, reviewRepository.findReviewById(5), productRepository.findProductById(3), customerRepository.findById(2)));
        interactionRepository.save(new Interaction(8, 2, 0, reviewRepository.findReviewById(6), productRepository.findProductById(4), customerRepository.findById(3)));
        interactionRepository.save(new Interaction(3, 1,0, reviewRepository.findReviewById(7), productRepository.findProductById(6), customerRepository.findById(2)));
    }

    @GetMapping
    public ResponseEntity<?> findAll(@RequestHeader("Authorization") String tokenWithPrefix){
        String token = tokenWithPrefix.substring(7);
        Map<String, Object> claims = jwtUtils.extractAllClaims(token);
        String role = claims.get("role").toString();

        if(role.contains("ADMIN")){
            return ResponseEntity.ok(interactionRepository.findAll());
        } else {
            return new ResponseEntity<String>("Forbidden", HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<?> findInteractionsByProductId(@RequestHeader("Authorization") String tokenWithPrefix, @PathVariable long productId){
        String token = tokenWithPrefix.substring(7);
        Map<String, Object> claims = jwtUtils.extractAllClaims(token);
        String role = claims.get("role").toString();

        if(role.contains("ADMIN")){
            return ResponseEntity.ok(interactionRepository.findInteractionsByProductId(productId));
        } else {
            return new ResponseEntity<String>("Forbidden", HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<?> findInteractionsByCustomerId(@RequestHeader("Authorization") String tokenWithPrefix, @PathVariable long customerId){
        String token = tokenWithPrefix.substring(7);
        Map<String, Object> claims = jwtUtils.extractAllClaims(token);
        String role = claims.get("role").toString();

        if(role.contains("ADMIN")){
            return ResponseEntity.ok(interactionRepository.findInteractionsByCustomerId(customerId));
        } else {
            return new ResponseEntity<String>("Forbidden", HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping
    public ResponseEntity<?> addInteraction(@RequestHeader("Authorization") String tokenWithPrefix, @RequestBody Interaction interaction){
        String token = tokenWithPrefix.substring(7);
        Map<String, Object> claims = jwtUtils.extractAllClaims(token);
        String role = claims.get("role").toString();

        if(role.contains("ADMIN")){
            interactionRepository.save(interaction);
            return ResponseEntity.ok(interaction);
        } else {
            return new ResponseEntity<String>("Forbidden", HttpStatus.FORBIDDEN);
        }
    }

    @PutMapping
    public ResponseEntity<?> updateInteraction(@RequestHeader("Authorization") String tokenWithPrefix, @RequestBody Interaction updatedInteraction){
        String token = tokenWithPrefix.substring(7);
        Map<String, Object> claims = jwtUtils.extractAllClaims(token);
        String role = claims.get("role").toString();
        long user_id = Long.parseLong(claims.get("user_id").toString());
        Interaction retrievedInteraction = interactionRepository.findInteractionById(updatedInteraction.getId());

        if(role.contains("ADMIN") || (role.contains("CUSTOMER") && retrievedInteraction.getCustomer().getId() == user_id)){
            retrievedInteraction.setProduct(updatedInteraction.getProduct());
            retrievedInteraction.setCustomer(updatedInteraction.getCustomer());
            retrievedInteraction.setReview(updatedInteraction.getReview());
            retrievedInteraction.setAmountClicks(updatedInteraction.getAmountClicks());

            interactionRepository.save(retrievedInteraction);

            return ResponseEntity.ok(retrievedInteraction);
        } else {
            return new ResponseEntity<String>("Forbidden", HttpStatus.FORBIDDEN);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteInteraction(@RequestHeader("Authorization") String tokenWithPrefix, @PathVariable long id){
        String token = tokenWithPrefix.substring(7);
        Map<String, Object> claims = jwtUtils.extractAllClaims(token);
        String role = claims.get("role").toString();

        if(role.contains("ADMIN")){
            Interaction interaction = interactionRepository.findInteractionById(id);

            if(interaction != null){
                interactionRepository.delete(interaction);
                return ResponseEntity.ok().build();
            } else{
                return ResponseEntity.notFound().build();
            }
        } else {
            return new ResponseEntity<String>("Forbidden", HttpStatus.FORBIDDEN);
        }
    }
}
