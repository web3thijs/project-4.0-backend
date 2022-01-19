package fact.it.backend.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "stocks")
public class Stock {

    @Id
    private ObjectId id;
    private ObjectId sizeId;
    private ObjectId colorId;
    private ObjectId productId;
    private int amountInStock;

    public Stock() {
    }

    public Stock(ObjectId id, ObjectId sizeId, ObjectId colorId, ObjectId productId, int amountInStock) {
        this.id = id;
        this.sizeId = sizeId;
        this.colorId = colorId;
        this.productId = productId;
        this.amountInStock = amountInStock;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public ObjectId getSizeId() {
        return sizeId;
    }

    public void setSizeId(ObjectId sizeId) {
        this.sizeId = sizeId;
    }

    public ObjectId getColorId() {
        return colorId;
    }

    public void setColorId(ObjectId colorId) {
        this.colorId = colorId;
    }

    public ObjectId getProductId() {
        return productId;
    }

    public void setProductId(ObjectId productId) {
        this.productId = productId;
    }

    public int getAmountInStock() {
        return amountInStock;
    }

    public void setAmountInStock(int amountInStock) {
        this.amountInStock = amountInStock;
    }
}
