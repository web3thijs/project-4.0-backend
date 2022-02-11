package fact.it.backend;

import com.fasterxml.jackson.databind.ObjectMapper;
import fact.it.backend.model.*;
import fact.it.backend.repository.CustomerRepository;
import fact.it.backend.repository.InteractionRepository;
import fact.it.backend.repository.ProductRepository;
import fact.it.backend.service.TokenGetService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isA;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@RequestMapping(path = "api/interactions")
@WithMockUser(username="admin", roles="ADMIN")
public class InteractionControllerUnitTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TokenGetService tokenGetService;

    @MockBean
    private InteractionRepository interactionRepository;

    @MockBean
    private ProductRepository productRepository;

    @MockBean
    private CustomerRepository customerRepository;

    private ObjectMapper mapper = new ObjectMapper();

    @Value("elision@gmail.com")
    private String emailAdmin;
    @Value("Password123")
    private String password;
    @Value("supporters@wwf.be")
    private String emailOrganization;

    @Test
    public void whenGetAllInteractions_thenReturnJsonInteraction() throws Exception{
        List<Interaction> allInteractions = interactionRepository.findAll();

        given(interactionRepository.findAll()).willReturn(allInteractions);

        mockMvc.perform(get("/api/interactions").header("Authorization", "Bearer " + tokenGetService.obtainAccessToken(emailAdmin, password)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", isA(ArrayList.class)));
    }

    @Test
    public void whenGetAllInteractionsUnauthorized_thenReturnJsonInteraction() throws Exception{
        List<Interaction> allInteractions = interactionRepository.findAll();

        given(interactionRepository.findAll()).willReturn(allInteractions);

        mockMvc.perform(get("/api/interactions").header("Authorization", "Bearer " + tokenGetService.obtainAccessToken(emailOrganization, password))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    public void whenGetInteractionByProductId_thenReturnJsonInteraction() throws Exception{
        Category categorySleutelhangers = new Category("sleutelhangers");
        Organization organizationWWF = new Organization("supporters@wwf.be", password, "+3223400920", "Belgium", "1000", "Emile Jacqmainlaan 90", Role.ORGANIZATION, "WWF", "BE0408656248", "BE0408656248", "Sinds de oprichting in 1966 is WWF-België één van de belangrijkste natuurbeschermingsorganisaties in ons land. Als lid van het wereldwijde WWF-netwerk nemen we deel aan grote nationale en internationale projecten om de natuur te beschermen en te zorgen voor een duurzame toekomst voor de generaties na ons.", "Onze slogan ‘Together Possible!’ belichaamt onze werkstrategie en onze visie op een planeet waar mens en natuur in harmonie leven. WWF is afhankelijk van de steun van donateurs en donatrices, en van de samenwerking met lokale gemeenschappen, jonge generaties, private en publieke partners om duurzame natuurbeschermingsoplossingen te vinden. Alleen samen kunnen we beschermen wat ons in leven houdt: bossen, oceaan, zoet water, fauna en flora.", "WWF zet zich in om de achteruitgang van de natuur op onze planeet te stoppen en om te bouwen aan een toekomst waar de mens in harmonie leeft met de natuur.", "+3223400920", "supporters@wwf.be", "https://adfinitas-statics-cdn.s3.eu-west-3.amazonaws.com/wwf/defisc-20/logo.jpg");
        Product productOrangoetanSleutelhanger = new Product(0,"WWF sleutelhanger orang-oetan", "Een schattige orang-oetan als sleutelhanger van 10cm.", 10.95, true, Arrays.asList("https://www.wwf.nl/globalassets/commerce/productafbeeldingen/wwf-sleutelhanger-orang-oetan-01.jpg", "https://www.wwf.nl/globalassets/commerce/productafbeeldingen/wwf-sleutelhanger-orang-oetan-02.jpg"), categorySleutelhangers, organizationWWF);

        given(productRepository.findProductById(0)).willReturn(productOrangoetanSleutelhanger);

        mockMvc.perform(get("/api/interactions/product/{productId}", 0).header("Authorization", "Bearer " + tokenGetService.obtainAccessToken(emailAdmin, password)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.*", isA(ArrayList.class)));
    }

    @Test
    public void whenGetInteractionByProductIdUnauthorized_thenReturnJsonInteraction() throws Exception{
        Customer customerGianniDeHerdt = new Customer("giannideherdt@gmail.com", password, "0479994529", "Belgium", "2200", "Kersstraat 17", Role.ADMIN, "Gianni" , "De Herdt");
        Review reviewGianniDeHerdtSleutelhanger = new Review( 5, "Zeer leuke sleutelhanger", "Hangt heel mooi aan mijn sleutelbundel. Lief en zacht!", customerGianniDeHerdt);
        Category categorySleutelhangers = new Category("sleutelhangers");
        Organization organizationWWF = new Organization("supporters@wwf.be", password, "+3223400920", "Belgium", "1000", "Emile Jacqmainlaan 90", Role.ORGANIZATION, "WWF", "BE0408656248", "BE0408656248", "Sinds de oprichting in 1966 is WWF-België één van de belangrijkste natuurbeschermingsorganisaties in ons land. Als lid van het wereldwijde WWF-netwerk nemen we deel aan grote nationale en internationale projecten om de natuur te beschermen en te zorgen voor een duurzame toekomst voor de generaties na ons.", "Onze slogan ‘Together Possible!’ belichaamt onze werkstrategie en onze visie op een planeet waar mens en natuur in harmonie leven. WWF is afhankelijk van de steun van donateurs en donatrices, en van de samenwerking met lokale gemeenschappen, jonge generaties, private en publieke partners om duurzame natuurbeschermingsoplossingen te vinden. Alleen samen kunnen we beschermen wat ons in leven houdt: bossen, oceaan, zoet water, fauna en flora.", "WWF zet zich in om de achteruitgang van de natuur op onze planeet te stoppen en om te bouwen aan een toekomst waar de mens in harmonie leeft met de natuur.", "+3223400920", "supporters@wwf.be", "https://adfinitas-statics-cdn.s3.eu-west-3.amazonaws.com/wwf/defisc-20/logo.jpg");
        Product productOrangoetanSleutelhanger = new Product("WWF sleutelhanger orang-oetan", "Een schattige orang-oetan als sleutelhanger van 10cm.", 10.95, true, Arrays.asList("https://www.wwf.nl/globalassets/commerce/productafbeeldingen/wwf-sleutelhanger-orang-oetan-01.jpg", "https://www.wwf.nl/globalassets/commerce/productafbeeldingen/wwf-sleutelhanger-orang-oetan-02.jpg"), categorySleutelhangers, organizationWWF);

//        Interaction interactionTest = new Interaction(4, 3, 1, reviewGianniDeHerdtSleutelhanger, productOrangoetanSleutelhanger, customerGianniDeHerdt);

        List<Interaction> interactions = interactionRepository.findInteractionsByProductId(productOrangoetanSleutelhanger.getId());
        given(interactionRepository.findInteractionsByProductId(productOrangoetanSleutelhanger.getId())).willReturn(interactions);

        mockMvc.perform(get("/api/interactions/product/{productId}", productOrangoetanSleutelhanger.getId()).header("Authorization", "Bearer " + tokenGetService.obtainAccessToken(emailOrganization, password))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    public void whenGetInteractionByCustomerId_thenReturnJsonInteraction() throws Exception{
        Customer customerGianniDeHerdt = new Customer("giannideherdt@gmail.com", password, "0479994529", "Belgium", "2200", "Kersstraat 17", Role.ADMIN, "Gianni" , "De Herdt");

        given(customerRepository.findCustomerById(0)).willReturn(customerGianniDeHerdt);

        mockMvc.perform(get("/api/interactions/customer/{customerId}", 0).header("Authorization", "Bearer " + tokenGetService.obtainAccessToken(emailAdmin, password)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.*", isA(ArrayList.class)));
    }

    @Test
    public void whenGetInteractionByCustomerIdUnauthorized_thenReturnJsonInteraction() throws Exception{
        Customer customerGianniDeHerdt = new Customer("giannideherdt@gmail.com", password, "0479994529", "Belgium", "2200", "Kersstraat 17", Role.ADMIN, "Gianni" , "De Herdt");
        Review reviewGianniDeHerdtSleutelhanger = new Review( 5, "Zeer leuke sleutelhanger", "Hangt heel mooi aan mijn sleutelbundel. Lief en zacht!", customerGianniDeHerdt);
        Category categorySleutelhangers = new Category("sleutelhangers");
        Organization organizationWWF = new Organization("supporters@wwf.be", password, "+3223400920", "Belgium", "1000", "Emile Jacqmainlaan 90", Role.ORGANIZATION, "WWF", "BE0408656248", "BE0408656248", "Sinds de oprichting in 1966 is WWF-België één van de belangrijkste natuurbeschermingsorganisaties in ons land. Als lid van het wereldwijde WWF-netwerk nemen we deel aan grote nationale en internationale projecten om de natuur te beschermen en te zorgen voor een duurzame toekomst voor de generaties na ons.", "Onze slogan ‘Together Possible!’ belichaamt onze werkstrategie en onze visie op een planeet waar mens en natuur in harmonie leven. WWF is afhankelijk van de steun van donateurs en donatrices, en van de samenwerking met lokale gemeenschappen, jonge generaties, private en publieke partners om duurzame natuurbeschermingsoplossingen te vinden. Alleen samen kunnen we beschermen wat ons in leven houdt: bossen, oceaan, zoet water, fauna en flora.", "WWF zet zich in om de achteruitgang van de natuur op onze planeet te stoppen en om te bouwen aan een toekomst waar de mens in harmonie leeft met de natuur.", "+3223400920", "supporters@wwf.be", "https://adfinitas-statics-cdn.s3.eu-west-3.amazonaws.com/wwf/defisc-20/logo.jpg");
        Product productOrangoetanSleutelhanger = new Product("WWF sleutelhanger orang-oetan", "Een schattige orang-oetan als sleutelhanger van 10cm.", 10.95, true, Arrays.asList("https://www.wwf.nl/globalassets/commerce/productafbeeldingen/wwf-sleutelhanger-orang-oetan-01.jpg", "https://www.wwf.nl/globalassets/commerce/productafbeeldingen/wwf-sleutelhanger-orang-oetan-02.jpg"), categorySleutelhangers, organizationWWF);


//        Interaction interactionTest = new Interaction(4, 3, 1, reviewGianniDeHerdtSleutelhanger, productOrangoetanSleutelhanger, customerGianniDeHerdt);

        List<Interaction> interactions = interactionRepository.findInteractionsByCustomerId(0);
        given(interactionRepository.findInteractionsByCustomerId(0)).willReturn(interactions);

        mockMvc.perform(get("/api/interactions/customer/{customerId}", 0).header("Authorization", "Bearer " + tokenGetService.obtainAccessToken(emailOrganization, password))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    public void whenPostInteraction_thenReturnJsonInteraction() throws Exception{
        Customer customerGianniDeHerdt = new Customer("giannideherdt@gmail.com", password, "0479994529", "Belgium", "2200", "Kersstraat 17", Role.ADMIN, "Gianni" , "De Herdt");
        Review reviewGianniDeHerdtSleutelhanger = new Review( 5, "Zeer leuke sleutelhanger", "Hangt heel mooi aan mijn sleutelbundel. Lief en zacht!", customerGianniDeHerdt);
        Category categorySleutelhangers = new Category("sleutelhangers");
        Organization organizationWWF = new Organization("supporters@wwf.be", password, "+3223400920", "Belgium", "1000", "Emile Jacqmainlaan 90", Role.ORGANIZATION, "WWF", "BE0408656248", "BE0408656248", "Sinds de oprichting in 1966 is WWF-België één van de belangrijkste natuurbeschermingsorganisaties in ons land. Als lid van het wereldwijde WWF-netwerk nemen we deel aan grote nationale en internationale projecten om de natuur te beschermen en te zorgen voor een duurzame toekomst voor de generaties na ons.", "Onze slogan ‘Together Possible!’ belichaamt onze werkstrategie en onze visie op een planeet waar mens en natuur in harmonie leven. WWF is afhankelijk van de steun van donateurs en donatrices, en van de samenwerking met lokale gemeenschappen, jonge generaties, private en publieke partners om duurzame natuurbeschermingsoplossingen te vinden. Alleen samen kunnen we beschermen wat ons in leven houdt: bossen, oceaan, zoet water, fauna en flora.", "WWF zet zich in om de achteruitgang van de natuur op onze planeet te stoppen en om te bouwen aan een toekomst waar de mens in harmonie leeft met de natuur.", "+3223400920", "supporters@wwf.be", "https://adfinitas-statics-cdn.s3.eu-west-3.amazonaws.com/wwf/defisc-20/logo.jpg");
        Product productOrangoetanSleutelhanger = new Product("WWF sleutelhanger orang-oetan", "Een schattige orang-oetan als sleutelhanger van 10cm.", 10.95, true, Arrays.asList("https://www.wwf.nl/globalassets/commerce/productafbeeldingen/wwf-sleutelhanger-orang-oetan-01.jpg", "https://www.wwf.nl/globalassets/commerce/productafbeeldingen/wwf-sleutelhanger-orang-oetan-02.jpg"), categorySleutelhangers, organizationWWF);

        Interaction interactionPost = new Interaction(4, 3, 1, reviewGianniDeHerdtSleutelhanger, productOrangoetanSleutelhanger, customerGianniDeHerdt);


        mockMvc.perform(post("/api/interactions").header("Authorization", "Bearer " + tokenGetService.obtainAccessToken(emailAdmin, password))
                        .content(mapper.writeValueAsString(interactionPost))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.amountClicks", is(4)))
                .andExpect(jsonPath("$.amountCart", is(3)))
                .andExpect(jsonPath("$.amountBought", is(1)))
                .andExpect(jsonPath("$.product.name", is("WWF sleutelhanger orang-oetan")))
                .andExpect(jsonPath("$.customer.email", is("giannideherdt@gmail.com")))
                .andExpect(jsonPath("$.review.score", is(5.0)));
    }

    @Test
    public void whenPostInteractionNotAuthorized_thenReturnForbidden() throws Exception{
        Customer customerGianniDeHerdt = new Customer("giannideherdt@gmail.com", password, "0479994529", "Belgium", "2200", "Kersstraat 17", Role.ADMIN, "Gianni" , "De Herdt");
        Review reviewGianniDeHerdtSleutelhanger = new Review( 5, "Zeer leuke sleutelhanger", "Hangt heel mooi aan mijn sleutelbundel. Lief en zacht!", customerGianniDeHerdt);
        Category categorySleutelhangers = new Category("sleutelhangers");
        Organization organizationWWF = new Organization("supporters@wwf.be", password, "+3223400920", "Belgium", "1000", "Emile Jacqmainlaan 90", Role.ORGANIZATION, "WWF", "BE0408656248", "BE0408656248", "Sinds de oprichting in 1966 is WWF-België één van de belangrijkste natuurbeschermingsorganisaties in ons land. Als lid van het wereldwijde WWF-netwerk nemen we deel aan grote nationale en internationale projecten om de natuur te beschermen en te zorgen voor een duurzame toekomst voor de generaties na ons.", "Onze slogan ‘Together Possible!’ belichaamt onze werkstrategie en onze visie op een planeet waar mens en natuur in harmonie leven. WWF is afhankelijk van de steun van donateurs en donatrices, en van de samenwerking met lokale gemeenschappen, jonge generaties, private en publieke partners om duurzame natuurbeschermingsoplossingen te vinden. Alleen samen kunnen we beschermen wat ons in leven houdt: bossen, oceaan, zoet water, fauna en flora.", "WWF zet zich in om de achteruitgang van de natuur op onze planeet te stoppen en om te bouwen aan een toekomst waar de mens in harmonie leeft met de natuur.", "+3223400920", "supporters@wwf.be", "https://adfinitas-statics-cdn.s3.eu-west-3.amazonaws.com/wwf/defisc-20/logo.jpg");
        Product productOrangoetanSleutelhanger = new Product("WWF sleutelhanger orang-oetan", "Een schattige orang-oetan als sleutelhanger van 10cm.", 10.95, true, Arrays.asList("https://www.wwf.nl/globalassets/commerce/productafbeeldingen/wwf-sleutelhanger-orang-oetan-01.jpg", "https://www.wwf.nl/globalassets/commerce/productafbeeldingen/wwf-sleutelhanger-orang-oetan-02.jpg"), categorySleutelhangers, organizationWWF);

        Interaction interactionPost = new Interaction(4, 3, 1, reviewGianniDeHerdtSleutelhanger, productOrangoetanSleutelhanger, customerGianniDeHerdt);

        mockMvc.perform(post("/api/interactions").header("Authorization", "Bearer " + tokenGetService.obtainAccessToken(emailOrganization, password))
                        .content(mapper.writeValueAsString(interactionPost))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    public void givenInteraction_whenPutInteraction_thenReturnJsonInteraction() throws Exception{
        Customer customerGianniDeHerdt = new Customer(0,"giannideherdt@gmail.com", password, "0479994529", "Belgium", "2200", "Kersstraat 17", Role.ADMIN, "Gianni" , "De Herdt");
        Review reviewGianniDeHerdtSleutelhanger = new Review( 5, "Zeer leuke sleutelhanger", "Hangt heel mooi aan mijn sleutelbundel. Lief en zacht!", customerGianniDeHerdt);
        Category categorySleutelhangers = new Category("sleutelhangers");
        Organization organizationWWF = new Organization("supporters@wwf.be", password, "+3223400920", "Belgium", "1000", "Emile Jacqmainlaan 90", Role.ORGANIZATION, "WWF", "BE0408656248", "BE0408656248", "Sinds de oprichting in 1966 is WWF-België één van de belangrijkste natuurbeschermingsorganisaties in ons land. Als lid van het wereldwijde WWF-netwerk nemen we deel aan grote nationale en internationale projecten om de natuur te beschermen en te zorgen voor een duurzame toekomst voor de generaties na ons.", "Onze slogan ‘Together Possible!’ belichaamt onze werkstrategie en onze visie op een planeet waar mens en natuur in harmonie leven. WWF is afhankelijk van de steun van donateurs en donatrices, en van de samenwerking met lokale gemeenschappen, jonge generaties, private en publieke partners om duurzame natuurbeschermingsoplossingen te vinden. Alleen samen kunnen we beschermen wat ons in leven houdt: bossen, oceaan, zoet water, fauna en flora.", "WWF zet zich in om de achteruitgang van de natuur op onze planeet te stoppen en om te bouwen aan een toekomst waar de mens in harmonie leeft met de natuur.", "+3223400920", "supporters@wwf.be", "https://adfinitas-statics-cdn.s3.eu-west-3.amazonaws.com/wwf/defisc-20/logo.jpg");
        Product productOrangoetanSleutelhanger = new Product(0,"WWF sleutelhanger orang-oetan", "Een schattige orang-oetan als sleutelhanger van 10cm.", 10.95, true, Arrays.asList("https://www.wwf.nl/globalassets/commerce/productafbeeldingen/wwf-sleutelhanger-orang-oetan-01.jpg", "https://www.wwf.nl/globalassets/commerce/productafbeeldingen/wwf-sleutelhanger-orang-oetan-02.jpg"), categorySleutelhangers, organizationWWF);

        Interaction interactionPut = new Interaction(4, 3, 1, reviewGianniDeHerdtSleutelhanger, productOrangoetanSleutelhanger, customerGianniDeHerdt);

        given(interactionRepository.findInteractionById(interactionPut.getId())).willReturn(interactionPut);

        Interaction updatedInteraction = new Interaction(5, 3, 1, reviewGianniDeHerdtSleutelhanger, productOrangoetanSleutelhanger, customerGianniDeHerdt);

        mockMvc.perform(put("/api/interactions").header("Authorization", "Bearer " + tokenGetService.obtainAccessToken(emailAdmin, password))
                        .content(mapper.writeValueAsString(updatedInteraction))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void whenPutInteractionNotAuthorized_thenReturnForbidden() throws Exception{
        Customer customerGianniDeHerdt = new Customer("giannideherdt@gmail.com", password, "0479994529", "Belgium", "2200", "Kersstraat 17", Role.ADMIN, "Gianni" , "De Herdt");
        Review reviewGianniDeHerdtSleutelhanger = new Review( 5, "Zeer leuke sleutelhanger", "Hangt heel mooi aan mijn sleutelbundel. Lief en zacht!", customerGianniDeHerdt);
        Category categorySleutelhangers = new Category("sleutelhangers");
        Organization organizationWWF = new Organization("supporters@wwf.be", password, "+3223400920", "Belgium", "1000", "Emile Jacqmainlaan 90", Role.ORGANIZATION, "WWF", "BE0408656248", "BE0408656248", "Sinds de oprichting in 1966 is WWF-België één van de belangrijkste natuurbeschermingsorganisaties in ons land. Als lid van het wereldwijde WWF-netwerk nemen we deel aan grote nationale en internationale projecten om de natuur te beschermen en te zorgen voor een duurzame toekomst voor de generaties na ons.", "Onze slogan ‘Together Possible!’ belichaamt onze werkstrategie en onze visie op een planeet waar mens en natuur in harmonie leven. WWF is afhankelijk van de steun van donateurs en donatrices, en van de samenwerking met lokale gemeenschappen, jonge generaties, private en publieke partners om duurzame natuurbeschermingsoplossingen te vinden. Alleen samen kunnen we beschermen wat ons in leven houdt: bossen, oceaan, zoet water, fauna en flora.", "WWF zet zich in om de achteruitgang van de natuur op onze planeet te stoppen en om te bouwen aan een toekomst waar de mens in harmonie leeft met de natuur.", "+3223400920", "supporters@wwf.be", "https://adfinitas-statics-cdn.s3.eu-west-3.amazonaws.com/wwf/defisc-20/logo.jpg");
        Product productOrangoetanSleutelhanger = new Product("WWF sleutelhanger orang-oetan", "Een schattige orang-oetan als sleutelhanger van 10cm.", 10.95, true, Arrays.asList("https://www.wwf.nl/globalassets/commerce/productafbeeldingen/wwf-sleutelhanger-orang-oetan-01.jpg", "https://www.wwf.nl/globalassets/commerce/productafbeeldingen/wwf-sleutelhanger-orang-oetan-02.jpg"), categorySleutelhangers, organizationWWF);

        Interaction interactionPut = new Interaction(4, 3, 1, reviewGianniDeHerdtSleutelhanger, productOrangoetanSleutelhanger, customerGianniDeHerdt);

        given(interactionRepository.findInteractionById(interactionPut.getId())).willReturn(interactionPut);

        Interaction updatedInteraction = new Interaction(5, 3, 1, reviewGianniDeHerdtSleutelhanger, productOrangoetanSleutelhanger, customerGianniDeHerdt);

        mockMvc.perform(put("/api/interactions").header("Authorization", "Bearer " + tokenGetService.obtainAccessToken(emailOrganization, password))
                        .content(mapper.writeValueAsString(updatedInteraction))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    public void givenInteraction_whenDeleteInteraction_thenStatusOk() throws Exception {
        Customer customerGianniDeHerdt = new Customer("giannideherdt@gmail.com", password, "0479994529", "Belgium", "2200", "Kersstraat 17", Role.ADMIN, "Gianni" , "De Herdt");
        Review reviewGianniDeHerdtSleutelhanger = new Review( 5, "Zeer leuke sleutelhanger", "Hangt heel mooi aan mijn sleutelbundel. Lief en zacht!", customerGianniDeHerdt);
        Category categorySleutelhangers = new Category("sleutelhangers");
        Organization organizationWWF = new Organization("supporters@wwf.be", password, "+3223400920", "Belgium", "1000", "Emile Jacqmainlaan 90", Role.ORGANIZATION, "WWF", "BE0408656248", "BE0408656248", "Sinds de oprichting in 1966 is WWF-België één van de belangrijkste natuurbeschermingsorganisaties in ons land. Als lid van het wereldwijde WWF-netwerk nemen we deel aan grote nationale en internationale projecten om de natuur te beschermen en te zorgen voor een duurzame toekomst voor de generaties na ons.", "Onze slogan ‘Together Possible!’ belichaamt onze werkstrategie en onze visie op een planeet waar mens en natuur in harmonie leven. WWF is afhankelijk van de steun van donateurs en donatrices, en van de samenwerking met lokale gemeenschappen, jonge generaties, private en publieke partners om duurzame natuurbeschermingsoplossingen te vinden. Alleen samen kunnen we beschermen wat ons in leven houdt: bossen, oceaan, zoet water, fauna en flora.", "WWF zet zich in om de achteruitgang van de natuur op onze planeet te stoppen en om te bouwen aan een toekomst waar de mens in harmonie leeft met de natuur.", "+3223400920", "supporters@wwf.be", "https://adfinitas-statics-cdn.s3.eu-west-3.amazonaws.com/wwf/defisc-20/logo.jpg");
        Product productOrangoetanSleutelhanger = new Product("WWF sleutelhanger orang-oetan", "Een schattige orang-oetan als sleutelhanger van 10cm.", 10.95, true, Arrays.asList("https://www.wwf.nl/globalassets/commerce/productafbeeldingen/wwf-sleutelhanger-orang-oetan-01.jpg", "https://www.wwf.nl/globalassets/commerce/productafbeeldingen/wwf-sleutelhanger-orang-oetan-02.jpg"), categorySleutelhangers, organizationWWF);

        Interaction interactionToBeDeleted = new Interaction(4, 3, 1, reviewGianniDeHerdtSleutelhanger, productOrangoetanSleutelhanger, customerGianniDeHerdt);

        given(interactionRepository.findInteractionById(interactionToBeDeleted.getId())).willReturn(interactionToBeDeleted);

        mockMvc.perform(delete("/api/interactions/{id}", interactionToBeDeleted.getId()).header("Authorization", "Bearer " + tokenGetService.obtainAccessToken(emailAdmin, password))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void givenInteraction_whenDeleteInteraction_thenStatusNotFound() throws Exception {
        given(interactionRepository.findInteractionById(12345)).willReturn(null);

        mockMvc.perform(delete("/api/interactions/{id}", 12345).header("Authorization", "Bearer " + tokenGetService.obtainAccessToken(emailAdmin, password))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

    }

    @Test
    public void whenDeleteInteractionNotAuthorized_thenReturnForbidden() throws Exception{
        Customer customerGianniDeHerdt = new Customer("giannideherdt@gmail.com", password, "0479994529", "Belgium", "2200", "Kersstraat 17", Role.ADMIN, "Gianni" , "De Herdt");
        Review reviewGianniDeHerdtSleutelhanger = new Review( 5, "Zeer leuke sleutelhanger", "Hangt heel mooi aan mijn sleutelbundel. Lief en zacht!", customerGianniDeHerdt);
        Category categorySleutelhangers = new Category("sleutelhangers");
        Organization organizationWWF = new Organization("supporters@wwf.be", password, "+3223400920", "Belgium", "1000", "Emile Jacqmainlaan 90", Role.ORGANIZATION, "WWF", "BE0408656248", "BE0408656248", "Sinds de oprichting in 1966 is WWF-België één van de belangrijkste natuurbeschermingsorganisaties in ons land. Als lid van het wereldwijde WWF-netwerk nemen we deel aan grote nationale en internationale projecten om de natuur te beschermen en te zorgen voor een duurzame toekomst voor de generaties na ons.", "Onze slogan ‘Together Possible!’ belichaamt onze werkstrategie en onze visie op een planeet waar mens en natuur in harmonie leven. WWF is afhankelijk van de steun van donateurs en donatrices, en van de samenwerking met lokale gemeenschappen, jonge generaties, private en publieke partners om duurzame natuurbeschermingsoplossingen te vinden. Alleen samen kunnen we beschermen wat ons in leven houdt: bossen, oceaan, zoet water, fauna en flora.", "WWF zet zich in om de achteruitgang van de natuur op onze planeet te stoppen en om te bouwen aan een toekomst waar de mens in harmonie leeft met de natuur.", "+3223400920", "supporters@wwf.be", "https://adfinitas-statics-cdn.s3.eu-west-3.amazonaws.com/wwf/defisc-20/logo.jpg");
        Product productOrangoetanSleutelhanger = new Product("WWF sleutelhanger orang-oetan", "Een schattige orang-oetan als sleutelhanger van 10cm.", 10.95, true, Arrays.asList("https://www.wwf.nl/globalassets/commerce/productafbeeldingen/wwf-sleutelhanger-orang-oetan-01.jpg", "https://www.wwf.nl/globalassets/commerce/productafbeeldingen/wwf-sleutelhanger-orang-oetan-02.jpg"), categorySleutelhangers, organizationWWF);

        Interaction interactionToBeDeleted = new Interaction(4, 3, 1, reviewGianniDeHerdtSleutelhanger, productOrangoetanSleutelhanger, customerGianniDeHerdt);

        given(interactionRepository.findInteractionById(interactionToBeDeleted.getId())).willReturn(interactionToBeDeleted);

        mockMvc.perform(delete("/api/interactions/{id}", interactionToBeDeleted.getId()).header("Authorization", "Bearer " + tokenGetService.obtainAccessToken(emailOrganization, password))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    public void whenAddClickInteraction_thenReturnCreated() throws Exception {
        Map<String,Object> input=new HashMap<>();
        input.put("productId", "1");
        input.put("customerId", "2");
        mockMvc.perform(post("/api/interactions/addClick").header("Authorization", "Bearer " + tokenGetService.obtainAccessToken(emailAdmin, password))
                        .content(mapper.writeValueAsString(input))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void whenAddClickInteractionUnauthorized_thenReturnForbidden() throws Exception {
        Map<String,Object> input=new HashMap<>();
        input.put("productId", "1");
        input.put("customerId", "2");
        mockMvc.perform(post("/api/interactions/addClick").header("Authorization", "Bearer " + tokenGetService.obtainAccessToken(emailOrganization, password))
                        .content(mapper.writeValueAsString(input))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    public void whenAddBuyInteraction_thenReturnCreated() throws Exception {
        Map<String,Object> input=new HashMap<>();
        input.put("productId", "1");
        input.put("customerId", "2");
        mockMvc.perform(post("/api/interactions/addBuy").header("Authorization", "Bearer " + tokenGetService.obtainAccessToken(emailAdmin, password))
                        .content(mapper.writeValueAsString(input))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void whenAddBuyInteractionUnauthorized_thenReturnForbidden() throws Exception {
        Map<String,Object> input=new HashMap<>();
        input.put("productId", "1");
        input.put("customerId", "2");
        mockMvc.perform(post("/api/interactions/addBuy").header("Authorization", "Bearer " + tokenGetService.obtainAccessToken(emailOrganization, password))
                        .content(mapper.writeValueAsString(input))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    public void whenAddCartInteraction_thenReturnCreated() throws Exception {
        Map<String,Object> input=new HashMap<>();
        input.put("productId", "1");
        input.put("customerId", "2");
        mockMvc.perform(post("/api/interactions/addCart").header("Authorization", "Bearer " + tokenGetService.obtainAccessToken(emailAdmin, password))
                        .content(mapper.writeValueAsString(input))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    @Test
    public void whenAddCartInteractionUnauthorized_thenReturnForbidden() throws Exception {
        Map<String,Object> input=new HashMap<>();
        input.put("productId", "1");
        input.put("customerId", "2");
        mockMvc.perform(post("/api/interactions/addCart").header("Authorization", "Bearer " + tokenGetService.obtainAccessToken(emailOrganization, password))
                        .content(mapper.writeValueAsString(input))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }
    
}
