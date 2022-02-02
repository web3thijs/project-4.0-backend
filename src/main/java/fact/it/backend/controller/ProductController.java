package fact.it.backend.controller;

import fact.it.backend.model.*;
import fact.it.backend.repository.CategoryRepository;
import fact.it.backend.repository.OrganizationRepository;
import fact.it.backend.repository.ProductRepository;
import fact.it.backend.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "api/products")
public class ProductController {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    OrganizationRepository organizationRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @PostConstruct
    public void fillDatabase(){
        productRepository.save(new Product("WWF sleutelhanger orang-oetan", "Een schattige orang-oetan als sleutelhanger van 10cm.", 10.95, true, Arrays.asList("https://www.wwf.nl/globalassets/commerce/productafbeeldingen/wwf-sleutelhanger-orang-oetan-01.jpg", "https://www.wwf.nl/globalassets/commerce/productafbeeldingen/wwf-sleutelhanger-orang-oetan-02.jpg"), categoryRepository.findCategoryById(2), organizationRepository.findById(7)));
        productRepository.save(new Product("WWF sleutelhanger panda", "De enige echte WWF-panda als sleutelhanger van 10cm!", 10.95, true, Arrays.asList("https://www.wwf.nl/globalassets/commerce/productafbeeldingen/wwf-sleutelhanger-panda-01.jpg", "https://www.wwf.nl/globalassets/commerce/productafbeeldingen/wwf-sleutelhanger-panda-02.jpg"), categoryRepository.findCategoryById(2), organizationRepository.findById(7)));
        productRepository.save(new Product("Lief beertje Rode Kruis", "Slaap goed met de officiële beertjes-knuffel van het Rode Kruis!", 6.00, true, Arrays.asList("https://webcat.staci.com/WebCat3/custom/0231/_default//static/produits/P00042_MD.jpg"), categoryRepository.findCategoryById(1), organizationRepository.findById(12)));
        productRepository.save(new Product("Balpen blauw", "Steun het Rode Kruis door het aankopen en gebruiken van de officiële balpennen!", 3.5, true, Arrays.asList("https://shop.rodekruis.nl/pub/media/catalog/product/cache/c3031995953b3e91d5674d5b0a0af4b5/n/r/nrk.5011_1.jpg", "https://webcat.staci.com/WebCat3/custom/0231/_default//static/produits/P00012_MD.jpg"), categoryRepository.findCategoryById(3), organizationRepository.findById(12)));
        productRepository.save(new Product("Stiften damiaanactie", "Schrijf leprapatiënten niet af. Koop of verkoop de gekende rode, blauwe, groene en zwarte Damiaanactiestiften. Een pakje van vier kost €7.", 7.00, true, Arrays.asList("https://damiaanactie-shop.be/wp-content/uploads/2020/11/damien-eshop-042.jpg", "https://damiaanactie-shop.be/wp-content/uploads/2020/11/damien-eshop-046.jpg", "https://damiaanactie-shop.be/wp-content/uploads/2020/11/damien-eshop-052.jpg", "https://damiaanactie-shop.be/wp-content/uploads/2020/11/damien-eshop-053.jpg", "https://damiaanactie-shop.be/wp-content/uploads/2020/11/damien-eshop-054.jpg", "https://damiaanactie-shop.be/wp-content/uploads/2020/11/damien-eshop-055.jpg"), categoryRepository.findCategoryById(3), organizationRepository.findById(11)));
        productRepository.save(new Product("Koffiemok bedrukt","Steun het Rode Kruis door het aankopen en gebruiken van de officiële koffiemokken!", 6.5, true, Arrays.asList("https://i.etsystatic.com/26520550/r/il/72e51c/3329982877/il_340x270.3329982877_kdtx.jpg"),  categoryRepository.findCategoryById(6), organizationRepository.findById(12)));
        productRepository.save(new Product("Solidair mondmasker wit","Draag met trots de vlinders van Damiaanactie, terwijl je jezelf en anderen rondom je beschermt. Alle opbrengst gaat rechtstreeks naar onze projecten.", 8.00, true, Arrays.asList("https://damiaanactie-shop.be/wp-content/uploads/2021/12/Ontwerp-zonder-titel-3.jpg"),  categoryRepository.findCategoryById(8), organizationRepository.findById(11)));
        productRepository.save(new Product("Solidair mondmasker groen","Trotseer de koude winterdagen met deze groene maskers van Damiaanactie. Vrolijke mondmaskers die deze sombere dagen wat kleur geven en opwarmen.", 8.00, true, Arrays.asList("https://damiaanactie-shop.be/wp-content/uploads/2021/01/masque-damien-240web.jpg"),  categoryRepository.findCategoryById(8), organizationRepository.findById(11)));
        productRepository.save(new Product("2 solidaire mondmaskers", "Draag met trots de vlinders van Damiaanactie, terwijl je jezelf en anderen rondom je beschermt. Alle opbrengst gaat rechtstreeks naar onze projecten (€ 15  voor 2 mondmaskers).", 15.00, true, Arrays.asList("https://damiaanactie-shop.be/wp-content/uploads/2021/12/Ontwerp-zonder-titel-2.jpg"), categoryRepository.findCategoryById(8), organizationRepository.findById(11)));
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

    @GetMapping
    public Page<Product> findAll(@RequestParam(required = false, defaultValue = "0") Integer page, @RequestParam(required = false, defaultValue = "name") String sort, @RequestParam(required = false) String order, @RequestParam(required = false)String categorie, @RequestParam(required = false) String vzw, @RequestParam(required = false)Double prijsgt, @RequestParam(required = false)Double prijslt ){
            if(order != null && order.equals("desc")){
                Pageable requestedPageWithSortDesc = PageRequest.of(page, 9, Sort.by(sort).descending());
                Page<Product> products = productRepository.findAll(requestedPageWithSortDesc);
                return products;
            }
            else{
                Pageable requestedPageWithSort = PageRequest.of(page, 9, Sort.by(sort).ascending());
                Page<Product> products = productRepository.findAll(requestedPageWithSort);
                return products;
            }
        }
    @GetMapping("/organization/{organizationId}")
    public Page<Product> findProductsByOrganizationId(@PathVariable long organizationId, @RequestParam(required = false, defaultValue = "0") int page, @RequestParam(required = false, defaultValue = "name") String sort, @RequestParam(required = false)String order){
            if(order != null && order.equals("desc")){
                Pageable requestedPageWithSortDesc = PageRequest.of(page, 9, Sort.by(sort).descending());
                Page<Product> products = productRepository.findProductsByOrganizationId(organizationId,requestedPageWithSortDesc);
                return products;
            }
            else{
                Pageable requestedPageWithSort = PageRequest.of(page, 9, Sort.by(sort).ascending());
                Page<Product> products = productRepository.findProductsByOrganizationId(organizationId,requestedPageWithSort);
                return products;
            }
        }

    @GetMapping("/{id}")
    public Product find(@PathVariable long id){

        return productRepository.findProductById(id);
    }

    @PostMapping
    public ResponseEntity<?> addProduct(@RequestHeader("Authorization") String tokenWithPrefix, @RequestBody Product product){
        String token = tokenWithPrefix.substring(7);
        Map<String, Object> claims = jwtUtils.extractAllClaims(token);
        String role = claims.get("role").toString();
        long user_id = Long.parseLong(claims.get("user_id").toString());

        if(role.contains("ADMIN") || (role.contains("ORGANIZATION") && product.getOrganization().getId() == user_id)){
            productRepository.save(product);
            return ResponseEntity.ok(product);
        } else {
            return new ResponseEntity<String>("Forbidden", HttpStatus.FORBIDDEN);
        }
    }

    @PutMapping
    public ResponseEntity<?> updateProduct(@RequestHeader("Authorization") String tokenWithPrefix, @RequestBody Product updatedProduct){
        String token = tokenWithPrefix.substring(7);
        Map<String, Object> claims = jwtUtils.extractAllClaims(token);
        String role = claims.get("role").toString();
        long user_id = Long.parseLong(claims.get("user_id").toString());

        if(role.contains("ADMIN") || (role.contains("ORGANIZATION") && updatedProduct.getOrganization().getId() == user_id)){
            Product retrievedProduct = productRepository.findProductById(updatedProduct.getId());

            retrievedProduct.setCategory(updatedProduct.getCategory());
            retrievedProduct.setOrganization(updatedProduct.getOrganization());
            retrievedProduct.setName(updatedProduct.getName());
            retrievedProduct.setPrice(updatedProduct.getPrice());
            retrievedProduct.setDescription(updatedProduct.getDescription());
            retrievedProduct.setActive(updatedProduct.isActive());
            retrievedProduct.setImageUrl(updatedProduct.getImageUrl());

            productRepository.save(retrievedProduct);
            return ResponseEntity.ok(retrievedProduct);
        } else {
            return new ResponseEntity<String>("Forbidden", HttpStatus.FORBIDDEN);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteProduct(@RequestHeader("Authorization") String tokenWithPrefix, @PathVariable long id){
        String token = tokenWithPrefix.substring(7);
        Map<String, Object> claims = jwtUtils.extractAllClaims(token);
        String role = claims.get("role").toString();

        if(role.contains("ADMIN")){
            Product product = productRepository.findProductById(id);

            if(product != null){
                productRepository.delete(product);
                return ResponseEntity.ok().build();
            } else{
                return ResponseEntity.notFound().build();
            }
        } else {
            return new ResponseEntity<String>("Forbidden", HttpStatus.FORBIDDEN);
        }
    }
}