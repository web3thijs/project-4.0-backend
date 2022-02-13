package fact.it.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Organization extends User{
    @NotNull(message = "Organization name is required.")
    @Size(min = 1, max = 255, message = "First name should have at least 1 or a maximum of 255 characters.")
    private String organizationName;

    @NotNull(message = "Registration number is required.")
    @Pattern(regexp = "^[0-1]{1}[0-9]{9}$", message = "Company registration number starts with a 0 or 1 followed by 9 [0-9] numbers.")
    private String companyRegistrationNr;

    @NotNull(message = "Vat number is required.")
    @Pattern(regexp = "^BE+[0-9]{10}$", message = "Company VAT number starts with BE followed by 10 [0-9] numbers.")
    private String vatNr;

    @Column(length = 750)
    @Size(max = 750, message = "Who cannot be longer than 600 characters.")
    private String who;

    @Column(length = 750)
    @Size(max = 750, message = "What cannot be longer than 600 characters.")
    private String what;

    @Column(length = 750)
    @Size(max = 750, message = "Help cannot be longer than 600 characters.")
    private String help;

    @Pattern(regexp = "(^[0-9+]{2}(\\+?\\-? *[0-9 \\/]{6,17})$)", message = "Phone number for support is not valid.")
    private String supportPhoneNr;

    @Email(message = "Support email is not valid.")
    private String supportEmail;

    private String imageUrl;

    @JsonIgnore
    @OneToMany(mappedBy = "organization")
    private List<Product> products = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "organization")
    private List<Donation> donations = new ArrayList<>();

    public Organization() {
    }

    public Organization(String email, String password, String phoneNr, String country, String postalCode, String address, Role role, String organizationName, String companyRegistrationNr, String vatNr, String who, String what, String help, String supportPhoneNr, String supportEmail, String imageUrl) {
        super(email, password, phoneNr, country, postalCode, address, role);
        this.organizationName = organizationName;
        this.companyRegistrationNr = companyRegistrationNr;
        this.vatNr = vatNr;
        this.who = who;
        this.what = what;
        this.help = help;
        this.supportPhoneNr = supportPhoneNr;
        this.supportEmail = supportEmail;
        this.imageUrl = imageUrl;
    }
    public Organization(long id, String email, String password, String phoneNr, String country, String postalCode, String address, Role role, String organizationName, String companyRegistrationNr, String vatNr, String who, String what, String help, String supportPhoneNr, String supportEmail, String imageUrl) {
        super(id, email, password, phoneNr, country, postalCode, address, role);
        this.organizationName = organizationName;
        this.companyRegistrationNr = companyRegistrationNr;
        this.vatNr = vatNr;
        this.who = who;
        this.what = what;
        this.help = help;
        this.supportPhoneNr = supportPhoneNr;
        this.supportEmail = supportEmail;
        this.imageUrl = imageUrl;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getCompanyRegistrationNr() {
        return companyRegistrationNr;
    }

    public void setCompanyRegistrationNr(String companyRegistrationNr) {
        this.companyRegistrationNr = companyRegistrationNr;
    }

    public String getVatNr() {
        return vatNr;
    }

    public void setVatNr(String vatNr) {
        this.vatNr = vatNr;
    }

    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
    }

    public String getWhat() {
        return what;
    }

    public void setWhat(String what) {
        this.what = what;
    }

    public String getHelp() {
        return help;
    }

    public void setHelp(String help) {
        this.help = help;
    }

    public String getSupportPhoneNr() {
        return supportPhoneNr;
    }

    public void setSupportPhoneNr(String supportPhoneNr) {
        this.supportPhoneNr = supportPhoneNr;
    }

    public String getSupportEmail() {
        return supportEmail;
    }

    public void setSupportEmail(String supportEmail) {
        this.supportEmail = supportEmail;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public List<Donation> getDonations() {
        return donations;
    }

    public void setDonations(List<Donation> donations) {
        this.donations = donations;
    }
}
