package fact.it.backend.controller;

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

    @PostConstruct
    public void fillDatabase(){
        stockRepository.save(new Stock(50, sizeRepository.findSizeById(5), colorRepository.findColorById(4), productRepository.findProductById(1)));
//        stockRepository.save(new Stock(sizeRepository.findSizeById(8), colorRepository.findColorById(4), productOrangoetanKnuffel, 20));
        stockRepository.save(new Stock(50, sizeRepository.findSizeById(8), colorRepository.findColorById(9), productRepository.findProductById(2)));
        stockRepository.save(new Stock(200, sizeRepository.findSizeById(8), colorRepository.findColorById(2), productRepository.findProductById(4)));
        stockRepository.save(new Stock(100, sizeRepository.findSizeById(8), colorRepository.findColorById(8), productRepository.findProductById(5)));
        stockRepository.save(new Stock(50, sizeRepository.findSizeById(5), colorRepository.findColorById(2), productRepository.findProductById(6)));
        stockRepository.save(new Stock(200, sizeRepository.findSizeById(2), colorRepository.findColorById(2), productRepository.findProductById(7)));
        stockRepository.save(new Stock(150, sizeRepository.findSizeById(2), colorRepository.findColorById(3), productRepository.findProductById(8)));
        stockRepository.save(new Stock(200, sizeRepository.findSizeById(2), colorRepository.findColorById(2), productRepository.findProductById(9)));
//        stockRepository.save(new Stock(sizeRepository.findSizeById(2), colorRepository.findColorById(2), productShirtMannenTP, 25));
//        stockRepository.save(new Stock(sizeRepository.findSizeById(3), colorRepository.findColorById(2), productShirtMannenTP, 50));
//        stockRepository.save(new Stock(sizeRepository.findSizeById(2), colorRepository.findColorById(6), productShirtVrouwenTP, 50));
//        stockRepository.save(new Stock(sizeRepository.findSizeById(1), colorRepository.findColorById(6), productShirtVrouwenTP, 40));
//        stockRepository.save(new Stock(sizeRepository.findSizeById(9), colorRepository.findColorById(6), productDrinkbusTP, 80));
//        stockRepository.save(new Stock(sizeRepository.findSizeById(10), colorRepository.findColorById(1), productGeurkaarsBZN, 40));
//        stockRepository.save(new Stock(sizeRepository.findSizeById(8), colorRepository.findColorById(5), productGeurstokjesBZN, 30));
//        stockRepository.save(new Stock(sizeRepository.findSizeById(10), colorRepository.findColorById(2), productSojakaarsBZN, 20));
//        stockRepository.save(new Stock(sizeRepository.findSizeById(8), colorRepository.findColorById(2), product3pennenBKKS, 60));
//        stockRepository.save(new Stock(sizeRepository.findSizeById(2), colorRepository.findColorById(2), productMondmaskerBKKS, 25));
//        stockRepository.save(new Stock(sizeRepository.findSizeById(1), colorRepository.findColorById(9), productShirtBKKS, 15));
//        stockRepository.save(new Stock(sizeRepository.findSizeById(2), colorRepository.findColorById(9), productShirtBKKS, 30));
//        stockRepository.save(new Stock(sizeRepository.findSizeById(3), colorRepository.findColorById(9), productShirtBKKS, 30));
    }

    @GetMapping
    public Page<Stock> findAll(@RequestParam(required = false, defaultValue = "0") Integer page, @RequestParam(required = false, defaultValue = "amountInStock")String sort, @RequestParam(required = false)String order){
            if(order != null && order.equals("desc")){
                Pageable requestedPageWithSortDesc = PageRequest.of(page, 9, Sort.by(sort).descending());
                Page<Stock> stocks = stockRepository.findAll(requestedPageWithSortDesc);
                return stocks;
            }
            else{
                Pageable requestedPageWithSort = PageRequest.of(page, 9, Sort.by(sort).ascending());
                Page<Stock> stocks = stockRepository.findAll(requestedPageWithSort);
                return stocks;
            }
        }

    @GetMapping("/product/{productId}")
    public List<Stock> findStocksByProductId(@PathVariable long productId){
        return stockRepository.findStocksByProductId(productId);
    }

    @PostMapping
    public ResponseEntity<?> addStock(@RequestHeader("Authorization") String tokenWithPrefix, @RequestBody Stock stock){
        String token = tokenWithPrefix.substring(7);
        Map<String, Object> claims = jwtUtils.extractAllClaims(token);
        String role = claims.get("role").toString();
        long user_id = Long.parseLong(claims.get("user_id").toString());

        if(role.contains("ADMIN")){
            stockRepository.save(stock);
            return ResponseEntity.ok(stock);
        } else {
            return new ResponseEntity<String>("Forbidden", HttpStatus.FORBIDDEN);
        }
    }

    @PutMapping
    public ResponseEntity<?> updateStock(@RequestHeader("Authorization") String tokenWithPrefix, @RequestBody Stock updatedStock){
        String token = tokenWithPrefix.substring(7);
        Map<String, Object> claims = jwtUtils.extractAllClaims(token);
        String role = claims.get("role").toString();
        long user_id = Long.parseLong(claims.get("user_id").toString());

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
    public ResponseEntity deleteStock(@RequestHeader("Authorization") String tokenWithPrefix, @PathVariable long id){
        String token = tokenWithPrefix.substring(7);
        Map<String, Object> claims = jwtUtils.extractAllClaims(token);
        String role = claims.get("role").toString();
        long user_id = Long.parseLong(claims.get("user_id").toString());

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
