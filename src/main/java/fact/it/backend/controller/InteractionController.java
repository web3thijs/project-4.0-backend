package fact.it.backend.controller;

import fact.it.backend.model.*;
import fact.it.backend.repository.InteractionRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;

@RequestMapping(path = "api/interactions")
@RestController
public class InteractionController {

    @Autowired
    InteractionRepository interactionRepository;

    @GetMapping("")
    public List<Interaction> findAll(){
        return interactionRepository.findAll();
    }

    @GetMapping("/product/{productId}")
    public List<Interaction> findInteractionsByProductId(@PathVariable String productId){
        return interactionRepository.findInteractionsByProductId(productId);
    }

    @GetMapping("/customer/{customerId}")
    public List<Interaction> findInteractionsByCustomerId(@PathVariable String customerId){
        return interactionRepository.findInteractionsByCustomerId(customerId);
    }

    @PostMapping("")
    public Interaction addInteraction(@RequestBody Interaction interaction){
        interactionRepository.save(interaction);
        return interaction;
    }

    @PutMapping("")
    public Interaction updateInteraction(@RequestBody Interaction updatedInteraction){
        Interaction retrievedInteraction = interactionRepository.findInteractionById(updatedInteraction.getId());

        retrievedInteraction.setProduct(updatedInteraction.getProduct());
        retrievedInteraction.setCustomer(updatedInteraction.getCustomer());
        retrievedInteraction.setReview(updatedInteraction.getReview());
        retrievedInteraction.setAmountClicks(updatedInteraction.getAmountClicks());

        interactionRepository.save(retrievedInteraction);

        return retrievedInteraction;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteInteraction(@PathVariable String id){
        Interaction interaction = interactionRepository.findInteractionById(id);

        if(interaction != null){
            interactionRepository.delete(interaction);
            return ResponseEntity.ok().build();
        } else{
            return ResponseEntity.notFound().build();
        }
    }
}
