package fact.it.backend.controller;

import fact.it.backend.model.Product;
import fact.it.backend.model.Size;
import fact.it.backend.repository.SizeRepository;
import fact.it.backend.util.JwtUtils;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

@RequestMapping(path = "api/sizes")
@RestController
public class SizeController {

    @Autowired
    SizeRepository sizeRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @GetMapping("")
    public Page<Size> findAll(@RequestParam int page) {
        Pageable requestedPage = PageRequest.of(page, 8);
        Page<Size> sizes = sizeRepository.findAll(requestedPage);
        return sizes;}

    @GetMapping("{/:id}")
    public Size findById(@PathVariable String id) {return sizeRepository.findSizeById(id);}

    @PostMapping("")
    public ResponseEntity<?> addSize(@RequestHeader("Authorization") String tokenWithPrefix, @RequestBody Size size){
        String token = tokenWithPrefix.substring(7);
        Map<String, Object> claims = jwtUtils.extractAllClaims(token);
        String role = claims.get("role").toString();
        String user_id = claims.get("user_id").toString();

        if(role.contains("ADMIN")){
            sizeRepository.save(size);
            return ResponseEntity.ok(size);
        } else {
            return new ResponseEntity<String>("Forbidden", HttpStatus.FORBIDDEN);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteSize(@RequestHeader("Authorization") String tokenWithPrefix, @PathVariable String id){
        String token = tokenWithPrefix.substring(7);
        Map<String, Object> claims = jwtUtils.extractAllClaims(token);
        String role = claims.get("role").toString();

        if(role.contains("ADMIN")){
            Size size = sizeRepository.findSizeById(id);

            if(size != null){
                sizeRepository.delete(size);
                return ResponseEntity.ok().build();
            } else{
                return ResponseEntity.notFound().build();
            }
        } else {
            return new ResponseEntity<String>("Forbidden", HttpStatus.FORBIDDEN);
        }
    }
}
