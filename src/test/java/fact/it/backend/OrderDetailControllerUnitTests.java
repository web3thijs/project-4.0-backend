package fact.it.backend;

import com.fasterxml.jackson.databind.ObjectMapper;
import fact.it.backend.model.*;
import fact.it.backend.repository.OrderDetailRepository;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@RequestMapping(path = "api/orderDetails")
@WithMockUser(username="admin", roles="ADMIN")
public class OrderDetailControllerUnitTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TokenGetService tokenGetService;

    @MockBean
    private OrderDetailRepository orderDetailRepository;

    private ObjectMapper mapper = new ObjectMapper();

    @Value("giannideherdt@gmail.com")
    private String emailAdmin;
    @Value("jolienfoets@gmail.com")
    private String emailCustomer;
    @Value("supporters@wwf.be")
    private String emailOrganization;
    @Value("Password123")
    private String password;

    /* @Test
    public void whenGetAllOrderDetails_thenReturnJsonOrderDetail() throws Exception{
        Pageable requestedPage = PageRequest.of(0, 8, Sort.by("name").descending());

        Page<OrderDetail> allOrderDetails = orderDetailRepository.findAll(requestedPage);

        given(orderDetailRepository.findAll(requestedPage)).willReturn(allOrderDetails);

        mockMvc.perform(get("/api/orderDetails"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", isA(ArrayList.class)));
    }

    @Test
    public void whenGetAllOrderDetailsWithParams_thenReturnJsonOrderDetail() throws Exception{
        Pageable requestedPage = PageRequest.of(0, 8, Sort.by("name").descending());

        Page<OrderDetail> allOrderDetailsWithParams = orderDetailRepository.findAll(requestedPage);

        given(orderDetailRepository.findAll(requestedPage)).willReturn(allOrderDetailsWithParams);

        mockMvc.perform(get("/api/orderDetails?page=0&sort=name&orderDetail=desc"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", isA(ArrayList.class)));

    }*/
    @Test
    public void whenGetOrderDetailsByOrderId_thenReturnJsonOrderDetails() throws Exception{
        Category categorySleutelhangers = new Category("sleutelhangers");
        Organization organizationWWF = new Organization("supporters@wwf.be", password, "+3223400920", "Belgium", "1000", "Emile Jacqmainlaan 90", Role.ORGANIZATION, "WWF", "BE0408656248", "BE0408656248", "Sinds de oprichting in 1966 is WWF-België één van de belangrijkste natuurbeschermingsorganisaties in ons land. Als lid van het wereldwijde WWF-netwerk nemen we deel aan grote nationale en internationale projecten om de natuur te beschermen en te zorgen voor een duurzame toekomst voor de generaties na ons.", "Onze slogan ‘Together Possible!’ belichaamt onze werkstrategie en onze visie op een planeet waar mens en natuur in harmonie leven. WWF is afhankelijk van de steun van donateurs en donatrices, en van de samenwerking met lokale gemeenschappen, jonge generaties, private en publieke partners om duurzame natuurbeschermingsoplossingen te vinden. Alleen samen kunnen we beschermen wat ons in leven houdt: bossen, oceaan, zoet water, fauna en flora.", "WWF zet zich in om de achteruitgang van de natuur op onze planeet te stoppen en om te bouwen aan een toekomst waar de mens in harmonie leeft met de natuur.", "+3223400920", "supporters@wwf.be", "https://adfinitas-statics-cdn.s3.eu-west-3.amazonaws.com/wwf/defisc-20/logo.jpg");
        Product productOrangoetanSleutelhanger = new Product("WWF sleutelhanger orang-oetan", "Een schattige orang-oetan als sleutelhanger van 10cm.", 10.95, true, Arrays.asList("https://www.wwf.nl/globalassets/commerce/productafbeeldingen/wwf-sleutelhanger-orang-oetan-01.jpg", "https://www.wwf.nl/globalassets/commerce/productafbeeldingen/wwf-sleutelhanger-orang-oetan-02.jpg"), categorySleutelhangers, organizationWWF);
        Customer customerThijsWouters = new Customer("thijswouters@gmail.com", password, "0479954719", "Hoekstraat 165", "1680", "Belgium", Role.ADMIN, "Thijs" , "Wouters");
        Color colorBruin = new Color("bruin");
        Size sizeSmall = new Size("Small");
        Order orderThijsWouters = new Order(new Date(), true, customerThijsWouters);

        OrderDetail orderDetailTest = new OrderDetail(2, productOrangoetanSleutelhanger, orderThijsWouters,sizeSmall, colorBruin);

        given(orderDetailRepository.findOrderDetailsByOrderId(orderThijsWouters.getId())).willReturn(Arrays.asList(orderDetailTest));


        mockMvc.perform(get("/api/orderdetails/order/{orderId}", 0).header("Authorization", "Bearer " + tokenGetService.obtainAccessToken(emailAdmin, password))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", isA(ArrayList.class)));
    }

    @Test
    public void whenGetOrderDetailsByOrderIdNotFound_thenReturnNotFound() throws Exception{
        given(orderDetailRepository.findOrderDetailsByOrderId(0)).willReturn(null);


        mockMvc.perform(get("/api/orderdetails/order/{orderId}", 10).header("Authorization", "Bearer " + tokenGetService.obtainAccessToken(emailAdmin, password))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void whenGetOrderDetailsByOrderIdNotAuthorized_thenReturnJsonOrderDetails() throws Exception{
        Category categorySleutelhangers = new Category("sleutelhangers");
        Organization organizationWWF = new Organization("supporters@wwf.be", password, "+3223400920", "Belgium", "1000", "Emile Jacqmainlaan 90", Role.ORGANIZATION, "WWF", "BE0408656248", "BE0408656248", "Sinds de oprichting in 1966 is WWF-België één van de belangrijkste natuurbeschermingsorganisaties in ons land. Als lid van het wereldwijde WWF-netwerk nemen we deel aan grote nationale en internationale projecten om de natuur te beschermen en te zorgen voor een duurzame toekomst voor de generaties na ons.", "Onze slogan ‘Together Possible!’ belichaamt onze werkstrategie en onze visie op een planeet waar mens en natuur in harmonie leven. WWF is afhankelijk van de steun van donateurs en donatrices, en van de samenwerking met lokale gemeenschappen, jonge generaties, private en publieke partners om duurzame natuurbeschermingsoplossingen te vinden. Alleen samen kunnen we beschermen wat ons in leven houdt: bossen, oceaan, zoet water, fauna en flora.", "WWF zet zich in om de achteruitgang van de natuur op onze planeet te stoppen en om te bouwen aan een toekomst waar de mens in harmonie leeft met de natuur.", "+3223400920", "supporters@wwf.be", "https://adfinitas-statics-cdn.s3.eu-west-3.amazonaws.com/wwf/defisc-20/logo.jpg");
        Product productOrangoetanSleutelhanger = new Product("WWF sleutelhanger orang-oetan", "Een schattige orang-oetan als sleutelhanger van 10cm.", 10.95, true, Arrays.asList("https://www.wwf.nl/globalassets/commerce/productafbeeldingen/wwf-sleutelhanger-orang-oetan-01.jpg", "https://www.wwf.nl/globalassets/commerce/productafbeeldingen/wwf-sleutelhanger-orang-oetan-02.jpg"), categorySleutelhangers, organizationWWF);
        Customer customerThijsWouters = new Customer("thijswouters@gmail.com", password, "0479954719", "Hoekstraat 165", "1680", "Belgium", Role.ADMIN, "Thijs" , "Wouters");
        Color colorBruin = new Color("bruin");
        Size sizeSmall = new Size("Small");
        Order orderThijsWouters = new Order(new Date(), true, customerThijsWouters);

        OrderDetail orderDetailTest = new OrderDetail(2, productOrangoetanSleutelhanger, orderThijsWouters,sizeSmall, colorBruin);

        given(orderDetailRepository.findOrderDetailsByOrderId(orderThijsWouters.getId())).willReturn(Arrays.asList(orderDetailTest));


        mockMvc.perform(get("/api/orderdetails/order/{orderId}", 0).header("Authorization", "Bearer " + tokenGetService.obtainAccessToken(emailOrganization, password))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.*", isA(ArrayList.class)));
    }

    @Test
    public void whenGetOrderDetailsByOrganizationId_thenReturnJsonOrderDetails() throws Exception{
        Category categorySleutelhangers = new Category("sleutelhangers");
        Organization organizationWWF = new Organization("supporters@wwf.be", password, "+3223400920", "Belgium", "1000", "Emile Jacqmainlaan 90", Role.ORGANIZATION, "WWF", "BE0408656248", "BE0408656248", "Sinds de oprichting in 1966 is WWF-België één van de belangrijkste natuurbeschermingsorganisaties in ons land. Als lid van het wereldwijde WWF-netwerk nemen we deel aan grote nationale en internationale projecten om de natuur te beschermen en te zorgen voor een duurzame toekomst voor de generaties na ons.", "Onze slogan ‘Together Possible!’ belichaamt onze werkstrategie en onze visie op een planeet waar mens en natuur in harmonie leven. WWF is afhankelijk van de steun van donateurs en donatrices, en van de samenwerking met lokale gemeenschappen, jonge generaties, private en publieke partners om duurzame natuurbeschermingsoplossingen te vinden. Alleen samen kunnen we beschermen wat ons in leven houdt: bossen, oceaan, zoet water, fauna en flora.", "WWF zet zich in om de achteruitgang van de natuur op onze planeet te stoppen en om te bouwen aan een toekomst waar de mens in harmonie leeft met de natuur.", "+3223400920", "supporters@wwf.be", "https://adfinitas-statics-cdn.s3.eu-west-3.amazonaws.com/wwf/defisc-20/logo.jpg");
        Product productOrangoetanSleutelhanger = new Product("WWF sleutelhanger orang-oetan", "Een schattige orang-oetan als sleutelhanger van 10cm.", 10.95, true, Arrays.asList("https://www.wwf.nl/globalassets/commerce/productafbeeldingen/wwf-sleutelhanger-orang-oetan-01.jpg", "https://www.wwf.nl/globalassets/commerce/productafbeeldingen/wwf-sleutelhanger-orang-oetan-02.jpg"), categorySleutelhangers, organizationWWF);
        Customer customerThijsWouters = new Customer("thijswouters@gmail.com", password, "0479954719", "Hoekstraat 165", "1680", "Belgium", Role.ADMIN, "Thijs" , "Wouters");
        Color colorBruin = new Color("bruin");
        Size sizeSmall = new Size("Small");
        Order orderThijsWouters = new Order(new Date(), true, customerThijsWouters);

        OrderDetail orderDetailTest = new OrderDetail(2, productOrangoetanSleutelhanger, orderThijsWouters,sizeSmall, colorBruin);

        given(orderDetailRepository.findOrderDetailById(orderThijsWouters.getId())).willReturn(orderDetailTest);


        mockMvc.perform(get("/api/orderdetails/organization", 0).header("Authorization", "Bearer " + tokenGetService.obtainAccessToken(emailOrganization, password))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", isA(ArrayList.class)));
    }

    @Test
    public void whenGetOrderDetailsByOrganizationIdUnauthorized_thenReturnJsonOrderDetails() throws Exception{
        Category categorySleutelhangers = new Category("sleutelhangers");
        Organization organizationWWF = new Organization("supporters@wwf.be", password, "+3223400920", "Belgium", "1000", "Emile Jacqmainlaan 90", Role.ORGANIZATION, "WWF", "BE0408656248", "BE0408656248", "Sinds de oprichting in 1966 is WWF-België één van de belangrijkste natuurbeschermingsorganisaties in ons land. Als lid van het wereldwijde WWF-netwerk nemen we deel aan grote nationale en internationale projecten om de natuur te beschermen en te zorgen voor een duurzame toekomst voor de generaties na ons.", "Onze slogan ‘Together Possible!’ belichaamt onze werkstrategie en onze visie op een planeet waar mens en natuur in harmonie leven. WWF is afhankelijk van de steun van donateurs en donatrices, en van de samenwerking met lokale gemeenschappen, jonge generaties, private en publieke partners om duurzame natuurbeschermingsoplossingen te vinden. Alleen samen kunnen we beschermen wat ons in leven houdt: bossen, oceaan, zoet water, fauna en flora.", "WWF zet zich in om de achteruitgang van de natuur op onze planeet te stoppen en om te bouwen aan een toekomst waar de mens in harmonie leeft met de natuur.", "+3223400920", "supporters@wwf.be", "https://adfinitas-statics-cdn.s3.eu-west-3.amazonaws.com/wwf/defisc-20/logo.jpg");
        Product productOrangoetanSleutelhanger = new Product("WWF sleutelhanger orang-oetan", "Een schattige orang-oetan als sleutelhanger van 10cm.", 10.95, true, Arrays.asList("https://www.wwf.nl/globalassets/commerce/productafbeeldingen/wwf-sleutelhanger-orang-oetan-01.jpg", "https://www.wwf.nl/globalassets/commerce/productafbeeldingen/wwf-sleutelhanger-orang-oetan-02.jpg"), categorySleutelhangers, organizationWWF);
        Customer customerThijsWouters = new Customer("thijswouters@gmail.com", password, "0479954719", "Hoekstraat 165", "1680", "Belgium", Role.ADMIN, "Thijs" , "Wouters");
        Color colorBruin = new Color("bruin");
        Size sizeSmall = new Size("Small");
        Order orderThijsWouters = new Order(new Date(), true, customerThijsWouters);

        OrderDetail orderDetailTest = new OrderDetail(2, productOrangoetanSleutelhanger, orderThijsWouters,sizeSmall, colorBruin);

        given(orderDetailRepository.findOrderDetailById(orderThijsWouters.getId())).willReturn(orderDetailTest);


        mockMvc.perform(get("/api/orderdetails/organization", 0).header("Authorization", "Bearer " + tokenGetService.obtainAccessToken(emailCustomer, password))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.*", isA(ArrayList.class)));
    }

    @Test
    public void whenPostOrderDetail_thenReturnJsonOrderDetail() throws Exception{
        Category categorySleutelhangers = new Category("sleutelhangers");
        Organization organizationWWF = new Organization("supporters@wwf.be", password, "+3223400920", "Belgium", "1000", "Emile Jacqmainlaan 90", Role.ORGANIZATION, "WWF", "BE0408656248", "BE0408656248", "Sinds de oprichting in 1966 is WWF-België één van de belangrijkste natuurbeschermingsorganisaties in ons land. Als lid van het wereldwijde WWF-netwerk nemen we deel aan grote nationale en internationale projecten om de natuur te beschermen en te zorgen voor een duurzame toekomst voor de generaties na ons.", "Onze slogan ‘Together Possible!’ belichaamt onze werkstrategie en onze visie op een planeet waar mens en natuur in harmonie leven. WWF is afhankelijk van de steun van donateurs en donatrices, en van de samenwerking met lokale gemeenschappen, jonge generaties, private en publieke partners om duurzame natuurbeschermingsoplossingen te vinden. Alleen samen kunnen we beschermen wat ons in leven houdt: bossen, oceaan, zoet water, fauna en flora.", "WWF zet zich in om de achteruitgang van de natuur op onze planeet te stoppen en om te bouwen aan een toekomst waar de mens in harmonie leeft met de natuur.", "+3223400920", "supporters@wwf.be", "https://adfinitas-statics-cdn.s3.eu-west-3.amazonaws.com/wwf/defisc-20/logo.jpg");
        Product productOrangoetanSleutelhanger = new Product("WWF sleutelhanger orang-oetan", "Een schattige orang-oetan als sleutelhanger van 10cm.", 10.95, true, Arrays.asList("https://www.wwf.nl/globalassets/commerce/productafbeeldingen/wwf-sleutelhanger-orang-oetan-01.jpg", "https://www.wwf.nl/globalassets/commerce/productafbeeldingen/wwf-sleutelhanger-orang-oetan-02.jpg"), categorySleutelhangers, organizationWWF);
        Customer customerThijsWouters = new Customer("thijswouters@gmail.com", password, "0479954719", "Hoekstraat 165", "1680", "Belgium", Role.ADMIN, "Thijs" , "Wouters");
        Color colorBruin = new Color("bruin");
        Size sizeSmall = new Size("Small");
        Order orderThijsWouters = new Order(new Date(), true, customerThijsWouters);

        OrderDetail orderDetailPost = new OrderDetail(2, productOrangoetanSleutelhanger, orderThijsWouters,sizeSmall, colorBruin);

        mockMvc.perform(post("/api/orderdetails").header("Authorization", "Bearer " + tokenGetService.obtainAccessToken(emailAdmin, password))
                        .content(mapper.writeValueAsString(orderDetailPost))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(0)))
                .andExpect(jsonPath("$.amount", is(2)))
                .andExpect(jsonPath("$.product.name", is("WWF sleutelhanger orang-oetan")))
                .andExpect(jsonPath("$.order.completed", is(true)))
                .andExpect(jsonPath("$.size.name", is("Small")))
                .andExpect(jsonPath("$.color.name", is("bruin")))
                .andExpect(jsonPath("$.order.customer.email", is("thijswouters@gmail.com")));
    }

    @Test
    public void whenPostOrderDetailNotAuthorized_thenReturnForbidden() throws Exception{
        Category categorySleutelhangers = new Category("sleutelhangers");
        Organization organizationWWF = new Organization("supporters@wwf.be", password, "+3223400920", "Belgium", "1000", "Emile Jacqmainlaan 90", Role.ORGANIZATION, "WWF", "BE0408656248", "BE0408656248", "Sinds de oprichting in 1966 is WWF-België één van de belangrijkste natuurbeschermingsorganisaties in ons land. Als lid van het wereldwijde WWF-netwerk nemen we deel aan grote nationale en internationale projecten om de natuur te beschermen en te zorgen voor een duurzame toekomst voor de generaties na ons.", "Onze slogan ‘Together Possible!’ belichaamt onze werkstrategie en onze visie op een planeet waar mens en natuur in harmonie leven. WWF is afhankelijk van de steun van donateurs en donatrices, en van de samenwerking met lokale gemeenschappen, jonge generaties, private en publieke partners om duurzame natuurbeschermingsoplossingen te vinden. Alleen samen kunnen we beschermen wat ons in leven houdt: bossen, oceaan, zoet water, fauna en flora.", "WWF zet zich in om de achteruitgang van de natuur op onze planeet te stoppen en om te bouwen aan een toekomst waar de mens in harmonie leeft met de natuur.", "+3223400920", "supporters@wwf.be", "https://adfinitas-statics-cdn.s3.eu-west-3.amazonaws.com/wwf/defisc-20/logo.jpg");
        Product productOrangoetanSleutelhanger = new Product("WWF sleutelhanger orang-oetan", "Een schattige orang-oetan als sleutelhanger van 10cm.", 10.95, true, Arrays.asList("https://www.wwf.nl/globalassets/commerce/productafbeeldingen/wwf-sleutelhanger-orang-oetan-01.jpg", "https://www.wwf.nl/globalassets/commerce/productafbeeldingen/wwf-sleutelhanger-orang-oetan-02.jpg"), categorySleutelhangers, organizationWWF);
        Customer customerThijsWouters = new Customer("thijswouters@gmail.com", password, "0479954719", "Hoekstraat 165", "1680", "Belgium", Role.ADMIN, "Thijs" , "Wouters");
        Color colorBruin = new Color("bruin");
        Size sizeSmall = new Size("Small");
        Order orderThijsWouters = new Order(new Date(), true, customerThijsWouters);

        OrderDetail orderDetailPost = new OrderDetail(2, productOrangoetanSleutelhanger, orderThijsWouters,sizeSmall, colorBruin);

        mockMvc.perform(post("/api/orderdetails").header("Authorization", "Bearer " + tokenGetService.obtainAccessToken(emailOrganization, password))
                        .content(mapper.writeValueAsString(orderDetailPost))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    public void givenOrderDetail_whenPutOrderDetail_thenReturnJsonOrderDetail() throws Exception{
        Category categorySleutelhangers = new Category("sleutelhangers");
        Organization organizationWWF = new Organization("supporters@wwf.be", password, "+3223400920", "Belgium", "1000", "Emile Jacqmainlaan 90", Role.ORGANIZATION, "WWF", "BE0408656248", "BE0408656248", "Sinds de oprichting in 1966 is WWF-België één van de belangrijkste natuurbeschermingsorganisaties in ons land. Als lid van het wereldwijde WWF-netwerk nemen we deel aan grote nationale en internationale projecten om de natuur te beschermen en te zorgen voor een duurzame toekomst voor de generaties na ons.", "Onze slogan ‘Together Possible!’ belichaamt onze werkstrategie en onze visie op een planeet waar mens en natuur in harmonie leven. WWF is afhankelijk van de steun van donateurs en donatrices, en van de samenwerking met lokale gemeenschappen, jonge generaties, private en publieke partners om duurzame natuurbeschermingsoplossingen te vinden. Alleen samen kunnen we beschermen wat ons in leven houdt: bossen, oceaan, zoet water, fauna en flora.", "WWF zet zich in om de achteruitgang van de natuur op onze planeet te stoppen en om te bouwen aan een toekomst waar de mens in harmonie leeft met de natuur.", "+3223400920", "supporters@wwf.be", "https://adfinitas-statics-cdn.s3.eu-west-3.amazonaws.com/wwf/defisc-20/logo.jpg");
        Product productOrangoetanSleutelhanger = new Product(0,"WWF sleutelhanger orang-oetan", "Een schattige orang-oetan als sleutelhanger van 10cm.", 10.95, true, Arrays.asList("https://www.wwf.nl/globalassets/commerce/productafbeeldingen/wwf-sleutelhanger-orang-oetan-01.jpg", "https://www.wwf.nl/globalassets/commerce/productafbeeldingen/wwf-sleutelhanger-orang-oetan-02.jpg"), categorySleutelhangers, organizationWWF);
        Customer customerThijsWouters = new Customer("thijswouters@gmail.com", password, "0479954719", "Hoekstraat 165", "1680", "Belgium", Role.ADMIN, "Thijs" , "Wouters");
        Color colorBruin = new Color(0,"bruin");
        Size sizeSmall = new Size(0,"Small");
        Order orderThijsWouters = new Order(new Date(), true, customerThijsWouters);

        OrderDetail orderDetailPut = new OrderDetail(2, productOrangoetanSleutelhanger, orderThijsWouters,sizeSmall, colorBruin);

        given(orderDetailRepository.findById(orderDetailPut.getId())).willReturn(Optional.of(orderDetailPut));

        OrderDetail updatedOrderDetail = new OrderDetail(3, productOrangoetanSleutelhanger, orderThijsWouters,sizeSmall, colorBruin);

        mockMvc.perform(put("/api/orderdetails").header("Authorization", "Bearer " + tokenGetService.obtainAccessToken(emailAdmin, password))
                        .content(mapper.writeValueAsString(updatedOrderDetail))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void whenPutOrderDetailNotAuthorized_thenReturnForbidden() throws Exception{
        Category categorySleutelhangers = new Category("sleutelhangers");
        Organization organizationWWF = new Organization("supporters@wwf.be", password, "+3223400920", "Belgium", "1000", "Emile Jacqmainlaan 90", Role.ORGANIZATION, "WWF", "BE0408656248", "BE0408656248", "Sinds de oprichting in 1966 is WWF-België één van de belangrijkste natuurbeschermingsorganisaties in ons land. Als lid van het wereldwijde WWF-netwerk nemen we deel aan grote nationale en internationale projecten om de natuur te beschermen en te zorgen voor een duurzame toekomst voor de generaties na ons.", "Onze slogan ‘Together Possible!’ belichaamt onze werkstrategie en onze visie op een planeet waar mens en natuur in harmonie leven. WWF is afhankelijk van de steun van donateurs en donatrices, en van de samenwerking met lokale gemeenschappen, jonge generaties, private en publieke partners om duurzame natuurbeschermingsoplossingen te vinden. Alleen samen kunnen we beschermen wat ons in leven houdt: bossen, oceaan, zoet water, fauna en flora.", "WWF zet zich in om de achteruitgang van de natuur op onze planeet te stoppen en om te bouwen aan een toekomst waar de mens in harmonie leeft met de natuur.", "+3223400920", "supporters@wwf.be", "https://adfinitas-statics-cdn.s3.eu-west-3.amazonaws.com/wwf/defisc-20/logo.jpg");
        Product productOrangoetanSleutelhanger = new Product("WWF sleutelhanger orang-oetan", "Een schattige orang-oetan als sleutelhanger van 10cm.", 10.95, true, Arrays.asList("https://www.wwf.nl/globalassets/commerce/productafbeeldingen/wwf-sleutelhanger-orang-oetan-01.jpg", "https://www.wwf.nl/globalassets/commerce/productafbeeldingen/wwf-sleutelhanger-orang-oetan-02.jpg"), categorySleutelhangers, organizationWWF);
        Customer customerThijsWouters = new Customer("thijswouters@gmail.com", password, "0479954719", "Hoekstraat 165", "1680", "Belgium", Role.ADMIN, "Thijs" , "Wouters");
        Color colorBruin = new Color("bruin");
        Size sizeSmall = new Size("Small");
        Order orderThijsWouters = new Order(new Date(), true, customerThijsWouters);

        OrderDetail orderDetailPut = new OrderDetail(2, productOrangoetanSleutelhanger, orderThijsWouters,sizeSmall, colorBruin);

        given(orderDetailRepository.findById(orderDetailPut.getId())).willReturn(Optional.of(orderDetailPut));

        OrderDetail updatedOrderDetail = new OrderDetail(3, productOrangoetanSleutelhanger, orderThijsWouters,sizeSmall, colorBruin);

        mockMvc.perform(put("/api/orderdetails").header("Authorization", "Bearer " + tokenGetService.obtainAccessToken(emailOrganization, password))
                        .content(mapper.writeValueAsString(updatedOrderDetail))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    public void givenOrderDetail_whenDeleteOrderDetail_thenStatusOk() throws Exception {
        Category categorySleutelhangers = new Category("sleutelhangers");
        Organization organizationWWF = new Organization("supporters@wwf.be", password, "+3223400920", "Belgium", "1000", "Emile Jacqmainlaan 90", Role.ORGANIZATION, "WWF", "BE0408656248", "BE0408656248", "Sinds de oprichting in 1966 is WWF-België één van de belangrijkste natuurbeschermingsorganisaties in ons land. Als lid van het wereldwijde WWF-netwerk nemen we deel aan grote nationale en internationale projecten om de natuur te beschermen en te zorgen voor een duurzame toekomst voor de generaties na ons.", "Onze slogan ‘Together Possible!’ belichaamt onze werkstrategie en onze visie op een planeet waar mens en natuur in harmonie leven. WWF is afhankelijk van de steun van donateurs en donatrices, en van de samenwerking met lokale gemeenschappen, jonge generaties, private en publieke partners om duurzame natuurbeschermingsoplossingen te vinden. Alleen samen kunnen we beschermen wat ons in leven houdt: bossen, oceaan, zoet water, fauna en flora.", "WWF zet zich in om de achteruitgang van de natuur op onze planeet te stoppen en om te bouwen aan een toekomst waar de mens in harmonie leeft met de natuur.", "+3223400920", "supporters@wwf.be", "https://adfinitas-statics-cdn.s3.eu-west-3.amazonaws.com/wwf/defisc-20/logo.jpg");
        Product productOrangoetanSleutelhanger = new Product("WWF sleutelhanger orang-oetan", "Een schattige orang-oetan als sleutelhanger van 10cm.", 10.95, true, Arrays.asList("https://www.wwf.nl/globalassets/commerce/productafbeeldingen/wwf-sleutelhanger-orang-oetan-01.jpg", "https://www.wwf.nl/globalassets/commerce/productafbeeldingen/wwf-sleutelhanger-orang-oetan-02.jpg"), categorySleutelhangers, organizationWWF);
        Customer customerThijsWouters = new Customer("thijswouters@gmail.com", password, "0479954719", "Hoekstraat 165", "1680", "Belgium", Role.ADMIN, "Thijs" , "Wouters");
        Color colorBruin = new Color("bruin");
        Size sizeSmall = new Size("Small");
        Order orderThijsWouters = new Order(new Date(), true, customerThijsWouters);

        OrderDetail orderDetailToBeDeleted = new OrderDetail(2, productOrangoetanSleutelhanger, orderThijsWouters,sizeSmall, colorBruin);

        given(orderDetailRepository.findById(orderDetailToBeDeleted.getId())).willReturn(Optional.of(orderDetailToBeDeleted));

        mockMvc.perform(delete("/api/orderdetails/{id}", orderDetailToBeDeleted.getId()).header("Authorization", "Bearer " + tokenGetService.obtainAccessToken(emailAdmin, password))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void givenOrderDetail_whenDeleteOrderDetail_thenStatusNotFound() throws Exception {
        given(orderDetailRepository.findById(12345L)).willReturn(null);

        mockMvc.perform(delete("/api/orderdetails/{id}", 123456789).header("Authorization", "Bearer " + tokenGetService.obtainAccessToken(emailAdmin, password))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

    }

    @Test
    public void whenDeleteOrderDetailNotAuthorized_thenReturnForbidden() throws Exception{
        Category categorySleutelhangers = new Category("sleutelhangers");
        Organization organizationWWF = new Organization("supporters@wwf.be", password, "+3223400920", "Belgium", "1000", "Emile Jacqmainlaan 90", Role.ORGANIZATION, "WWF", "BE0408656248", "BE0408656248", "Sinds de oprichting in 1966 is WWF-België één van de belangrijkste natuurbeschermingsorganisaties in ons land. Als lid van het wereldwijde WWF-netwerk nemen we deel aan grote nationale en internationale projecten om de natuur te beschermen en te zorgen voor een duurzame toekomst voor de generaties na ons.", "Onze slogan ‘Together Possible!’ belichaamt onze werkstrategie en onze visie op een planeet waar mens en natuur in harmonie leven. WWF is afhankelijk van de steun van donateurs en donatrices, en van de samenwerking met lokale gemeenschappen, jonge generaties, private en publieke partners om duurzame natuurbeschermingsoplossingen te vinden. Alleen samen kunnen we beschermen wat ons in leven houdt: bossen, oceaan, zoet water, fauna en flora.", "WWF zet zich in om de achteruitgang van de natuur op onze planeet te stoppen en om te bouwen aan een toekomst waar de mens in harmonie leeft met de natuur.", "+3223400920", "supporters@wwf.be", "https://adfinitas-statics-cdn.s3.eu-west-3.amazonaws.com/wwf/defisc-20/logo.jpg");
        Product productOrangoetanSleutelhanger = new Product("WWF sleutelhanger orang-oetan", "Een schattige orang-oetan als sleutelhanger van 10cm.", 10.95, true, Arrays.asList("https://www.wwf.nl/globalassets/commerce/productafbeeldingen/wwf-sleutelhanger-orang-oetan-01.jpg", "https://www.wwf.nl/globalassets/commerce/productafbeeldingen/wwf-sleutelhanger-orang-oetan-02.jpg"), categorySleutelhangers, organizationWWF);
        Customer customerThijsWouters = new Customer("thijswouters@gmail.com", password, "0479954719", "Hoekstraat 165", "1680", "Belgium", Role.ADMIN, "Thijs" , "Wouters");
        Color colorBruin = new Color("bruin");
        Size sizeSmall = new Size("Small");
        Order orderThijsWouters = new Order(new Date(), true, customerThijsWouters);

        OrderDetail orderDetailToBeDeleted = new OrderDetail(2, productOrangoetanSleutelhanger, orderThijsWouters,sizeSmall, colorBruin);


        given(orderDetailRepository.findOrderDetailById(orderDetailToBeDeleted.getId())).willReturn(null);

        mockMvc.perform(delete("/api/orderdetails/{id}", orderDetailToBeDeleted.getId()).header("Authorization", "Bearer " + tokenGetService.obtainAccessToken(emailCustomer, password))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    
}
