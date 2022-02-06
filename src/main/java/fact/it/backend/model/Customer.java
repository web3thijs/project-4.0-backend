package fact.it.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Customer extends User{
    private String firstName;
    private String lastName;

    @JsonIgnore
    @OneToMany(mappedBy = "customer")
    private List<Review> reviews = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "customer")
    private List<Order> orders = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "customer")
    private List<Interaction> interactions = new ArrayList<>();

    public Customer() {
    }

    public Customer(String email, String password, String phoneNr, String country, String postalCode, String address, Role role, String firstName, String lastName) {
        super(email, password, phoneNr, country, postalCode, address, role);
        this.firstName = firstName;
        this.lastName = lastName;
    }
    public Customer(long id,String email, String password, String phoneNr, String country, String postalCode, String address, Role role, String firstName, String lastName) {
        super(id, email, password, phoneNr, country, postalCode, address, role);
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public List<Interaction> getInteractions() {
        return interactions;
    }

    public void setInteractions(List<Interaction> interactions) {
        this.interactions = interactions;
    }
}
