package fact.it.backend.controller;

import fact.it.backend.model.Category;
import fact.it.backend.model.Color;
import fact.it.backend.model.Product;
import fact.it.backend.model.Role;
import fact.it.backend.repository.ColorRepository;
import fact.it.backend.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping(path = "api/colors")
@RestController
public class ColorController {

    @Autowired
    ColorRepository colorRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @PostConstruct
    public void fillDatabase(){
        colorRepository.save(new Color("rood"));
        colorRepository.save(new Color("wit"));
        colorRepository.save(new Color("groen"));
        colorRepository.save(new Color("bruin"));
        colorRepository.save(new Color("geel"));
        colorRepository.save(new Color("roze"));
        colorRepository.save(new Color("beige"));
        colorRepository.save(new Color("mix"));
        colorRepository.save(new Color("zwart en wit"));
    }
  
    @GetMapping
    public Page<Color> findAll(@RequestParam(required = false, defaultValue = "0") Integer page, @RequestParam(required = false, defaultValue = "name") String sort, @RequestParam(required = false) String order) {
            if(order != null && order.equals("desc")){
                Pageable requestedPageWithSortDesc = PageRequest.of(page, 9, Sort.by(sort).descending());
                Page<Color> colors = colorRepository.findAll(requestedPageWithSortDesc);
                return colors;
            }
            else{
                Pageable requestedPageWithSort = PageRequest.of(page, 9, Sort.by(sort).ascending());
                Page<Color> colors = colorRepository.findAll(requestedPageWithSort);
                return colors;
            }
        }

    @GetMapping("/{id}")
    public Color findById(@PathVariable long id) { return colorRepository.findColorById(id); }

    @PostMapping
    public ResponseEntity<?> addColor(@RequestHeader("Authorization") String tokenWithPrefix, @RequestBody Color color){
        String token = tokenWithPrefix.substring(7);
        Map<String, Object> claims = jwtUtils.extractAllClaims(token);
        String role = claims.get("role").toString();

        if(role.contains("ADMIN")){
            colorRepository.save(color);
            return ResponseEntity.ok(color);
        } else {
            return new ResponseEntity<String>("Forbidden", HttpStatus.FORBIDDEN);
        }
    }

    @PutMapping
    public ResponseEntity<?> updateColor(@RequestHeader("Authorization") String tokenWithPrefix, @RequestBody Color updatedColor){
        String token = tokenWithPrefix.substring(7);
        Map<String, Object> claims = jwtUtils.extractAllClaims(token);
        String role = claims.get("role").toString();

        if(role.contains("ADMIN")){
            Color retrievedColor = colorRepository.findColorById(updatedColor.getId());

            retrievedColor.setName(updatedColor.getName());
            colorRepository.save(updatedColor);

            return ResponseEntity.ok(updatedColor);
        } else {
            return new ResponseEntity<String>("Forbidden", HttpStatus.FORBIDDEN);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteColor(@RequestHeader("Authorization") String tokenWithPrefix, @PathVariable long id){
        String token = tokenWithPrefix.substring(7);
        Map<String, Object> claims = jwtUtils.extractAllClaims(token);
        String role = claims.get("role").toString();

        if(role.contains("ADMIN")){
            Color color = colorRepository.findColorById(id);

            if(color != null){
                colorRepository.delete(color);
                return ResponseEntity.ok().build();
            } else{
                return ResponseEntity.notFound().build();
            }
        } else {
            return new ResponseEntity<String>("Forbidden", HttpStatus.FORBIDDEN);
        }
    }
}
