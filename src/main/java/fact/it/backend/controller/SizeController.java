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
