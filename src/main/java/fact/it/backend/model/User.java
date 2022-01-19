package fact.it.backend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
public class User {
    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phoneNr;
    private String address;
    private String postalCode;
    private String country;
    private boolean isAdmin;
    private boolean isOrganization;
    private String orgName;
    private String companyRegistrationNr;
    private String vatNr;
    private String about;
    private String supportPhoneNr;
    private String supportEmail;

    public User() {
    }

    public User(String firstName, String lastName, String email, String password, String phoneNr, String address, String postalCode, String country, boolean isAdmin, boolean isOrganization, String orgName, String companyRegistrationNr, String vatNr, String about, String supportPhoneNr, String supportEmail) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phoneNr = phoneNr;
        this.address = address;
        this.postalCode = postalCode;
        this.country = country;
        this.isAdmin = isAdmin;
        this.isOrganization = isOrganization;
        this.orgName = orgName;
        this.companyRegistrationNr = companyRegistrationNr;
        this.vatNr = vatNr;
        this.about = about;
        this.supportPhoneNr = supportPhoneNr;
        this.supportEmail = supportEmail;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public boolean isOrganization() {
        return isOrganization;
    }

    public void setOrganization(boolean organization) {
        isOrganization = organization;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
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
