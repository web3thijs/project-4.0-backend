package fact.it.backend.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
public class Organization extends User{
    private String organizationName;
    private String companyRegistrationNr;
    private String vatNr;
    private String about;
    private String supportPhoneNr;
    private String supportEmail;

    public Organization() {
    }

    public Organization(String email, String password, String phoneNr, String address, String postalCode, String country, Role role, String organizationName, String companyRegistrationNr, String vatNr, String about, String supportPhoneNr, String supportEmail) {
        super(email, password, phoneNr, address, postalCode, country, role);
        this.organizationName = organizationName;
        this.companyRegistrationNr = companyRegistrationNr;
        this.vatNr = vatNr;
        this.about = about;
        this.supportPhoneNr = supportPhoneNr;
        this.supportEmail = supportEmail;
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

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
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
}
