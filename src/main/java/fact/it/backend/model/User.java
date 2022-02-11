package fact.it.backend.model;


import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="appUsers")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @NotNull(message = "Email is required.")
    @Email(message = "Email is invalid.")
    private String email;

    @NotNull(message = "Password is required.")
    private String password;

    @NotNull(message = "Phone number is required")
    @Pattern(regexp = "(^[0-9+]{2}(\\+?\\-? *[0-9 \\/]{6,17})$)", message = "Phone number is invalid.")
    private String phoneNr;

    @NotNull(message = "Country is required")
    private String country;

    @NotNull(message = "Postal code is required.")
    @Pattern(regexp = "^[1-9]{1}[0-9]{3}$", message = "Postal code is invalid.")
    private String postalCode;

    @NotNull(message = "Address is required.")
    private String address;
    @NotNull(message = "Role is required.")
    private Role role;

    public User() {
    }

    public User(String email, String password, String phoneNr, String country, String postalCode, String address, Role role) {
        this.email = email;
        this.password = password;
        this.phoneNr = phoneNr;
        this.country = country;
        this.postalCode = postalCode;
        this.address = address;
        this.role = role;
    }
    public User(long id, String email, String password, String phoneNr, String country, String postalCode, String address, Role role) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.phoneNr = phoneNr;
        this.country = country;
        this.postalCode = postalCode;
        this.address = address;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNr() {
        return phoneNr;
    }

    public void setPhoneNr(String phoneNr) {
        this.phoneNr = phoneNr;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
