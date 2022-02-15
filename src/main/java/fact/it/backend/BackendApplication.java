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
        //Test
        if(categoryRepository.findAll().size() == 0){
            categoryRepository.save(new Category("Knuffels"));
            categoryRepository.save(new Category("Sleutelhangers"));
            categoryRepository.save(new Category("Pennen"));
            categoryRepository.save(new Category("Shirts mannen"));
            categoryRepository.save(new Category("Shirts vrouwen"));
            categoryRepository.save(new Category("Koffiekoppen"));
            categoryRepository.save(new Category("Drinkbussen"));
            categoryRepository.save(new Category("Mondmaskers"));
            categoryRepository.save(new Category("Kaarsen en geuren"));
        }

        if(colorRepository.findAll().size() == 0){
            colorRepository.save(new Color("Rood"));
            colorRepository.save(new Color("Wit"));
            colorRepository.save(new Color("Groen"));
            colorRepository.save(new Color("Bruin"));
            colorRepository.save(new Color("Geel"));
            colorRepository.save(new Color("Roze"));
            colorRepository.save(new Color("Beige"));
            colorRepository.save(new Color("Mix"));
            colorRepository.save(new Color("Zwart en wit"));
        }

        String password = passwordEncoder.encode("Password123");

        if(customerRepository.findAll().size() == 0){
            customerRepository.save(new Customer("giannideherdt@gmail.com", password, "0479994529", "Belgium", "2200", "Kersstraat 17", Role.ADMIN, "Gianni" , "De Herdt"));
            customerRepository.save(new Customer("thijswouters@gmail.com", password, "0479954719", "Belgium", "1680", "Hoekstraat 165", Role.ADMIN, "Thijs" , "Wouters"));
            customerRepository.save(new Customer("jolienfoets@gmail.com", password, "0466544922", "Belgium", "1700", "Stepelaar 6A", Role.CUSTOMER, "Jolien" , "Foets"));
            customerRepository.save(new Customer("boblourdaux@gmail.com", password, "0495946569", "Belgium", "3040", "Sint-Schepersberg 45", Role.CUSTOMER, "Bob" , "Lourdaux"));
            customerRepository.save(new Customer("kevinmaes@gmail.com", password, "0476281912", "Belgium", "2260", "Lambertuslaan 42", Role.CUSTOMER, "Kevin" , "Maes"));
            customerRepository.save(new Customer("helderceyssens@gmail.com", password, "0476596168", "Belgium", "1540", "Koepel 186", Role.CUSTOMER, "Helder" , "Ceyssens"));
            customerRepository.save(new Customer("brent.eerlingen@elision.be", password, "+32011192819", "Belgium", "3800", "Steverstraat 56", Role.CUSTOMER, "Brent" , "Eerlingen"));
        }

        if(organizationRepository.findAll().size() == 0){
            organizationRepository.save(new Organization("supporters@wwf.be", password, "+32023400922", "Belgium", "1000", "Emile Jacqmainlaan 90", Role.ORGANIZATION,"WWF", "0123384623", "BE0408656248", "Sinds de oprichting in 1966 is WWF-België één van de belangrijkste natuurbeschermingsorganisaties in ons land. Als lid van het wereldwijde WWF-netwerk nemen we deel aan grote nationale en internationale projecten om de natuur te beschermen en te zorgen voor een duurzame toekomst voor de generaties na ons.", "Onze slogan ‘Together Possible!’ belichaamt onze werkstrategie en onze visie op een planeet waar mens en natuur in harmonie leven. WWF is afhankelijk van de steun van donateurs en donatrices, en van de samenwerking met lokale gemeenschappen, jonge generaties, private en publieke partners om duurzame natuurbeschermingsoplossingen te vinden. Alleen samen kunnen we beschermen wat ons in leven houdt: bossen, oceaan, zoet water, fauna en flora.", "WWF zet zich in om de achteruitgang van de natuur op onze planeet te stoppen en om te bouwen aan een toekomst waar de mens in harmonie leeft met de natuur.", "+32023400922", "supporters@wwf.be", "https://adfinitas-statics-cdn.s3.eu-west-3.amazonaws.com/wwf/defisc-20/logo.jpg"));
            organizationRepository.save(new Organization("info@bkks.be", password, "+32011192819", "Belgium", "3800", "Diesterstraat 67A bus 001", Role.ORGANIZATION,"Belgisch KinderKanker Steunfonds", "0123321123", "BE0835627680", "Het Belgisch Kinder Kanker Steunfonds realiseert dromen en wensen van kinderen en betrekt het ganse gezin hierbij. Niet alleen het zieke kindje maar ook broers of zusjes en de ouders worden op deze speciale dag niet vergeten.", "Het Belgisch Kinder Kanker Steunfonds bezoekt regelmatig ziekenhuizen waar we dan alle aanwezige kinderen verwennen met speelgoed. En/of we schenken materiaal aan de afdeling. We denken ook aan het verplegend personeel en brengen voor hen ook een lekkere, kleine attentie mee. Want zij zorgen dag in, dag uit met heel veel liefde en toewijding voor al deze kinderen", "Zelfs in een land als België is het mogelijk om als gezin in financiële problemen te geraken na langdurige ziekte zoals kanker. Wij geven aan deze gezinnen dan financiële steun om deze moeilijke periode door te komen en op die manier al wat financiële zorgen en stress weg te nemen. Dit kunnen we mogelijk maken dankzij de “Vrienden van Bkks”", "0479695051", "supporters@wwf.be", "https://belgischkinderkankersteunfonds.be/wp-content/uploads/2020/02/cropped-logo-bkks-1.png"));
            organizationRepository.save(new Organization("welkom@bzn.be", password, "+32032012210", "Belgium", "2100", "Herentalsebaan 74", Role.ORGANIZATION,"Bond Zonder Naam", "0123321623", "BE0469514642", "De Bond zonder Naam (BZN) is een Vlaamse maatschappijkritische beweging die vooral bekend is van de gratis maandspreuken die uitnodigen tot reflectie, verdieping en verandering. Uiterlijk, interesses, gewoonten, leeftijden, culturen of nationaliteiten: het zijn stuk voor stuk verschillen tussen mensen die we soms als grenzen ervaren. Bond zonder Naam wil deze grenzen overbruggen en ook het omgaan met diversiteit stimuleren opdat samen leven echt samenleven wordt, met respect voor diversiteit en ieders kwaliteit.", "Bond zonder Naam schreef voor de periode 2016-2020 een beleidsplan over onze visie, acties en campagnes, vormingsaanbod en vrijwilligerswerk. Wat heeft Bond zonder Naam nog in petto? Waar zien we groeikansen? Welke pijnpunten in onze samenleving willen we blootleggen en aanpakken? We lieten ons inspireren door het verhaal van bevriende organisaties, experten, leden en vrijwilligers. Telkens staan de meest kwetsbaren onder ons centraal: mensen in armoede, vluchtelingen, chronische zieken of mensen aan de buitenste rand van de samenleving. Iedereen verdient een betekenis- en hoopvol leven. Wij weten wat ons te doen staat.", "Bond zonder Naam wil zoveel mogelijk mensen vanuit het hart aanspreken en met elkaar verbinden. En zo een samenleving creëren die een warm verschil maakt, vooral voor wie minder kansen krijgt. Concreet willen we dit waarmaken door mensen te inspireren en helpen in hun groei. Want hoe meer inzicht we in onszelf en de wereld hebben, hoe meer we elkaar te bieden hebben. Dit engagement maken we hard voor en met mensen in kansarmoede, eenzaamheid of gevangenschap.", "+32479875051", "supporters@wwf.be", "https://www.bzn.be/graphics/default-socialmedia.jpg"));
            organizationRepository.save(new Organization("maite@think-pink.be", password, "+32475406602", "Belgium", "1070", "Researchdreef 12", Role.ORGANIZATION,"Think Pink", "0183321123", "BE0810893274", "Think Pink is de nationale borstkankerorganisatie die zich dagelijks inzet voor borstkankerpatiënten en hun familie.\n" +
                    "\n" +
                    "Think Pink heeft vier centrale doelstellingen:\n" +
                    "\n" +
                    "informeren en sensibiliseren\n" +
                    "patiëntenrechten verdedigen \n" +
                    "financieren van wetenschappelijk onderzoek\n" +
                    "ondersteunen van zorg- en nazorgprojecten", "Borstkankerpatiënten hebben een basisrecht op de best mogelijke zorg. Wij willen elke dag opnieuw blijven inzetten voor een nog betere behandeling, informatieverstrekking, zorg en nazorg. Ook de financiële impact voor lotgenoten en hun familie is voor ons van groot belang.", "Dankzij donaties en fondsenwervingsacties konden wij de afgelopen jaren onderzoek naar een revolutionaire radiotherapiebehandeling bij borstkanker steunen. De nieuwe techniek kan jaarlijks tot 20 levens redden én zware hartaanvallen of longkankersymptomen bij tot wel 100 patiënten helpen voorkomen. Crawlbuikbestraling is een feit. Wij willen ons blijven inzetten voor deze revolutionaire behandeling. Daarom financieren we de komende twee jaar de opleidingen voor zorgpersoneel in Belgische radiotherapiecentra.", "+32479675051", "info@think-pink.be", "https://upload.wikimedia.org/wikipedia/commons/4/4b/Logo_think-pink.jpg"));
            organizationRepository.save(new Organization("info@damiaanactie.be", password, "+32024225911", "Belgium", "1081", "Leopold II-laan 263", Role.ORGANIZATION,"Damiaanactie", "1123321123", "BE0406694670", "Damiaanactie is een Belgische medische non-profitorganisatie die zich inzet voor mensen met lepra, tuberculose en andere ziektes die vooral de kwetsbaarste bevolkingsgroepen treffen.", "Om lepra, tbc en leishmaniasis voorgoed te bestrijden zetten wij actief in op de volgende domeinen:\n" +
                    "Medische hulp\n" +
                    "Actieve opsporing van getroffen personen\n" +
                    "Informeren en sensibiliseren\n" +
                    "Opleidingen\n" +
                    "Onderzoek\n" +
                    "Steunen met kennis en materiaal\n" +
                    "Care after Cure", "Bij Damiaanactie zijn de vrijwilligersinitiatieven gevarieerd en er is geen vast aantal uren. Of je nu één uur of het hele jaar door wil meedoen, je vindt altijd een formule die bij jou past. Voor al deze missies zullen onze vrijwilligerscoaches jouw bevoorrechte aanspreekpunt zijn voor een hechte en menselijke relatie.", "+32024225911", "info@damiaanactie.be", "https://damiaanactie.be/wp-content/uploads/2019/10/RGB-LOGO-DA-NL-transparant.png"));
            organizationRepository.save(new Organization("info@rodekruis.be", password, "+32154433228", "Belgium", "2800", "Motstraat 40", Role.ORGANIZATION,"Rode Kruis Vlaanderen", "1122321123", "BE0455024129", "Wij zijn een onafhankelijke vrijwilligersorganisatie. Via het Belgische Rode Kruis maken we deel uit van de Internationale Rode Kruis- en Rode Halve Maanbeweging. Onze missie is drieledig:\n" +
                    "\n" +
                    "Opkomen voor kwetsbare mensen in binnen- en buitenland.\n" +
                    "Actief zijn op het vlak van rampenbestrijding, zelfredzaamheid en bloedvoorziening.\n" +
                    "Bij dat alles doen we maximaal een beroep op vrijwilligers.", "Hulp verlenen bij rampen is één van onze basisopdrachten. Als partner van de overheid maken we deel uit van de algemene nood- en interventieplanning van de medische, sanitaire en psychosociale hulpverlening en nemen bij een ramp 3 belangrijke taken op ons: medische hulpverlening, vervoer van gewonden en psychosociale ondersteuning. ", "Bij een ramp beschikken de gewone hulpdiensten niet altijd over voldoende middelen. Onze SIT’s (Snel Interventie Team) springen hen meteen bij. Ze brengen alle materiaal mee om snel een medische post op te bouwen: opblaasbare tenten, verlichting, draagberries, medisch materiaal en spoedmedicatie. Onder leiding van artsen en verpleegkundigen bieden we gewonden de eerste zorgen. ", "+32015443322", "info@rodekruis.be", "https://www.rodekruis.be/img/logo.svg?1557833551"));
            organizationRepository.save(new Organization("elision@gmail.com", password, "+32011771725", "Belgium", "3500", "Kempische Steenweg 311, Verdieping 5", Role.ADMIN,"Elision", "1123921123", "BE0884167272", "", "", "", "+32011771725", "elision@gmail.com", "https://pbs.twimg.com/ext_tw_video_thumb/1229311494317277186/pu/img/8I5awCeVJQnLTiwL?format=jpg&name=large"));

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
        }

        if(productRepository.findAll().size() == 0){
            productRepository.save(new Product("WWF sleutelhanger orang-oetan", "Een schattige orang-oetan als sleutelhanger van 10cm.", 10.95, true, Arrays.asList("https://www.wwf.nl/globalassets/commerce/productafbeeldingen/wwf-sleutelhanger-orang-oetan-01.jpg", "https://www.wwf.nl/globalassets/commerce/productafbeeldingen/wwf-sleutelhanger-orang-oetan-02.jpg"), categoryRepository.findCategoryById(2), organizationRepository.findOrganizationById(8)));
            productRepository.save(new Product("WWF sleutelhanger panda", "De enige echte WWF-panda als sleutelhanger van 10cm!", 10.95, true, Arrays.asList("https://www.wwf.nl/globalassets/commerce/productafbeeldingen/wwf-sleutelhanger-panda-01.jpg", "https://www.wwf.nl/globalassets/commerce/productafbeeldingen/wwf-sleutelhanger-panda-02.jpg"), categoryRepository.findCategoryById(2), organizationRepository.findOrganizationById(8)));
            productRepository.save(new Product("Lief beertje Rode Kruis", "Slaap goed met de officiële beertjes-knuffel van het Rode Kruis!", 6.00, true, Arrays.asList("https://www.company2wear.nl/media/catalog/product/cache/1/image/9df78eab33525d08d6e5fb8d27136e95/b/e/beertje_borstlogo.jpg"), categoryRepository.findCategoryById(1), organizationRepository.findOrganizationById(13)));
            productRepository.save(new Product("Balpen blauw", "Steun het Rode Kruis door het aankopen en gebruiken van de officiële balpennen!", 3.5, true, Arrays.asList("https://shop.rodekruis.nl/pub/media/catalog/product/cache/c3031995953b3e91d5674d5b0a0af4b5/n/r/nrk.5011_1.jpg", "https://webcat.staci.com/WebCat3/custom/0231/_default//static/produits/P00012_MD.jpg"), categoryRepository.findCategoryById(3), organizationRepository.findOrganizationById(13)));
            productRepository.save(new Product("Stiften damiaanactie", "Schrijf leprapatiënten niet af. Koop of verkoop de gekende rode, blauwe, groene en zwarte Damiaanactiestiften. Een pakje van vier kost €7.", 7.00, true, Arrays.asList("https://damiaanactie-shop.be/wp-content/uploads/2020/11/damien-eshop-042.jpg", "https://damiaanactie-shop.be/wp-content/uploads/2020/11/damien-eshop-046.jpg", "https://damiaanactie-shop.be/wp-content/uploads/2020/11/damien-eshop-052.jpg", "https://damiaanactie-shop.be/wp-content/uploads/2020/11/damien-eshop-053.jpg", "https://damiaanactie-shop.be/wp-content/uploads/2020/11/damien-eshop-054.jpg", "https://damiaanactie-shop.be/wp-content/uploads/2020/11/damien-eshop-055.jpg"), categoryRepository.findCategoryById(3), organizationRepository.findOrganizationById(12)));
            productRepository.save(new Product("Koffiemok bedrukt","Steun het Rode Kruis door het aankopen en gebruiken van de officiële koffiemokken!", 6.5, true, Arrays.asList("https://i.etsystatic.com/26520550/r/il/72e51c/3329982877/il_340x270.3329982877_kdtx.jpg"),  categoryRepository.findCategoryById(6), organizationRepository.findOrganizationById(13)));
            productRepository.save(new Product("Solidair mondmasker wit","Draag met trots de vlinders van Damiaanactie, terwijl je jezelf en anderen rondom je beschermt. Alle opbrengst gaat rechtstreeks naar onze projecten.", 8.00, true, Arrays.asList("https://damiaanactie-shop.be/wp-content/uploads/2021/12/Ontwerp-zonder-titel-3.jpg"),  categoryRepository.findCategoryById(8), organizationRepository.findOrganizationById(12)));
            productRepository.save(new Product("Solidair mondmasker groen","Trotseer de koude winterdagen met deze groene maskers van Damiaanactie. Vrolijke mondmaskers die deze sombere dagen wat kleur geven en opwarmen.", 8.00, true, Arrays.asList("https://damiaanactie-shop.be/wp-content/uploads/2021/01/masque-damien-240web.jpg"),  categoryRepository.findCategoryById(8), organizationRepository.findOrganizationById(12)));
            productRepository.save(new Product("2 solidaire mondmaskers", "Draag met trots de vlinders van Damiaanactie, terwijl je jezelf en anderen rondom je beschermt. Alle opbrengst gaat rechtstreeks naar onze projecten (€ 15  voor 2 mondmaskers).", 15.00, true, Arrays.asList("https://damiaanactie-shop.be/wp-content/uploads/2021/12/Ontwerp-zonder-titel-2.jpg"), categoryRepository.findCategoryById(8), organizationRepository.findOrganizationById(12)));
            productRepository.save(new Product("Run for Think Pink T-shirt heren (wit)", "Net zoals bij elke sport, hoort ook bij het lopen een juiste outfit. Vertegenwoordig met dit T-shirt Think Pink op elk evenement of draag hem tijdens het trainen.", 9.99, true, Arrays.asList("https://www.think-pink.be/Portals/0/dtxArt/blok-afbeelding/bestand/20190820Think-Pink-2019Jeroen-Willems102-2_medium_1ba503c4-ce81-4f84-abbe-c5f02cef0356.jpg", "https://www.think-pink.be/Portals/0/dtxArt/blok-galerij/afbeelding/bestand/image_groot_ac0905cd-8657-4133-b05f-703e783572ba.png"), categoryRepository.findCategoryById(4), organizationRepository.findOrganizationById(11)));
            productRepository.save(new Product("Run for Think Pink T-shirt dames (roze)", "Net zoals bij elke sport, hoort ook bij het lopen een juiste outfit. Vertegenwoordig met dit T-shirt Think Pink op elk evenement of draag hem tijdens het trainen.", 9.99, true, Arrays.asList("https://www.think-pink.be/Portals/0/dtxArt/blok-afbeelding/bestand/6932E44F-510E-4C52-B60D-27AA69CD83A4_medium_6262025e-4c7e-468e-a0a2-2cb0237d0050.jpg"), categoryRepository.findCategoryById(5), organizationRepository.findOrganizationById(11)));
            productRepository.save(new Product("Drinkbus Think Pink","Deze leuke Think Pink-drinkbus zorgt voor de nodige verfrissing tijdens het sporten", 4.99, true, Arrays.asList("https://www.think-pink.be/Portals/0/dtxArt/blok-galerij/afbeelding/bestand/Drinkbus1_groot_3918fe9e-785f-4dd5-a652-071148ed6146.jpg", "https://www.think-pink.be/Portals/0/dtxArt/blok-galerij/afbeelding/bestand/DSC1350groot6936867c-357f-4227-890b-58c5_groot_3dd61d86-f996-49ab-ac4a-6be2443b20a5.jpeg"), categoryRepository.findCategoryById(7), organizationRepository.findOrganizationById(11)));
            productRepository.save(new Product("Geurkaars Bond zonder Naam", "Geurkaars ter ondersteuning van de Bond zonder Naam", 8.00, true, Arrays.asList("https://i.imgur.com/9uiTrqz.png"), categoryRepository.findCategoryById(9), organizationRepository.findOrganizationById(10)));
            productRepository.save(new Product("Geurstokjes Bond zonder Naam", "Geurstokjes ter ondersteuning van de Bond zonder Naam",  5.00,  true, Arrays.asList("https://i.imgur.com/fW2Jkm2.png"),categoryRepository.findCategoryById(9), organizationRepository.findOrganizationById(10)));
            productRepository.save(new Product("Sojakaars Bond zonder Naam", "Sojakaars ter ondersteuning van de Bond zonder Naam", 10.00, true, Arrays.asList("https://i.imgur.com/aOWdepM.png"),categoryRepository.findCategoryById(9), organizationRepository.findOrganizationById(10)));
            productRepository.save(new Product("3 stylo's Belgisch KinderKanker Fonds", "3 pennen met het logo van het Belgisch KinderKanker Fonds.", 6, true, Arrays.asList("https://www.online-fanshop.be/wp-content/uploads/2020/11/stylo-bkks.png"), categoryRepository.findCategoryById(3),organizationRepository.findOrganizationById(9)));
            productRepository.save(new Product("Mondmasker Belgisch KinderKanker Fonds", "Mondmasker met het logo van het Belgisch KinderKanker Fonds.",  8.25, true, Arrays.asList("https://www.online-fanshop.be/wp-content/uploads/2020/11/mondmasker-BKKS.png"), categoryRepository.findCategoryById(8), organizationRepository.findOrganizationById(9)));
            productRepository.save(new Product("Stanno Pride T-Shirt", "Dit stretchy sportshirt, onderdeel van de Stanno Pride collectie, is voorzien van het logo van BKKS.", 24.99,  true, Arrays.asList("https://i.imgur.com/dLA2Rab.png"), categoryRepository.findCategoryById(4), organizationRepository.findOrganizationById(9)));
        }

        if(stockRepository.findAll().size() == 0){
            stockRepository.save(new Stock(50, sizeRepository.findSizeById(5), colorRepository.findColorById(4), productRepository.findProductById(1)));
            stockRepository.save(new Stock(20,sizeRepository.findSizeById(8), colorRepository.findColorById(4), productRepository.findProductById(1)));
            stockRepository.save(new Stock(50, sizeRepository.findSizeById(8), colorRepository.findColorById(9), productRepository.findProductById(2)));
            stockRepository.save(new Stock(200, sizeRepository.findSizeById(8), colorRepository.findColorById(2), productRepository.findProductById(4)));
            stockRepository.save(new Stock(100, sizeRepository.findSizeById(8), colorRepository.findColorById(8), productRepository.findProductById(5)));
            stockRepository.save(new Stock(50, sizeRepository.findSizeById(5), colorRepository.findColorById(2), productRepository.findProductById(6)));
            stockRepository.save(new Stock(200, sizeRepository.findSizeById(2), colorRepository.findColorById(2), productRepository.findProductById(7)));
            stockRepository.save(new Stock(150, sizeRepository.findSizeById(2), colorRepository.findColorById(3), productRepository.findProductById(8)));
            stockRepository.save(new Stock(200, sizeRepository.findSizeById(2), colorRepository.findColorById(2), productRepository.findProductById(9)));
            stockRepository.save(new Stock(25, sizeRepository.findSizeById(2), colorRepository.findColorById(2), productRepository.findProductById(10)));
            stockRepository.save(new Stock(50, sizeRepository.findSizeById(3), colorRepository.findColorById(2), productRepository.findProductById(10)));
            stockRepository.save(new Stock(50, sizeRepository.findSizeById(2), colorRepository.findColorById(6), productRepository.findProductById(11)));
            stockRepository.save(new Stock(40, sizeRepository.findSizeById(1), colorRepository.findColorById(6), productRepository.findProductById(11)));
            stockRepository.save(new Stock(80, sizeRepository.findSizeById(9), colorRepository.findColorById(6), productRepository.findProductById(12)));
            stockRepository.save(new Stock(40, sizeRepository.findSizeById(10), colorRepository.findColorById(1), productRepository.findProductById(13)));
            stockRepository.save(new Stock(30, sizeRepository.findSizeById(8), colorRepository.findColorById(5), productRepository.findProductById(14)));
            stockRepository.save(new Stock(20, sizeRepository.findSizeById(10), colorRepository.findColorById(2), productRepository.findProductById(15)));
            stockRepository.save(new Stock(60, sizeRepository.findSizeById(8), colorRepository.findColorById(2), productRepository.findProductById(16)));
            stockRepository.save(new Stock(25, sizeRepository.findSizeById(2), colorRepository.findColorById(2), productRepository.findProductById(17)));
            stockRepository.save(new Stock(15, sizeRepository.findSizeById(1), colorRepository.findColorById(9), productRepository.findProductById(18)));
            stockRepository.save(new Stock(30, sizeRepository.findSizeById(2), colorRepository.findColorById(9), productRepository.findProductById(18)));
            stockRepository.save(new Stock(30, sizeRepository.findSizeById(3), colorRepository.findColorById(9), productRepository.findProductById(18)));
        }

        if(orderDetailRepository.findAll().size() == 0){
            orderDetailRepository.save(new OrderDetail(2, productRepository.findProductById(1), orderRepository.findOrderById(1), sizeRepository.findSizeById(7), colorRepository.findColorById(7)));
            orderDetailRepository.save(new OrderDetail(1, productRepository.findProductById(3), orderRepository.findOrderById(1), sizeRepository.findSizeById(8), colorRepository.findColorById(8)));
            orderDetailRepository.save(new OrderDetail(1, productRepository.findProductById(2), orderRepository.findOrderById(2), sizeRepository.findSizeById(5), colorRepository.findColorById(5)));
            orderDetailRepository.save(new OrderDetail(4, productRepository.findProductById(2), orderRepository.findOrderById(3), sizeRepository.findSizeById(5), colorRepository.findColorById(5)));
            orderDetailRepository.save(new OrderDetail(1, productRepository.findProductById(3), orderRepository.findOrderById(4), sizeRepository.findSizeById(6), colorRepository.findColorById(6)));
            orderDetailRepository.save(new OrderDetail(5, productRepository.findProductById(4), orderRepository.findOrderById(5), sizeRepository.findSizeById(7), colorRepository.findColorById(7)));
            orderDetailRepository.save(new OrderDetail(2, productRepository.findProductById(6), orderRepository.findOrderById(5), sizeRepository.findSizeById(5), colorRepository.findColorById(5)));
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
            interactionRepository.save(new Interaction(6, 0, 0, reviewRepository.findReviewById(2), productRepository.findProductById(2), customerRepository.findById(2)));
            interactionRepository.save(new Interaction(2, 1, 1, reviewRepository.findReviewById(5), productRepository.findProductById(3), customerRepository.findById(2)));
            interactionRepository.save(new Interaction(8, 2, 0, reviewRepository.findReviewById(6), productRepository.findProductById(4), customerRepository.findById(3)));
            interactionRepository.save(new Interaction(3, 1,0, reviewRepository.findReviewById(7), productRepository.findProductById(6), customerRepository.findById(2)));
        }
    }
}
