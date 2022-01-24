package fact.it.backend.model;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Collection;

@Document(collection = "users")
public class Customer extends User{
    private String firstName;
    private String lastName;

    @DBRef
    private Collection<Order> orders;

    @DBRef
    private Collection<Interaction> interactions;

    @DBRef
    private Collection<Review> reviews;


    public Customer() {
    }

    public Customer(String email, String password, String phoneNr, String address, String postalCode, String country, Role role, String firstName, String lastName) {
        super(email, password, phoneNr, address, postalCode, country, role);
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
}
