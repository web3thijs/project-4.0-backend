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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

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
    @Autowired
    UserRepository userRepository;

    @PostConstruct
    public void fillDB(){
        Category categoryKnuffels = new Category("knuffels");
        Category categorySleutelhangers = new Category("sleutelhangers");
        Category categoryPennen = new Category("pennen");
        Category categoryShirtsMannen = new Category("shirts mannen");
        Category categoryShirtsVrouwen = new Category("shirts vrouwen");
        Category categoryKoffieKoppen = new Category("koffiekoppen");
        Category categoryDrinkbussen = new Category("drinkbussen");
        Category categoryMondmaskers = new Category("mondmaskers");
        Color colorRood = new Color("rood");
        Color colorWit = new Color("wit");
        Color colorGroen = new Color("groen");
        Color colorBruin = new Color("bruin");
        Color colorRoze = new Color("roze");
        Color colorBeige = new Color("beige");
        Color colorMix = new Color("mix");
        Color colorZwartWit = new Color("zwart en wit");
        Size sizeSmall = new Size("Small");
        Size sizeMedium = new Size("Medium");
        Size sizeLarge = new Size("Large");
        Size sizeExtraLarge = new Size("ExtraLarge");
        Size size10cm = new Size("10 cm");
        Size size11cm = new Size("11 cm");
        Size size14cm = new Size("14 cm");
        Size size15cm = new Size("15 cm");
        Size size05l = new Size("0,5 liter");
        Review reviewGianniDeHerdtSleutelhanger = new Review( 5, "Zeer leuke sleutelhanger", "Hangt heel mooi aan mijn sleutelbundel. Lief en zacht!");
        Review reviewGianniDeHerdtOrangoetanKnuffel = new Review( 5, "Zeer leuke knuffel", "");
        Review reviewThijsWoutersPandaSleutelhanger = new Review(3.5, "Mooi", "");
        Review reviewThijsWoutersBeertjeRK = new Review(3.5, "De beer", "Beetje lelijk maar heeft een groot hart");
        Review reviewJolienFoetsPen = new Review(4.5, "Schrijft goed!", "");
        Review reviewJolienFoetsKoffieKop = new Review(5, "Stevige kop.", "Perfect om de ochtend mee te beginnen, warmte blijft goed binnen de koffiekop.");
        Customer customerGianniDeHerdt = new Customer("giannidh@gmail.com", "password123", "0479994529", "Kersstraat 17", "2200", "Belgium", Role.CUSTOMER,"Gianni" , "De Herdt", false);
        Customer customerThijsWouters = new Customer("thijswouters@gmail.com", "password123", "0479954719", "Hoekstraat 165", "1680", "Belgium", Role.CUSTOMER,"Thijs" , "Wouters", true);
        Customer customerJolienFoets = new Customer("jolienfoets@gmail.com", "jolien123", "0476584982", "Stepelaar 6A", "1700", "Belgium", Role.CUSTOMER,"Jolien" , "Foets", false);
        Organization organizationWWF = new Organization("supporters@wwf.be", "wwf123", "+3223400920", "Emile Jacqmainlaan 90", "1000", "Belgium", Role.ORGANIZATION, "WWF", "BE0408656248", "BE0408656248", "Sinds de oprichting in 1966 is WWF-België één van de belangrijkste natuurbeschermingsorganisaties in ons land. Als lid van het wereldwijde WWF-netwerk nemen we deel aan grote nationale en internationale projecten om de natuur te beschermen en te zorgen voor een duurzame toekomst voor de generaties na ons.", "Onze slogan ‘Together Possible!’ belichaamt onze werkstrategie en onze visie op een planeet waar mens en natuur in harmonie leven. WWF is afhankelijk van de steun van donateurs en donatrices, en van de samenwerking met lokale gemeenschappen, jonge generaties, private en publieke partners om duurzame natuurbeschermingsoplossingen te vinden. Alleen samen kunnen we beschermen wat ons in leven houdt: bossen, oceaan, zoet water, fauna en flora.", "WWF zet zich in om de achteruitgang van de natuur op onze planeet te stoppen en om te bouwen aan een toekomst waar de mens in harmonie leeft met de natuur.", "+3223400920", "supporters@wwf.be", Arrays.asList("https://adfinitas-statics-cdn.s3.eu-west-3.amazonaws.com/wwf/defisc-20/logo.jpg"));
        Organization organizationThinkPink = new Organization("maite@think-pink.be", "maite123", "+32475406602", "Researchdreef 12", "1070", "Belgium", Role.ORGANIZATION, "Think Pink", "0810893274", "BE0810893274", "Think Pink is de nationale borstkankerorganisatie die zich dagelijks inzet voor borstkankerpatiënten en hun familie.\n" +
                "\n" +
                "Think Pink heeft vier centrale doelstellingen:\n" +
                "\n" +
                "informeren en sensibiliseren\n" +
                "patiëntenrechten verdedigen \n" +
                "financieren van wetenschappelijk onderzoek\n" +
                "ondersteunen van zorg- en nazorgprojecten", "Borstkankerpatiënten hebben een basisrecht op de best mogelijke zorg. Wij willen elke dag opnieuw blijven inzetten voor een nog betere behandeling, informatieverstrekking, zorg en nazorg. Ook de financiële impact voor lotgenoten en hun familie is voor ons van groot belang.", "Dankzij donaties en fondsenwervingsacties konden wij de afgelopen jaren onderzoek naar een revolutionaire radiotherapiebehandeling bij borstkanker steunen. De nieuwe techniek kan jaarlijks tot 20 levens redden én zware hartaanvallen of longkankersymptomen bij tot wel 100 patiënten helpen voorkomen. Crawlbuikbestraling is een feit. Wij willen ons blijven inzetten voor deze revolutionaire behandeling. Daarom financieren we de komende twee jaar de opleidingen voor zorgpersoneel in Belgische radiotherapiecentra.", "+32475406602", "info@think-pink.be", Arrays.asList("https://upload.wikimedia.org/wikipedia/commons/4/4b/Logo_think-pink.jpg"));
        Organization organizationDamiaanactie = new Organization("info@damiaanactie.be", "damiaanactie123", "+3224225911", "Leopold II-laan 263", "1081", "Belgium", Role.ORGANIZATION, "Damiaanactie", "0406694670", "BE05000000007575", "Damiaanactie is een Belgische medische non-profitorganisatie die zich inzet voor mensen met lepra, tuberculose en andere ziektes die vooral de kwetsbaarste bevolkingsgroepen treffen.", "Om lepra, tbc en leishmaniasis voorgoed te bestrijden zetten wij actief in op de volgende domeinen:\n" +
                "Medische hulp\n" +
                "Actieve opsporing van getroffen personen\n" +
                "Informeren en sensibiliseren\n" +
                "Opleidingen\n" +
                "Onderzoek\n" +
                "Steunen met kennis en materiaal\n" +
                "Care after Cure", "Bij Damiaanactie zijn de vrijwilligersinitiatieven gevarieerd en er is geen vast aantal uren. Of je nu één uur of het hele jaar door wil meedoen, je vindt altijd een formule die bij jou past. Voor al deze missies zullen onze vrijwilligerscoaches jouw bevoorrechte aanspreekpunt zijn voor een hechte en menselijke relatie.", "+3224225911", "info@damiaanactie.be", Arrays.asList("https://damiaanactie.be/wp-content/uploads/2019/10/RGB-LOGO-DA-NL-transparant.png", "https://damiaanactie.be/wp-content/themes/action-damien/assets/img/logo.png"));
        Organization organizationRodeKruis = new Organization("info@rodekruis.be", "rodekruis123", "+3215443322", "Motstraat 40", "2800", "Belgium", Role.ORGANIZATION, "Rode Kruis Vlaanderen", "2154897956", "BE0455024129", "Wij zijn een onafhankelijke vrijwilligersorganisatie. Via het Belgische Rode Kruis maken we deel uit van de Internationale Rode Kruis- en Rode Halve Maanbeweging. Onze missie is drieledig:\n" +
                "\n" +
                "Opkomen voor kwetsbare mensen in binnen- en buitenland.\n" +
                "Actief zijn op het vlak van rampenbestrijding, zelfredzaamheid en bloedvoorziening.\n" +
                "Bij dat alles doen we maximaal een beroep op vrijwilligers.", "Hulp verlenen bij rampen is één van onze basisopdrachten. Als partner van de overheid maken we deel uit van de algemene nood- en interventieplanning van de medische, sanitaire en psychosociale hulpverlening en nemen bij een ramp 3 belangrijke taken op ons: medische hulpverlening, vervoer van gewonden en psychosociale ondersteuning. ", "Bij een ramp beschikken de gewone hulpdiensten niet altijd over voldoende middelen. Onze SIT’s (Snel Interventie Team) springen hen meteen bij. Ze brengen alle materiaal mee om snel een medische post op te bouwen: opblaasbare tenten, verlichting, draagberries, medisch materiaal en spoedmedicatie. Onder leiding van artsen en verpleegkundigen bieden we gewonden de eerste zorgen. ", "+3215443322", "info@rodekruis.be", Arrays.asList("https://www.rodekruis.be/img/logo.svg?1557833551"));
        Order order1GianniDeHerdt = new Order(customerGianniDeHerdt, new Date());
        Order order2GianniDeHerdt = new Order(customerGianniDeHerdt, new Date());
        Order order1ThijsWouters = new Order(customerThijsWouters, new Date());
        Order order2ThijsWouters = new Order(customerThijsWouters, new Date());
        Order orderJolienFoets = new Order(customerJolienFoets, new Date());
        Product productOrangoetanSleutelhanger = new Product(categorySleutelhangers, organizationWWF, "WWF sleutelhanger orang-oetan", 10.95, "Een schattige orang-oetan als sleutelhanger van 10cm.", true, Arrays.asList("https://www.wwf.nl/globalassets/commerce/productafbeeldingen/wwf-sleutelhanger-orang-oetan-01.jpg", "https://www.wwf.nl/globalassets/commerce/productafbeeldingen/wwf-sleutelhanger-orang-oetan-02.jpg"));
        Product productPandaSleutelhanger = new Product(categorySleutelhangers, organizationWWF, "WWF sleutelhanger panda", 10.95, "De enige echte WWF-panda als sleutelhanger van 10cm!", true, Arrays.asList("https://www.wwf.nl/globalassets/commerce/productafbeeldingen/wwf-sleutelhanger-panda-01.jpg", "https://www.wwf.nl/globalassets/commerce/productafbeeldingen/wwf-sleutelhanger-panda-02.jpg"));
        Product productOrangoetanKnuffel = new Product(categoryKnuffels, organizationWWF, "Orang-oetan light pakket", 30.00, "Adopteer (figuurlijk) een orang-oetan! Dit pakket bevat een 15cm grote orang-oeten-knuffel en een boekje met informatie over orang-oetans!", true, Arrays.asList("https://www.wwf.nl/globalassets/commerce/productafbeeldingen/adoptiepakket-light-orang-oetan.jpg", "https://www.wwf.nl/globalassets/commerce/productafbeeldingen/wwf-knuffel-orang-oetan-30-cm.jpg"));
        Product productBeertjeRK = new Product(categoryKnuffels, organizationRodeKruis, "Lief beertje Rode Kruis", 6.00, "Slaap goed met de officiële beertjes-knuffel van het Rode Kruis!", true, Arrays.asList("https://webcat.staci.com/WebCat3/custom/0231/_default//static/produits/P00042_MD.jpg"));
        Product productBalpenBlauw = new Product(categoryPennen, organizationRodeKruis, "Balpen blauw", 3.5, "Steun het Rode Kruis door het aankopen en gebruiken van de officiële balpennen!", true, Arrays.asList("https://shop.rodekruis.nl/pub/media/catalog/product/cache/c3031995953b3e91d5674d5b0a0af4b5/n/r/nrk.5011_1.jpg", "https://webcat.staci.com/WebCat3/custom/0231/_default//static/produits/P00012_MD.jpg"));
        Product productStiftenDA = new Product(categoryPennen, organizationDamiaanactie, "Stiften damiaanactie", 7.00, "Schrijf leprapatiënten niet af. Koop of verkoop de gekende rode, blauwe, groene en zwarte Damiaanactiestiften. Een pakje van vier kost €7.", true, Arrays.asList("https://damiaanactie-shop.be/wp-content/uploads/2020/11/damien-eshop-042.jpg", "https://damiaanactie-shop.be/wp-content/uploads/2020/11/damien-eshop-046.jpg", "https://damiaanactie-shop.be/wp-content/uploads/2020/11/damien-eshop-052.jpg", "https://damiaanactie-shop.be/wp-content/uploads/2020/11/damien-eshop-053.jpg", "https://damiaanactie-shop.be/wp-content/uploads/2020/11/damien-eshop-054.jpg", "https://damiaanactie-shop.be/wp-content/uploads/2020/11/damien-eshop-055.jpg"));
        Product productKoffieKopRK = new Product(categoryKoffieKoppen, organizationRodeKruis, "Koffiemok bedrukt", 6.5, "Steun het Rode Kruis door het aankopen en gebruiken van de officiële koffiemokken!", true, Arrays.asList("https://webcat.staci.com/WebCat3/custom/0231/_default//static/produits/007685_MD.jpg"));
        Product productMondmaskerWit = new Product(categoryMondmaskers, organizationDamiaanactie, "Solidair mondmasker wit", 8.00, "Draag met trots de vlinders van Damiaanactie, terwijl je jezelf en anderen rondom je beschermt. Alle opbrengst gaat rechtstreeks naar onze projecten.", true, Arrays.asList("https://damiaanactie-shop.be/wp-content/uploads/2021/12/Ontwerp-zonder-titel-3.jpg"));
        Product productMondmaskerGroen = new Product(categoryMondmaskers, organizationDamiaanactie, "Solidair mondmasker groen", 8.00, "Trotseer de koude winterdagen met deze groene maskers van Damiaanactie. Vrolijke mondmaskers die deze sombere dagen wat kleur geven en opwarmen.", true, Arrays.asList("https://damiaanactie-shop.be/wp-content/uploads/2021/01/masque-damien-240web.jpg"));
        Product product2Mondmaskers = new Product(categoryMondmaskers, organizationDamiaanactie, "2 solidaire mondmaskers", 15.00, "Draag met trots de vlinders van Damiaanactie, terwijl je jezelf en anderen rondom je beschermt. Alle opbrengst gaat rechtstreeks naar onze projecten (€ 15  voor 2 mondmaskers).", true, Arrays.asList("https://damiaanactie-shop.be/wp-content/uploads/2021/12/Ontwerp-zonder-titel-2.jpg"));
        Product productShirtMannenTP = new Product(categoryShirtsMannen, organizationThinkPink, "Run for Think Pink T-shirt heren (wit)", 9.99, "Net zoals bij elke sport, hoort ook bij het lopen een juiste outfit. Vertegenwoordig met dit T-shirt Think Pink op elk evenement of draag hem tijdens het trainen. Het loopshirt heeft een zacht aanvoelende stof en dankzij de Equarea-technologie en de verluchtingen onder de arme wordt de transpiratie perfect afgevoerd.", true, Arrays.asList("https://www.think-pink.be/Portals/0/dtxArt/blok-afbeelding/bestand/20190820Think-Pink-2019Jeroen-Willems102-2_medium_1ba503c4-ce81-4f84-abbe-c5f02cef0356.jpg", "https://www.think-pink.be/Portals/0/dtxArt/blok-galerij/afbeelding/bestand/image_groot_ac0905cd-8657-4133-b05f-703e783572ba.png"));
        Product productShirtVrouwenTP = new Product(categoryShirtsVrouwen, organizationThinkPink, "Run for Think Pink T-shirt dames (roze)", 9.99, "Net zoals bij elke sport, hoort ook bij het lopen een juiste outfit. Vertegenwoordig met dit T-shirt Think Pink op elk evenement of draag hem tijdens het trainen. Het loopshirt heeft een zacht aanvoelende stof en dankzij de Equarea-technologie en de verluchtingen onder de arme wordt de transpiratie perfect afgevoerd.", true, Arrays.asList("https://www.think-pink.be/Portals/0/dtxArt/blok-afbeelding/bestand/6932E44F-510E-4C52-B60D-27AA69CD83A4_medium_6262025e-4c7e-468e-a0a2-2cb0237d0050.jpg"));
        Product productDrinkbusTP = new Product(categoryDrinkbussen, organizationThinkPink, "Drinkbus Think Pink", 4.99, "Deze leuke Think Pink-drinkbus zorgt voor de nodige verfrissing tijdens het sporten", true, Arrays.asList("https://www.think-pink.be/Portals/0/dtxArt/blok-galerij/afbeelding/bestand/Drinkbus1_groot_3918fe9e-785f-4dd5-a652-071148ed6146.jpg", "https://www.think-pink.be/Portals/0/dtxArt/blok-galerij/afbeelding/bestand/DSC1350groot6936867c-357f-4227-890b-58c5_groot_3dd61d86-f996-49ab-ac4a-6be2443b20a5.jpeg"));


        if(categoryRepository.count()==0){
            categoryRepository.save(categoryKnuffels);
            categoryRepository.save(categorySleutelhangers);
            categoryRepository.save(categoryPennen);
            categoryRepository.save(categoryKoffieKoppen);
            categoryRepository.save(categoryShirtsMannen);
            categoryRepository.save(categoryShirtsVrouwen);
            categoryRepository.save(categoryDrinkbussen);
        }
        if(colorRepository.count() == 0){
            colorRepository.save(colorRood);
            colorRepository.save(colorGroen);
            colorRepository.save(colorBruin);
            colorRepository.save(colorRoze);
            colorRepository.save(colorWit);
            colorRepository.save(colorBeige);
            colorRepository.save(colorZwartWit);
        }
        if(userRepository.count() == 0){
            customerRepository.save(customerGianniDeHerdt);
            customerRepository.save(customerThijsWouters);
            customerRepository.save(customerJolienFoets);
            organizationRepository.save(organizationWWF);
            organizationRepository.save(organizationRodeKruis);
            organizationRepository.save(organizationDamiaanactie);
            organizationRepository.save(organizationThinkPink);
        }
        if(interactionRepository.count() == 0){
            interactionRepository.save(new Interaction(productOrangoetanSleutelhanger, customerGianniDeHerdt,reviewGianniDeHerdtSleutelhanger, 4));
            interactionRepository.save(new Interaction(productOrangoetanKnuffel, customerGianniDeHerdt,reviewGianniDeHerdtOrangoetanKnuffel, 3));
            interactionRepository.save(new Interaction(productPandaSleutelhanger, customerThijsWouters, reviewThijsWoutersPandaSleutelhanger, 6));
            interactionRepository.save(new Interaction(productBeertjeRK, customerThijsWouters, reviewThijsWoutersBeertjeRK, 2));
            interactionRepository.save(new Interaction(productBalpenBlauw, customerJolienFoets, reviewJolienFoetsPen, 8));
            interactionRepository.save(new Interaction(productKoffieKopRK, customerThijsWouters, reviewJolienFoetsKoffieKop, 3));
        }
        if(orderRepository.count()==0){
            orderRepository.save(order1GianniDeHerdt);
            orderRepository.save(order2GianniDeHerdt);
            orderRepository.save(order1ThijsWouters);
            orderRepository.save(order2ThijsWouters);
            orderRepository.save(orderJolienFoets);
        }
        if(orderDetailRepository.count()==0){
            orderDetailRepository.save(new OrderDetail(productOrangoetanSleutelhanger, order1GianniDeHerdt, size10cm, colorBruin, 2));
            orderDetailRepository.save(new OrderDetail(productOrangoetanKnuffel, order1GianniDeHerdt, size15cm, colorBruin, 1));
            orderDetailRepository.save(new OrderDetail(productPandaSleutelhanger, order2GianniDeHerdt, size10cm, colorZwartWit, 1));
            orderDetailRepository.save(new OrderDetail(productPandaSleutelhanger, order1ThijsWouters, size10cm, colorZwartWit, 4));
            orderDetailRepository.save(new OrderDetail(productBeertjeRK, order2ThijsWouters, size11cm, colorBeige, 1));
            orderDetailRepository.save(new OrderDetail(productBalpenBlauw, orderJolienFoets, size14cm, colorWit, 5));
            orderDetailRepository.save(new OrderDetail(productKoffieKopRK, orderJolienFoets, size10cm, colorWit, 2));
            orderDetailRepository.save(new OrderDetail(productStiftenDA, order2ThijsWouters, size15cm, colorMix, 1));
            orderDetailRepository.save(new OrderDetail(productStiftenDA, order1GianniDeHerdt, size15cm, colorMix, 2));

        }
        if(productRepository.count() == 0){
            productRepository.save(productOrangoetanSleutelhanger);
            productRepository.save(productPandaSleutelhanger);
            productRepository.save(productOrangoetanKnuffel);
            productRepository.save(productBalpenBlauw);
            productRepository.save(productStiftenDA);
            productRepository.save(productKoffieKopRK);
            productRepository.save(productMondmaskerWit);
            productRepository.save(productMondmaskerGroen);
            productRepository.save(product2Mondmaskers);
            productRepository.save(productShirtMannenTP);
            productRepository.save(productShirtVrouwenTP);
            productRepository.save(productDrinkbusTP);
        }
        if(reviewRepository.count() == 0){
            reviewRepository.save(reviewGianniDeHerdtSleutelhanger);
            reviewRepository.save(reviewGianniDeHerdtOrangoetanKnuffel);
            reviewRepository.save(reviewThijsWoutersPandaSleutelhanger);
            reviewRepository.save(reviewThijsWoutersBeertjeRK);
            reviewRepository.save(reviewJolienFoetsPen);
            reviewRepository.save(reviewJolienFoetsKoffieKop);
        }
        if(sizeRepository.count() == 0){
            sizeRepository.save(sizeSmall);
            sizeRepository.save(sizeMedium);
            sizeRepository.save(sizeLarge);
            sizeRepository.save(sizeExtraLarge);
            sizeRepository.save(size10cm);
            sizeRepository.save(size14cm);
            sizeRepository.save(size15cm);
            sizeRepository.save(size05l);
        }
        if(stockRepository.count() == 0){
            stockRepository.save(new Stock(size10cm, colorBruin, productOrangoetanSleutelhanger, 50));
            stockRepository.save(new Stock(size15cm, colorBruin, productOrangoetanKnuffel, 20));
            stockRepository.save(new Stock(size15cm, colorZwartWit, productPandaSleutelhanger, 50));
            stockRepository.save(new Stock(size15cm, colorWit, productBalpenBlauw, 200));
            stockRepository.save(new Stock(size15cm, colorMix, productStiftenDA, 100));
            stockRepository.save(new Stock(size10cm, colorWit, productKoffieKopRK, 50));
            stockRepository.save(new Stock(sizeMedium, colorWit, productMondmaskerWit, 200));
            stockRepository.save(new Stock(sizeMedium, colorGroen, productMondmaskerGroen, 150));
            stockRepository.save(new Stock(sizeMedium, colorWit, product2Mondmaskers, 200));
            stockRepository.save(new Stock(sizeMedium, colorWit, productShirtMannenTP, 25));
            stockRepository.save(new Stock(sizeLarge, colorWit, productShirtMannenTP, 50));
            stockRepository.save(new Stock(sizeMedium, colorRoze, productShirtVrouwenTP, 50));
            stockRepository.save(new Stock(size05l, colorRoze, productDrinkbusTP, 80));
        }

        System.out.println("DB test categories: " + categoryRepository.findAll().size() + " categories.");
        System.out.println("DB test colors: " + colorRepository.findAll().size() + " colors.");
        System.out.println("DB test sizes: " + sizeRepository.findAll().size() + " sizes.");
        System.out.println("DB test reviews: " + reviewRepository.findAll().size() + " reviews.");
        System.out.println("DB test customers: " + customerRepository.findByRole(Role.CUSTOMER).size() + " customers.");
        System.out.println("DB test organizations: " + organizationRepository.findByRole(Role.ORGANIZATION).size() + " organizations.");
        System.out.println("DB test orders: " + orderRepository.findAll().size() + " orders.");
        System.out.println("DB test orders: " + orderDetailRepository.findAll().size() + " orderdetails.");
        System.out.println("DB test products: " + productRepository.findAll().size() + " products.");
        System.out.println("DB test interactions: " + interactionRepository.findAll().size() + " interactions.");
        System.out.println("DB test stocks: " + stockRepository.findAll().size() + " stocks.");
    }
}
