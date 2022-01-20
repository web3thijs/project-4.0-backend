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

    @PostConstruct
    public void fillDB(){
        if(interactionRepository.count() == 0){
            Category category1 = new Category("61e7e7ba38d73c28cb36c3eb", "shirts");
            Category category2 = new Category("61e7e7ba38d73c28cb36c3ec", "pants");
            Customer customer1 = new Customer("giannidh@gmail.com", "password123", "0479994529", "Kersstraat 17", "2200", "Belgium", Role.CUSTOMER,"Gianni" , "De Herdt", false);
            Customer customer2 = new Customer("thijswouters@gmail.com", "password123", "0479954719", "Hoekstraat 165", "1680", "Belgium", Role.CUSTOMER,"Thijs" , "Wouters", true);
            Organization organization1 = new Organization("supporters@wwf.be", "wwf123", "+3223400920", "Emile Jacqmainlaan 90", "1000", "Belgium", Role.ORGANIZATION, "WWF", "BE0408656248", "BE0408656248", "Het World Wide Fund for Nature – waarvan de Nederlandse tak Wereld Natuur Fonds heet en de Amerikaanse World Wildlife Fund – is een wereldwijd opererende organisatie voor bescherming van de natuur", "+3223400920", "supporters@wwf.be");
            Product product1 = new Product("61e6c2c183f852129f4ffff3", category1, organization1, "T-shirt", 13.99, "Plain T-shirt", true, "Google.com");
            Product product2 = new Product("61e6c2c183f852129f4ffff4", category2, organization1, "Jeans", 23.99, "Plain Jeans", true, "Google.com");
            interactionRepository.save(new Interaction("61e802077497a90f52ca03d4", product1, customer1, new ObjectId().toString(), 26));
            interactionRepository.save(new Interaction("61e802077497a90f52ca03d8", product2, customer2, new ObjectId().toString(), 23));
        }
        System.out.println("DB test interactions: " + interactionRepository.findAll().size() + " interactions.");
    }

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
        retrievedInteraction.setReviewId(updatedInteraction.getReviewId());
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
