package fact.it.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="appOrder")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "A date is required.")
    private Date date;

    @NotNull(message = "Completed must be true or false.")
    private boolean completed = false;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;

    @JsonIgnore
    @OneToMany(mappedBy = "order")
    private List<OrderDetail> orderDetails = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "order")
    private List<Donation> donations = new ArrayList<>();

    public Order() {
    }

    public Order(Date date, boolean completed, Customer customer) {
        this.date = date;
        this.completed = completed;
        this.customer = customer;
    }
    public Order(long id, Date date, boolean completed, Customer customer) {
        this.id = id;
        this.date = date;
        this.completed = completed;
        this.customer = customer;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public List<Donation> getDonations() {
        return donations;
    }
}
