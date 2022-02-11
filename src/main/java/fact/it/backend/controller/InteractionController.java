package fact.it.backend.controller;

import fact.it.backend.dto.AddToInteractionDTO;
import fact.it.backend.exception.ResourceNotFoundException;
import fact.it.backend.model.*;
import fact.it.backend.repository.CustomerRepository;
import fact.it.backend.repository.InteractionRepository;
import fact.it.backend.repository.ProductRepository;
import fact.it.backend.repository.ReviewRepository;
import fact.it.backend.service.InteractionService;
import fact.it.backend.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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

    InteractionService interactionService;

    public InteractionController(InteractionService interactionService) {
        this.interactionService = interactionService;
    }

    @GetMapping
    public ResponseEntity<?> findAll(@RequestHeader("Authorization") String tokenWithPrefix){
        String token = tokenWithPrefix.substring(7);
        Map<String, Object> claims = jwtUtils.extractAllClaims(token);
        String role = claims.get("role").toString();

        if(role.contains("ADMIN")){
            return ResponseEntity.ok(interactionRepository.findAll());
        } else {
            return new ResponseEntity<String>("Not authorized", HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<?> findInteractionsByProductId(@RequestHeader("Authorization") String tokenWithPrefix, @PathVariable long productId) throws ResourceNotFoundException {
        String token = tokenWithPrefix.substring(7);
        Map<String, Object> claims = jwtUtils.extractAllClaims(token);
        String role = claims.get("role").toString();

        if(role.contains("ADMIN")){
            Product product = productRepository.findProductById(productId);
            if(product.getInteractions().size() != 0){
                return ResponseEntity.ok(interactionRepository.findInteractionsByProductId(productId));
            }
            else{
                throw new ResourceNotFoundException("No interactions found for product with id: " + productId);
            }
        } else {
            return new ResponseEntity<String>("Not authorized", HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<?> findInteractionsByCustomerId(@RequestHeader("Authorization") String tokenWithPrefix, @PathVariable long customerId) throws ResourceNotFoundException {
        String token = tokenWithPrefix.substring(7);
        Map<String, Object> claims = jwtUtils.extractAllClaims(token);
        String role = claims.get("role").toString();

        if(role.contains("ADMIN")){
            Customer customer = customerRepository.findCustomerById(customerId);
            if(customer.getInteractions().size() != 0){
                return ResponseEntity.ok(interactionRepository.findInteractionsByCustomerId(customerId));
            }
            else{
                throw new ResourceNotFoundException("No interactions found for customer with id: " + customerId);
            }
        } else {
            return new ResponseEntity<String>("Not authorized", HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping
    public ResponseEntity<?> addInteraction(@RequestHeader("Authorization") String tokenWithPrefix, @Valid @RequestBody Interaction interaction){
        String token = tokenWithPrefix.substring(7);
        Map<String, Object> claims = jwtUtils.extractAllClaims(token);
        String role = claims.get("role").toString();

        if(role.contains("ADMIN")){
            interactionRepository.save(interaction);
            return ResponseEntity.ok(interaction);
        } else {
            return new ResponseEntity<String>("Not authorized", HttpStatus.FORBIDDEN);
        }
    }



    @PutMapping
    public ResponseEntity<?> updateInteraction(@RequestHeader("Authorization") String tokenWithPrefix, @Valid @RequestBody Interaction updatedInteraction) throws ResourceNotFoundException {
        String token = tokenWithPrefix.substring(7);
        Map<String, Object> claims = jwtUtils.extractAllClaims(token);
        String role = claims.get("role").toString();
        long user_id = Long.parseLong(claims.get("user_id").toString());
        Interaction retrievedInteraction = interactionRepository.findInteractionById(updatedInteraction.getId());

        if(retrievedInteraction != null){
            if(role.contains("ADMIN") || (role.contains("CUSTOMER") && retrievedInteraction.getCustomer().getId() == user_id)){
                retrievedInteraction.setProduct(productRepository.findProductById(updatedInteraction.getProduct().getId()));
                retrievedInteraction.setCustomer(customerRepository.findCustomerById(updatedInteraction.getCustomer().getId()));
                retrievedInteraction.setReview(reviewRepository.findReviewById(updatedInteraction.getReview().getId()));
                retrievedInteraction.setAmountClicks(updatedInteraction.getAmountClicks());

                interactionRepository.save(retrievedInteraction);

                return ResponseEntity.ok(retrievedInteraction);
            } else {
                return new ResponseEntity<String>("Not authorized", HttpStatus.FORBIDDEN);
            }
        } else{
            throw new ResourceNotFoundException("Cannot update. Interaction not found for this id: " + updatedInteraction.getId());
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteInteraction(@RequestHeader("Authorization") String tokenWithPrefix, @PathVariable long id) throws ResourceNotFoundException {
        String token = tokenWithPrefix.substring(7);
        Map<String, Object> claims = jwtUtils.extractAllClaims(token);
        String role = claims.get("role").toString();

        if(role.contains("ADMIN")){
            Interaction interaction = interactionRepository.findInteractionById(id);

            if(interaction != null){
                interactionRepository.delete(interaction);
                return ResponseEntity.ok().build();
            } else{
                throw new ResourceNotFoundException("Cannot delete. Interaction not found for this id: " + id);
            }
        } else {
            return new ResponseEntity<String>("Not authorized", HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping("/addClick")
    public ResponseEntity addClick(@RequestHeader("Authorization") String tokenWithPrefix, @Valid @RequestBody AddToInteractionDTO addToInteractionDTO){
        String token = tokenWithPrefix.substring(7);
        Map<String, Object> claims = jwtUtils.extractAllClaims(token);
        String role = claims.get("role").toString();
        long user_id = Long.parseLong(claims.get("user_id").toString());

        if(role.contains("ADMIN") || (role.contains("CUSTOMER"))){
            interactionService.addClick(addToInteractionDTO);
            HashMap<String, Object> map = new HashMap<>();
            map.put("status", "Added");
            return new ResponseEntity<Object>(map, HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("Not authorized", HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping("/addBuy")
    public ResponseEntity addBought(@RequestHeader("Authorization") String tokenWithPrefix, @Valid @RequestBody AddToInteractionDTO addToInteractionDTO){
        String token = tokenWithPrefix.substring(7);
        Map<String, Object> claims = jwtUtils.extractAllClaims(token);
        String role = claims.get("role").toString();
        long user_id = Long.parseLong(claims.get("user_id").toString());

        if(role.contains("ADMIN") || (role.contains("CUSTOMER"))){
            interactionService.addBuy(addToInteractionDTO);
            HashMap<String, Object> map = new HashMap<>();
            map.put("status", "Added");
            return new ResponseEntity<Object>(map, HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("Not authorized", HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping("/addCart")
    public ResponseEntity addCart(@RequestHeader("Authorization") String tokenWithPrefix, @Valid @RequestBody AddToInteractionDTO addToInteractionDTO){
        String token = tokenWithPrefix.substring(7);
        Map<String, Object> claims = jwtUtils.extractAllClaims(token);
        String role = claims.get("role").toString();
        long user_id = Long.parseLong(claims.get("user_id").toString());

        if(role.contains("ADMIN") || (role.contains("CUSTOMER"))){
            interactionService.addCart(addToInteractionDTO);
            HashMap<String, Object> map = new HashMap<>();
            map.put("status", "Added");
            return new ResponseEntity<Object>(map, HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("Not authorized", HttpStatus.FORBIDDEN);
        }
    }
}
