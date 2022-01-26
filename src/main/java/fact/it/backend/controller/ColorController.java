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
  
    @GetMapping("")
    public Page<Color> findAll(@RequestParam(required = false) Integer page, @RequestParam(required = false) String sort, @RequestParam(required = false) String order) {
        Integer pageable = page;
        if(page == null){
            pageable = 0;
        }
        if(sort != null){
            if(order != null && order.equals("desc")){
                Pageable requestedPageWithSortDesc = PageRequest.of(pageable, 8, Sort.by(sort).descending());
                Page<Color> colors = colorRepository.findAll(requestedPageWithSortDesc);
                return colors;
            }
            else{
                Pageable requestedPageWithSort = PageRequest.of(pageable, 8, Sort.by(sort).ascending());
                Page<Color> colors = colorRepository.findAll(requestedPageWithSort);
                return colors;
            }
        }else{
            Pageable requestedPage = PageRequest.of(pageable, 8, Sort.by("name").ascending());
            Page<Color> colors = colorRepository.findAll(requestedPage);
            return colors;
        }
    }

    @GetMapping("/{id}")
    public Color findById(@PathVariable String id) { return colorRepository.findColorById(id); }

    @PostMapping("")
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

    @DeleteMapping("/{id}")
    public ResponseEntity deleteColor(@RequestHeader("Authorization") String tokenWithPrefix, @PathVariable String id){
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
