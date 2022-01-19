package fact.it.backend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "interactions")
public class Interaction {

    @Id
    private String id;
    private String productId;
    private String customerId;
    private String reviewId;
    private Number amountClicks;

    public Interaction(){

    }

    public Interaction(String productId, String customerId, String reviewId, Number amountClicks) {
        this.productId = productId;
        this.customerId = customerId;
        this.reviewId = reviewId;
        this.amountClicks = amountClicks;
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
