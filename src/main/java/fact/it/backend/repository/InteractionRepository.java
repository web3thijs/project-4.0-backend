package fact.it.backend.repository;

import fact.it.backend.model.Interaction;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InteractionRepository extends JpaRepository<Interaction, Long> {
    List<Interaction> findAll();
    List<Interaction> findInteractionsByProductId(long productId);
    List<Interaction> findInteractionsByCustomerId(long customerId);
    Interaction findInteractionByProductIdAndCustomerId(long productId, long customerId);
    Interaction findInteractionById(long id);
    List<Interaction> findInteractionsByProductIdAndCustomerId(Long productId, Long customerId);
}

