package fact.it.backend.controller;

import fact.it.backend.exception.ResourceNotFoundException;
import fact.it.backend.model.Category;
import fact.it.backend.model.OrderDetail;
import fact.it.backend.model.Product;
import fact.it.backend.model.Size;
import fact.it.backend.repository.SizeRepository;
import fact.it.backend.util.JwtUtils;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "api/sizes")
public class SizeController {

    @Autowired
    SizeRepository sizeRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @GetMapping
    public ResponseEntity<?> findAll(@RequestParam(required = false, defaultValue = "0") Integer page, @RequestParam(required = false, defaultValue = "name") String sort, @RequestParam(required = false) String order) {
        if (order != null && order.equals("desc")) {
            Pageable requestedPageWithSortDesc = PageRequest.of(page, 8, Sort.by(sort).descending());
            Page<Size> sizes = sizeRepository.findAll(requestedPageWithSortDesc);
            return ResponseEntity.ok().body(sizes);
        } else {
            Pageable requestedPageWithSort = PageRequest.of(page, 8, Sort.by(sort).ascending());
            Page<Size> sizes = sizeRepository.findAll(requestedPageWithSort);
            return ResponseEntity.ok().body(sizes);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable long id) throws ResourceNotFoundException {
        Size size = sizeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Size not found for this id: " + id));
        return ResponseEntity.ok().body(size);
    }

    @PostMapping
    public ResponseEntity<?> addSize(@RequestHeader("Authorization") String tokenWithPrefix, @Valid @RequestBody Size size) {
        String token = tokenWithPrefix.substring(7);
        Map<String, Object> claims = jwtUtils.extractAllClaims(token);
        String role = claims.get("role").toString();
        long user_id = Long.parseLong(claims.get("user_id").toString());

        if (role.contains("ADMIN")) {
            sizeRepository.save(size);
            return ResponseEntity.ok(size);
        } else {
            return new ResponseEntity<String>("Not authorized", HttpStatus.FORBIDDEN);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteSize(@RequestHeader("Authorization") String tokenWithPrefix, @PathVariable long id) throws ResourceNotFoundException {
        String token = tokenWithPrefix.substring(7);
        Map<String, Object> claims = jwtUtils.extractAllClaims(token);
        String role = claims.get("role").toString();

        if (role.contains("ADMIN")) {
            Size size = sizeRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Size not found for this id: " + id));

            sizeRepository.delete(size);
            return ResponseEntity.ok().build();
        } else {
            return new ResponseEntity<String>("Forbidden", HttpStatus.FORBIDDEN);
        }
    }
}
