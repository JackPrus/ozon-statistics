package by.prusco.ozonstatistics.service;

import by.prusco.ozonstatistics.config.exception.UncorrectUserException;
import by.prusco.ozonstatistics.dto.UserDetailsCustom;
import by.prusco.ozonstatistics.dto.request.UserRequest;
import by.prusco.ozonstatistics.entity.Authorities;
import by.prusco.ozonstatistics.entity.UserCustom;
import by.prusco.ozonstatistics.repository.AuthorityRepository;
import by.prusco.ozonstatistics.repository.UserCustomRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;

@Service
@Transactional
public class UserDetailsServiceCustom implements UserDetailsService {

    @Autowired
    UserCustomRepository userCustomRepository;
    @Autowired
    AuthorityRepository authrorityRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserCustom user = findUserByLoginOrEmail(username);
        return new UserDetailsCustom(user);
    }

    public UserCustom findUserByLoginOrEmail(String username) throws UsernameNotFoundException {

//        Optional<UserCustom> returnValue = userCustomRepository.findUserCustomByLogon(username);
//        if (returnValue.isEmpty()){
//            returnValue = userCustomRepository.findUserCustomByEmail(username);
//        }
//        if (returnValue.isEmpty()){
//            throw new UsernameNotFoundException("User not found. Username : " + username);
//        }
//        return returnValue.get();

        return userCustomRepository.findUserCustomByLogon(username)
                .orElse(userCustomRepository.findUserCustomByEmail(username)
                        .orElseThrow(() ->
                                new UsernameNotFoundException("User not found. Username : " + username)));
    }

    public UserCustom createUser(UserRequest userRequest) {
        if (!userRequest.getPassword().equals(userRequest.getPasswordRepeat()))
            throw new UncorrectUserException("passwords missmatched");
        UserCustom user = new UserCustom();

        user.setAuthorities(new HashSet<>(Collections.singleton(authrorityRepository.findAuthorityByAuthority(Authorities.USER.getName()))));
        user.setLogon(userRequest.getLogin());
        user.setEncodedPassword(bCryptPasswordEncoder.encode(userRequest.getPassword()));
        user.setEmail(userRequest.getEmail());
        return userCustomRepository.save(user);
    }

    public UserCustom updateUserData(UserRequest userRequest) {
        UserCustom user = findUserByLoginOrEmail(userRequest.getLogin());
        //update logic
        return userCustomRepository.save(user);
    }

    public boolean deleteUser(UserRequest userRequest){
        UserCustom user = findUserByLoginOrEmail(userRequest.getLogin());
        userCustomRepository.delete(user);
        return true;
    }
}
