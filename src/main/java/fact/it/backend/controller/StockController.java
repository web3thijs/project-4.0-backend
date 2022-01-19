package fact.it.backend.controller;

import fact.it.backend.model.Product;
import fact.it.backend.model.Stock;
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

    @PostConstruct
    public void fillDB(){
        if(stockRepository.count() == 0){
            ObjectId id1 = new ObjectId();
            ObjectId id2 = new ObjectId();
            stockRepository.save(new Stock(id1, id1, id1, id1, 10));
            stockRepository.save(new Stock(id2, id2, id2, id2, 15));
        }
        System.out.println("DB test stocks: " + stockRepository.findAll().size() + " stocks.");

    }

    @GetMapping("")
    public List<Stock> findAll(){
        return stockRepository.findAll();
    }

    @GetMapping("/product/{productId}")
    public List<Stock> findStocksByProductId(@PathVariable ObjectId productId){
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

        retrievedStock.setSizeId(updatedStock.getSizeId());
        retrievedStock.setColorId(updatedStock.getColorId());
        retrievedStock.setProductId(updatedStock.getProductId());
        retrievedStock.setAmountInStock(updatedStock.getAmountInStock());

        stockRepository.save(retrievedStock);

        return retrievedStock;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteStock(@PathVariable ObjectId id){
        Stock stock = stockRepository.findStockById(id);

        if(stock != null){
            stockRepository.delete(stock);
            return ResponseEntity.ok().build();
        }else{
            return ResponseEntity.notFound().build();
        }
    }
}
