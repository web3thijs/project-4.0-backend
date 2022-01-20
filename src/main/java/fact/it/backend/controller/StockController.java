package fact.it.backend.controller;

import fact.it.backend.model.Size;
import fact.it.backend.model.Stock;
import fact.it.backend.repository.StockRepository;
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
            Size size1 = new Size("61e7ca11710259397a88e7cf", "S");
            Size size2 = new Size("61e7ca11710259397a88e7d0", "M");
            String id1 = new String().toString();
            String id2 = new String().toString();
            stockRepository.save(new Stock(size1, id1, id1, 10));
            stockRepository.save(new Stock(size2, id2, id2, 15));
        }
        System.out.println("DB test stocks: " + stockRepository.findAll().size() + " stocks.");

    }

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
        retrievedStock.setColorId(updatedStock.getColorId());
        retrievedStock.setProductId(updatedStock.getProductId());
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
