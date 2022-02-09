package fact.it.backend.model;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Min(value = 0, message = "Stock cannot be less than 0.")
    @Max(value = 99999, message = "Stock cannot be larger than 99999.")
    private int amountInStock;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "size_id", referencedColumnName = "id")
    private Size size;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "color_id", referencedColumnName = "id")
    private Color color;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;

    public Stock() {
    }

    public Stock(int amountInStock, Size size, Color color, Product product) {
        this.amountInStock = amountInStock;
        this.size = size;
        this.color = color;
        this.product = product;
    }
    public Stock(long id,int amountInStock, Size size, Color color, Product product) {
        this.id = id;
        this.amountInStock = amountInStock;
        this.size = size;
        this.color = color;
        this.product = product;
    }

    public Long getId() {
        return id;
    }

    public int getAmountInStock() {
        return amountInStock;
    }

    public void setAmountInStock(int amountInStock) {
        this.amountInStock = amountInStock;
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
}
