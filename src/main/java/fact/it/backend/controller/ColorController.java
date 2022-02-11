package fact.it.backend.controller;

import fact.it.backend.exception.ResourceNotFoundException;
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
import javax.validation.Valid;
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
  
    @GetMapping
    public List<Color> findAll(@RequestParam(required = false, defaultValue = "0") Integer page, @RequestParam(required = false, defaultValue = "name") String sort, @RequestParam(required = false) String order) {
            if(order != null && order.equals("desc")){
                List<Color> colors = colorRepository.findAll(Sort.by(sort).descending());
                return colors;
            }
            else{
                List<Color> colors = colorRepository.findAll(Sort.by(sort).ascending());
                return colors;
            }
        }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable long id) throws ResourceNotFoundException {
        Color color = colorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Color not found for this id: " + id));
        return ResponseEntity.ok().body(color);
    }

    @PostMapping
    public ResponseEntity<?> addColor(@RequestHeader("Authorization") String tokenWithPrefix, @Valid @RequestBody Color color){
        String token = tokenWithPrefix.substring(7);
        Map<String, Object> claims = jwtUtils.extractAllClaims(token);
        String role = claims.get("role").toString();

        if(role.contains("ADMIN")){
            colorRepository.save(color);
            return ResponseEntity.ok(color);
        } else {
            return new ResponseEntity<String>("Not authorized", HttpStatus.FORBIDDEN);
        }
    }

    @PutMapping
    public ResponseEntity<?> updateColor(@RequestHeader("Authorization") String tokenWithPrefix, @Valid @RequestBody Color updatedColor) throws ResourceNotFoundException {
        String token = tokenWithPrefix.substring(7);
        Map<String, Object> claims = jwtUtils.extractAllClaims(token);
        String role = claims.get("role").toString();

        if(role.contains("ADMIN")){
            Color retrievedColor = colorRepository.findById(updatedColor.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Cannot update. category not found for this id: " + updatedColor.getId()));

            retrievedColor.setName(updatedColor.getName());

            colorRepository.save(retrievedColor);

            return ResponseEntity.ok(updatedColor);
        } else {
            return new ResponseEntity<String>("Not authorized", HttpStatus.FORBIDDEN);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteColor(@RequestHeader("Authorization") String tokenWithPrefix, @PathVariable long id) throws ResourceNotFoundException {
        String token = tokenWithPrefix.substring(7);
        Map<String, Object> claims = jwtUtils.extractAllClaims(token);
        String role = claims.get("role").toString();

        if(role.contains("ADMIN")){
            Color color = colorRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Cannot delete. category not found for this id: " + id));

                colorRepository.delete(color);
                return ResponseEntity.ok().build();
        } else {
            return new ResponseEntity<String>("Not authorized", HttpStatus.FORBIDDEN);
        }
    }
}
