package fact.it.backend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Collection;

@Document(collection = "reviews")
public class Review {

    @Id
    private String id;
    private String productId;
    private String customerId;
    private Number score;
    private String title;
    private String text;

    @DBRef
    private Interaction interaction;

    public Review(){

    }

    public Review(String productId, String customerId, Number score, String title, String text) {
        this.productId = productId;
        this.customerId = customerId;
        this.score = score;
        this.title = title;
        this.text = text;
    }

    public String getId() {
        return id;
    }

    public Number getScore() {
        return score;
    }

    public void setScore(Number score) {
        this.score = score;
    }

    public String getTitle() {
        return title;
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

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
