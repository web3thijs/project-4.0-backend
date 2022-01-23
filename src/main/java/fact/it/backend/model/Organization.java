package fact.it.backend.model;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Collection;
import java.util.List;

@Document(collection = "users")
public class Organization extends User{
    private String organizationName;
    private String companyRegistrationNr;
    private String vatNr;
    private String who;
    private String what;
    private String help;
    private String supportPhoneNr;
    private String supportEmail;
    private List<String> imageUrl;


    @DBRef
    private Collection<Product> products;


    public Organization() {
    }

    public Organization(String email, String password, String phoneNr, String address, String postalCode, String country, Role role, String organizationName, String companyRegistrationNr, String vatNr, String who, String what, String help, String supportPhoneNr, String supportEmail, List<String> imageUrl) {
        super(email, password, phoneNr, address, postalCode, country, role);
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

    public List<String> getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(List<String> imageUrl) {
        this.imageUrl = imageUrl;
    }
}
