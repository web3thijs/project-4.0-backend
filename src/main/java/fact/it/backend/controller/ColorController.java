package fact.it.backend.controller;

import fact.it.backend.model.Color;
import fact.it.backend.repository.ColorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;

@RequestMapping(path = "api/colors")
@RestController
public class ColorController {

    @Autowired
    ColorRepository colorRepository;

    @PostConstruct
    public void fillDB(){
        if(colorRepository.count() == 0){
             colorRepository.save(new Color("red"));
             colorRepository.save(new Color("green"));
             colorRepository.save(new Color("blue"));
        }
        System.out.println("DB test colors: " + colorRepository.findAll().size() + " colors.");
    }

    @GetMapping("")
    public List<Color> findAll() {return colorRepository.findAll();}

    @PostMapping("")
    public Color addColor(@RequestBody Color color){
        colorRepository.save(color);
        return color;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteColor(@PathVariable String id){
        Color color = colorRepository.findColorById(id);

        if(color != null){
            colorRepository.delete(color);
            return ResponseEntity.ok().build();
        } else{
            return ResponseEntity.notFound().build();
        }
    }
}
