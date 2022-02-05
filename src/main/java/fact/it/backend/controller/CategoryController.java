package fact.it.backend.controller;

import fact.it.backend.model.AuthResponse;
import fact.it.backend.model.Category;
import fact.it.backend.model.Product;
import fact.it.backend.repository.CategoryRepository;
import fact.it.backend.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "api/categories")
public class CategoryController {
    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @GetMapping
    public List<Category> findAll(@RequestParam(required = false, defaultValue = "name") String sort, @RequestParam(required = false)String order){
            if(order != null && order.equals("desc")){
                return categoryRepository.findAll(Sort.by(sort).descending());
            }
            else{
                return categoryRepository.findAll(Sort.by(sort).ascending());
            }
        }

    @GetMapping("/{id}")
    public Category findById(@PathVariable long id) { return categoryRepository.findCategoryById(id); }

    @PostMapping
    public ResponseEntity<?> addCategory(@RequestHeader("Authorization") String tokenWithPrefix, @RequestBody Category category){
        String token = tokenWithPrefix.substring(7);
        Map<String, Object> claims = jwtUtils.extractAllClaims(token);
        String role = claims.get("role").toString();

        if(role.contains("ADMIN")){
            categoryRepository.save(category);
            return ResponseEntity.ok(category);
        } else {
            return new ResponseEntity<String>("Forbidden", HttpStatus.FORBIDDEN);
        }
    }

    @PutMapping
    public ResponseEntity<?> updateCategory(@RequestHeader("Authorization") String tokenWithPrefix, @RequestBody Category updatedCategory){
        String token = tokenWithPrefix.substring(7);
        Map<String, Object> claims = jwtUtils.extractAllClaims(token);
        String role = claims.get("role").toString();

        if(role.contains("ADMIN")){
            Category retrievedCategory = categoryRepository.findCategoryById(updatedCategory.getId());

            retrievedCategory.setName(updatedCategory.getName());

            categoryRepository.save(retrievedCategory);

            return ResponseEntity.ok(retrievedCategory);
        } else {
            return new ResponseEntity<String>("Forbidden", HttpStatus.FORBIDDEN);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteCategory(@RequestHeader("authorization") String tokenWithPrefix, @PathVariable long id){
        String token = tokenWithPrefix.substring(7);
        Map<String, Object> claims = jwtUtils.extractAllClaims(token);
        String role = claims.get("role").toString();

        if(role.contains("ADMIN")){
            Category category = categoryRepository.findCategoryById(id);

            if(category != null){
                categoryRepository.delete(category);
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return new ResponseEntity<String>("Forbidden", HttpStatus.FORBIDDEN);
        }
    }
}
