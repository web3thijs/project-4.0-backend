package fact.it.backend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "interactions")
public class Interaction {

    @Id
    private String id;
    private Product product;
    private String customerId;
    private String reviewId;
    private Number amountClicks;

    public Interaction(){

    }

    public Interaction(String id, Product product, String customerId, String reviewId, Number amountClicks) {
        this.id = id;
        this.product = product;
        this.customerId = customerId;
        this.reviewId = reviewId;
        this.amountClicks = amountClicks;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getReviewId() {
        return reviewId;
    }

    public void setReviewId(String reviewId) {
        this.reviewId = reviewId;
    }

    public Number getAmountClicks() {
        return amountClicks;
    }

    public void setAmountClicks(Number amountClicks) {
        this.amountClicks = amountClicks;
    }
}
