package fact.it.backend;

import com.fasterxml.jackson.databind.ObjectMapper;
import fact.it.backend.model.Category;
import fact.it.backend.model.Organization;
import fact.it.backend.model.Product;
import fact.it.backend.model.Role;
import fact.it.backend.repository.ProductRepository;
import fact.it.backend.service.TokenGetService;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isA;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@RequestMapping(path = "api/organizations")
@WithMockUser(username="admin", roles="ADMIN")
public class ProductControllerUnitTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TokenGetService tokenGetService;

    @MockBean
    private ProductRepository productRepository;

    private ObjectMapper mapper = new ObjectMapper();

    @Value("giannideherdt@gmail.com")
    private String emailAdmin;
    @Value("jolienfoets@gmail.com")
    private String emailCustomer;
    @Value("Password123")
    private String password;


    @Test
    public void whenGetAllProducts_thenReturnJsonProduct() throws Exception{
       Pageable requestedPageWithSort = PageRequest.of(0, 8, Sort.by("name").ascending());
        JSONObject allProducts = productRepository.filterProductsBasedOnKeywords(8, 11, 0, 999999999, "",requestedPageWithSort);

        given(productRepository.filterProductsBasedOnKeywords(8, 11, 0, 999999999, "", requestedPageWithSort)).willReturn(allProducts);

        mockMvc.perform(get("/api/products")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void whenGetAllProductsDesc_thenReturnJsonProduct() throws Exception{
        Pageable requestedPageWithSort = PageRequest.of(0, 8, Sort.by("name").ascending());
        JSONObject allProducts = productRepository.filterProductsBasedOnKeywords(8, 11, 0, 999999999, "",requestedPageWithSort);

        given(productRepository.filterProductsBasedOnKeywords(8, 11, 0, 999999999, "", requestedPageWithSort)).willReturn(allProducts);

        mockMvc.perform(get("/api/products?order=desc")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void whenGetAllProductsByOrganizationId_thenReturnJsonProduct() throws Exception{
        Pageable requestedPageWithSort = PageRequest.of(0, 8, Sort.by("name").ascending());
        Organization organizationWWF = new Organization(11,"supporters@wwf.be", password, "+3223400920", "Belgium", "1000", "Emile Jacqmainlaan 90", Role.ORGANIZATION, "WWF", "BE0408656248", "BE0408656248", "Sinds de oprichting in 1966 is WWF-België één van de belangrijkste natuurbeschermingsorganisaties in ons land. Als lid van het wereldwijde WWF-netwerk nemen we deel aan grote nationale en internationale projecten om de natuur te beschermen en te zorgen voor een duurzame toekomst voor de generaties na ons.", "Onze slogan ‘Together Possible!’ belichaamt onze werkstrategie en onze visie op een planeet waar mens en natuur in harmonie leven. WWF is afhankelijk van de steun van donateurs en donatrices, en van de samenwerking met lokale gemeenschappen, jonge generaties, private en publieke partners om duurzame natuurbeschermingsoplossingen te vinden. Alleen samen kunnen we beschermen wat ons in leven houdt: bossen, oceaan, zoet water, fauna en flora.", "WWF zet zich in om de achteruitgang van de natuur op onze planeet te stoppen en om te bouwen aan een toekomst waar de mens in harmonie leeft met de natuur.", "+3223400920", "supporters@wwf.be", "https://adfinitas-statics-cdn.s3.eu-west-3.amazonaws.com/wwf/defisc-20/logo.jpg");
        JSONObject allProducts = productRepository.filterProductsOrganizationId(organizationWWF.getId(),requestedPageWithSort);

        given(productRepository.filterProductsOrganizationId(organizationWWF.getId(),requestedPageWithSort)).willReturn(allProducts);

        mockMvc.perform(get("/api/products/organization/{organizationId}", organizationWWF.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void whenGetAllProductsByOrganizationIdDesc_thenReturnJsonProduct() throws Exception{
        Pageable requestedPageWithSort = PageRequest.of(0, 8, Sort.by("name").ascending());
        Organization organizationWWF = new Organization(11,"supporters@wwf.be", password, "+3223400920", "Belgium", "1000", "Emile Jacqmainlaan 90", Role.ORGANIZATION, "WWF", "BE0408656248", "BE0408656248", "Sinds de oprichting in 1966 is WWF-België één van de belangrijkste natuurbeschermingsorganisaties in ons land. Als lid van het wereldwijde WWF-netwerk nemen we deel aan grote nationale en internationale projecten om de natuur te beschermen en te zorgen voor een duurzame toekomst voor de generaties na ons.", "Onze slogan ‘Together Possible!’ belichaamt onze werkstrategie en onze visie op een planeet waar mens en natuur in harmonie leven. WWF is afhankelijk van de steun van donateurs en donatrices, en van de samenwerking met lokale gemeenschappen, jonge generaties, private en publieke partners om duurzame natuurbeschermingsoplossingen te vinden. Alleen samen kunnen we beschermen wat ons in leven houdt: bossen, oceaan, zoet water, fauna en flora.", "WWF zet zich in om de achteruitgang van de natuur op onze planeet te stoppen en om te bouwen aan een toekomst waar de mens in harmonie leeft met de natuur.", "+3223400920", "supporters@wwf.be", "https://adfinitas-statics-cdn.s3.eu-west-3.amazonaws.com/wwf/defisc-20/logo.jpg");

        JSONObject allProducts = productRepository.filterProductsOrganizationId(organizationWWF.getId(),requestedPageWithSort);

        given(productRepository.filterProductsOrganizationId(organizationWWF.getId(),requestedPageWithSort)).willReturn(allProducts);

        mockMvc.perform(get("/api/products/organization/{organizationId}?order=desc", organizationWWF.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void whenGetProductById_thenReturnJsonProduct() throws Exception{
        Category categorySleutelhangers = new Category("sleutelhangers");
        Organization organizationWWF = new Organization("supporters@wwf.be", password, "+3223400920", "Belgium", "1000", "Emile Jacqmainlaan 90", Role.ORGANIZATION, "WWF", "BE0408656248", "BE0408656248", "Sinds de oprichting in 1966 is WWF-België één van de belangrijkste natuurbeschermingsorganisaties in ons land. Als lid van het wereldwijde WWF-netwerk nemen we deel aan grote nationale en internationale projecten om de natuur te beschermen en te zorgen voor een duurzame toekomst voor de generaties na ons.", "Onze slogan ‘Together Possible!’ belichaamt onze werkstrategie en onze visie op een planeet waar mens en natuur in harmonie leven. WWF is afhankelijk van de steun van donateurs en donatrices, en van de samenwerking met lokale gemeenschappen, jonge generaties, private en publieke partners om duurzame natuurbeschermingsoplossingen te vinden. Alleen samen kunnen we beschermen wat ons in leven houdt: bossen, oceaan, zoet water, fauna en flora.", "WWF zet zich in om de achteruitgang van de natuur op onze planeet te stoppen en om te bouwen aan een toekomst waar de mens in harmonie leeft met de natuur.", "+3223400920", "supporters@wwf.be", "https://adfinitas-statics-cdn.s3.eu-west-3.amazonaws.com/wwf/defisc-20/logo.jpg");
        Product productTest = new Product("WWF sleutelhanger orang-oetan", "Een schattige orang-oetan als sleutelhanger van 10cm.", 10.95, true, Arrays.asList("https://www.wwf.nl/globalassets/commerce/productafbeeldingen/wwf-sleutelhanger-orang-oetan-01.jpg", "https://www.wwf.nl/globalassets/commerce/productafbeeldingen/wwf-sleutelhanger-orang-oetan-02.jpg"), categorySleutelhangers, organizationWWF);

        given(productRepository.findById(productTest.getId())).willReturn(Optional.of(productTest));

        mockMvc.perform(get("/api/products/{id}", productTest.getId()))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(0)))
                .andExpect(jsonPath("$.name", is("WWF sleutelhanger orang-oetan")))
                .andExpect(jsonPath("$.price", is(10.95)))
                .andExpect(jsonPath("$.category.name", is("sleutelhangers")))
                .andExpect(jsonPath("$.organization.organizationName", is("WWF")));
    }


    @Test
    public void whenPostProduct_thenReturnJsonProduct() throws Exception{
        Category categorySleutelhangers = new Category("sleutelhangers");
        Organization organizationWWF = new Organization("supporters@wwf.be", password, "+3223400920", "Belgium", "1000", "Emile Jacqmainlaan 90", Role.ORGANIZATION, "WWF", "BE0408656248", "BE0408656248", "Sinds de oprichting in 1966 is WWF-België één van de belangrijkste natuurbeschermingsorganisaties in ons land. Als lid van het wereldwijde WWF-netwerk nemen we deel aan grote nationale en internationale projecten om de natuur te beschermen en te zorgen voor een duurzame toekomst voor de generaties na ons.", "Onze slogan ‘Together Possible!’ belichaamt onze werkstrategie en onze visie op een planeet waar mens en natuur in harmonie leven. WWF is afhankelijk van de steun van donateurs en donatrices, en van de samenwerking met lokale gemeenschappen, jonge generaties, private en publieke partners om duurzame natuurbeschermingsoplossingen te vinden. Alleen samen kunnen we beschermen wat ons in leven houdt: bossen, oceaan, zoet water, fauna en flora.", "WWF zet zich in om de achteruitgang van de natuur op onze planeet te stoppen en om te bouwen aan een toekomst waar de mens in harmonie leeft met de natuur.", "+3223400920", "supporters@wwf.be", "https://adfinitas-statics-cdn.s3.eu-west-3.amazonaws.com/wwf/defisc-20/logo.jpg");
        Product productPost = new Product("WWF sleutelhanger orang-oetan", "Een schattige orang-oetan als sleutelhanger van 10cm.", 10.95, true, Arrays.asList("https://www.wwf.nl/globalassets/commerce/productafbeeldingen/wwf-sleutelhanger-orang-oetan-01.jpg", "https://www.wwf.nl/globalassets/commerce/productafbeeldingen/wwf-sleutelhanger-orang-oetan-02.jpg"), categorySleutelhangers, organizationWWF);

        mockMvc.perform(post("/api/products").header("Authorization", "Bearer " + tokenGetService.obtainAccessToken(emailAdmin, password))
                        .content(mapper.writeValueAsString(productPost))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(0)))
                .andExpect(jsonPath("$.name", is("WWF sleutelhanger orang-oetan")))
                .andExpect(jsonPath("$.price", is(10.95)))
                .andExpect(jsonPath("$.category.name", is("sleutelhangers")))
                .andExpect(jsonPath("$.organization.organizationName", is("WWF")));
    }

    @Test
    public void whenPostProductNotAuthorized_thenReturnForbidden() throws Exception{
        Category categorySleutelhangers = new Category("sleutelhangers");
        Organization organizationWWF = new Organization("supporters@wwf.be", password, "+3223400920", "Belgium", "1000", "Emile Jacqmainlaan 90", Role.ORGANIZATION, "WWF", "BE0408656248", "BE0408656248", "Sinds de oprichting in 1966 is WWF-België één van de belangrijkste natuurbeschermingsorganisaties in ons land. Als lid van het wereldwijde WWF-netwerk nemen we deel aan grote nationale en internationale projecten om de natuur te beschermen en te zorgen voor een duurzame toekomst voor de generaties na ons.", "Onze slogan ‘Together Possible!’ belichaamt onze werkstrategie en onze visie op een planeet waar mens en natuur in harmonie leven. WWF is afhankelijk van de steun van donateurs en donatrices, en van de samenwerking met lokale gemeenschappen, jonge generaties, private en publieke partners om duurzame natuurbeschermingsoplossingen te vinden. Alleen samen kunnen we beschermen wat ons in leven houdt: bossen, oceaan, zoet water, fauna en flora.", "WWF zet zich in om de achteruitgang van de natuur op onze planeet te stoppen en om te bouwen aan een toekomst waar de mens in harmonie leeft met de natuur.", "+3223400920", "supporters@wwf.be", "https://adfinitas-statics-cdn.s3.eu-west-3.amazonaws.com/wwf/defisc-20/logo.jpg");
        Product productPost = new Product("WWF sleutelhanger orang-oetan", "Een schattige orang-oetan als sleutelhanger van 10cm.", 10.95, true, Arrays.asList("https://www.wwf.nl/globalassets/commerce/productafbeeldingen/wwf-sleutelhanger-orang-oetan-01.jpg", "https://www.wwf.nl/globalassets/commerce/productafbeeldingen/wwf-sleutelhanger-orang-oetan-02.jpg"), categorySleutelhangers, organizationWWF);

        mockMvc.perform(post("/api/products").header("Authorization", "Bearer " + tokenGetService.obtainAccessToken(emailCustomer, password))
                        .content(mapper.writeValueAsString(productPost))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    public void givenProduct_whenPutProduct_thenReturnJsonProduct() throws Exception{
        Category categorySleutelhangers = new Category("sleutelhangers");
        Organization organizationWWF = new Organization(0,"supporters@wwf.be", password, "+3223400920", "Belgium", "1000", "Emile Jacqmainlaan 90", Role.ORGANIZATION, "WWF", "BE0408656248", "BE0408656248", "Sinds de oprichting in 1966 is WWF-België één van de belangrijkste natuurbeschermingsorganisaties in ons land. Als lid van het wereldwijde WWF-netwerk nemen we deel aan grote nationale en internationale projecten om de natuur te beschermen en te zorgen voor een duurzame toekomst voor de generaties na ons.", "Onze slogan ‘Together Possible!’ belichaamt onze werkstrategie en onze visie op een planeet waar mens en natuur in harmonie leven. WWF is afhankelijk van de steun van donateurs en donatrices, en van de samenwerking met lokale gemeenschappen, jonge generaties, private en publieke partners om duurzame natuurbeschermingsoplossingen te vinden. Alleen samen kunnen we beschermen wat ons in leven houdt: bossen, oceaan, zoet water, fauna en flora.", "WWF zet zich in om de achteruitgang van de natuur op onze planeet te stoppen en om te bouwen aan een toekomst waar de mens in harmonie leeft met de natuur.", "+3223400920", "supporters@wwf.be", "https://adfinitas-statics-cdn.s3.eu-west-3.amazonaws.com/wwf/defisc-20/logo.jpg");
        Product productPut = new Product(0,"WWF sleutelhanger orang-oetan", "Een schattige orang-oetan als sleutelhanger van 10cm.", 10.95, true, Arrays.asList("https://www.wwf.nl/globalassets/commerce/productafbeeldingen/wwf-sleutelhanger-orang-oetan-01.jpg", "https://www.wwf.nl/globalassets/commerce/productafbeeldingen/wwf-sleutelhanger-orang-oetan-02.jpg"), categorySleutelhangers, organizationWWF);

        given(productRepository.findById(productPut.getId())).willReturn(Optional.of(productPut));

        Product updatedProduct = new Product("WWF sleutelhanger orang-oetan", "Een schattige orang-oetan als sleutelhanger van 10cm.", 12.95, true, Arrays.asList("https://www.wwf.nl/globalassets/commerce/productafbeeldingen/wwf-sleutelhanger-orang-oetan-01.jpg", "https://www.wwf.nl/globalassets/commerce/productafbeeldingen/wwf-sleutelhanger-orang-oetan-02.jpg"), categorySleutelhangers, organizationWWF);

        mockMvc.perform(put("/api/products").header("Authorization", "Bearer " + tokenGetService.obtainAccessToken(emailAdmin, password))
                        .content(mapper.writeValueAsString(updatedProduct))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(0)))
                .andExpect(jsonPath("$.name", is("WWF sleutelhanger orang-oetan")))
                .andExpect(jsonPath("$.price", is(12.95)));
    }

    @Test
    public void whenPutProductNotAuthorized_thenReturnForbidden() throws Exception{
        Category categorySleutelhangers = new Category("sleutelhangers");
        Organization organizationWWF = new Organization("supporters@wwf.be", password, "+3223400920", "Belgium", "1000", "Emile Jacqmainlaan 90", Role.ORGANIZATION, "WWF", "BE0408656248", "BE0408656248", "Sinds de oprichting in 1966 is WWF-België één van de belangrijkste natuurbeschermingsorganisaties in ons land. Als lid van het wereldwijde WWF-netwerk nemen we deel aan grote nationale en internationale projecten om de natuur te beschermen en te zorgen voor een duurzame toekomst voor de generaties na ons.", "Onze slogan ‘Together Possible!’ belichaamt onze werkstrategie en onze visie op een planeet waar mens en natuur in harmonie leven. WWF is afhankelijk van de steun van donateurs en donatrices, en van de samenwerking met lokale gemeenschappen, jonge generaties, private en publieke partners om duurzame natuurbeschermingsoplossingen te vinden. Alleen samen kunnen we beschermen wat ons in leven houdt: bossen, oceaan, zoet water, fauna en flora.", "WWF zet zich in om de achteruitgang van de natuur op onze planeet te stoppen en om te bouwen aan een toekomst waar de mens in harmonie leeft met de natuur.", "+3223400920", "supporters@wwf.be", "https://adfinitas-statics-cdn.s3.eu-west-3.amazonaws.com/wwf/defisc-20/logo.jpg");
        Product productPut = new Product("WWF sleutelhanger orang-oetan", "Een schattige orang-oetan als sleutelhanger van 10cm.", 10.95, true, Arrays.asList("https://www.wwf.nl/globalassets/commerce/productafbeeldingen/wwf-sleutelhanger-orang-oetan-01.jpg", "https://www.wwf.nl/globalassets/commerce/productafbeeldingen/wwf-sleutelhanger-orang-oetan-02.jpg"), categorySleutelhangers, organizationWWF);

        given(productRepository.findProductById(productPut.getId())).willReturn(productPut);

        Product updatedProduct = new Product("WWF sleutelhanger orang-oetan", "Een schattige orang-oetan als sleutelhanger van 10cm.", 12.95, true, Arrays.asList("https://www.wwf.nl/globalassets/commerce/productafbeeldingen/wwf-sleutelhanger-orang-oetan-01.jpg", "https://www.wwf.nl/globalassets/commerce/productafbeeldingen/wwf-sleutelhanger-orang-oetan-02.jpg"), categorySleutelhangers, organizationWWF);

        mockMvc.perform(put("/api/products").header("Authorization", "Bearer " + tokenGetService.obtainAccessToken(emailCustomer, password))
                        .content(mapper.writeValueAsString(updatedProduct))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    public void givenProduct_whenDeleteProduct_thenStatusOk() throws Exception {
        Category categorySleutelhangers = new Category("sleutelhangers");
        Organization organizationWWF = new Organization("supporters@wwf.be", password, "+3223400920", "Belgium", "1000", "Emile Jacqmainlaan 90", Role.ORGANIZATION, "WWF", "BE0408656248", "BE0408656248", "Sinds de oprichting in 1966 is WWF-België één van de belangrijkste natuurbeschermingsorganisaties in ons land. Als lid van het wereldwijde WWF-netwerk nemen we deel aan grote nationale en internationale projecten om de natuur te beschermen en te zorgen voor een duurzame toekomst voor de generaties na ons.", "Onze slogan ‘Together Possible!’ belichaamt onze werkstrategie en onze visie op een planeet waar mens en natuur in harmonie leven. WWF is afhankelijk van de steun van donateurs en donatrices, en van de samenwerking met lokale gemeenschappen, jonge generaties, private en publieke partners om duurzame natuurbeschermingsoplossingen te vinden. Alleen samen kunnen we beschermen wat ons in leven houdt: bossen, oceaan, zoet water, fauna en flora.", "WWF zet zich in om de achteruitgang van de natuur op onze planeet te stoppen en om te bouwen aan een toekomst waar de mens in harmonie leeft met de natuur.", "+3223400920", "supporters@wwf.be", "https://adfinitas-statics-cdn.s3.eu-west-3.amazonaws.com/wwf/defisc-20/logo.jpg");
        Product productToBeDeleted = new Product("WWF sleutelhanger orang-oetan", "Een schattige orang-oetan als sleutelhanger van 10cm.", 10.95, true, Arrays.asList("https://www.wwf.nl/globalassets/commerce/productafbeeldingen/wwf-sleutelhanger-orang-oetan-01.jpg", "https://www.wwf.nl/globalassets/commerce/productafbeeldingen/wwf-sleutelhanger-orang-oetan-02.jpg"), categorySleutelhangers, organizationWWF);

        given(productRepository.findById(productToBeDeleted.getId())).willReturn(Optional.of(productToBeDeleted));

        mockMvc.perform(delete("/api/products/{id}", productToBeDeleted.getId()).header("Authorization", "Bearer " + tokenGetService.obtainAccessToken(emailAdmin, password))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void givenProduct_whenDeleteProduct_thenStatusNotFound() throws Exception {
        given(productRepository.findProductById(12345)).willReturn(null);

        mockMvc.perform(delete("/api/products/{id}", 12345).header("Authorization", "Bearer " + tokenGetService.obtainAccessToken(emailAdmin, password))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

    }

    @Test
    public void whenDeleteProductNotAuthorized_thenReturnForbidden() throws Exception{
        Category categorySleutelhangers = new Category("sleutelhangers");
        Organization organizationWWF = new Organization("supporters@wwf.be", password, "+3223400920", "Belgium", "1000", "Emile Jacqmainlaan 90", Role.ORGANIZATION, "WWF", "BE0408656248", "BE0408656248", "Sinds de oprichting in 1966 is WWF-België één van de belangrijkste natuurbeschermingsorganisaties in ons land. Als lid van het wereldwijde WWF-netwerk nemen we deel aan grote nationale en internationale projecten om de natuur te beschermen en te zorgen voor een duurzame toekomst voor de generaties na ons.", "Onze slogan ‘Together Possible!’ belichaamt onze werkstrategie en onze visie op een planeet waar mens en natuur in harmonie leven. WWF is afhankelijk van de steun van donateurs en donatrices, en van de samenwerking met lokale gemeenschappen, jonge generaties, private en publieke partners om duurzame natuurbeschermingsoplossingen te vinden. Alleen samen kunnen we beschermen wat ons in leven houdt: bossen, oceaan, zoet water, fauna en flora.", "WWF zet zich in om de achteruitgang van de natuur op onze planeet te stoppen en om te bouwen aan een toekomst waar de mens in harmonie leeft met de natuur.", "+3223400920", "supporters@wwf.be", "https://adfinitas-statics-cdn.s3.eu-west-3.amazonaws.com/wwf/defisc-20/logo.jpg");
        Product productToBeDeleted = new Product("WWF sleutelhanger orang-oetan", "Een schattige orang-oetan als sleutelhanger van 10cm.", 10.95, true, Arrays.asList("https://www.wwf.nl/globalassets/commerce/productafbeeldingen/wwf-sleutelhanger-orang-oetan-01.jpg", "https://www.wwf.nl/globalassets/commerce/productafbeeldingen/wwf-sleutelhanger-orang-oetan-02.jpg"), categorySleutelhangers, organizationWWF);
        given(productRepository.findProductById(productToBeDeleted.getId())).willReturn(null);

        mockMvc.perform(delete("/api/products/{id}", productToBeDeleted.getId()).header("Authorization", "Bearer " + tokenGetService.obtainAccessToken(emailCustomer, password))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }
}
