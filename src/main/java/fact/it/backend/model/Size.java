package fact.it.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Size {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique= true)
    @NotNull(message = "Name is required.")
    @javax.validation.constraints.Size(min = 1, max = 50, message = "Name should have at least 1 or a maximum of 50 characters.")
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "size")
    private List<Stock> stocks = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "size")
    private List<OrderDetail> orderDetails = new ArrayList<>();

    public Size() {
    }

    public Size(String name) {
        this.name = name;
    }
    public Size(long id,String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Stock> getStocks() {
        return stocks;
    }

    public List<OrderDetail> getOrderDetails() {
        return orderDetails;
    }
}
