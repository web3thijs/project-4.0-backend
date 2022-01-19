package fact.it.backend.controller;

import fact.it.backend.model.Size;
import fact.it.backend.repository.SizeRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;

@RestController
public class SizeController {

    @Autowired
    SizeRepository sizeRepository;

    @PostConstruct
    public void fillDB(){
        if(sizeRepository.count() == 0){
            ObjectId id1 = new ObjectId();
            ObjectId id2 = new ObjectId();
            ObjectId id3 = new ObjectId();
            sizeRepository.save(new Size(id1, "S"));
            sizeRepository.save(new Size(id2, "M"));
            sizeRepository.save(new Size(id3, "L"));
        }
        System.out.println("DB test sizes: " + sizeRepository.findAll().size() + " sizes.");
    }

    @GetMapping("/sizes")
    public List<Size> findAll() {return sizeRepository.findAll();}

    @PostMapping("/sizes")
    public Size addSize(@RequestBody Size size){
        sizeRepository.save(size);
        return size;
    }

    @DeleteMapping("/sizes/{id}")
    public ResponseEntity deleteSize(@PathVariable ObjectId id){
        Size size = sizeRepository.findSizeById(id);

        if(size != null){
            sizeRepository.delete(size);
            return ResponseEntity.ok().build();
        } else{
            return ResponseEntity.notFound().build();
        }
    }
}
