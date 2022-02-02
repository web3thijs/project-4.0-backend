package fact.it.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Color {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "color")
    private List<Stock> stocks = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "color")
    private List<OrderDetail> orderDetails = new ArrayList<>();

    public Color() {
    }

    public Color(String name) {
        this.name = name;
    }
    public Color(long id,String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
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
