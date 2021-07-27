package com.holyrandom.library.service;

import com.holyrandom.library.entity.ApplicationUser;
import com.holyrandom.library.exception.ConflictException;
import com.holyrandom.library.exception.NotFoundException;
import com.holyrandom.library.repository.ApplicationUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Slf4j
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

    public ApplicationUser createUser(ApplicationUser applicationUser) {
        var optional = applicationUserRepository.findByEmail(applicationUser.getEmail());

        if (optional.isPresent()) {
            throw new ConflictException("User already exists");
        }

        var created = applicationUserRepository.save(applicationUser);

        log.info("Created new user {}", applicationUser);

        return created;
    }

    public Page<ApplicationUser> getAll(Pageable pageable) {
        return applicationUserRepository.findAll(pageable);
    }

    public ApplicationUser get(Long id) {
        return applicationUserRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User with id " + id + " not found"));
    }

    public ApplicationUser update(Long id, ApplicationUser applicationUser) {
        var toBeUpdated = get(id);
        toBeUpdated.setFirstName(applicationUser.getFirstName());
        toBeUpdated.setLastName(applicationUser.getLastName());
        toBeUpdated.setEmail(applicationUser.getEmail());
        toBeUpdated.setPassword(applicationUser.getPassword());

        return applicationUserRepository.save(toBeUpdated);
    }

    public void delete(Long id) {
        var applicationUser = get(id);
        applicationUser.setEnabled(false);
        applicationUserRepository.save(applicationUser);

        log.info("User {} was disabled", applicationUser);
    }
}
