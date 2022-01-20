package fact.it.backend.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
public class Customer extends User{
    private String firstName;
    private String lastName;
    private boolean isAdmin;

    public Customer() {
    }

    public Customer(String email, String password, String phoneNr, String address, String postalCode, String country, Role role, String firstName, String lastName, boolean isAdmin) {
        super(email, password, phoneNr, address, postalCode, country, role);
        this.firstName = firstName;
        this.lastName = lastName;
        this.isAdmin = isAdmin;
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

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
}
