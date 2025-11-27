package id.ac.tazkia.minibank.service;

import id.ac.tazkia.minibank.entity.User;
import id.ac.tazkia.minibank.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminUserService {

    private final UserRepository repo;

    public void approve(Long id) {
        User u = repo.findById(id).orElseThrow();
        u.setApproved(true);
        u.setEnabled(true);
        repo.save(u);
    }
}
