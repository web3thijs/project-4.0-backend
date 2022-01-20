package fact.it.backend.repository;

import fact.it.backend.model.Organization;
import fact.it.backend.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    List<User> findAll();
    User findUserById(String id);
}