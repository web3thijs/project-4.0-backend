package fact.it.backend.repository;

import fact.it.backend.model.Color;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ColorRepository extends MongoRepository<Color, String> {
    Page<Color> findAll(Pageable pageable);
    Color findColorById(String id);
}
