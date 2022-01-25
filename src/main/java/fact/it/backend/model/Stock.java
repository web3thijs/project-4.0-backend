package fact.it.backend.model;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.Persistable;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "stocks")
public class Stock implements Persistable<String> {

    @Id
    private String id;
    private Size size;
    private Color color;
    private Product product;
    private int amountInStock;
    @CreatedDate
    private Date createdAt;

    @LastModifiedDate
    private Date updatedAt;

    public Stock() {
    }

    public Stock(Size size, Color color, Product product, int amountInStock, Date createdAt) {
        this.size = size;
        this.color = color;
        this.product = product;
        this.amountInStock = amountInStock;
        this.createdAt = createdAt;
    }

    public Date getCreatedDate() {
        return createdAt;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdAt = createdDate;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getId() {
        return id;
    }

    @Override
    public boolean isNew() {
        return false;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getAmountInStock() {
        return amountInStock;
    }

    public void setAmountInStock(int amountInStock) {
        this.amountInStock = amountInStock;
    }
}
