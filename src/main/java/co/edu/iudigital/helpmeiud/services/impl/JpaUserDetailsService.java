package co.edu.iudigital.helpmeiud.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.iudigital.helpmeiud.models.entities.Consumer;
import co.edu.iudigital.helpmeiud.repositories.IConsumerRepository;

@Service
public class JpaUserDetailsService implements UserDetailsService{

    @Autowired
    private IConsumerRepository consumerRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Consumer user = consumerRepository.findByUsername(username);

        if(user == null){
            throw new UsernameNotFoundException(String.format("User %s not found", username));
        }

        List<GrantedAuthority> authorities = user.getRoles().stream()
        .map(r -> new SimpleGrantedAuthority(r.getName()))
        .collect(Collectors.toList());

        return new User(user.getUsername(),
        user.getPassword(), 
        true, 
        true, 
        true, 
        true, 
        authorities);
    }
    
    
}
