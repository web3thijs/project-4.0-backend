package fact.it.backend.repository;

import fact.it.backend.model.Interaction;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InteractionRepository extends MongoRepository<Interaction, String> {
    List<Interaction> findAll();
    List<Interaction> findInteractionsByProductId(String productId);
    List<Interaction> findInteractionsByCustomerId(String customerId);
    Interaction findInteractionById(String id);
}

