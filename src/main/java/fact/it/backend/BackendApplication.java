package fact.it.backend;

import fact.it.backend.controller.ProductController;
import fact.it.backend.model.*;
import fact.it.backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Date;

@SpringBootApplication
public class BackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ColorRepository colorRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    DonationRepository donationRepository;

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
    public void fillDatabase(){
        if(categoryRepository.findAll().size() == 0){
            categoryRepository.save(new Category("knuffels"));
            categoryRepository.save(new Category("sleutelhangers"));
            categoryRepository.save(new Category("pennen"));
            categoryRepository.save(new Category("shirts mannen"));
            categoryRepository.save(new Category("shirts vrouwen"));
            categoryRepository.save(new Category("koffiekoppen"));
            categoryRepository.save(new Category("drinkbussen"));
            categoryRepository.save(new Category("mondmaskers"));
            categoryRepository.save(new Category("kaarsen en geuren"));
        }

        if(colorRepository.findAll().size() == 0){
            colorRepository.save(new Color("rood"));
            colorRepository.save(new Color("wit"));
            colorRepository.save(new Color("groen"));
            colorRepository.save(new Color("bruin"));
            colorRepository.save(new Color("geel"));
            colorRepository.save(new Color("roze"));
            colorRepository.save(new Color("beige"));
            colorRepository.save(new Color("mix"));
            colorRepository.save(new Color("zwart en wit"));
        }

        String password = passwordEncoder.encode("Password123");

        if(customerRepository.findAll().size() == 0){
            customerRepository.save(new Customer("giannideherdt@gmail.com", password, "0479994529", "Belgium", "2200", "Kersstraat 17", Role.ADMIN, "Gianni" , "De Herdt"));
            customerRepository.save(new Customer("thijswouters@gmail.com", password, "0479954719", "Belgium", "1680", "Hoekstraat 165", Role.ADMIN, "Thijs" , "Wouters"));
            customerRepository.save(new Customer("jolienfoets@gmail.com", password, "0466544922", "Belgium", "1700", "Stepelaar 6A", Role.CUSTOMER, "Jolien" , "Foets"));
            customerRepository.save(new Customer("boblourdaux@gmail.com", password, "0495946569", "Belgium", "3040", "Sint-Schepersberg 45", Role.CUSTOMER, "Bob" , "Lourdaux"));
            customerRepository.save(new Customer("kevinmaes@gmail.com", password, "0476281912", "Belgium", "2260", "Lambertuslaan 42", Role.CUSTOMER, "Kevin" , "Maes"));
            customerRepository.save(new Customer("helderceyssens@gmail.com", password, "0476596168", "Belgium", "1540", "Koepel 186", Role.CUSTOMER, "Helder" , "Ceyssens"));
        }

        if(organizationRepository.findAll().size() == 0){
            organizationRepository.save(new Organization("supporters@wwf.be", password, "+3223400920", "Emile Jacqmainlaan 90", "1000", "Belgium", Role.ORGANIZATION,"WWF", "BE0408656248", "BE0408656248", "Sinds de oprichting in 1966", "Onze slogan ‘Together Possible!’", "WWF zet zich in", "+3223400920", "supporters@wwf.be", "https://adfinitas-statics-cdn.s3.eu-west-3.amazonaws.com/wwf/defisc-20/logo.jpg"));
            organizationRepository.save(new Organization("info@bkks.be", password, "+3211192819", "Diesterstraat 67A bus 001", "3800", "Belgium", Role.ORGANIZATION,"Belgisch KinderKanker Steunfonds", "BE0835627680", "0835627680", "Het Belgisch Kinder Kanker", "Het Belgisch Kinder", "Zelfsn dan financiële steun om deze moeilijke", "+3223400920", "supporters@wwf.be", "https://belgischkinderkankersteunfonds.be/wp-content/uploads/2020/02/cropped-logo-bkks-1.png"));
            organizationRepository.save(new Organization("welkom@bzn.be", password, "032012210", "Herentalsebaan 74", "2100", "Belgium", Role.ORGANIZATION,"Bond Zonder Naam", "BE0469514642", "BE0469514642", "De Bond zonder Naam (BZN) is een Vlaamse", "Bond zonder Naam schreef voor de", "Bond zonder Naam wil zoveel", "+3223400920", "supporters@wwf.be", "https://www.bzn.be/graphics/default-socialmedia.jpg"));
            organizationRepository.save(new Organization("maite@think-pink.be", password, "+32475406602", "Researchdreef 12", "1070", "Belgium", Role.ORGANIZATION,"Think Pink", "0810893274", "BE0810893274", "Think Pink is de nationale", "ondersteunen van zorg- en nazorgprojecten", "Borstkankanker steunen.", "+32475406602", "info@think-pink.be", "https://upload.wikimedia.org/wikipedia/commons/4/4b/Logo_think-pink.jpg"));
            organizationRepository.save(new Organization("info@damiaanactie.be", password, "+3224225911", "Leopold II-laan 263", "1081", "Belgium", Role.ORGANIZATION,"Damiaanactie", "0406694670", "BE05000000007575", "Damiaanactie is een Belgische medische non-profitorganisatie die zich inzet voor mensen met lepra, tuberculose en andere ziektes die vooral de kwetsbaarste bevolkingsgroepen treffen.", "Om lepra, tbc", "Bij Damiaanactie", "+3224225911", "info@damiaanactie.be", "https://damiaanactie.be/wp-content/uploads/2019/10/RGB-LOGO-DA-NL-transparant.png"));
            organizationRepository.save(new Organization("info@rodekruis.be", password, "+3215443322", "Motstraat 40", "2800", "Belgium", Role.ORGANIZATION,"Rode Kruis Vlaanderen", "2154897956", "BE0455024129", "Wij zijn", "Hulp", "Bij een ramp ", "+3215443322", "info@rodekruis.be", "https://www.rodekruis.be/img/logo.svg?1557833551"));
        }

        if(orderRepository.findAll().size() == 0){
            orderRepository.save(new Order(new Date(), true, customerRepository.findById(1)));
            orderRepository.save(new Order(new Date(), false , customerRepository.findById(1)));
            orderRepository.save(new Order(new Date(),true, customerRepository.findById(2)));
            orderRepository.save(new Order(new Date(), false , customerRepository.findById(2)));
            orderRepository.save(new Order(new Date(), false , customerRepository.findById(3)));
        }

        if(sizeRepository.findAll().size() == 0){
            sizeRepository.save(new Size("Small"));
            sizeRepository.save(new Size("Medium"));
            sizeRepository.save(new Size("Large"));
            sizeRepository.save(new Size("ExtraLarge"));
            sizeRepository.save(new Size("10 cm"));
            sizeRepository.save(new Size("11 cm"));
            sizeRepository.save(new Size("14 cm"));
            sizeRepository.save(new Size("15 cm"));
            sizeRepository.save(new Size("0,5 liter"));
            sizeRepository.save(new Size("100 gram"));
            sizeRepository.save(new Size("100 gram"));
        }

        if(productRepository.findAll().size() == 0){
            productRepository.save(new Product("WWF sleutelhanger orang-oetan", "Een schattige orang-oetan als sleutelhanger van 10cm.", 10.95, true, Arrays.asList("https://www.wwf.nl/globalassets/commerce/productafbeeldingen/wwf-sleutelhanger-orang-oetan-01.jpg", "https://www.wwf.nl/globalassets/commerce/productafbeeldingen/wwf-sleutelhanger-orang-oetan-02.jpg"), categoryRepository.findCategoryById(2), organizationRepository.findOrganizationById(7)));
            productRepository.save(new Product("WWF sleutelhanger panda", "De enige echte WWF-panda als sleutelhanger van 10cm!", 10.95, true, Arrays.asList("https://www.wwf.nl/globalassets/commerce/productafbeeldingen/wwf-sleutelhanger-panda-01.jpg", "https://www.wwf.nl/globalassets/commerce/productafbeeldingen/wwf-sleutelhanger-panda-02.jpg"), categoryRepository.findCategoryById(2), organizationRepository.findOrganizationById(7)));
            productRepository.save(new Product("Lief beertje Rode Kruis", "Slaap goed met de officiële beertjes-knuffel van het Rode Kruis!", 6.00, true, Arrays.asList("https://webcat.staci.com/WebCat3/custom/0231/_default//static/produits/P00042_MD.jpg"), categoryRepository.findCategoryById(1), organizationRepository.findOrganizationById(12)));
            productRepository.save(new Product("Balpen blauw", "Steun het Rode Kruis door het aankopen en gebruiken van de officiële balpennen!", 3.5, true, Arrays.asList("https://shop.rodekruis.nl/pub/media/catalog/product/cache/c3031995953b3e91d5674d5b0a0af4b5/n/r/nrk.5011_1.jpg", "https://webcat.staci.com/WebCat3/custom/0231/_default//static/produits/P00012_MD.jpg"), categoryRepository.findCategoryById(3), organizationRepository.findOrganizationById(12)));
            productRepository.save(new Product("Stiften damiaanactie", "Schrijf leprapatiënten niet af. Koop of verkoop de gekende rode, blauwe, groene en zwarte Damiaanactiestiften. Een pakje van vier kost €7.", 7.00, true, Arrays.asList("https://damiaanactie-shop.be/wp-content/uploads/2020/11/damien-eshop-042.jpg", "https://damiaanactie-shop.be/wp-content/uploads/2020/11/damien-eshop-046.jpg", "https://damiaanactie-shop.be/wp-content/uploads/2020/11/damien-eshop-052.jpg", "https://damiaanactie-shop.be/wp-content/uploads/2020/11/damien-eshop-053.jpg", "https://damiaanactie-shop.be/wp-content/uploads/2020/11/damien-eshop-054.jpg", "https://damiaanactie-shop.be/wp-content/uploads/2020/11/damien-eshop-055.jpg"), categoryRepository.findCategoryById(3), organizationRepository.findOrganizationById(11)));
            productRepository.save(new Product("Koffiemok bedrukt","Steun het Rode Kruis door het aankopen en gebruiken van de officiële koffiemokken!", 6.5, true, Arrays.asList("https://i.etsystatic.com/26520550/r/il/72e51c/3329982877/il_340x270.3329982877_kdtx.jpg"),  categoryRepository.findCategoryById(6), organizationRepository.findOrganizationById(12)));
            productRepository.save(new Product("Solidair mondmasker wit","Draag met trots de vlinders van Damiaanactie, terwijl je jezelf en anderen rondom je beschermt. Alle opbrengst gaat rechtstreeks naar onze projecten.", 8.00, true, Arrays.asList("https://damiaanactie-shop.be/wp-content/uploads/2021/12/Ontwerp-zonder-titel-3.jpg"),  categoryRepository.findCategoryById(8), organizationRepository.findOrganizationById(11)));
            productRepository.save(new Product("Solidair mondmasker groen","Trotseer de koude winterdagen met deze groene maskers van Damiaanactie. Vrolijke mondmaskers die deze sombere dagen wat kleur geven en opwarmen.", 8.00, true, Arrays.asList("https://damiaanactie-shop.be/wp-content/uploads/2021/01/masque-damien-240web.jpg"),  categoryRepository.findCategoryById(8), organizationRepository.findOrganizationById(11)));
            productRepository.save(new Product("2 solidaire mondmaskers", "Draag met trots de vlinders van Damiaanactie, terwijl je jezelf en anderen rondom je beschermt. Alle opbrengst gaat rechtstreeks naar onze projecten (€ 15  voor 2 mondmaskers).", 15.00, true, Arrays.asList("https://damiaanactie-shop.be/wp-content/uploads/2021/12/Ontwerp-zonder-titel-2.jpg"), categoryRepository.findCategoryById(8), organizationRepository.findOrganizationById(11)));
//        Product productShirtMannenTP =new Product("Run for Think Pink T-shirt heren (wit)", categoryRepository.findCategoryById(4), organizationThinkPink, 9.99, "Net zoals bij elke sport, hoort ook bij het lopen een juiste outfit. Vertegenwoordig met dit T-shirt Think Pink op elk evenement of draag hem tijdens het trainen. Het loopshirt heeft een zacht aanvoelende stof en dankzij de Equarea-technologie en de verluchtingen onder de arme wordt de transpiratie perfect afgevoerd.", true, Arrays.asList("https://www.think-pink.be/Portals/0/dtxArt/blok-afbeelding/bestand/20190820Think-Pink-2019Jeroen-Willems102-2_medium_1ba503c4-ce81-4f84-abbe-c5f02cef0356.jpg", "https://www.think-pink.be/Portals/0/dtxArt/blok-galerij/afbeelding/bestand/image_groot_ac0905cd-8657-4133-b05f-703e783572ba.png"));
//        Product productShirtVrouwenTP =new Product(categoryRepository.findCategoryById(5), organizationThinkPink, "Run for Think Pink T-shirt dames (roze)", 9.99, "Net zoals bij elke sport, hoort ook bij het lopen een juiste outfit. Vertegenwoordig met dit T-shirt Think Pink op elk evenement of draag hem tijdens het trainen. Het loopshirt heeft een zacht aanvoelende stof en dankzij de Equarea-technologie en de verluchtingen onder de arme wordt de transpiratie perfect afgevoerd.", true, Arrays.asList("https://www.think-pink.be/Portals/0/dtxArt/blok-afbeelding/bestand/6932E44F-510E-4C52-B60D-27AA69CD83A4_medium_6262025e-4c7e-468e-a0a2-2cb0237d0050.jpg"));
//        Product productDrinkbusTP =new Product(categoryRepository.findCategoryById(7), organizationThinkPink, "Drinkbus Think Pink", 4.99, "Deze leuke Think Pink-drinkbus zorgt voor de nodige verfrissing tijdens het sporten", true, Arrays.asList("https://www.think-pink.be/Portals/0/dtxArt/blok-galerij/afbeelding/bestand/Drinkbus1_groot_3918fe9e-785f-4dd5-a652-071148ed6146.jpg", "https://www.think-pink.be/Portals/0/dtxArt/blok-galerij/afbeelding/bestand/DSC1350groot6936867c-357f-4227-890b-58c5_groot_3dd61d86-f996-49ab-ac4a-6be2443b20a5.jpeg"));
//        Product productGeurkaarsBZN =new Product(categoryRepository.findCategoryById(9), organizationBZN, "Geurkaars Bond zonder Naam", 8.00, "Geurkaars ter ondersteuning van de Bond zonder Naam", true, Arrays.asList("https://i.imgur.com/9uiTrqz.png", "https://scontent-bru2-1.xx.fbcdn.net/v/t45.1600-4/cp0/q90/spS444/s526x296/17948818_23842562753170682_2138087182974320640_n.png.jpg?_nc_cat=111&ccb=1-5&_nc_sid=67cdda&_nc_ohc=EyD0jzQTYhwAX8aElSS&_nc_ht=scontent-bru2-1.xx&oh=00_AT9zk-4Xe2ziHjz1DDk6jyXObJY4jxlAXvgCWRr2CJXTyQ&oe=61F2E9BC"));
//        Product productGeurstokjesBZN =new Product(categoryRepository.findCategoryById(9), organizationBZN, "Geurstokjes Bond zonder Naam", 5.00, "Geurstokjes ter ondersteuning van de Bond zonder Naam", true, Arrays.asList("https://i.imgur.com/fW2Jkm2.png"));
//        Product productSojakaarsBZN =new Product(categoryRepository.findCategoryById(9), organizationBZN, "Sojakaars Bond zonder Naam", 10.00, "Sojakaars ter ondersteuning van de Bond zonder Naam", true, Arrays.asList("https://i.imgur.com/aOWdepM.png"));
//        Product product3pennenBKKS =new Product(categoryRepository.findCategoryById(3), organizationBKKS, "3 stylo's Belgisch KinderKanker Fonds", 6, "3 pennen met het logo van het Belgisch KinderKanker Fonds.", true, Arrays.asList("https://www.online-fanshop.be/wp-content/uploads/2020/11/stylo-bkks.png"));
//        Product productMondmaskerBKKS =new Product(categoryRepository.findCategoryById(8), organizationBKKS, "Mondmasker Belgisch KinderKanker Fonds", 8.25, "Mondmasker met het logo van het Belgisch KinderKanker Fonds.", true, Arrays.asList("https://www.online-fanshop.be/wp-content/uploads/2020/11/mondmasker-BKKS.png"));
//        Product productShirtBKKS =new Product(categoryRepository.findCategoryById(4), organizationBKKS, "Stanno Pride T-Shirt", 24.99, "Dit stretchy sportshirt, onderdeel van de Stanno Pride collectie, is voorzien van het logo van BKKS. De ClimaTec finish zorgt voor optimale vochtafvoer en dankzij de ventilerende mesh-structuur op de bovenzijde en in de zij voelt het shirt licht en luchtig aan. De kraag is gemaakt van zacht rib-materiaal. De achterzijde van het shirt is net wat langer en heeft en mooie ronde afwerking.", true, Arrays.asList("https://i.imgur.com/dLA2Rab.png"));
        }

        if(stockRepository.findAll().size() == 0){
            stockRepository.save(new Stock(50, sizeRepository.findSizeById(5), colorRepository.findColorById(4), productRepository.findProductById(1)));
//        stockRepository.save(new Stock(sizeRepository.findSizeById(8), colorRepository.findColorById(4), productOrangoetanKnuffel, 20));
            stockRepository.save(new Stock(50, sizeRepository.findSizeById(8), colorRepository.findColorById(9), productRepository.findProductById(2)));
            stockRepository.save(new Stock(200, sizeRepository.findSizeById(8), colorRepository.findColorById(2), productRepository.findProductById(4)));
            stockRepository.save(new Stock(100, sizeRepository.findSizeById(8), colorRepository.findColorById(8), productRepository.findProductById(5)));
            stockRepository.save(new Stock(50, sizeRepository.findSizeById(5), colorRepository.findColorById(2), productRepository.findProductById(6)));
            stockRepository.save(new Stock(200, sizeRepository.findSizeById(2), colorRepository.findColorById(2), productRepository.findProductById(7)));
            stockRepository.save(new Stock(150, sizeRepository.findSizeById(2), colorRepository.findColorById(3), productRepository.findProductById(8)));
            stockRepository.save(new Stock(200, sizeRepository.findSizeById(2), colorRepository.findColorById(2), productRepository.findProductById(9)));
//        stockRepository.save(new Stock(sizeRepository.findSizeById(2), colorRepository.findColorById(2), productShirtMannenTP, 25));
//        stockRepository.save(new Stock(sizeRepository.findSizeById(3), colorRepository.findColorById(2), productShirtMannenTP, 50));
//        stockRepository.save(new Stock(sizeRepository.findSizeById(2), colorRepository.findColorById(6), productShirtVrouwenTP, 50));
//        stockRepository.save(new Stock(sizeRepository.findSizeById(1), colorRepository.findColorById(6), productShirtVrouwenTP, 40));
//        stockRepository.save(new Stock(sizeRepository.findSizeById(9), colorRepository.findColorById(6), productDrinkbusTP, 80));
//        stockRepository.save(new Stock(sizeRepository.findSizeById(10), colorRepository.findColorById(1), productGeurkaarsBZN, 40));
//        stockRepository.save(new Stock(sizeRepository.findSizeById(8), colorRepository.findColorById(5), productGeurstokjesBZN, 30));
//        stockRepository.save(new Stock(sizeRepository.findSizeById(10), colorRepository.findColorById(2), productSojakaarsBZN, 20));
//        stockRepository.save(new Stock(sizeRepository.findSizeById(8), colorRepository.findColorById(2), product3pennenBKKS, 60));
//        stockRepository.save(new Stock(sizeRepository.findSizeById(2), colorRepository.findColorById(2), productMondmaskerBKKS, 25));
//        stockRepository.save(new Stock(sizeRepository.findSizeById(1), colorRepository.findColorById(9), productShirtBKKS, 15));
//        stockRepository.save(new Stock(sizeRepository.findSizeById(2), colorRepository.findColorById(9), productShirtBKKS, 30));
//        stockRepository.save(new Stock(sizeRepository.findSizeById(3), colorRepository.findColorById(9), productShirtBKKS, 30));
        }

        if(orderDetailRepository.findAll().size() == 0){
            orderDetailRepository.save(new OrderDetail(2, productRepository.findProductById(1), orderRepository.findOrderById(1), sizeRepository.findSizeById(7), colorRepository.findColorById(4)));
            orderDetailRepository.save(new OrderDetail(1, productRepository.findProductById(3), orderRepository.findOrderById(1), sizeRepository.findSizeById(8), colorRepository.findColorById(4)));
            orderDetailRepository.save(new OrderDetail(1, productRepository.findProductById(2), orderRepository.findOrderById(2), sizeRepository.findSizeById(5), colorRepository.findColorById(9)));
            orderDetailRepository.save(new OrderDetail(4, productRepository.findProductById(2), orderRepository.findOrderById(3), sizeRepository.findSizeById(5), colorRepository.findColorById(9)));
            orderDetailRepository.save(new OrderDetail(1, productRepository.findProductById(3), orderRepository.findOrderById(4), sizeRepository.findSizeById(6), colorRepository.findColorById(7)));
            orderDetailRepository.save(new OrderDetail(5, productRepository.findProductById(4), orderRepository.findOrderById(5), sizeRepository.findSizeById(7), colorRepository.findColorById(2)));
            orderDetailRepository.save(new OrderDetail(2, productRepository.findProductById(6), orderRepository.findOrderById(5), sizeRepository.findSizeById(5), colorRepository.findColorById(2)));
            orderDetailRepository.save(new OrderDetail(1, productRepository.findProductById(5), orderRepository.findOrderById(4), sizeRepository.findSizeById(8), colorRepository.findColorById(8)));
            orderDetailRepository.save(new OrderDetail(2, productRepository.findProductById(5), orderRepository.findOrderById(1), sizeRepository.findSizeById(8), colorRepository.findColorById(8)));
        }

        if(reviewRepository.findAll().size() == 0){
            reviewRepository.save(new Review( 5, "Zeer leuke sleutelhanger", "Hangt heel mooi aan mijn sleutelbundel. Lief en zacht!", customerRepository.findById(1)));
            reviewRepository.save(new Review(3.5, "Mooi", "", customerRepository.findById(2)));
            reviewRepository.save(new Review( 5, "Zeer leuke knuffel", "", customerRepository.findById(1)));
            reviewRepository.save(new Review(3.5, "Mooi", "", customerRepository.findById(2)));
            reviewRepository.save(new Review(3.5, "De beer", "Beetje lelijk maar heeft een groot hart", customerRepository.findById(2)));
            reviewRepository.save(new Review(4.5, "Schrijft goed!", "", customerRepository.findById(3)));
            reviewRepository.save(new Review(5, "Stevige kop.", "Perfect om de ochtend mee te beginnen, warmte blijft goed binnen de koffiekop.", customerRepository.findById(3)));
        }

        if(interactionRepository.findAll().size() == 0){
            interactionRepository.save(new Interaction(4, 3, 1, reviewRepository.findReviewById(1), productRepository.findProductById(1), customerRepository.findById(1)));
            interactionRepository.save(new Interaction(3, 2, 2, reviewRepository.findReviewById(3), productRepository.findProductById(1), customerRepository.findById(1)));
            interactionRepository.save(new Interaction(6, 0, 0, reviewRepository.findReviewById(2), productRepository.findProductById(2), customerRepository.findById(2)));
            interactionRepository.save(new Interaction(2, 1, 1, reviewRepository.findReviewById(5), productRepository.findProductById(3), customerRepository.findById(2)));
            interactionRepository.save(new Interaction(8, 2, 0, reviewRepository.findReviewById(6), productRepository.findProductById(4), customerRepository.findById(3)));
            interactionRepository.save(new Interaction(3, 1,0, reviewRepository.findReviewById(7), productRepository.findProductById(6), customerRepository.findById(2)));
        }
    }
}
