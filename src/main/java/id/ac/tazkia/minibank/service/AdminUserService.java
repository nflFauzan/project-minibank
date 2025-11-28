package id.ac.tazkia.minibank.service;

import id.ac.tazkia.minibank.entity.User;
import id.ac.tazkia.minibank.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminUserService {

    private final UserRepository userRepo;

    public List<User> listPending() {
        return userRepo.findByApprovedFalse();
    }

    public User findById(Long id) {
        return userRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    public void approve(Long id) {
        User u = findById(id);
        u.setApproved(true);
        u.setEnabled(true);
        // NOTE: sesuai pilihan kamu (free module switching) kita **tidak** mengubah roles di DB.
        userRepo.save(u);
    }

    public void reject(Long id) {
        // Pilihan implementasi: hapus user pending atau beri flag rejected.
        // Saya pilih: hapus record pendaftaran agar antrian bersih.
        userRepo.deleteById(id);
    }
}
