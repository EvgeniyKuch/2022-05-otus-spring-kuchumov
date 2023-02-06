package ru.otus.library.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ru.otus.library.domain.User;
import ru.otus.library.repository.UserRepository;

import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component("userDetailsService")
public class DomainUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @HystrixCommand
    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        String lowercaseLogin = login.toLowerCase(Locale.ENGLISH);
        return userRepository.findByLogin(lowercaseLogin).map(this::createSpringSecurityUser)
                .orElseThrow(() -> new UsernameNotFoundException("User " + lowercaseLogin + " was not found in the database"));
    }

    private org.springframework.security.core.userdetails.User createSpringSecurityUser(User user) {
        Set<GrantedAuthority> grantedAuthorities = user.getAuthorities().stream()
                .map(SimpleGrantedAuthority::new).collect(Collectors.toSet());
        return new org.springframework.security.core.userdetails.User(user.getLogin(), user.getPassword(), grantedAuthorities);
    }
}
