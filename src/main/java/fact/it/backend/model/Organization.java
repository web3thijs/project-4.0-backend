package fact.it.backend.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Organization extends User{
    private String organizationName;
    private String companyRegistrationNr;
    private String vatNr;
    @Column(length=5000)
    private String who;
    @Column(length=5000)
    private String what;
    @Column(length=5000)
    private String help;
    private String supportPhoneNr;
    private String supportEmail;
    private String imageUrl;

    @OneToMany(mappedBy = "organization")
    private List<Product> products = new ArrayList<>();

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
