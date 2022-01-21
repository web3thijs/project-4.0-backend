package fact.it.backend.controller;

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

    @PostConstruct
    public void fillDB(){
        if(colorRepository.count() == 0){
             colorRepository.save(new Color("61e7c6f1abd83a51b5208b01","red"));
             colorRepository.save(new Color("61e7c6f1abd83a51b5208b02", "green"));
             colorRepository.save(new Color("61e7c6f1abd83a51b5208b03", "blue"));
        }

        System.out.println("DB test colors: " + colorRepository.findAll().size() + " colors.");
    }

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
