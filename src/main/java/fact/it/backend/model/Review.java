package fact.it.backend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;
import javax.validation.constraints.Size;

@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull(message = "Score cannot be null.")
    @Min(value = 0, message = "The minimum score is 0/5.")
    @Max(value = 5, message = "The maximum score is 5/5.")
    private double score;

    @NotEmpty
    @Size(max = 100, message = "Title can be a maximum of 100 characters.")
    private String title;

    @Size(max = 255, message = "Text can be a maximum of 255 characters.")
    private String text;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;

    @OneToOne
    private Interaction interaction;

    public Review() {
    }

    public Review(double score, String title, String text, Customer customer) {
        this.score = score;
        this.title = title;
        this.text = text;
        this.customer = customer;
    }

    public long getId() {
        return id;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
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

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
