package fact.it.backend.controller;

import fact.it.backend.model.*;
import fact.it.backend.repository.StockRepository;
import fact.it.backend.util.JwtUtils;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

@RequestMapping(path = "api/stocks")
@RestController
public class StockController {

    @Autowired
    StockRepository stockRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @GetMapping("")
    public Page<Stock> findAll(@RequestParam(required = false) Integer page, @RequestParam(required = false)String sort, @RequestParam(required = false)String order){
        Integer pageable = page;
        if(page == null){
            pageable = 0;
        }
        if(sort != null){
            if(order != null && order.equals("desc")){
                Pageable requestedPageWithSortDesc = PageRequest.of(pageable, 8, Sort.by(sort).descending());
                Page<Stock> stocks = stockRepository.findAll(requestedPageWithSortDesc);
                return stocks;
            }
            else{
                Pageable requestedPageWithSort = PageRequest.of(pageable, 8, Sort.by(sort).ascending());
                Page<Stock> stocks = stockRepository.findAll(requestedPageWithSort);
                return stocks;
            }
        }else{
            Pageable requestedPage = PageRequest.of(pageable, 8, Sort.by("amountInStock").ascending());
            Page<Stock> stocks = stockRepository.findAll(requestedPage);
            return stocks;
        }
    }

    @GetMapping("/product/{productId}")
    public List<Stock> findStocksByProductId(@PathVariable String productId){
        return stockRepository.findStocksByProductId(productId);
    }

    @PostMapping("")
    public ResponseEntity<?> addStock(@RequestHeader("Authorization") String tokenWithPrefix, @RequestBody Stock stock){
        String token = tokenWithPrefix.substring(7);
        Map<String, Object> claims = jwtUtils.extractAllClaims(token);
        String role = claims.get("role").toString();
        String user_id = claims.get("user_id").toString();

        if(role.contains("ADMIN")){
            stockRepository.save(stock);
            return ResponseEntity.ok(stock);
        } else {
            return new ResponseEntity<String>("Forbidden", HttpStatus.FORBIDDEN);
        }
    }

    @PutMapping("")
    public ResponseEntity<?> updateStock(@RequestHeader("Authorization") String tokenWithPrefix, @RequestBody Stock updatedStock){
        String token = tokenWithPrefix.substring(7);
        Map<String, Object> claims = jwtUtils.extractAllClaims(token);
        String role = claims.get("role").toString();
        String user_id = claims.get("user_id").toString();

        if(role.contains("ADMIN")){
            Stock retrievedStock = stockRepository.findStockById(updatedStock.getId());

            retrievedStock.setSize(updatedStock.getSize());
            retrievedStock.setColor(updatedStock.getColor());
            retrievedStock.setProduct(updatedStock.getProduct());
            retrievedStock.setAmountInStock(updatedStock.getAmountInStock());

            stockRepository.save(retrievedStock);

            return ResponseEntity.ok(retrievedStock);
        } else {
            return new ResponseEntity<String>("Forbidden", HttpStatus.FORBIDDEN);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteStock(@RequestHeader("Authorization") String tokenWithPrefix, @PathVariable String id){
        String token = tokenWithPrefix.substring(7);
        Map<String, Object> claims = jwtUtils.extractAllClaims(token);
        String role = claims.get("role").toString();
        String user_id = claims.get("user_id").toString();

        if(role.contains("ADMIN")){
            Stock stock = stockRepository.findStockById(id);

            if(stock != null){
                stockRepository.delete(stock);
                return ResponseEntity.ok().build();
            }else{
                return ResponseEntity.notFound().build();
            }
        } else {
            return new ResponseEntity<String>("Forbidden", HttpStatus.FORBIDDEN);
        }
    }
}
