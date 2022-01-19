package fact.it.backend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "stocks")
public class Stock {

    @Id
    private String id;
    private String sizeId;
    private String colorId;
    private String productId;
    private int amountInStock;

    public Stock() {
    }

    public Stock(String sizeId, String colorId, String productId, int amountInStock) {
        this.sizeId = sizeId;
        this.colorId = colorId;
        this.productId = productId;
        this.amountInStock = amountInStock;
    }

    public String getId() {
        return id;
    }

    public String getSizeId() {
        return sizeId;
    }

    public void setSizeId(String sizeId) {
        this.sizeId = sizeId;
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
