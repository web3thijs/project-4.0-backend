package fact.it.backend.controller;

import fact.it.backend.model.User;
import fact.it.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;

@RequestMapping(path = "api/users")
@RestController
public class UserController {
    @Autowired
    UserRepository userRepository;

    @PostConstruct
    public void fillDB(){
        if(userRepository.count() == 0){
            for(int i = 1; i < 4; i++){
                userRepository.save(new User("FirstName" + i, "LastName" + i, "test" + i + "@test.test", "hash", "+324000000", "testaddress" + i, "2400", "Country" + i, false, false, "", "", "", "", "", ""));
            }
        }

        System.out.println("DB test users: " + userRepository.findAll().size());
    }

    @GetMapping("")
    public List<User> findAll() { return userRepository.findAll(); }

    @GetMapping("/{id}")
    public User findById(@PathVariable String id) { return userRepository.findUserById(id); }

    @PutMapping("")
    public User updateUser(@RequestBody User updatedUser){
        User retrievedUser = userRepository.findUserById(updatedUser.getId());

        retrievedUser.setFirstName(updatedUser.getFirstName());
        retrievedUser.setLastName(updatedUser.getLastName());
        retrievedUser.setEmail(updatedUser.getEmail());
        retrievedUser.setPassword(updatedUser.getPassword());
        retrievedUser.setPhoneNr(updatedUser.getPhoneNr());
        retrievedUser.setAddress(updatedUser.getAddress());
        retrievedUser.setPostalCode(updatedUser.getPostalCode());
        retrievedUser.setCountry(updatedUser.getCountry());
        retrievedUser.setAdmin(updatedUser.isAdmin());
        retrievedUser.setOrganization(updatedUser.isOrganization());
        retrievedUser.setOrgName(updatedUser.getOrgName());
        retrievedUser.setCompanyRegistrationNr(updatedUser.getCompanyRegistrationNr());
        retrievedUser.setVatNr(updatedUser.getVatNr());
        retrievedUser.setAbout(updatedUser.getAbout());
        retrievedUser.setPhoneNr(updatedUser.getPhoneNr());
        retrievedUser.setSupportEmail(updatedUser.getSupportEmail());

        userRepository.save(retrievedUser);

        return retrievedUser;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteUser(@PathVariable String id){
        User user = userRepository.findUserById(id);

        if(user != null){
            userRepository.delete(user);
            return ResponseEntity.ok().build();
        } else{
            return ResponseEntity.notFound().build();
        }
    }
}
