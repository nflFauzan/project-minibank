package id.ac.tazkia.minibank.service;

import id.ac.tazkia.minibank.entity.Nasabah;
import id.ac.tazkia.minibank.entity.NasabahStatus;
import id.ac.tazkia.minibank.repository.NasabahRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SupervisorNasabahApprovalService {

    private final NasabahRepository nasabahRepository;

    public List<Nasabah> listPending() {
        return nasabahRepository.findByStatusOrderByCreatedAtDesc(NasabahStatus.PENDING);
    }

    public long pendingCount() {
        return nasabahRepository.countByStatus(NasabahStatus.PENDING);
    }

    public Nasabah getByIdOrThrow(Long id) {
        return nasabahRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Nasabah tidak ditemukan: " + id));
    }

    @Transactional
    public void approve(Long id, String supervisorName, String notes) {
        Nasabah n = getByIdOrThrow(id);

        n.setStatus(NasabahStatus.ACTIVE);
        n.setApprovedBy(supervisorName);
        n.setApprovedAt(LocalDateTime.now());
        n.setApprovalNotes(notes);
        n.setRejectionReason(null);

        nasabahRepository.save(n);
    }

    @Transactional
    public void reject(Long id, String supervisorName, String reason, String notes) {
        Nasabah n = getByIdOrThrow(id);

        n.setStatus(NasabahStatus.REJECTED);
        n.setApprovedBy(supervisorName);
        n.setApprovedAt(LocalDateTime.now());
        n.setApprovalNotes(notes);
        n.setRejectionReason(reason);

        nasabahRepository.save(n);
    }
}
