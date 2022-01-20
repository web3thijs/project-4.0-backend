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

    @PostConstruct
    public void fillDB(){
        if(stockRepository.count() == 0){
            Category category1 = new Category("61e7e7ba38d73c28cb36c3eb", "shirts");
            Category category2 = new Category("61e7e7ba38d73c28cb36c3ec", "pants");
            Size size1 = new Size("61e7ca11710259397a88e7cf", "S");
            Size size2 = new Size("61e7ca11710259397a88e7d0", "M");
            Color color1 = new Color("61e7c6f1abd83a51b5208b01", "red");
            Color color2 = new Color("61e7c6f1abd83a51b5208b02", "green");
            Organization organization1 = new Organization("supporters@wwf.be", "wwf123", "+3223400920", "Emile Jacqmainlaan 90", "1000", "Belgium", Role.ORGANIZATION, "WWF", "BE0408656248", "BE0408656248", "Het World Wide Fund for Nature – waarvan de Nederlandse tak Wereld Natuur Fonds heet en de Amerikaanse World Wildlife Fund – is een wereldwijd opererende organisatie voor bescherming van de natuur", "+3223400920", "supporters@wwf.be");
            Product product1 = new Product("61e6c2c183f852129f4ffff3", category1, organization1, "T-shirt", 13.99, "Plain T-shirt", true, "Google.com");
            Product product2 = new Product("61e6c2c183f852129f4ffff4", category2, organization1, "Jeans", 23.99, "Plain Jeans", true, "Google.com");
            stockRepository.save(new Stock(size1, color1, product1, 10));
            stockRepository.save(new Stock(size2, color2, product2, 15));
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
