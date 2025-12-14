package id.ac.tazkia.minibank.security;

import id.ac.tazkia.minibank.entity.User;
import id.ac.tazkia.minibank.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepo;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String uname = (username == null) ? "" : username.trim();

        User u = userRepo.findByUsername(uname)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (!u.isApproved()) {
            throw new DisabledException("User not approved");
        }

        Set<GrantedAuthority> authorities = new HashSet<>();
        if (u.getRoles() != null) {
            u.getRoles().forEach(r -> {
                if (r != null && r.getName() != null) {
                    String roleName = r.getName().trim();
                    // amankan: Spring Security "hasRole('ADMIN')" butuh "ROLE_ADMIN"
                    if (!roleName.startsWith("ROLE_")) roleName = "ROLE_" + roleName;
                    authorities.add(new SimpleGrantedAuthority(roleName));
                }
            });
        }

        if (authorities.isEmpty()) {
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        }

        return org.springframework.security.core.userdetails.User.builder()
                .username(u.getUsername())
                .password(u.getPassword())
                .disabled(!u.isEnabled())
                .authorities(authorities)
                .build();
    }
}
