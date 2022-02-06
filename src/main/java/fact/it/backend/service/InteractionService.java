package fact.it.backend.service;

import fact.it.backend.dto.AddClickDTO;
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

    public void addClick(AddClickDTO addClickDTO){
        if(interactionRepository.findInteractionsByProductIdAndCustomerId(addClickDTO.getProductId(), addClickDTO.getCustomerId()).size() == 0){
            Interaction newInteraction = new Interaction(1, 0, 0, productRepository.findProductById(addClickDTO.getProductId()), customerRepository.findCustomerById(addClickDTO.getCustomerId()));
            interactionRepository.save(newInteraction);
        } else {
            Interaction interaction = interactionRepository.findInteractionByProductIdAndCustomerId(addClickDTO.getProductId(), addClickDTO.getCustomerId());
            interaction.setAmountClicks(interaction.getAmountClicks() + 1);
            interactionRepository.save(interaction);
        }
    }
}
