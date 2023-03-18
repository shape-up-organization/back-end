package br.com.shapeup.common.config.security;

import br.com.shapeup.adapters.output.repository.jpa.UserRepositoryJpa;
import br.com.shapeup.adapters.output.repository.model.UserEntity;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepositoryJpa userRepositoryJpa;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<UserEntity> userResponse = userRepositoryJpa.findByEmail(email);
        return userResponse.map(MyUserDetailsInfo::new).orElseThrow(() -> new UsernameNotFoundException("Couldn't find an User with email: " + email));
    }
}
