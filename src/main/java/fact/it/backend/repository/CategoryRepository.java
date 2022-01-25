package fact.it.backend.repository;

import fact.it.backend.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends MongoRepository<Category, String> {
    Page<Category> findAll(Pageable pageable);
    Category findCategoryById(String id);
}
