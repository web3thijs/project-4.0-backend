package fact.it.backend.controller;

import fact.it.backend.model.Size;
import fact.it.backend.repository.SizeRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;

@RequestMapping(path = "api/sizes")
@RestController
public class SizeController {

    @Autowired
    SizeRepository sizeRepository;

    @PostConstruct
    public void fillDB(){
        if(sizeRepository.count() == 0){
            sizeRepository.save(new Size("61e7ca11710259397a88e7cf","S"));
            sizeRepository.save(new Size("61e7ca11710259397a88e7d0","M"));
            sizeRepository.save(new Size("61e7ca11710259397a88e7d1","L"));
        }
        System.out.println("DB test sizes: " + sizeRepository.findAll().size() + " sizes.");
    }

    @GetMapping("")
    public List<Size> findAll() {return sizeRepository.findAll();}

    @PostMapping("")
    public Size addSize(@RequestBody Size size){
        sizeRepository.save(size);
        return size;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteSize(@PathVariable String id){
        Size size = sizeRepository.findSizeById(id);

        if(size != null){
            sizeRepository.delete(size);
            return ResponseEntity.ok().build();
        } else{
            return ResponseEntity.notFound().build();
        }
    }
}
