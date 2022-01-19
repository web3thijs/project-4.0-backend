package fact.it.backend.repository;

import fact.it.backend.model.Size;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface SizeRepository extends MongoRepository<Size, String> {
    List<Size> findAll();
    Size findSizeById(ObjectId id);
}
