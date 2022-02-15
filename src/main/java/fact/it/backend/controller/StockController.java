package fact.it.backend.controller;

import fact.it.backend.exception.ResourceNotFoundException;
import fact.it.backend.model.*;
import fact.it.backend.repository.ColorRepository;
import fact.it.backend.repository.ProductRepository;
import fact.it.backend.repository.SizeRepository;
import fact.it.backend.repository.StockRepository;
import fact.it.backend.util.JwtUtils;
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
@RequestMapping(path = "api/stocks")
public class StockController {

    @Autowired
    StockRepository stockRepository;

    @Autowired
    SizeRepository sizeRepository;

    @Autowired
    ColorRepository colorRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @GetMapping
    public ResponseEntity<?> findAll(@RequestParam(required = false, defaultValue = "0") Integer page, @RequestParam(required = false, defaultValue = "amountInStock")String sort, @RequestParam(required = false)String order){
            if(order != null && order.equals("desc")){
                Pageable requestedPageWithSortDesc = PageRequest.of(page, 8, Sort.by(sort).descending());
                Page<Stock> stocks = stockRepository.findAll(requestedPageWithSortDesc);
                return ResponseEntity.ok(stocks);
            }
            else{
                Pageable requestedPageWithSort = PageRequest.of(page, 8, Sort.by(sort).ascending());
                Page<Stock> stocks = stockRepository.findAll(requestedPageWithSort);
                return ResponseEntity.ok(stocks);
            }
        }

    @GetMapping("/product/{productId}")
    public ResponseEntity<?> findStocksByProductId(@PathVariable long productId) throws ResourceNotFoundException {
        List<Stock> stocks = stockRepository.findStocksByProductId(productId);

        if(stocks.size() != 0){
            return ResponseEntity.ok(stocks);
        }else{
            throw new ResourceNotFoundException("No stocks found for product with id: " + productId);
        }
    }

    @PostMapping
    public ResponseEntity<?> addStock(@RequestHeader("Authorization") String tokenWithPrefix, @Valid @RequestBody Stock stock){
        String token = tokenWithPrefix.substring(7);
        Map<String, Object> claims = jwtUtils.extractAllClaims(token);
        String role = claims.get("role").toString();
        long user_id = Long.parseLong(claims.get("user_id").toString());

        if(role.contains("ADMIN") || (role.contains("ORGANIZATION"))){
            stockRepository.save(stock);
            return ResponseEntity.ok(stock);
        } else {
            return new ResponseEntity<String>("Not authorized", HttpStatus.FORBIDDEN);
        }
    }

    @PutMapping
    public ResponseEntity<?> updateStock(@RequestHeader("Authorization") String tokenWithPrefix, @Valid @RequestBody Stock updatedStock) throws ResourceNotFoundException {
        String token = tokenWithPrefix.substring(7);
        Map<String, Object> claims = jwtUtils.extractAllClaims(token);
        String role = claims.get("role").toString();
        long user_id = Long.parseLong(claims.get("user_id").toString());

        if(role.contains("ADMIN") || (role.contains("ORGANIZATION") && updatedStock.getProduct().getOrganization().getId() == user_id)){
            Stock retrievedStock = stockRepository.findById(updatedStock.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Cannot update. Stock not found for this id: " + updatedStock.getId()));

            retrievedStock.setSize(sizeRepository.findSizeById(updatedStock.getSize().getId()));
            retrievedStock.setColor(colorRepository.findColorById(updatedStock.getColor().getId()));
            retrievedStock.setProduct(productRepository.findProductById(updatedStock.getProduct().getId()));
            retrievedStock.setAmountInStock(updatedStock.getAmountInStock());

            stockRepository.save(retrievedStock);

            return ResponseEntity.ok(retrievedStock);
        } else {
            return new ResponseEntity<String>("Not authorized", HttpStatus.FORBIDDEN);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteStock(@RequestHeader("Authorization") String tokenWithPrefix, @PathVariable long id) throws ResourceNotFoundException {
        String token = tokenWithPrefix.substring(7);
        Map<String, Object> claims = jwtUtils.extractAllClaims(token);
        String role = claims.get("role").toString();
        long user_id = Long.parseLong(claims.get("user_id").toString());

        if(role.contains("ADMIN") || (role.contains("ORGANIZATION") && stockRepository.findStockById(id).getProduct().getOrganization().getId() == user_id)){
            Stock stock = stockRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Cannot delete. Stock not found for this id: " + id));

                stockRepository.delete(stock);
                return ResponseEntity.ok().build();
        } else {
            return new ResponseEntity<String>("Not authorized", HttpStatus.FORBIDDEN);
        }
    }
}
