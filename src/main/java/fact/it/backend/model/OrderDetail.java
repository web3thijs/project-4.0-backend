package fact.it.backend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "orderdetails")
public class OrderDetail {

    @Id
    private String id;
    private String productId;
    private String orderId;
    private String sizeId;
    private String colorId;
    private Number amount;

    public OrderDetail(){

    }

    public OrderDetail(String productId, String orderId, String sizeId, String colorId, Number amount) {
        this.productId = productId;
        this.orderId = orderId;
        this.sizeId = sizeId;
        this.colorId = colorId;
        this.amount = amount;
    }

    public String getId() {
        return id;
    }
    
    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
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

    public Number getAmount() {
        return amount;
    }

    public void setAmount(Number amount) {
        this.amount = amount;
    }
}
