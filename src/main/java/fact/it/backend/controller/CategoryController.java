package fact.it.backend.controller;

import fact.it.backend.model.Category;
import fact.it.backend.repository.CategoryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
            categoryRepository.save(new Category(new ObjectId(), "Category 1"));
            categoryRepository.save(new Category(new ObjectId(), "Category 2"));
            categoryRepository.save(new Category(new ObjectId(), "Category 3"));
        }

        System.out.println("DB test: " + categoryRepository.findAll().size());
    }

    @GetMapping("")
    public List<Category> findAll() { return categoryRepository.findAll(); }

    
}
