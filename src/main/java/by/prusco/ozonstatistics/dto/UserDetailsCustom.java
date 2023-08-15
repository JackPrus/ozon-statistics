package by.prusco.ozonstatistics.dto;

import by.prusco.ozonstatistics.entity.UserCustom;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

public class UserDetailsCustom implements UserDetails {

    private static final long serialVersionUID = 5343478654L;
    private final UserCustom userCustom;

    public UserDetailsCustom(UserCustom userCustom ) {
        this.userCustom = userCustom;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return userCustom.getAuthorities().stream()
                .map(auth -> new SimpleGrantedAuthority(auth.getAuthority()))
                .collect(Collectors.toSet());
    }

    @Override
    public String getPassword() {
        return userCustom.getEncodedPassword();
    }

    @Override
    public String getUsername() {
        return userCustom.getLogon();
    }

    @Override
    public boolean isAccountNonExpired() {return true;}

    @Override
    public boolean isAccountNonLocked() {return true;}

    @Override
    public boolean isCredentialsNonExpired() {return true;}

    @Override
    public boolean isEnabled() {return true;}
}
