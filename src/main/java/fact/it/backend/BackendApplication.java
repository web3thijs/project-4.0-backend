package fact.it.backend;

import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import fact.it.backend.model.*;
import fact.it.backend.repository.*;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;

import javax.annotation.PostConstruct;
import java.util.Date;

@SpringBootApplication
public class BackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer customizer() {
        return builder -> builder.serializerByType(ObjectId.class, new ToStringSerializer());
    }

    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    ColorRepository colorRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    InteractionRepository interactionRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    OrderDetailRepository orderDetailRepository;
    @Autowired
    OrganizationRepository organizationRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    ReviewRepository reviewRepository;
    @Autowired
    SizeRepository sizeRepository;
    @Autowired
    StockRepository stockRepository;

    @PostConstruct
    public void fillDB(){
        Category category1 = new Category("shirts");
        Category category2 = new Category("pants");
        Color color1 = new Color("red");
        Color color2 = new Color("green");
        Size size1 = new Size("S");
        Size size2 = new Size("M");
        Review review1 = new Review( 4, "Good", "");
        Review review2 = new Review(4.5, "Nice product", "I liked it.");
        Customer customer1 = new Customer("giannidh@gmail.com", "password123", "0479994529", "Kersstraat 17", "2200", "Belgium", Role.CUSTOMER,"Gianni" , "De Herdt", false);
        Customer customer2 = new Customer("thijswouters@gmail.com", "password123", "0479954719", "Hoekstraat 165", "1680", "Belgium", Role.CUSTOMER,"Thijs" , "Wouters", true);
        Organization organization1 = new Organization("supporters@wwf.be", "wwf123", "+3223400920", "Emile Jacqmainlaan 90", "1000", "Belgium", Role.ORGANIZATION, "WWF", "BE0408656248", "BE0408656248", "Het World Wide Fund for Nature – waarvan de Nederlandse tak Wereld Natuur Fonds heet en de Amerikaanse World Wildlife Fund – is een wereldwijd opererende organisatie voor bescherming van de natuur", "+3223400920", "supporters@wwf.be");
        Order order1 = new Order( customer1, new Date());
        Order order2 = new Order( customer2, new Date());
        Product product1 = new Product(category1, organization1, "T-shirt", 13.99, "Plain T-shirt", true, "Google.com");
        Product product2 = new Product( category2, organization1, "Jeans", 23.99, "Plain Jeans", true, "Google.com");


        if(categoryRepository.count()==0){
            categoryRepository.save(category1);
            categoryRepository.save(category2);
        }
        if(colorRepository.count() == 0){
            colorRepository.save(color1);
            colorRepository.save(color2);
        }
        if(customerRepository.count() == 0){
            customerRepository.save(customer1);
            customerRepository.save(customer2);
        }
        if(interactionRepository.count() == 0){
            interactionRepository.save(new Interaction(product1, customer1,review1, 26));
            interactionRepository.save(new Interaction(product2, customer2, review2, 23));
        }
        if(orderRepository.count()==0){
            orderRepository.save(new Order( customer1, new Date()));
            orderRepository.save(new Order(customer2, new Date()));
            orderRepository.save(new Order(customer1, new Date()));
        }
        if(orderDetailRepository.count()==0){
            orderDetailRepository.save(new OrderDetail(product1, order1, size1, color1, 2));
            orderDetailRepository.save(new OrderDetail(product2, order2, size2, color2, 4));
            orderDetailRepository.save(new OrderDetail(product1, order1, size2, color2, 6));

        }
        if(organizationRepository.count() == 0){
            organizationRepository.save(organization1);
        }
        if(productRepository.count() == 0){
            productRepository.save(product1);
            productRepository.save(product2);
        }
        if(reviewRepository.count() == 0){
            reviewRepository.save(review1);
            reviewRepository.save(review2);
        }
        if(sizeRepository.count() == 0){
            sizeRepository.save(size1);
            sizeRepository.save(size2);
        }
        if(stockRepository.count() == 0){
            stockRepository.save(new Stock(size1, color1, product1, 10));
            stockRepository.save(new Stock(size2, color2, product2, 15));
        }

        System.out.println("DB test categories: " + categoryRepository.findAll().size() + " categories.");
        System.out.println("DB test colors: " + colorRepository.findAll().size() + " colors.");
        System.out.println("DB test customers: " + customerRepository.findByRole(Role.CUSTOMER).size() + " customers.");
        System.out.println("DB test interactions: " + interactionRepository.findAll().size() + " interactions.");
        System.out.println("DB test orders: " + orderRepository.findAll().size() + " orders.");
        System.out.println("DB test orderdetails " + orderDetailRepository.findAll().size() + " orderdetails.");
        System.out.println("DB test organizations: " + organizationRepository.findByRole(Role.ORGANIZATION).size() + " organizations.");
        System.out.println("DB test products: " + productRepository.findAll().size() + " products.");
        System.out.println("DB test reviews: " + reviewRepository.findAll().size() + " reviews.");
        System.out.println("DB test sizes: " + sizeRepository.findAll().size() + " sizes.");
        System.out.println("DB test stocks: " + stockRepository.findAll().size() + " stocks.");

    }
}
