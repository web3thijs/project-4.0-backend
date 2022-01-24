package fact.it.backend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Collection;

@Document(collection = "reviews")
public class Review {

    @Id
    private String id;
    private Number score;
    private String title;
    private String text;
    private Customer customer;

    @DBRef
    private Interaction interaction;

    public Review(){

    }

    public Review( Number score, String title, String text, Customer customer) {
        this.score = score;
        this.title = title;
        this.text = text;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
