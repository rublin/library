package com.holyrandom.library.service;

import com.holyrandom.library.repository.ApplicationUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
@RequiredArgsConstructor
public class ApplicationUserService implements UserDetailsService {

    private final ApplicationUserRepository applicationUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var optional = applicationUserRepository.findByEmail(username);

        if (optional.isEmpty()) {
            throw new UsernameNotFoundException("User " + username + " not found");
        }

        var applicationUser = optional.get();
        return new User(applicationUser.getEmail(),
                applicationUser.getPassword(),
                applicationUser.isEnabled(),
                true,
                true,
                !applicationUser.isLocked(),
                new HashSet<>());
    }
}
