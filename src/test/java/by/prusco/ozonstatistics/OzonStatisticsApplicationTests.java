package by.prusco.ozonstatistics;

import by.prusco.ozonstatistics.dto.request.UserRequest;
import by.prusco.ozonstatistics.entity.Authorities;
import by.prusco.ozonstatistics.entity.Authority;
import by.prusco.ozonstatistics.entity.UserCustom;
import by.prusco.ozonstatistics.repository.AuthorityRepository;
import by.prusco.ozonstatistics.repository.UserCustomRepository;
import by.prusco.ozonstatistics.service.UserDetailsServiceCustom;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Collections;
import java.util.HashSet;

@SpringBootTest
class OzonStatisticsApplicationTests {

    @Autowired
    UserCustomRepository userCustomRepository;
    @Autowired
    AuthorityRepository authorityRepository;
    @Autowired
    UserDetailsServiceCustom userDetailsService;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Test
    void contextLoads() {
    }

    @Test
    void createAuthorities() {
        Authority userAuthority = authorityRepository.findAuthorityByAuthority(Authorities.USER.getName());
        Authority superadminAuthority = authorityRepository.findAuthorityByAuthority(Authorities.SUPERADMIN.getName());
        Authority unsubscribedUserAuthority = authorityRepository.findAuthorityByAuthority(Authorities.UNSUBSCRIBEDUSER.getName());
        Authority adminAuthority = authorityRepository.findAuthorityByAuthority(Authorities.ADMIN.getName());

        if (userAuthority == null) {
            userAuthority = new Authority(Authorities.USER.getName());
            saveAuthority(userAuthority);
        }
        if (adminAuthority == null) {
            adminAuthority = new Authority(Authorities.ADMIN.getName());
            saveAuthority(adminAuthority);
        }
        if (superadminAuthority == null) {
            superadminAuthority = new Authority(Authorities.SUPERADMIN.getName());
            saveAuthority(superadminAuthority);
        }
        if (unsubscribedUserAuthority == null) {
            unsubscribedUserAuthority = new Authority(Authorities.UNSUBSCRIBEDUSER.getName());
            saveAuthority(unsubscribedUserAuthority);
        }
    }

    @Test
    void createSuperAdmin() {
        int superadminquantity = (int) userCustomRepository.findAll().stream()
                .flatMap(u -> u.getAuthorities().stream())
                .filter(a -> a.getAuthority().equals(Authorities.SUPERADMIN.getName()))
                .count();

        if (superadminquantity == 0) {
            UserCustom superadmin = new UserCustom();
            superadmin.setLogon("superadmin");
            superadmin.setEmail("streetbunt9@mail.ru");
            superadmin.setEncodedPassword(bCryptPasswordEncoder.encode("1234"));
            superadmin.setAuthorities(new HashSet<>(Collections.singleton(authorityRepository.findAuthorityByAuthority(Authorities.SUPERADMIN.getName()))));
            superadmin.setFirstName("Dmitry");
            superadmin.setLastName("Prus");
            superadmin = userCustomRepository.save(superadmin);
            System.out.printf("Superadmin {%s} saved", superadmin.getLogon());
        }
    }

    @Test
    void createSimpleUser() {
        if (userCustomRepository.findUserCustomByLogon("qwer").isEmpty()) {
            UserRequest user1 = new UserRequest();
            user1.setEmail("user1@mail.ru");
            user1.setPassword("qwer");
            user1.setLogin("qwer");
            user1.setPasswordRepeat("qwer");
            saveUser(user1);
        }
        if (userCustomRepository.findUserCustomByLogon("rewq").isEmpty()) {
            UserRequest user2 = new UserRequest();
            user2.setEmail("user2@mail.ru");
            user2.setPassword("rewq");
            user2.setLogin("rewq");
            user2.setPasswordRepeat("rewq");
            saveUser(user2);
        }
    }

    private Authority saveAuthority(Authority authority) {
        Authority returnValue = authorityRepository.save(authority);
        System.out.printf("Authority {%s} saved", returnValue.getAuthority());
        return returnValue;
    }

    private UserCustom saveUser(UserRequest userRequest) {
        UserCustom returnValue = userDetailsService.createUser(userRequest);
        System.out.printf("Authority {%s} saved", returnValue.getLogon());
        return returnValue;
    }

}
