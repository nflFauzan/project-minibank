package id.ac.tazkia.minibank.security;

import org.springframework.security.authentication.DisabledException;
import id.ac.tazkia.minibank.entity.User;
import id.ac.tazkia.minibank.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User u = userRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        if (!u.isApproved()) {
            // prevent login until approved
            throw new DisabledException("User not approved");
        }
        Set<GrantedAuthority> authorities = u.getRoles().stream()
                .map(r -> new SimpleGrantedAuthority(r.getName()))
                .collect(Collectors.toSet());
        // Always give ROLE_USER if not admin roles assigned
        if (authorities.isEmpty()) {
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        }
        return new org.springframework.security.core.userdetails.User(u.getUsername(), u.getPassword(), u.isEnabled(),
                true, true, true, authorities);
    }
}
