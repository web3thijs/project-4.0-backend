package fact.it.backend.controller;

import fact.it.backend.model.*;
import fact.it.backend.repository.StockRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;

@RequestMapping(path = "api/stocks")
@RestController
public class StockController {

    @Autowired
    StockRepository stockRepository;

    @GetMapping("")
    public List<Stock> findAll(){
        return stockRepository.findAll();
    }

    @GetMapping("/product/{productId}")
    public List<Stock> findStocksByProductId(@PathVariable String productId){
        return stockRepository.findStocksByProductId(productId);
    }

    @PostMapping("")
    public Stock addStock(@RequestBody Stock stock){
        stockRepository.save(stock);
        return stock;
    }

    @PutMapping("")
    public Stock updateStock(@RequestBody Stock updatedStock){
        Stock retrievedStock = stockRepository.findStockById(updatedStock.getId());

        retrievedStock.setSize(updatedStock.getSize());
        retrievedStock.setColor(updatedStock.getColor());
        retrievedStock.setProduct(updatedStock.getProduct());
        retrievedStock.setAmountInStock(updatedStock.getAmountInStock());

        stockRepository.save(retrievedStock);

        return retrievedStock;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteStock(@PathVariable String id){
        Stock stock = stockRepository.findStockById(id);

        if(stock != null){
            stockRepository.delete(stock);
            return ResponseEntity.ok().build();
        }else{
            return ResponseEntity.notFound().build();
        }
    }
}
