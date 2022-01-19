package fact.it.backend.controller;

import fact.it.backend.model.Color;
import fact.it.backend.repository.ColorRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;

@RestController
public class ColorController {

    @Autowired
    ColorRepository colorRepository;

    @PostConstruct
    public void fillDB(){
        if(colorRepository.count() == 0){
             ObjectId id1 = new ObjectId();
             ObjectId id2 = new ObjectId();
             ObjectId id3 = new ObjectId();
             colorRepository.save(new Color(id1, "red"));
             colorRepository.save(new Color(id2, "green"));
             colorRepository.save(new Color(id3, "blue"));
        }
        System.out.println("DB test colors: " + colorRepository.findAll().size() + " colors.");
    }

    @GetMapping("/colors")
    public List<Color> findAll() {return colorRepository.findAll();}

    @PostMapping("/colors")
    public Color addColor(@RequestBody Color color){
        colorRepository.save(color);
        return color;
    }

    @DeleteMapping("/colors/{id}")
    public ResponseEntity deleteColor(@PathVariable ObjectId id){
        Color color = colorRepository.findColorById(id);

        if(color != null){
            colorRepository.delete(color);
            return ResponseEntity.ok().build();
        } else{
            return ResponseEntity.notFound().build();
        }
    }
}
