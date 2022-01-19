package fact.it.backend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "orderdetails")
public class OrderDetail {

    @Id
    private String id;
    private Product product;
    private Order order;
    private Size size;
    private String colorId;
    private Number amount;

    public OrderDetail(){

    }

    public OrderDetail(Product product, Order order, Size size, String colorId, Number amount) {
        this.product = product;
        this.order = order;
        this.size = size;
        this.colorId = colorId;
        this.amount = amount;
    }

    public String getId() {
        return id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
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

    public Number getAmount() {
        return amount;
    }

    public void setAmount(Number amount) {
        this.amount = amount;
    }
}
