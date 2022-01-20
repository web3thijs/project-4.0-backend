package fact.it.backend.controller;

import fact.it.backend.model.Category;
import fact.it.backend.model.Product;
import fact.it.backend.repository.CategoryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;

@RequestMapping(path = "api/categories")
@RestController
public class CategoryController {

    @Autowired
    CategoryRepository categoryRepository;

    @PostConstruct
    public void fillDB(){
        if(categoryRepository.count() == 0){
            categoryRepository.save(new Category("61e7e7ba38d73c28cb36c3eb","shirts"));
            categoryRepository.save(new Category("61e7e7ba38d73c28cb36c3ec","pants"));
            categoryRepository.save(new Category("61e7e7ba38d73c28cb36c3ed","hats"));
        }

        System.out.println("DB test: " + categoryRepository.findAll().size());
    }

    @GetMapping("")
    public List<Category> findAll() { return categoryRepository.findAll(); }

    @GetMapping("/{id}")
    public Category findById(@PathVariable String id) { return categoryRepository.findCategoryById(id); }

    @PostMapping("")
    public Category addCategory(@RequestBody Category category){
        categoryRepository.save(category);
        return category;
    }

    @PutMapping("")
    public Category updateCategory(@RequestBody Category updatedCategory){
        Category retrievedCategory = categoryRepository.findCategoryById(updatedCategory.getId());

        retrievedCategory.setName(updatedCategory.getName());

        categoryRepository.save(retrievedCategory);

        return retrievedCategory;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteCategory(@PathVariable String id){
        Category category = categoryRepository.findCategoryById(id);

        if(category != null){
            categoryRepository.delete(category);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
