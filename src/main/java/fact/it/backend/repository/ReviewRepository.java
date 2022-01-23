package fact.it.backend.repository;

import fact.it.backend.model.Review;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends MongoRepository<Review, String> {
    List<Review> findAll();
    Review findReviewById(String id);
}
