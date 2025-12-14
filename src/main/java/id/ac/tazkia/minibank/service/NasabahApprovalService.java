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
    public void approve(Long id, String supervisorName, String notes) {
        Nasabah n = nasabahRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Nasabah tidak ditemukan: " + id));

        n.setStatus(NasabahStatus.ACTIVE);
        n.setApprovedBy(supervisorName);
        n.setApprovedAt(LocalDateTime.now());
        n.setApprovalNotes(notes);
        n.setRejectionReason(null);

        nasabahRepository.save(n);
    }
}
