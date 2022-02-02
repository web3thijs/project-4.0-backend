package fact.it.backend;

import com.fasterxml.jackson.databind.ObjectMapper;
import fact.it.backend.model.*;
import fact.it.backend.repository.DonationRepository;
import fact.it.backend.repository.DonationRepository;
import fact.it.backend.service.TokenGetService;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.Date;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@RequestMapping(path = "api/donations")
@WithMockUser(username="admin", roles="ADMIN")
public class DonationControllerUnitTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TokenGetService tokenGetService;

    @MockBean
    private DonationRepository donationRepository;

    private ObjectMapper mapper = new ObjectMapper();
    private Date date = new Date();
    @Value("giannideherdt@gmail.com")
    private String emailAdmin;
    @Value("supporters@wwf.be")
    private String emailOrganization;
    @Value("jolienfoets@gmail.com")
    private String emailCustomer;
    @Value("Password123")
    private String password;


    /*@Test
    public void whenGetDonationByCustomerId_thenReturnJsonDonations() throws Exception{
        String id = new ObjectId().toString();
        Order order1ThijsWouters = new Order(id, customerThijsWouters, new Date(), new Date());
        Pageable requestedPage = PageRequest.of(0, 8);

        Page<Donation> allDonations = donationRepository.findDonationsByOrderId(order1ThijsWouters.getId(), requestedPage);
        given(donationRepository.findDonationsByOrderId(order1ThijsWouters.getId(), requestedPage)).willReturn(allDonations);

        mockMvc.perform(get("/api/donations/order/{orderId}", order1ThijsWouters.getId()).header("Authorization", "Bearer " + tokenGetService.obtainAccessToken(emailAdmin, password)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.amount", is(2.5)));
    }*/

    @Test
    public void whenPostDonation_thenReturnJsonDonation() throws Exception{
        Customer customerThijsWouters = new Customer("thijswouters@gmail.com", password, "0479954719", "Hoekstraat 165", "1680", "Belgium", Role.ADMIN, new Date(), "Thijs" , "Wouters");
        Order order1ThijsWouters = new Order(customerThijsWouters, new Date(), new Date());
        Organization organizationWWF = new Organization("supporters@wwf.be", password, "+3223400920", "Emile Jacqmainlaan 90", "1000", "Belgium", Role.ORGANIZATION, new Date(), "WWF", "BE0408656248", "BE0408656248", "Sinds de oprichting in 1966 is WWF-België één van de belangrijkste natuurbeschermingsorganisaties in ons land. Als lid van het wereldwijde WWF-netwerk nemen we deel aan grote nationale en internationale projecten om de natuur te beschermen en te zorgen voor een duurzame toekomst voor de generaties na ons.", "Onze slogan ‘Together Possible!’ belichaamt onze werkstrategie en onze visie op een planeet waar mens en natuur in harmonie leven. WWF is afhankelijk van de steun van donateurs en donatrices, en van de samenwerking met lokale gemeenschappen, jonge generaties, private en publieke partners om duurzame natuurbeschermingsoplossingen te vinden. Alleen samen kunnen we beschermen wat ons in leven houdt: bossen, oceaan, zoet water, fauna en flora.", "WWF zet zich in om de achteruitgang van de natuur op onze planeet te stoppen en om te bouwen aan een toekomst waar de mens in harmonie leeft met de natuur.", "+3223400920", "supporters@wwf.be", Arrays.asList("https://adfinitas-statics-cdn.s3.eu-west-3.amazonaws.com/wwf/defisc-20/logo.jpg"));
        Donation donationPost = new Donation(order1ThijsWouters, organizationWWF, 2.5, new Date());

        mockMvc.perform(post("/api/donations").header("Authorization", "Bearer " + tokenGetService.obtainAccessToken(emailAdmin, password))
                        .content(mapper.writeValueAsString(donationPost))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.amount", is(2.5)))
                .andExpect(jsonPath("$.order.customer.email", is("thijswouters@gmail.com")))
                .andExpect(jsonPath("$.organization.organizationName", is("WWF")));
    }

    @Test
    public void whenPostDonationNotAuthorized_thenReturnForbidden() throws Exception{
        Customer customerThijsWouters = new Customer("thijswouters@gmail.com", password, "0479954719", "Hoekstraat 165", "1680", "Belgium", Role.ADMIN, new Date(), "Thijs" , "Wouters");
        Order order1ThijsWouters = new Order(customerThijsWouters, new Date(), new Date());
        Organization organizationWWF = new Organization("supporters@wwf.be", password, "+3223400920", "Emile Jacqmainlaan 90", "1000", "Belgium", Role.ORGANIZATION, new Date(), "WWF", "BE0408656248", "BE0408656248", "Sinds de oprichting in 1966 is WWF-België één van de belangrijkste natuurbeschermingsorganisaties in ons land. Als lid van het wereldwijde WWF-netwerk nemen we deel aan grote nationale en internationale projecten om de natuur te beschermen en te zorgen voor een duurzame toekomst voor de generaties na ons.", "Onze slogan ‘Together Possible!’ belichaamt onze werkstrategie en onze visie op een planeet waar mens en natuur in harmonie leven. WWF is afhankelijk van de steun van donateurs en donatrices, en van de samenwerking met lokale gemeenschappen, jonge generaties, private en publieke partners om duurzame natuurbeschermingsoplossingen te vinden. Alleen samen kunnen we beschermen wat ons in leven houdt: bossen, oceaan, zoet water, fauna en flora.", "WWF zet zich in om de achteruitgang van de natuur op onze planeet te stoppen en om te bouwen aan een toekomst waar de mens in harmonie leeft met de natuur.", "+3223400920", "supporters@wwf.be", Arrays.asList("https://adfinitas-statics-cdn.s3.eu-west-3.amazonaws.com/wwf/defisc-20/logo.jpg"));
        Donation donationPost = new Donation(order1ThijsWouters, organizationWWF, 2.5, new Date());
        mockMvc.perform(post("/api/donations").header("Authorization", "Bearer " + tokenGetService.obtainAccessToken(emailOrganization, password))
                        .content(mapper.writeValueAsString(donationPost))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    public void givenDonation_whenPutDonation_thenReturnJsonDonation() throws Exception{
        Customer customerThijsWouters = new Customer("thijswouters@gmail.com", password, "0479954719", "Hoekstraat 165", "1680", "Belgium", Role.ADMIN, new Date(), "Thijs" , "Wouters");
        Order order1ThijsWouters = new Order(customerThijsWouters, new Date(), new Date());
        Organization organizationWWF = new Organization("supporters@wwf.be", password, "+3223400920", "Emile Jacqmainlaan 90", "1000", "Belgium", Role.ORGANIZATION, new Date(), "WWF", "BE0408656248", "BE0408656248", "Sinds de oprichting in 1966 is WWF-België één van de belangrijkste natuurbeschermingsorganisaties in ons land. Als lid van het wereldwijde WWF-netwerk nemen we deel aan grote nationale en internationale projecten om de natuur te beschermen en te zorgen voor een duurzame toekomst voor de generaties na ons.", "Onze slogan ‘Together Possible!’ belichaamt onze werkstrategie en onze visie op een planeet waar mens en natuur in harmonie leven. WWF is afhankelijk van de steun van donateurs en donatrices, en van de samenwerking met lokale gemeenschappen, jonge generaties, private en publieke partners om duurzame natuurbeschermingsoplossingen te vinden. Alleen samen kunnen we beschermen wat ons in leven houdt: bossen, oceaan, zoet water, fauna en flora.", "WWF zet zich in om de achteruitgang van de natuur op onze planeet te stoppen en om te bouwen aan een toekomst waar de mens in harmonie leeft met de natuur.", "+3223400920", "supporters@wwf.be", Arrays.asList("https://adfinitas-statics-cdn.s3.eu-west-3.amazonaws.com/wwf/defisc-20/logo.jpg"));
        Donation donationPut = new Donation(order1ThijsWouters, organizationWWF, 2.5, new Date());

        given(donationRepository.findDonationById(donationPut.getId())).willReturn(donationPut);

        Donation updatedDonation = new Donation(order1ThijsWouters, organizationWWF, 4, new Date());

        mockMvc.perform(put("/api/donations").header("Authorization", "Bearer " + tokenGetService.obtainAccessToken(emailAdmin, password))
                        .content(mapper.writeValueAsString(updatedDonation))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.amount", is(4)))
                .andExpect(jsonPath("$.order.customer.email", is("thijswouters@gmail.com")))
                .andExpect(jsonPath("$.organization.organizationName", is("WWF")));
    }

    @Test
    public void whenPutDonationNotAuthorized_thenReturnForbidden() throws Exception{
        String id = new ObjectId().toString();
        Customer customerThijsWouters = new Customer("thijswouters@gmail.com", password, "0479954719", "Hoekstraat 165", "1680", "Belgium", Role.ADMIN, new Date(), "Thijs" , "Wouters");
        Order order1ThijsWouters = new Order(customerThijsWouters, new Date(), new Date());
        Organization organizationWWF = new Organization("supporters@wwf.be", password, "+3223400920", "Emile Jacqmainlaan 90", "1000", "Belgium", Role.ORGANIZATION, new Date(), "WWF", "BE0408656248", "BE0408656248", "Sinds de oprichting in 1966 is WWF-België één van de belangrijkste natuurbeschermingsorganisaties in ons land. Als lid van het wereldwijde WWF-netwerk nemen we deel aan grote nationale en internationale projecten om de natuur te beschermen en te zorgen voor een duurzame toekomst voor de generaties na ons.", "Onze slogan ‘Together Possible!’ belichaamt onze werkstrategie en onze visie op een planeet waar mens en natuur in harmonie leven. WWF is afhankelijk van de steun van donateurs en donatrices, en van de samenwerking met lokale gemeenschappen, jonge generaties, private en publieke partners om duurzame natuurbeschermingsoplossingen te vinden. Alleen samen kunnen we beschermen wat ons in leven houdt: bossen, oceaan, zoet water, fauna en flora.", "WWF zet zich in om de achteruitgang van de natuur op onze planeet te stoppen en om te bouwen aan een toekomst waar de mens in harmonie leeft met de natuur.", "+3223400920", "supporters@wwf.be", Arrays.asList("https://adfinitas-statics-cdn.s3.eu-west-3.amazonaws.com/wwf/defisc-20/logo.jpg"));
        Donation donationPut = new Donation(id, order1ThijsWouters, organizationWWF, 2.5, new Date());

        given(donationRepository.findDonationById(donationPut.getId())).willReturn(donationPut);

        Donation updatedDonation = new Donation(id, order1ThijsWouters, organizationWWF, 4, new Date());

        mockMvc.perform(put("/api/donations").header("Authorization", "Bearer " + tokenGetService.obtainAccessToken(emailOrganization, password))
                        .content(mapper.writeValueAsString(updatedDonation))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    public void givenDonation_whenDeleteDonation_thenStatusOk() throws Exception {
        String id = new ObjectId().toString();
        Customer customerThijsWouters = new Customer("thijswouters@gmail.com", password, "0479954719", "Hoekstraat 165", "1680", "Belgium", Role.ADMIN, new Date(), "Thijs" , "Wouters");
        Order order1ThijsWouters = new Order(customerThijsWouters, new Date(), new Date());
        Organization organizationWWF = new Organization("supporters@wwf.be", password, "+3223400920", "Emile Jacqmainlaan 90", "1000", "Belgium", Role.ORGANIZATION, new Date(), "WWF", "BE0408656248", "BE0408656248", "Sinds de oprichting in 1966 is WWF-België één van de belangrijkste natuurbeschermingsorganisaties in ons land. Als lid van het wereldwijde WWF-netwerk nemen we deel aan grote nationale en internationale projecten om de natuur te beschermen en te zorgen voor een duurzame toekomst voor de generaties na ons.", "Onze slogan ‘Together Possible!’ belichaamt onze werkstrategie en onze visie op een planeet waar mens en natuur in harmonie leven. WWF is afhankelijk van de steun van donateurs en donatrices, en van de samenwerking met lokale gemeenschappen, jonge generaties, private en publieke partners om duurzame natuurbeschermingsoplossingen te vinden. Alleen samen kunnen we beschermen wat ons in leven houdt: bossen, oceaan, zoet water, fauna en flora.", "WWF zet zich in om de achteruitgang van de natuur op onze planeet te stoppen en om te bouwen aan een toekomst waar de mens in harmonie leeft met de natuur.", "+3223400920", "supporters@wwf.be", Arrays.asList("https://adfinitas-statics-cdn.s3.eu-west-3.amazonaws.com/wwf/defisc-20/logo.jpg"));
        Donation donationToBeDeleted = new Donation(id, order1ThijsWouters, organizationWWF, 2.5, new Date());

        given(donationRepository.findDonationById(id)).willReturn(donationToBeDeleted);

        mockMvc.perform(delete("/api/donations/{id}", id).header("Authorization", "Bearer " + tokenGetService.obtainAccessToken(emailAdmin, password))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void givenDonation_whenDeleteDonation_thenStatusNotFound() throws Exception {
        given(donationRepository.findDonationById("XXX")).willReturn(null);

        mockMvc.perform(delete("/api/donations/{id}", "XXX").header("Authorization", "Bearer " + tokenGetService.obtainAccessToken(emailAdmin, password))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

    }

    @Test
    public void whenDeleteDonationNotAuthorized_thenReturnForbidden() throws Exception{
        given(donationRepository.findDonationById("XXX")).willReturn(null);

        mockMvc.perform(delete("/api/donations/{id}", "XXX").header("Authorization", "Bearer " + tokenGetService.obtainAccessToken(emailOrganization, password))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

}
