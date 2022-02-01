package fact.it.backend.controller;

import fact.it.backend.model.*;
import fact.it.backend.repository.OrganizationRepository;
import fact.it.backend.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "api/organizations")
public class OrganizationController {
    @Autowired
    OrganizationRepository organizationRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtils jwtUtils;

    @GetMapping
    public ResponseEntity<?> findAll(@RequestParam(required = false, defaultValue = "0") Integer page, @RequestParam(required = false, defaultValue = "organizationName")String sort, @RequestParam(required = false)String order){
                if(order != null && order.equals("desc")){
                    Pageable requestedPageWithSortDesc = PageRequest.of(page, 9, Sort.by(sort).descending());
                    Page<Organization> organizations = organizationRepository.findByRole(Role.ORGANIZATION, requestedPageWithSortDesc);
                    return ResponseEntity.ok(organizations);
                }
                else{
                    Pageable requestedPageWithSort = PageRequest.of(page, 9, Sort.by(sort).ascending());
                    Page<Organization> organizations = organizationRepository.findByRole(Role.ORGANIZATION, requestedPageWithSort);
                    return ResponseEntity.ok(organizations);
                }
        }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable long id){
        return ResponseEntity.ok(organizationRepository.findByRoleAndId(Role.ORGANIZATION, id));
    }

    @PutMapping
    public ResponseEntity<?> updateOrganization(@RequestHeader("Authorization") String tokenWithPrefix, @RequestBody Organization updatedOrganization){
        String token = tokenWithPrefix.substring(7);
        Map<String, Object> claims = jwtUtils.extractAllClaims(token);
        String role = claims.get("role").toString();
        long user_id = Long.parseLong(claims.get("user_id").toString());

        if(role.contains("ADMIN") || (role.contains("CUSTOMER") && updatedOrganization.getId() == user_id)){
            Organization retrievedOrganization= organizationRepository.findByRoleAndId(Role.ORGANIZATION, updatedOrganization.getId());

            retrievedOrganization.setEmail(updatedOrganization.getEmail());
            retrievedOrganization.setPassword(passwordEncoder.encode(updatedOrganization.getPassword()));
            retrievedOrganization.setPhoneNr(updatedOrganization.getPhoneNr());
            retrievedOrganization.setAddress(updatedOrganization.getAddress());
            retrievedOrganization.setPostalCode(updatedOrganization.getPostalCode());
            retrievedOrganization.setCountry(updatedOrganization.getCountry());
            retrievedOrganization.setRole(updatedOrganization.getRole());
            retrievedOrganization.setOrganizationName(updatedOrganization.getOrganizationName());
            retrievedOrganization.setCompanyRegistrationNr(updatedOrganization.getCompanyRegistrationNr());
            retrievedOrganization.setVatNr(updatedOrganization.getVatNr());
            retrievedOrganization.setWho(updatedOrganization.getWho());
            retrievedOrganization.setWhat(updatedOrganization.getWhat());
            retrievedOrganization.setHelp(updatedOrganization.getHelp());
            retrievedOrganization.setSupportPhoneNr(updatedOrganization.getSupportPhoneNr());
            retrievedOrganization.setSupportEmail(updatedOrganization.getSupportEmail());

            organizationRepository.save(retrievedOrganization);

            return ResponseEntity.ok(retrievedOrganization);
        } else {
            return new ResponseEntity<String>("Forbidden", HttpStatus.FORBIDDEN);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrganization(@RequestHeader("Authorization") String tokenWithPrefix, @PathVariable long id){
        String token = tokenWithPrefix.substring(7);
        Map<String, Object> claims = jwtUtils.extractAllClaims(token);
        String role = claims.get("role").toString();

        if(role.contains("ADMIN")){
            Organization organization = organizationRepository.findByRoleAndId(Role.ORGANIZATION, id);

            if(organization != null){
                organizationRepository.delete(organization);
                return ResponseEntity.ok().build();
            } else{
                return ResponseEntity.notFound().build();
            }
        } else {
            return new ResponseEntity<String>("Forbidden", HttpStatus.FORBIDDEN);
        }
    }


    @PostConstruct
    public void fillDatabase(){
        String password = new BCryptPasswordEncoder().encode("Password123");

        organizationRepository.save(new Organization("supporters@wwf.be", password, "+3223400920", "Emile Jacqmainlaan 90", "1000", "Belgium", Role.ORGANIZATION,"WWF", "BE0408656248", "BE0408656248", "Sinds de oprichting in 1966 is WWF-België één van de belangrijkste natuurbeschermingsorganisaties in ons land. Als lid van het wereldwijde WWF-netwerk nemen we deel aan grote nationale en internationale projecten om de natuur te beschermen en te zorgen voor een duurzame toekomst voor de generaties na ons.", "Onze slogan ‘Together Possible!’ belichaamt onze werkstrategie en onze visie op een planeet waar mens en natuur in harmonie leven. WWF is afhankelijk van de steun van donateurs en donatrices, en van de samenwerking met lokale gemeenschappen, jonge generaties, private en publieke partners om duurzame natuurbeschermingsoplossingen te vinden. Alleen samen kunnen we beschermen wat ons in leven houdt: bossen, oceaan, zoet water, fauna en flora.", "WWF zet zich in om de achteruitgang van de natuur op onze planeet te stoppen en om te bouwen aan een toekomst waar de mens in harmonie leeft met de natuur.", "+3223400920", "supporters@wwf.be", "https://adfinitas-statics-cdn.s3.eu-west-3.amazonaws.com/wwf/defisc-20/logo.jpg"));
        organizationRepository.save(new Organization("info@bkks.be", password, "+3211192819", "Diesterstraat 67A bus 001", "3800", "Belgium", Role.ORGANIZATION,"Belgisch KinderKanker Steunfonds", "BE0835627680", "0835627680", "Het Belgisch Kinder Kanker Steunfonds realiseert dromen en wensen van kinderen en betrekt het ganse gezin hierbij. Niet alleen het zieke kindje maar ook broers of zusjes en de ouders worden op deze speciale dag niet vergeten.", "Het Belgisch Kinder Kanker Steunfonds bezoekt regelmatig ziekenhuizen waar we dan alle aanwezige kinderen verwennen met speelgoed. En/of we schenken materiaal aan de afdeling. We denken ook aan het verplegend personeel en brengen voor hen ook een lekkere, kleine attentie mee. Want zij zorgen dag in, dag uit met heel veel liefde en toewijding voor al deze kinderen", "Zelfs in een land als België is het mogelijk om als gezin in financiële problemen te geraken na langdurige ziekte zoals kanker. Wij geven aan deze gezinnen dan financiële steun om deze moeilijke periode door te komen en op die manier al wat financiële zorgen en stress weg te nemen. Dit kunnen we mogelijk maken dankzij de “Vrienden van Bkks”", "+3223400920", "supporters@wwf.be", "https://belgischkinderkankersteunfonds.be/wp-content/uploads/2020/02/cropped-logo-bkks-1.png"));
        organizationRepository.save(new Organization("welkom@bzn.be", password, "032012210", "Herentalsebaan 74", "2100", "Belgium", Role.ORGANIZATION,"Bond Zonder Naam", "BE0469514642", "BE0469514642", "De Bond zonder Naam (BZN) is een Vlaamse maatschappijkritische beweging die vooral bekend is van de gratis maandspreuken die uitnodigen tot reflectie, verdieping en verandering. Uiterlijk, interesses, gewoonten, leeftijden, culturen of nationaliteiten: het zijn stuk voor stuk verschillen tussen mensen die we soms als grenzen ervaren. Bond zonder Naam wil deze grenzen overbruggen en ook het omgaan met diversiteit stimuleren opdat samen leven echt samenleven wordt, met respect voor diversiteit en ieders kwaliteit.", "Bond zonder Naam schreef voor de periode 2016-2020 een beleidsplan over onze visie, acties en campagnes, vormingsaanbod en vrijwilligerswerk. Wat heeft Bond zonder Naam nog in petto? Waar zien we groeikansen? Welke pijnpunten in onze samenleving willen we blootleggen en aanpakken? We lieten ons inspireren door het verhaal van bevriende organisaties, experten, leden en vrijwilligers. Telkens staan de meest kwetsbaren onder ons centraal: mensen in armoede, vluchtelingen, chronische zieken of mensen aan de buitenste rand van de samenleving. Iedereen verdient een betekenis- en hoopvol leven. Wij weten wat ons te doen staat.", "Bond zonder Naam wil zoveel mogelijk mensen vanuit het hart aanspreken en met elkaar verbinden. En zo een samenleving creëren die een warm verschil maakt, vooral voor wie minder kansen krijgt. Concreet willen we dit waarmaken door mensen te inspireren en helpen in hun groei. Want hoe meer inzicht we in onszelf en de wereld hebben, hoe meer we elkaar te bieden hebben. Dit engagement maken we hard voor en met mensen in kansarmoede, eenzaamheid of gevangenschap.", "+3223400920", "supporters@wwf.be", "https://www.bzn.be/graphics/default-socialmedia.jpg"));
        organizationRepository.save(new Organization("maite@think-pink.be", password, "+32475406602", "Researchdreef 12", "1070", "Belgium", Role.ORGANIZATION,"Think Pink", "0810893274", "BE0810893274", "Think Pink is de nationale borstkankerorganisatie die zich dagelijks inzet voor borstkankerpatiënten en hun familie.\n" +
                "\n" +
                "Think Pink heeft vier centrale doelstellingen:\n" +
                "\n" +
                "informeren en sensibiliseren\n" +
                "patiëntenrechten verdedigen \n" +
                "financieren van wetenschappelijk onderzoek\n" +
                "ondersteunen van zorg- en nazorgprojecten", "Borstkankerpatiënten hebben een basisrecht op de best mogelijke zorg. Wij willen elke dag opnieuw blijven inzetten voor een nog betere behandeling, informatieverstrekking, zorg en nazorg. Ook de financiële impact voor lotgenoten en hun familie is voor ons van groot belang.", "Dankzij donaties en fondsenwervingsacties konden wij de afgelopen jaren onderzoek naar een revolutionaire radiotherapiebehandeling bij borstkanker steunen. De nieuwe techniek kan jaarlijks tot 20 levens redden én zware hartaanvallen of longkankersymptomen bij tot wel 100 patiënten helpen voorkomen. Crawlbuikbestraling is een feit. Wij willen ons blijven inzetten voor deze revolutionaire behandeling. Daarom financieren we de komende twee jaar de opleidingen voor zorgpersoneel in Belgische radiotherapiecentra.", "+32475406602", "info@think-pink.be", "https://upload.wikimedia.org/wikipedia/commons/4/4b/Logo_think-pink.jpg"));
        organizationRepository.save(new Organization("info@damiaanactie.be", password, "+3224225911", "Leopold II-laan 263", "1081", "Belgium", Role.ORGANIZATION,"Damiaanactie", "0406694670", "BE05000000007575", "Damiaanactie is een Belgische medische non-profitorganisatie die zich inzet voor mensen met lepra, tuberculose en andere ziektes die vooral de kwetsbaarste bevolkingsgroepen treffen.", "Om lepra, tbc en leishmaniasis voorgoed te bestrijden zetten wij actief in op de volgende domeinen:\n" +
                "Medische hulp\n" +
                "Actieve opsporing van getroffen personen\n" +
                "Informeren en sensibiliseren\n" +
                "Opleidingen\n" +
                "Onderzoek\n" +
                "Steunen met kennis en materiaal\n" +
                "Care after Cure", "Bij Damiaanactie zijn de vrijwilligersinitiatieven gevarieerd en er is geen vast aantal uren. Of je nu één uur of het hele jaar door wil meedoen, je vindt altijd een formule die bij jou past. Voor al deze missies zullen onze vrijwilligerscoaches jouw bevoorrechte aanspreekpunt zijn voor een hechte en menselijke relatie.", "+3224225911", "info@damiaanactie.be", "https://damiaanactie.be/wp-content/uploads/2019/10/RGB-LOGO-DA-NL-transparant.png"));
        organizationRepository.save(new Organization("info@rodekruis.be", password, "+3215443322", "Motstraat 40", "2800", "Belgium", Role.ORGANIZATION,"Rode Kruis Vlaanderen", "2154897956", "BE0455024129", "Wij zijn een onafhankelijke vrijwilligersorganisatie. Via het Belgische Rode Kruis maken we deel uit van de Internationale Rode Kruis- en Rode Halve Maanbeweging. Onze missie is drieledig:\n" +
                        "\n" +
                        "Opkomen voor kwetsbare mensen in binnen- en buitenland.\n" +
                        "Actief zijn op het vlak van rampenbestrijding, zelfredzaamheid en bloedvoorziening.\n" +
                        "Bij dat alles doen we maximaal een beroep op vrijwilligers.", "Hulp verlenen bij rampen is één van onze basisopdrachten. Als partner van de overheid maken we deel uit van de algemene nood- en interventieplanning van de medische, sanitaire en psychosociale hulpverlening en nemen bij een ramp 3 belangrijke taken op ons: medische hulpverlening, vervoer van gewonden en psychosociale ondersteuning. ", "Bij een ramp beschikken de gewone hulpdiensten niet altijd over voldoende middelen. Onze SIT’s (Snel Interventie Team) springen hen meteen bij. Ze brengen alle materiaal mee om snel een medische post op te bouwen: opblaasbare tenten, verlichting, draagberries, medisch materiaal en spoedmedicatie. Onder leiding van artsen en verpleegkundigen bieden we gewonden de eerste zorgen. ", "+3215443322", "info@rodekruis.be", "https://www.rodekruis.be/img/logo.svg?1557833551"));
    }
}
