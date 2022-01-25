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

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer customizer() {
        return builder -> builder.serializerByType(ObjectId.class, new ToStringSerializer());
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
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
    @Autowired
    DonationRepository donationRepository;

    @PostConstruct
    public void fillDB(){
        String password = new BCryptPasswordEncoder().encode("Password123");
        Category categoryKnuffels = new Category("knuffels");
        Category categorySleutelhangers = new Category("sleutelhangers");
        Category categoryPennen = new Category("pennen");
        Category categoryShirtsMannen = new Category("shirts mannen");
        Category categoryShirtsVrouwen = new Category("shirts vrouwen");
        Category categoryKoffieKoppen = new Category("koffiekoppen");
        Category categoryDrinkbussen = new Category("drinkbussen");
        Category categoryMondmaskers = new Category("mondmaskers");
        Category categoryKaarsenGeuren = new Category("kaarsen en geuren");
        Color colorRood = new Color("rood");
        Color colorWit = new Color("wit");
        Color colorGroen = new Color("groen");
        Color colorBruin = new Color("bruin");
        Color colorGeel = new Color("geel");
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
        Size size100g = new Size("100 gram");

        Customer customerGianniDeHerdt = new Customer("giannideherdt@gmail.com", password, "0479994529", "Kersstraat 17", "2200", "Belgium", Role.ADMIN,"Gianni" , "De Herdt");
        Customer customerThijsWouters = new Customer("thijswouters@gmail.com", password, "0479954719", "Hoekstraat 165", "1680", "Belgium", Role.ADMIN,"Thijs" , "Wouters");
        Customer customerJolienFoets = new Customer("jolienfoets@gmail.com", password, "0466544922", "Stepelaar 6A", "1700", "Belgium", Role.CUSTOMER,"Jolien" , "Foets");
        Customer customerBobLourdaux = new Customer("boblourdaux@gmail.com", password, "0495946569", "Sint-Schepersberg 45", "3040", "Belgium", Role.CUSTOMER,"Bob" , "Lourdaux");
        Customer customerKevinMaes = new Customer("kevinmaes@gmail.com", password, "0476281912", "Lambertuslaan 42", "2260", "Belgium", Role.CUSTOMER,"Kevin" , "Maes");
        Customer customerHelderCeyssens = new Customer("helderceyssens@gmail.com", password, "0476596168", "Koepel 186", "1540", "Belgium", Role.CUSTOMER,"Helder" , "Ceyssens");
        Review reviewGianniDeHerdtSleutelhanger = new Review( 5, "Zeer leuke sleutelhanger", "Hangt heel mooi aan mijn sleutelbundel. Lief en zacht!", customerGianniDeHerdt);
        Review reviewGianniDeHerdtOrangoetanKnuffel = new Review( 5, "Zeer leuke knuffel", "", customerGianniDeHerdt);
        Review reviewThijsWoutersPandaSleutelhanger = new Review(3.5, "Mooi", "", customerThijsWouters);
        Review reviewThijsWoutersBeertjeRK = new Review(3.5, "De beer", "Beetje lelijk maar heeft een groot hart", customerThijsWouters);
        Review reviewJolienFoetsPen = new Review(4.5, "Schrijft goed!", "", customerJolienFoets);
        Review reviewJolienFoetsKoffieKop = new Review(5, "Stevige kop.", "Perfect om de ochtend mee te beginnen, warmte blijft goed binnen de koffiekop.", customerJolienFoets);
        Organization organizationWWF = new Organization("supporters@wwf.be", password, "+3223400920", "Emile Jacqmainlaan 90", "1000", "Belgium", Role.ORGANIZATION, "WWF", "BE0408656248", "BE0408656248", "Sinds de oprichting in 1966 is WWF-België één van de belangrijkste natuurbeschermingsorganisaties in ons land. Als lid van het wereldwijde WWF-netwerk nemen we deel aan grote nationale en internationale projecten om de natuur te beschermen en te zorgen voor een duurzame toekomst voor de generaties na ons.", "Onze slogan ‘Together Possible!’ belichaamt onze werkstrategie en onze visie op een planeet waar mens en natuur in harmonie leven. WWF is afhankelijk van de steun van donateurs en donatrices, en van de samenwerking met lokale gemeenschappen, jonge generaties, private en publieke partners om duurzame natuurbeschermingsoplossingen te vinden. Alleen samen kunnen we beschermen wat ons in leven houdt: bossen, oceaan, zoet water, fauna en flora.", "WWF zet zich in om de achteruitgang van de natuur op onze planeet te stoppen en om te bouwen aan een toekomst waar de mens in harmonie leeft met de natuur.", "+3223400920", "supporters@wwf.be", Arrays.asList("https://adfinitas-statics-cdn.s3.eu-west-3.amazonaws.com/wwf/defisc-20/logo.jpg"));
        Organization organizationBKKS = new Organization("info@bkks.be", password, "+3211192819", "Diesterstraat 67A bus 001", "3800", "Belgium", Role.ORGANIZATION, "Belgisch KinderKanker Steunfonds", "BE0835627680", "0835627680", "Het Belgisch Kinder Kanker Steunfonds realiseert dromen en wensen van kinderen en betrekt het ganse gezin hierbij. Niet alleen het zieke kindje maar ook broers of zusjes en de ouders worden op deze speciale dag niet vergeten.", "Het Belgisch Kinder Kanker Steunfonds bezoekt regelmatig ziekenhuizen waar we dan alle aanwezige kinderen verwennen met speelgoed. En/of we schenken materiaal aan de afdeling. We denken ook aan het verplegend personeel en brengen voor hen ook een lekkere, kleine attentie mee. Want zij zorgen dag in, dag uit met heel veel liefde en toewijding voor al deze kinderen", "Zelfs in een land als België is het mogelijk om als gezin in financiële problemen te geraken na langdurige ziekte zoals kanker. Wij geven aan deze gezinnen dan financiële steun om deze moeilijke periode door te komen en op die manier al wat financiële zorgen en stress weg te nemen. Dit kunnen we mogelijk maken dankzij de “Vrienden van Bkks”", "+3223400920", "supporters@wwf.be", Arrays.asList("https://belgischkinderkankersteunfonds.be/wp-content/uploads/2020/02/cropped-logo-bkks-1.png"));
        Organization organizationBZN = new Organization("welkom@bzn.be", password, "032012210", "Herentalsebaan 74", "2100", "Belgium", Role.ORGANIZATION, "Bond Zonder Naam", "BE0469514642", "BE0469514642", "De Bond zonder Naam (BZN) is een Vlaamse maatschappijkritische beweging die vooral bekend is van de gratis maandspreuken die uitnodigen tot reflectie, verdieping en verandering. Uiterlijk, interesses, gewoonten, leeftijden, culturen of nationaliteiten: het zijn stuk voor stuk verschillen tussen mensen die we soms als grenzen ervaren. Bond zonder Naam wil deze grenzen overbruggen en ook het omgaan met diversiteit stimuleren opdat samen leven echt samenleven wordt, met respect voor diversiteit en ieders kwaliteit.", "Bond zonder Naam schreef voor de periode 2016-2020 een beleidsplan over onze visie, acties en campagnes, vormingsaanbod en vrijwilligerswerk. Wat heeft Bond zonder Naam nog in petto? Waar zien we groeikansen? Welke pijnpunten in onze samenleving willen we blootleggen en aanpakken? We lieten ons inspireren door het verhaal van bevriende organisaties, experten, leden en vrijwilligers. Telkens staan de meest kwetsbaren onder ons centraal: mensen in armoede, vluchtelingen, chronische zieken of mensen aan de buitenste rand van de samenleving. Iedereen verdient een betekenis- en hoopvol leven. Wij weten wat ons te doen staat.", "Bond zonder Naam wil zoveel mogelijk mensen vanuit het hart aanspreken en met elkaar verbinden. En zo een samenleving creëren die een warm verschil maakt, vooral voor wie minder kansen krijgt. Concreet willen we dit waarmaken door mensen te inspireren en helpen in hun groei. Want hoe meer inzicht we in onszelf en de wereld hebben, hoe meer we elkaar te bieden hebben. Dit engagement maken we hard voor en met mensen in kansarmoede, eenzaamheid of gevangenschap.", "+3223400920", "supporters@wwf.be", Arrays.asList("https://www.bzn.be/graphics/default-socialmedia.jpg"));
        Organization organizationThinkPink = new Organization("maite@think-pink.be", password, "+32475406602", "Researchdreef 12", "1070", "Belgium", Role.ORGANIZATION, "Think Pink", "0810893274", "BE0810893274", "Think Pink is de nationale borstkankerorganisatie die zich dagelijks inzet voor borstkankerpatiënten en hun familie.\n" +
                "\n" +
                "Think Pink heeft vier centrale doelstellingen:\n" +
                "\n" +
                "informeren en sensibiliseren\n" +
                "patiëntenrechten verdedigen \n" +
                "financieren van wetenschappelijk onderzoek\n" +
                "ondersteunen van zorg- en nazorgprojecten", "Borstkankerpatiënten hebben een basisrecht op de best mogelijke zorg. Wij willen elke dag opnieuw blijven inzetten voor een nog betere behandeling, informatieverstrekking, zorg en nazorg. Ook de financiële impact voor lotgenoten en hun familie is voor ons van groot belang.", "Dankzij donaties en fondsenwervingsacties konden wij de afgelopen jaren onderzoek naar een revolutionaire radiotherapiebehandeling bij borstkanker steunen. De nieuwe techniek kan jaarlijks tot 20 levens redden én zware hartaanvallen of longkankersymptomen bij tot wel 100 patiënten helpen voorkomen. Crawlbuikbestraling is een feit. Wij willen ons blijven inzetten voor deze revolutionaire behandeling. Daarom financieren we de komende twee jaar de opleidingen voor zorgpersoneel in Belgische radiotherapiecentra.", "+32475406602", "info@think-pink.be", Arrays.asList("https://upload.wikimedia.org/wikipedia/commons/4/4b/Logo_think-pink.jpg"));
        Organization organizationDamiaanactie = new Organization("info@damiaanactie.be", password, "+3224225911", "Leopold II-laan 263", "1081", "Belgium", Role.ORGANIZATION, "Damiaanactie", "0406694670", "BE05000000007575", "Damiaanactie is een Belgische medische non-profitorganisatie die zich inzet voor mensen met lepra, tuberculose en andere ziektes die vooral de kwetsbaarste bevolkingsgroepen treffen.", "Om lepra, tbc en leishmaniasis voorgoed te bestrijden zetten wij actief in op de volgende domeinen:\n" +
                "Medische hulp\n" +
                "Actieve opsporing van getroffen personen\n" +
                "Informeren en sensibiliseren\n" +
                "Opleidingen\n" +
                "Onderzoek\n" +
                "Steunen met kennis en materiaal\n" +
                "Care after Cure", "Bij Damiaanactie zijn de vrijwilligersinitiatieven gevarieerd en er is geen vast aantal uren. Of je nu één uur of het hele jaar door wil meedoen, je vindt altijd een formule die bij jou past. Voor al deze missies zullen onze vrijwilligerscoaches jouw bevoorrechte aanspreekpunt zijn voor een hechte en menselijke relatie.", "+3224225911", "info@damiaanactie.be", Arrays.asList("https://damiaanactie.be/wp-content/uploads/2019/10/RGB-LOGO-DA-NL-transparant.png", "https://damiaanactie.be/wp-content/themes/action-damien/assets/img/logo.png"));
        Organization organizationRodeKruis = new Organization("info@rodekruis.be", password, "+3215443322", "Motstraat 40", "2800", "Belgium", Role.ORGANIZATION, "Rode Kruis Vlaanderen", "2154897956", "BE0455024129", "Wij zijn een onafhankelijke vrijwilligersorganisatie. Via het Belgische Rode Kruis maken we deel uit van de Internationale Rode Kruis- en Rode Halve Maanbeweging. Onze missie is drieledig:\n" +
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
        Product productKoffieKopRK = new Product(categoryKoffieKoppen, organizationRodeKruis, "Koffiemok bedrukt", 6.5, "Steun het Rode Kruis door het aankopen en gebruiken van de officiële koffiemokken!", true, Arrays.asList("https://i.etsystatic.com/26520550/r/il/72e51c/3329982877/il_340x270.3329982877_kdtx.jpg"));
        Product productMondmaskerWit = new Product(categoryMondmaskers, organizationDamiaanactie, "Solidair mondmasker wit", 8.00, "Draag met trots de vlinders van Damiaanactie, terwijl je jezelf en anderen rondom je beschermt. Alle opbrengst gaat rechtstreeks naar onze projecten.", true, Arrays.asList("https://damiaanactie-shop.be/wp-content/uploads/2021/12/Ontwerp-zonder-titel-3.jpg"));
        Product productMondmaskerGroen = new Product(categoryMondmaskers, organizationDamiaanactie, "Solidair mondmasker groen", 8.00, "Trotseer de koude winterdagen met deze groene maskers van Damiaanactie. Vrolijke mondmaskers die deze sombere dagen wat kleur geven en opwarmen.", true, Arrays.asList("https://damiaanactie-shop.be/wp-content/uploads/2021/01/masque-damien-240web.jpg"));
        Product product2Mondmaskers = new Product(categoryMondmaskers, organizationDamiaanactie, "2 solidaire mondmaskers", 15.00, "Draag met trots de vlinders van Damiaanactie, terwijl je jezelf en anderen rondom je beschermt. Alle opbrengst gaat rechtstreeks naar onze projecten (€ 15  voor 2 mondmaskers).", true, Arrays.asList("https://damiaanactie-shop.be/wp-content/uploads/2021/12/Ontwerp-zonder-titel-2.jpg"));
        Product productShirtMannenTP = new Product(categoryShirtsMannen, organizationThinkPink, "Run for Think Pink T-shirt heren (wit)", 9.99, "Net zoals bij elke sport, hoort ook bij het lopen een juiste outfit. Vertegenwoordig met dit T-shirt Think Pink op elk evenement of draag hem tijdens het trainen. Het loopshirt heeft een zacht aanvoelende stof en dankzij de Equarea-technologie en de verluchtingen onder de arme wordt de transpiratie perfect afgevoerd.", true, Arrays.asList("https://www.think-pink.be/Portals/0/dtxArt/blok-afbeelding/bestand/20190820Think-Pink-2019Jeroen-Willems102-2_medium_1ba503c4-ce81-4f84-abbe-c5f02cef0356.jpg", "https://www.think-pink.be/Portals/0/dtxArt/blok-galerij/afbeelding/bestand/image_groot_ac0905cd-8657-4133-b05f-703e783572ba.png"));
        Product productShirtVrouwenTP = new Product(categoryShirtsVrouwen, organizationThinkPink, "Run for Think Pink T-shirt dames (roze)", 9.99, "Net zoals bij elke sport, hoort ook bij het lopen een juiste outfit. Vertegenwoordig met dit T-shirt Think Pink op elk evenement of draag hem tijdens het trainen. Het loopshirt heeft een zacht aanvoelende stof en dankzij de Equarea-technologie en de verluchtingen onder de arme wordt de transpiratie perfect afgevoerd.", true, Arrays.asList("https://www.think-pink.be/Portals/0/dtxArt/blok-afbeelding/bestand/6932E44F-510E-4C52-B60D-27AA69CD83A4_medium_6262025e-4c7e-468e-a0a2-2cb0237d0050.jpg"));
        Product productDrinkbusTP = new Product(categoryDrinkbussen, organizationThinkPink, "Drinkbus Think Pink", 4.99, "Deze leuke Think Pink-drinkbus zorgt voor de nodige verfrissing tijdens het sporten", true, Arrays.asList("https://www.think-pink.be/Portals/0/dtxArt/blok-galerij/afbeelding/bestand/Drinkbus1_groot_3918fe9e-785f-4dd5-a652-071148ed6146.jpg", "https://www.think-pink.be/Portals/0/dtxArt/blok-galerij/afbeelding/bestand/DSC1350groot6936867c-357f-4227-890b-58c5_groot_3dd61d86-f996-49ab-ac4a-6be2443b20a5.jpeg"));
        Product productGeurkaarsBZN = new Product(categoryKaarsenGeuren, organizationBZN, "Geurkaars Bond zonder Naam", 8.00, "Geurkaars ter ondersteuning van de Bond zonder Naam", true, Arrays.asList("https://i.imgur.com/9uiTrqz.png", "https://scontent-bru2-1.xx.fbcdn.net/v/t45.1600-4/cp0/q90/spS444/s526x296/17948818_23842562753170682_2138087182974320640_n.png.jpg?_nc_cat=111&ccb=1-5&_nc_sid=67cdda&_nc_ohc=EyD0jzQTYhwAX8aElSS&_nc_ht=scontent-bru2-1.xx&oh=00_AT9zk-4Xe2ziHjz1DDk6jyXObJY4jxlAXvgCWRr2CJXTyQ&oe=61F2E9BC"));
        Product productGeurstokjesBZN = new Product(categoryKaarsenGeuren, organizationBZN, "Geurstokjes Bond zonder Naam", 5.00, "Geurstokjes ter ondersteuning van de Bond zonder Naam", true, Arrays.asList("https://i.imgur.com/fW2Jkm2.png"));
        Product productSojakaarsBZN = new Product(categoryKaarsenGeuren, organizationBZN, "Sojakaars Bond zonder Naam", 10.00, "Sojakaars ter ondersteuning van de Bond zonder Naam", true, Arrays.asList("https://i.imgur.com/aOWdepM.png"));
        Product product3pennenBKKS = new Product(categoryPennen, organizationBKKS, "3 stylo's Belgisch KinderKanker Fonds", 6, "3 pennen met het logo van het Belgisch KinderKanker Fonds.", true, Arrays.asList("https://www.online-fanshop.be/wp-content/uploads/2020/11/stylo-bkks.png"));
        Product productMondmaskerBKKS = new Product(categoryMondmaskers, organizationBKKS, "Mondmasker Belgisch KinderKanker Fonds", 8.25, "Mondmasker met het logo van het Belgisch KinderKanker Fonds.", true, Arrays.asList("https://www.online-fanshop.be/wp-content/uploads/2020/11/mondmasker-BKKS.png"));
        Product productShirtBKKS = new Product(categoryShirtsMannen, organizationBKKS, "Stanno Pride T-Shirt", 24.99, "Dit stretchy sportshirt, onderdeel van de Stanno Pride collectie, is voorzien van het logo van BKKS. De ClimaTec finish zorgt voor optimale vochtafvoer en dankzij de ventilerende mesh-structuur op de bovenzijde en in de zij voelt het shirt licht en luchtig aan. De kraag is gemaakt van zacht rib-materiaal. De achterzijde van het shirt is net wat langer en heeft en mooie ronde afwerking.", true, Arrays.asList("https://i.imgur.com/dLA2Rab.png"));


        if(categoryRepository.count()==0){
            categoryRepository.save(categoryKnuffels);
            categoryRepository.save(categorySleutelhangers);
            categoryRepository.save(categoryPennen);
            categoryRepository.save(categoryKoffieKoppen);
            categoryRepository.save(categoryShirtsMannen);
            categoryRepository.save(categoryShirtsVrouwen);
            categoryRepository.save(categoryDrinkbussen);
            categoryRepository.save(categoryKaarsenGeuren);
            categoryRepository.save(categoryMondmaskers);
        }
        if(colorRepository.count() == 0){
            colorRepository.save(colorRood);
            colorRepository.save(colorGroen);
            colorRepository.save(colorBruin);
            colorRepository.save(colorGeel);
            colorRepository.save(colorRoze);
            colorRepository.save(colorWit);
            colorRepository.save(colorBeige);
            colorRepository.save(colorZwartWit);
        }
        if(userRepository.count() == 0){
            customerRepository.save(customerGianniDeHerdt);
            customerRepository.save(customerThijsWouters);
            customerRepository.save(customerJolienFoets);
            customerRepository.save(customerBobLourdaux);
            customerRepository.save(customerKevinMaes);
            customerRepository.save(customerHelderCeyssens);
            organizationRepository.save(organizationWWF);
            organizationRepository.save(organizationRodeKruis);
            organizationRepository.save(organizationDamiaanactie);
            organizationRepository.save(organizationThinkPink);
            organizationRepository.save(organizationBZN);
            organizationRepository.save(organizationBKKS);
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
            productRepository.save(productGeurkaarsBZN);
            productRepository.save(productGeurstokjesBZN);
            productRepository.save(productSojakaarsBZN);
            productRepository.save(productMondmaskerBKKS);
            productRepository.save(product3pennenBKKS);
            productRepository.save(productShirtBKKS);
        }
        if(reviewRepository.count() == 0){
            reviewRepository.save(reviewGianniDeHerdtSleutelhanger);
            reviewRepository.save(reviewGianniDeHerdtOrangoetanKnuffel);
            reviewRepository.save(reviewThijsWoutersPandaSleutelhanger);
            reviewRepository.save(reviewThijsWoutersBeertjeRK);
            reviewRepository.save(reviewJolienFoetsPen);
            reviewRepository.save(reviewJolienFoetsKoffieKop);
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

        if(sizeRepository.count() == 0){
            sizeRepository.save(sizeSmall);
            sizeRepository.save(sizeMedium);
            sizeRepository.save(sizeLarge);
            sizeRepository.save(sizeExtraLarge);
            sizeRepository.save(size10cm);
            sizeRepository.save(size14cm);
            sizeRepository.save(size15cm);
            sizeRepository.save(size05l);
            sizeRepository.save(size100g);
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
            stockRepository.save(new Stock(sizeSmall, colorRoze, productShirtVrouwenTP, 40));
            stockRepository.save(new Stock(size05l, colorRoze, productDrinkbusTP, 80));
            stockRepository.save(new Stock(size100g, colorRood, productGeurkaarsBZN, 40));
            stockRepository.save(new Stock(size15cm, colorGeel, productGeurstokjesBZN, 30));
            stockRepository.save(new Stock(size100g, colorWit, productSojakaarsBZN, 20));
            stockRepository.save(new Stock(size15cm, colorWit, product3pennenBKKS, 60));
            stockRepository.save(new Stock(sizeMedium, colorWit, productMondmaskerBKKS, 25));
            stockRepository.save(new Stock(sizeSmall, colorZwartWit, productShirtBKKS, 15));
            stockRepository.save(new Stock(sizeMedium, colorZwartWit, productShirtBKKS, 30));
            stockRepository.save(new Stock(sizeLarge, colorZwartWit, productShirtBKKS, 30));
        }
        if(donationRepository.count() == 0){
            donationRepository.save(new Donation(productOrangoetanKnuffel, organizationWWF, 2.5));
            donationRepository.save(new Donation(productPandaSleutelhanger, organizationWWF, 1.5));
            donationRepository.save(new Donation(productShirtVrouwenTP, organizationThinkPink, 15));
            donationRepository.save(new Donation(productStiftenDA, organizationDamiaanactie, 1));
            donationRepository.save(new Donation(productMondmaskerGroen, organizationDamiaanactie, 2));
            donationRepository.save(new Donation(productKoffieKopRK, organizationRodeKruis, 10));
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
        System.out.println("DB test donations: " + donationRepository.findAll().size() + " donations.");
    }
}
