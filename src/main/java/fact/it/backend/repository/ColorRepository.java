package fact.it.backend.repository;

import fact.it.backend.model.Color;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ColorRepository extends MongoRepository<Color, String> {
    List<Color> findAll();
    Color findColorById(ObjectId id);
}
