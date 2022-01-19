package fact.it.backend.repository;

import fact.it.backend.model.Category;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends MongoRepository<Category, ObjectId> {
    List<Category> findAll();
    Category findByCategoryId(ObjectId id);
}
