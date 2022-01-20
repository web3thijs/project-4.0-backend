package fact.it.backend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "stocks")
public class Stock {

    @Id
    private String id;
    private Size size;
    private String colorId;
    private String productId;
    private int amountInStock;

    public Stock() {
    }

    public Stock(Size size, String colorId, String productId, int amountInStock) {
        this.size = size;
        this.colorId = colorId;
        this.productId = productId;
        this.amountInStock = amountInStock;
    }

    public String getId() {
        return id;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public String getColorId() {
        return colorId;
    }

    public void setColorId(String colorId) {
        this.colorId = colorId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public int getAmountInStock() {
        return amountInStock;
    }

    public void setAmountInStock(int amountInStock) {
        this.amountInStock = amountInStock;
    }
}
