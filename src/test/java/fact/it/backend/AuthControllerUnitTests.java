package fact.it.backend;

import com.fasterxml.jackson.databind.ObjectMapper;
import fact.it.backend.model.*;
import fact.it.backend.repository.CategoryRepository;
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
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isA;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@RequestMapping(path = "api/categories")
@WithMockUser(username = "admin", roles = "ADMIN")
public class AuthControllerUnitTests {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private TokenGetService tokenGetService;
    @Value("giannideherdt@gmail.com")
    private String emailAdmin;
    @Value("giannideherdt@gfail.com")
    private String emailBad;
    @Value("Password123")
    private String password;

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void whenRegisterCustomer_thenReturnOk() throws Exception {
        Customer customerPost = new Customer("giannideherdt@gmail.com", password, "0479994529", "Belgium", "2200", "Kersstraat 17", Role.CUSTOMER, "Gianni", "De Herdt");

        mockMvc.perform(post("/api/register/customer")
                        .content(mapper.writeValueAsString(customerPost))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.response", is("Succesful registration for customer " + customerPost.getEmail())));


    }
    @Test
    public void whenRegisterCustomer_thenReturnError() throws Exception {
        Customer customerPost = new Customer("giannideherdt@gmail.com", password, "0479994529", "Belgium", "2200", "Kersstraat 17", Role.CUSTOMER, "texttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttext", "De Herdt");

        mockMvc.perform(post("/api/register/customer")
                        .content(mapper.writeValueAsString(customerPost))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.response", is("Error during registration for customer " + customerPost.getEmail())));


    }

    @Test
    public void whenRegisterOrganization_thenReturnOk() throws Exception {
        Organization organizationPost = new Organization("supporters@wwf.be", password, "+3223400920", "Belgium", "1000", "Emile Jacqmainlaan 90", Role.ORGANIZATION, "WWF", "BE0408656248", "BE0408656248", "Sinds de oprichting in 1966 is WWF-België één van de belangrijkste natuurbeschermingsorganisaties in ons land.", "Onze slogan ‘Together Possible!’ belichaamt onze werkstrategie en onze visie op een planeet waar mens en natuur in harmonie leven. WWF is afhankelijk van de steun van donateurs en donatrices.", "WWF zet zich in om de achteruitgang van de natuur op onze planeet te stoppen en om te bouwen aan een toekomst waar de mens in harmonie leeft met de natuur.", "+3223400920", "supporters@wwf.be", "https://adfinitas-statics-cdn.s3.eu-west-3.amazonaws.com/wwf/defisc-20/logo.jpg");

        mockMvc.perform(post("/api/register/organization")
                        .content(mapper.writeValueAsString(organizationPost))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.response", is("Succesful registration for organization " + organizationPost.getEmail())));

    }

    @Test
    public void whenRegisterOrganizationBad_thenReturnError() throws Exception {
        Organization organizationPost = new Organization("supporters@wwf.be", password, "+3223400920", "Belgium", "1000", "Emile Jacqmainlaan 90", Role.ORGANIZATION, "WWF", "BE0408656248", "BE0408656248", "Sinds de oprichting in 1966 is WWF-België één van de belangrijkste natuurbeschermingsorganisaties in ons land. texttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttext", "Onze slogan ‘Together Possible!’ belichaamt onze werkstrategie en onze visie op een planeet waar mens en natuur in harmonie leven. WWF is afhankelijk van de steun van donateurs en donatrices.", "WWF zet zich in om de achteruitgang van de natuur op onze planeet te stoppen en om te bouwen aan een toekomst waar de mens in harmonie leeft met de natuur.", "+3223400920", "supporters@wwf.be", "https://adfinitas-statics-cdn.s3.eu-west-3.amazonaws.com/wwf/defisc-20/logo.jpg");

        mockMvc.perform(post("/api/register/organization")
                        .content(mapper.writeValueAsString(organizationPost))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.response", is("Error during registration for organization " + organizationPost.getEmail())));

    }

    @Test
    public void whenAuthenticate_thenReturnJsonToken() throws Exception{
        tokenGetService.obtainAccessToken(emailAdmin, password);

    }

/*    @Test
    public void whenAuthenticateBadCredentials_thenReturnJsonToken() throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        AuthRequest user = new AuthRequest();
        user.setPassword("d");
        user.setEmail("e");

        String json = mapper.writeValueAsString(user);
        mockMvc.perform(post("/api/authenticate")
                        .content(json)
                        .header("Content-Type", "application/json")
                        .accept("application/json"))
                .andExpect(status().isForbidden());
    }*/
}
