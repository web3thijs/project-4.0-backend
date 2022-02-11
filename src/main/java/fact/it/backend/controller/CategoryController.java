package fact.it.backend.controller;

import fact.it.backend.exception.ResourceNotFoundException;
import fact.it.backend.model.Category;
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

import javax.validation.Valid;
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
    public List<Category> findAll(@RequestParam(required = false, defaultValue = "0") Integer page, @RequestParam(required = false, defaultValue = "name") String sort, @RequestParam(required = false) String order) {
        if (order != null && order.equals("desc")) {
            return categoryRepository.findAll(Sort.by(sort).descending());
        } else {
            return categoryRepository.findAll(Sort.by(sort));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable long id) throws ResourceNotFoundException {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found for this id: " + id));
        return ResponseEntity.ok().body(category);
    }

    @PostMapping
    public ResponseEntity<?> addCategory(@RequestHeader("Authorization") String tokenWithPrefix, @Valid @RequestBody Category category) {
        String token = tokenWithPrefix.substring(7);
        Map<String, Object> claims = jwtUtils.extractAllClaims(token);
        String role = claims.get("role").toString();

        if (role.contains("ADMIN")) {
            categoryRepository.save(category);
            return ResponseEntity.ok(category);
        } else {
            return new ResponseEntity<String>("Not authorized", HttpStatus.FORBIDDEN);
        }
    }

    @PutMapping
    public ResponseEntity<?> updateCategory(@RequestHeader("Authorization") String tokenWithPrefix, @Valid @RequestBody Category updatedCategory) throws ResourceNotFoundException {
        String token = tokenWithPrefix.substring(7);
        Map<String, Object> claims = jwtUtils.extractAllClaims(token);
        String role = claims.get("role").toString();

        if (role.contains("ADMIN")) {
            Category retrievedCategory = categoryRepository.findById(updatedCategory.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Cannot update. category not found for this id: " + updatedCategory.getId()));

            retrievedCategory.setName(updatedCategory.getName());

            categoryRepository.save(retrievedCategory);

            return ResponseEntity.ok(retrievedCategory);
        } else {
            return new ResponseEntity<>("Not authorized", HttpStatus.FORBIDDEN);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteCategory(@RequestHeader("Authorization") String tokenWithPrefix, @PathVariable long id) throws ResourceNotFoundException {
        String token = tokenWithPrefix.substring(7);
        Map<String, Object> claims = jwtUtils.extractAllClaims(token);
        String role = claims.get("role").toString();

        if (role.contains("ADMIN")) {
            Category category = categoryRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Cannot delete. Category not found for this id: " + id));

            categoryRepository.delete(category);
            return ResponseEntity.ok().build();
        } else {
            return new ResponseEntity<String>("Not authorized", HttpStatus.FORBIDDEN);
        }
    }
}
