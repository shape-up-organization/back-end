package br.com.shapeup.common.config.security.service;

import br.com.shapeup.adapters.output.repository.jpa.UserRepositoryJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class ShapeUpUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepositoryJpa userRepositoryJpa;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepositoryJpa.findByEmail(username)
                .map(ShapeUpUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("No user found"));
    }
}
