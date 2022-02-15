package fact.it.backend;

import com.fasterxml.jackson.databind.ObjectMapper;
import fact.it.backend.model.*;
import fact.it.backend.repository.StockRepository;
import fact.it.backend.service.TokenGetService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@RequestMapping(path = "api/stocks")
@WithMockUser(username="admin", roles="ADMIN")
public class StockControllerUnitTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TokenGetService tokenGetService;

    @MockBean
    private StockRepository stockRepository;

    private ObjectMapper mapper = new ObjectMapper();

    @Value("giannideherdt@gmail.com")
    private String emailAdmin;
    @Value("supporters@wwf.be")
    private String emailOrganization;
    @Value("jolienfoets@gmail.com")
    private String emailCustomer;
    @Value("Password123")
    private String password;


    @Test
    public void whenGetAllStocks_thenReturnJsonStock() throws Exception{
        Pageable requestedPage = PageRequest.of(0, 8, Sort.by("name").ascending());

        Page<Stock> allStocks = stockRepository.findAll(requestedPage);

        given(stockRepository.findAll(requestedPage)).willReturn(allStocks);

        mockMvc.perform(get("/api/stocks")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void whenGetAllStocksDesc_thenReturnJsonStock() throws Exception{
        Pageable requestedPage = PageRequest.of(0, 8, Sort.by("name").descending());

        Page<Stock> allStocks = stockRepository.findAll(requestedPage);

        given(stockRepository.findAll(requestedPage)).willReturn(allStocks);

        mockMvc.perform(get("/api/stocks?order=desc")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void whenGetStockByProductId_thenReturnJsonStock() throws Exception{
        Category categorySleutelhangers = new Category("sleutelhangers");
        Organization organizationWWF = new Organization("supporters@wwf.be", password, "+3223400920", "Belgium", "1000", "Emile Jacqmainlaan 90", Role.ORGANIZATION, "WWF", "BE0408656248", "BE0408656248", "Sinds de oprichting in 1966 is WWF-België één van de belangrijkste natuurbeschermingsorganisaties in ons land. Als lid van het wereldwijde WWF-netwerk nemen we deel aan grote nationale en internationale projecten om de natuur te beschermen en te zorgen voor een duurzame toekomst voor de generaties na ons.", "Onze slogan ‘Together Possible!’ belichaamt onze werkstrategie en onze visie op een planeet waar mens en natuur in harmonie leven. WWF is afhankelijk van de steun van donateurs en donatrices, en van de samenwerking met lokale gemeenschappen, jonge generaties, private en publieke partners om duurzame natuurbeschermingsoplossingen te vinden. Alleen samen kunnen we beschermen wat ons in leven houdt: bossen, oceaan, zoet water, fauna en flora.", "WWF zet zich in om de achteruitgang van de natuur op onze planeet te stoppen en om te bouwen aan een toekomst waar de mens in harmonie leeft met de natuur.", "+3223400920", "supporters@wwf.be", "https://adfinitas-statics-cdn.s3.eu-west-3.amazonaws.com/wwf/defisc-20/logo.jpg");
        Product productOrangOetan = new Product(0,"WWF sleutelhanger orang-oetan", "Een schattige orang-oetan als sleutelhanger van 10cm.", 10.95, true, Arrays.asList("https://www.wwf.nl/globalassets/commerce/productafbeeldingen/wwf-sleutelhanger-orang-oetan-01.jpg", "https://www.wwf.nl/globalassets/commerce/productafbeeldingen/wwf-sleutelhanger-orang-oetan-02.jpg"), categorySleutelhangers, organizationWWF);
        Color colorBruin = new Color("bruin");
        Size sizeMedium = new Size( "Medium");
        Stock stockTest = new Stock(0, 50, sizeMedium, colorBruin, productOrangOetan);

        given(stockRepository.findStocksByProductId(0)).willReturn(Arrays.asList(stockTest));

        mockMvc.perform(get("/api/stocks/product/{productId}", 0)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

/*    @Test
    public void whenGetStockByProductIdNotFound_thenReturnJsonStock() throws Exception{
        given(stockRepository.findStocksByProductId(0)).willReturn(null);

        mockMvc.perform(get("/api/stocks/product/{productId}", 0)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }*/

    @Test
    public void whenPostStock_thenReturnJsonStock() throws Exception{
        Category categorySleutelhangers = new Category("sleutelhangers");
        Organization organizationWWF = new Organization("supporters@wwf.be", password, "+3223400920", "Belgium", "1000", "Emile Jacqmainlaan 90", Role.ORGANIZATION, "WWF", "BE0408656248", "BE0408656248", "Sinds de oprichting in 1966 is WWF-België één van de belangrijkste natuurbeschermingsorganisaties in ons land. Als lid van het wereldwijde WWF-netwerk nemen we deel aan grote nationale en internationale projecten om de natuur te beschermen en te zorgen voor een duurzame toekomst voor de generaties na ons.", "Onze slogan ‘Together Possible!’ belichaamt onze werkstrategie en onze visie op een planeet waar mens en natuur in harmonie leven. WWF is afhankelijk van de steun van donateurs en donatrices, en van de samenwerking met lokale gemeenschappen, jonge generaties, private en publieke partners om duurzame natuurbeschermingsoplossingen te vinden. Alleen samen kunnen we beschermen wat ons in leven houdt: bossen, oceaan, zoet water, fauna en flora.", "WWF zet zich in om de achteruitgang van de natuur op onze planeet te stoppen en om te bouwen aan een toekomst waar de mens in harmonie leeft met de natuur.", "+3223400920", "supporters@wwf.be", "https://adfinitas-statics-cdn.s3.eu-west-3.amazonaws.com/wwf/defisc-20/logo.jpg");
        Product productOrangOetan = new Product("WWF sleutelhanger orang-oetan", "Een schattige orang-oetan als sleutelhanger van 10cm.", 10.95, true, Arrays.asList("https://www.wwf.nl/globalassets/commerce/productafbeeldingen/wwf-sleutelhanger-orang-oetan-01.jpg", "https://www.wwf.nl/globalassets/commerce/productafbeeldingen/wwf-sleutelhanger-orang-oetan-02.jpg"), categorySleutelhangers, organizationWWF);
        Color colorBruin = new Color("bruin");
        Size sizeMedium = new Size( "Medium");
        Stock stockPost = new Stock(0,50, sizeMedium, colorBruin, productOrangOetan);

        mockMvc.perform(post("/api/stocks").header("Authorization", "Bearer " + tokenGetService.obtainAccessToken(emailAdmin, password))
                        .content(mapper.writeValueAsString(stockPost))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(0)))
                .andExpect(jsonPath("$.amountInStock", is(50)))
                .andExpect(jsonPath("$.size.name", is("Medium")))
                .andExpect(jsonPath("$.color.name", is("bruin")))
                .andExpect(jsonPath("$.product.name", is("WWF sleutelhanger orang-oetan")))
                .andExpect(jsonPath("$.product.category.name", is("sleutelhangers")))
                .andExpect(jsonPath("$.product.organization.organizationName", is("WWF")));
    }

    @Test
    public void whenPostStockNotAuthorized_thenReturnForbidden() throws Exception{
        Category categorySleutelhangers = new Category("sleutelhangers");
        Organization organizationWWF = new Organization("supporters@wwf.be", password, "+3223400920", "Belgium", "1000", "Emile Jacqmainlaan 90", Role.ORGANIZATION, "WWF", "BE0408656248", "BE0408656248", "Sinds de oprichting in 1966 is WWF-België één van de belangrijkste natuurbeschermingsorganisaties in ons land. Als lid van het wereldwijde WWF-netwerk nemen we deel aan grote nationale en internationale projecten om de natuur te beschermen en te zorgen voor een duurzame toekomst voor de generaties na ons.", "Onze slogan ‘Together Possible!’ belichaamt onze werkstrategie en onze visie op een planeet waar mens en natuur in harmonie leven. WWF is afhankelijk van de steun van donateurs en donatrices, en van de samenwerking met lokale gemeenschappen, jonge generaties, private en publieke partners om duurzame natuurbeschermingsoplossingen te vinden. Alleen samen kunnen we beschermen wat ons in leven houdt: bossen, oceaan, zoet water, fauna en flora.", "WWF zet zich in om de achteruitgang van de natuur op onze planeet te stoppen en om te bouwen aan een toekomst waar de mens in harmonie leeft met de natuur.", "+3223400920", "supporters@wwf.be", "https://adfinitas-statics-cdn.s3.eu-west-3.amazonaws.com/wwf/defisc-20/logo.jpg");
        Product productOrangOetan = new Product("WWF sleutelhanger orang-oetan", "Een schattige orang-oetan als sleutelhanger van 10cm.", 10.95, true, Arrays.asList("https://www.wwf.nl/globalassets/commerce/productafbeeldingen/wwf-sleutelhanger-orang-oetan-01.jpg", "https://www.wwf.nl/globalassets/commerce/productafbeeldingen/wwf-sleutelhanger-orang-oetan-02.jpg"), categorySleutelhangers, organizationWWF);
        Color colorBruin = new Color("bruin");
        Size sizeMedium = new Size( "Medium");
        Stock stockPost = new Stock(50, sizeMedium, colorBruin, productOrangOetan);

        mockMvc.perform(post("/api/stocks").header("Authorization", "Bearer " + tokenGetService.obtainAccessToken(emailCustomer, password))
                        .content(mapper.writeValueAsString(stockPost))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    public void givenStock_whenPutStock_thenReturnJsonStock() throws Exception{
        Category categorySleutelhangers = new Category(0,"sleutelhangers");
        Organization organizationWWF = new Organization(0,"supporters@wwf.be", password, "+3223400920", "Belgium", "1000", "Emile Jacqmainlaan 90", Role.ORGANIZATION, "WWF", "BE0408656248", "BE0408656248", "Sinds de oprichting in 1966 is WWF-België één van de belangrijkste natuurbeschermingsorganisaties in ons land. Als lid van het wereldwijde WWF-netwerk nemen we deel aan grote nationale en internationale projecten om de natuur te beschermen en te zorgen voor een duurzame toekomst voor de generaties na ons.", "Onze slogan ‘Together Possible!’ belichaamt onze werkstrategie en onze visie op een planeet waar mens en natuur in harmonie leven. WWF is afhankelijk van de steun van donateurs en donatrices, en van de samenwerking met lokale gemeenschappen, jonge generaties, private en publieke partners om duurzame natuurbeschermingsoplossingen te vinden. Alleen samen kunnen we beschermen wat ons in leven houdt: bossen, oceaan, zoet water, fauna en flora.", "WWF zet zich in om de achteruitgang van de natuur op onze planeet te stoppen en om te bouwen aan een toekomst waar de mens in harmonie leeft met de natuur.", "+3223400920", "supporters@wwf.be", "https://adfinitas-statics-cdn.s3.eu-west-3.amazonaws.com/wwf/defisc-20/logo.jpg");
        Product productOrangOetan = new Product(0,"WWF sleutelhanger orang-oetan", "Een schattige orang-oetan als sleutelhanger van 10cm.", 10.95, true, Arrays.asList("https://www.wwf.nl/globalassets/commerce/productafbeeldingen/wwf-sleutelhanger-orang-oetan-01.jpg", "https://www.wwf.nl/globalassets/commerce/productafbeeldingen/wwf-sleutelhanger-orang-oetan-02.jpg"), categorySleutelhangers, organizationWWF);
        Color colorBruin = new Color(0,"bruin");
        Size sizeMedium = new Size(0, "Medium");
        Stock stockPut = new Stock(0,50, sizeMedium, colorBruin, productOrangOetan);

        given(stockRepository.findById(stockPut.getId())).willReturn(Optional.of(stockPut));

        Stock updatedStock = new Stock( 0,49, sizeMedium, colorBruin, productOrangOetan);

        mockMvc.perform(put("/api/stocks").header("Authorization", "Bearer " + tokenGetService.obtainAccessToken(emailAdmin, password))
                        .content(mapper.writeValueAsString(updatedStock))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void whenPutStockNotAuthorized_thenReturnForbidden() throws Exception{
        Category categorySleutelhangers = new Category("sleutelhangers");
        Organization organizationWWF = new Organization("supporters@wwf.be", password, "+3223400920", "Belgium", "1000", "Emile Jacqmainlaan 90", Role.ORGANIZATION, "WWF", "BE0408656248", "BE0408656248", "Sinds de oprichting in 1966 is WWF-België één van de belangrijkste natuurbeschermingsorganisaties in ons land. Als lid van het wereldwijde WWF-netwerk nemen we deel aan grote nationale en internationale projecten om de natuur te beschermen en te zorgen voor een duurzame toekomst voor de generaties na ons.", "Onze slogan ‘Together Possible!’ belichaamt onze werkstrategie en onze visie op een planeet waar mens en natuur in harmonie leven. WWF is afhankelijk van de steun van donateurs en donatrices, en van de samenwerking met lokale gemeenschappen, jonge generaties, private en publieke partners om duurzame natuurbeschermingsoplossingen te vinden. Alleen samen kunnen we beschermen wat ons in leven houdt: bossen, oceaan, zoet water, fauna en flora.", "WWF zet zich in om de achteruitgang van de natuur op onze planeet te stoppen en om te bouwen aan een toekomst waar de mens in harmonie leeft met de natuur.", "+3223400920", "supporters@wwf.be", "https://adfinitas-statics-cdn.s3.eu-west-3.amazonaws.com/wwf/defisc-20/logo.jpg");
        Product productOrangOetan = new Product("WWF sleutelhanger orang-oetan", "Een schattige orang-oetan als sleutelhanger van 10cm.", 10.95, true, Arrays.asList("https://www.wwf.nl/globalassets/commerce/productafbeeldingen/wwf-sleutelhanger-orang-oetan-01.jpg", "https://www.wwf.nl/globalassets/commerce/productafbeeldingen/wwf-sleutelhanger-orang-oetan-02.jpg"), categorySleutelhangers, organizationWWF);
        Color colorBruin = new Color("bruin");
        Size sizeMedium = new Size( "Medium");
        Stock stockPut = new Stock(50, sizeMedium, colorBruin, productOrangOetan);

        given(stockRepository.findStockById(0)).willReturn(stockPut);

        Stock updatedStock = new Stock(49, sizeMedium, colorBruin, productOrangOetan);

        mockMvc.perform(put("/api/stocks").header("Authorization", "Bearer " + tokenGetService.obtainAccessToken(emailCustomer, password))
                        .content(mapper.writeValueAsString(updatedStock))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    public void givenStock_whenDeleteStock_thenStatusOk() throws Exception {
        Category categorySleutelhangers = new Category("sleutelhangers");
        Organization organizationWWF = new Organization("supporters@wwf.be", password, "+3223400920", "Belgium", "1000", "Emile Jacqmainlaan 90", Role.ORGANIZATION, "WWF", "BE0408656248", "BE0408656248", "Sinds de oprichting in 1966 is WWF-België één van de belangrijkste natuurbeschermingsorganisaties in ons land. Als lid van het wereldwijde WWF-netwerk nemen we deel aan grote nationale en internationale projecten om de natuur te beschermen en te zorgen voor een duurzame toekomst voor de generaties na ons.", "Onze slogan ‘Together Possible!’ belichaamt onze werkstrategie en onze visie op een planeet waar mens en natuur in harmonie leven. WWF is afhankelijk van de steun van donateurs en donatrices, en van de samenwerking met lokale gemeenschappen, jonge generaties, private en publieke partners om duurzame natuurbeschermingsoplossingen te vinden. Alleen samen kunnen we beschermen wat ons in leven houdt: bossen, oceaan, zoet water, fauna en flora.", "WWF zet zich in om de achteruitgang van de natuur op onze planeet te stoppen en om te bouwen aan een toekomst waar de mens in harmonie leeft met de natuur.", "+3223400920", "supporters@wwf.be", "https://adfinitas-statics-cdn.s3.eu-west-3.amazonaws.com/wwf/defisc-20/logo.jpg");
        Product productOrangOetan = new Product("WWF sleutelhanger orang-oetan", "Een schattige orang-oetan als sleutelhanger van 10cm.", 10.95, true, Arrays.asList("https://www.wwf.nl/globalassets/commerce/productafbeeldingen/wwf-sleutelhanger-orang-oetan-01.jpg", "https://www.wwf.nl/globalassets/commerce/productafbeeldingen/wwf-sleutelhanger-orang-oetan-02.jpg"), categorySleutelhangers, organizationWWF);
        Color colorBruin = new Color("bruin");
        Size sizeMedium = new Size( "Medium");
        Stock stockToBeDeleted = new Stock(50, sizeMedium, colorBruin, productOrangOetan);

        given(stockRepository.findById(0L)).willReturn(Optional.of(stockToBeDeleted));

        mockMvc.perform(delete("/api/stocks/{id}", 0).header("Authorization", "Bearer " + tokenGetService.obtainAccessToken(emailAdmin, password))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void givenStock_whenDeleteStock_thenStatusNotFound() throws Exception {
        given(stockRepository.findStockById(12345)).willReturn(null);

        mockMvc.perform(delete("/api/stocks/{id}", 12345).header("Authorization", "Bearer " + tokenGetService.obtainAccessToken(emailAdmin, password))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

    }

    @Test
    public void whenDeleteStockNotAuthorized_thenReturnForbidden() throws Exception{
        Category categorySleutelhangers = new Category("sleutelhangers");
        Organization organizationWWF = new Organization("supporters@wwf.be", password, "+3223400920", "Belgium", "1000", "Emile Jacqmainlaan 90", Role.ORGANIZATION, "WWF", "BE0408656248", "BE0408656248", "Sinds de oprichting in 1966 is WWF-België één van de belangrijkste natuurbeschermingsorganisaties in ons land. Als lid van het wereldwijde WWF-netwerk nemen we deel aan grote nationale en internationale projecten om de natuur te beschermen en te zorgen voor een duurzame toekomst voor de generaties na ons.", "Onze slogan ‘Together Possible!’ belichaamt onze werkstrategie en onze visie op een planeet waar mens en natuur in harmonie leven. WWF is afhankelijk van de steun van donateurs en donatrices, en van de samenwerking met lokale gemeenschappen, jonge generaties, private en publieke partners om duurzame natuurbeschermingsoplossingen te vinden. Alleen samen kunnen we beschermen wat ons in leven houdt: bossen, oceaan, zoet water, fauna en flora.", "WWF zet zich in om de achteruitgang van de natuur op onze planeet te stoppen en om te bouwen aan een toekomst waar de mens in harmonie leeft met de natuur.", "+3223400920", "supporters@wwf.be", "https://adfinitas-statics-cdn.s3.eu-west-3.amazonaws.com/wwf/defisc-20/logo.jpg");
        Product productOrangOetan = new Product("WWF sleutelhanger orang-oetan", "Een schattige orang-oetan als sleutelhanger van 10cm.", 10.95, true, Arrays.asList("https://www.wwf.nl/globalassets/commerce/productafbeeldingen/wwf-sleutelhanger-orang-oetan-01.jpg", "https://www.wwf.nl/globalassets/commerce/productafbeeldingen/wwf-sleutelhanger-orang-oetan-02.jpg"), categorySleutelhangers, organizationWWF);
        Color colorBruin = new Color("bruin");
        Size sizeMedium = new Size( "Medium");
        Stock stockToBeDeleted = new Stock(50, sizeMedium, colorBruin, productOrangOetan);

        given(stockRepository.findStockById(0)).willReturn(null);

        mockMvc.perform(delete("/api/stocks/{id}", 0).header("Authorization", "Bearer " + tokenGetService.obtainAccessToken(emailCustomer, password))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }
}
