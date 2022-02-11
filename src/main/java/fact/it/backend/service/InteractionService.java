package fact.it.backend.service;

import fact.it.backend.dto.AddToInteractionDTO;
import fact.it.backend.model.Interaction;
import fact.it.backend.repository.CustomerRepository;
import fact.it.backend.repository.InteractionRepository;
import fact.it.backend.repository.ProductRepository;
import fact.it.backend.repository.ReviewRepository;
import org.springframework.stereotype.Service;

@Service
public class InteractionService {
    InteractionRepository interactionRepository;
    ReviewRepository reviewRepository;
    ProductRepository productRepository;
    CustomerRepository customerRepository;

    public InteractionService(InteractionRepository interactionRepository, ReviewRepository reviewRepository, ProductRepository productRepository, CustomerRepository customerRepository) {
        this.interactionRepository = interactionRepository;
        this.reviewRepository = reviewRepository;
        this.productRepository = productRepository;
        this.customerRepository = customerRepository;
    }

    public void addClick(AddToInteractionDTO addToInteractionDTO){
        if(interactionRepository.findInteractionsByProductIdAndCustomerId(addToInteractionDTO.getProductId(), addToInteractionDTO.getCustomerId()).size() == 0){
            Interaction newInteraction = new Interaction(1, 0, 0, productRepository.findProductById(addToInteractionDTO.getProductId()), customerRepository.findCustomerById(addToInteractionDTO.getCustomerId()));
            interactionRepository.save(newInteraction);
        } else {
            Interaction interaction = interactionRepository.findInteractionByProductIdAndCustomerId(addToInteractionDTO.getProductId(), addToInteractionDTO.getCustomerId());
            interaction.setAmountClicks(interaction.getAmountClicks() + 1);
            interactionRepository.save(interaction);
        }
    }

    public void addBuy(AddToInteractionDTO addToInteractionDTO){
        if(interactionRepository.findInteractionsByProductIdAndCustomerId(addToInteractionDTO.getProductId(), addToInteractionDTO.getCustomerId()).size() == 0){
            Interaction newInteraction = new Interaction(0, 0, 1, productRepository.findProductById(addToInteractionDTO.getProductId()), customerRepository.findCustomerById(addToInteractionDTO.getCustomerId()));
            interactionRepository.save(newInteraction);
        } else {
            Interaction interaction = interactionRepository.findInteractionByProductIdAndCustomerId(addToInteractionDTO.getProductId(), addToInteractionDTO.getCustomerId());
            interaction.setAmountBought(interaction.getAmountBought() + 1);
            interactionRepository.save(interaction);
        }
    }


    public void addCart(AddToInteractionDTO addToInteractionDTO){
        if(interactionRepository.findInteractionsByProductIdAndCustomerId(addToInteractionDTO.getProductId(), addToInteractionDTO.getCustomerId()).size() == 0){
            Interaction newInteraction = new Interaction(0, 0, 1, productRepository.findProductById(addToInteractionDTO.getProductId()), customerRepository.findCustomerById(addToInteractionDTO.getCustomerId()));
            interactionRepository.save(newInteraction);
        } else {
            Interaction interaction = interactionRepository.findInteractionByProductIdAndCustomerId(addToInteractionDTO.getProductId(), addToInteractionDTO.getCustomerId());
            interaction.setAmountCart(interaction.getAmountCart() + 1);
            interactionRepository.save(interaction);
        }
    }
}
