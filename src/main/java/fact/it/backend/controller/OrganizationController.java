package fact.it.backend.controller;

import fact.it.backend.model.*;
import fact.it.backend.repository.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.List;

@RequestMapping(path = "api/organizations")
@RestController
public class OrganizationController {
    @Autowired
    OrganizationRepository organizationRepository;

    @PostConstruct
    public void fillDB(){
        if(organizationRepository.count() == 3){
            for(int i = 1; i < 4; i++){                organizationRepository.save(new Organization("test" + i + "@test.test", "passwd", "+3240000000", "teststraat" + i, "2400", "Belgium", Role.ORGANIZATION, "Org" + i, "regisnr", "vatnr", "about", "suppPhone", "suppMail"));

            }
        }

        System.out.println("DB test organizations: " + organizationRepository.findAll().size());
    }

    @GetMapping("")
    public List<Organization> findAll(){
        return organizationRepository.findByRole(Role.ORGANIZATION);
    }

    @GetMapping("/{id}")
    public Organization findById(@PathVariable String id){
        return organizationRepository.findByRoleAndId(Role.ORGANIZATION, id);
    }
}
