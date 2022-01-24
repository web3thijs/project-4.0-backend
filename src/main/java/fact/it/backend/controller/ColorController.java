package fact.it.backend.controller;

import fact.it.backend.model.Category;
import fact.it.backend.model.Color;
import fact.it.backend.model.Role;
import fact.it.backend.repository.ColorRepository;
import fact.it.backend.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
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

//    @GetMapping("")
//    public String findAll(@RequestHeader("authorization") String tokenWithPrefix) {
//        String token = tokenWithPrefix.substring(7);
//        Map<String, Object> claims = jwtUtils.extractAllClaims(token);
//        String role = claims.get("role").toString();
//
//        return role;
//    }
  
    @GetMapping("")
    public List<Color> findAll() {return colorRepository.findAll();}

    @GetMapping("/{id}")
    public Color findById(@PathVariable String id) { return colorRepository.findColorById(id); }

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
