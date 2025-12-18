package id.ac.tazkia.minibank.service;

import id.ac.tazkia.minibank.entity.Nasabah;
import id.ac.tazkia.minibank.entity.NasabahStatus;
import id.ac.tazkia.minibank.repository.NasabahRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class NasabahApprovalService {

    private final NasabahRepository nasabahRepository;

    @Transactional
    public void approve(Long nasabahId, String supervisorName, String notes) {
        Nasabah n = nasabahRepository.findById(nasabahId)
                .orElseThrow(() -> new IllegalArgumentException("Nasabah not found: " + nasabahId));

        // hanya boleh approve dari INACTIVE
        if (n.getStatus() != NasabahStatus.INACTIVE) {
            throw new IllegalStateException("Nasabah status bukan INACTIVE, tidak bisa di-approve. Status: " + n.getStatus());
        }

        n.setStatus(NasabahStatus.ACTIVE);
        n.setApprovedBy(supervisorName);
        n.setApprovedAt(LocalDateTime.now());
        n.setApprovalNotes(notes == null ? "" : notes);
        n.setRejectionReason(null);

        nasabahRepository.save(n);
    }

    @Transactional
    public void reject(Long nasabahId, String supervisorName, String reason) {
        Nasabah n = nasabahRepository.findById(nasabahId)
                .orElseThrow(() -> new IllegalArgumentException("Nasabah not found: " + nasabahId));

        if (n.getStatus() != NasabahStatus.INACTIVE) {
            throw new IllegalStateException("Nasabah status bukan INACTIVE, tidak bisa di-reject. Status: " + n.getStatus());
        }

        n.setStatus(NasabahStatus.REJECTED);
        n.setApprovedBy(supervisorName);
        n.setApprovedAt(LocalDateTime.now());
        n.setRejectionReason(reason == null ? "" : reason);

        nasabahRepository.save(n);
    }
}
