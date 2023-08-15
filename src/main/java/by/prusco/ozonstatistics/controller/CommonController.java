package by.prusco.ozonstatistics.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collection;

@Controller
public class CommonController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/test")
    public ResponseEntity<String> testMethod(HttpServletRequest request){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        String username = authentication.getName();

        System.out.printf("В  метод мы зашли. Юзер: {%s}, aurhotities: {%s}\n", username, authorities.toString());
        return ResponseEntity.ok().body("testpage");
    }
}
