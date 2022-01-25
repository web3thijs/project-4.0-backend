package fact.it.backend.repository;

import fact.it.backend.model.Size;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface SizeRepository extends MongoRepository<Size, String> {
    Page<Size> findAll(Pageable pageable);
    Size findSizeById(String id);
}
