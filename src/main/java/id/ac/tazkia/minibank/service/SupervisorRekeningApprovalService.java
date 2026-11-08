package id.ac.tazkia.minibank.service;

import id.ac.tazkia.minibank.entity.ActivityType;
import id.ac.tazkia.minibank.entity.Rekening;
import id.ac.tazkia.minibank.entity.RekeningStatus;
import id.ac.tazkia.minibank.repository.RekeningRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SupervisorRekeningApprovalService {

    private final RekeningRepository rekeningRepository;
    private final UserActivityService userActivityService;

    public long pendingCount() {
        return rekeningRepository.countByStatus(RekeningStatus.PENDING);
    }

    public Rekening getByIdOrThrow(Long id) {
        return rekeningRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Rekening tidak ditemukan: " + id));
    }

    public List<Rekening> listByStatuses(List<RekeningStatus> statuses) {
        if (statuses == null || statuses.isEmpty()) return List.of();
        if (statuses.size() == 1 && statuses.getFirst() == RekeningStatus.PENDING) {
            return rekeningRepository.findByStatusOrderByCreatedAtDesc(RekeningStatus.PENDING);
        }
        return rekeningRepository.findByStatusInOrderByApprovedAtDesc(statuses);
    }

    public List<Rekening> listAllCombined() {
        List<Rekening> pending = rekeningRepository.findByStatusOrderByCreatedAtDesc(RekeningStatus.PENDING);
        List<Rekening> history = rekeningRepository.findByStatusInOrderByApprovedAtDesc(
                List.of(RekeningStatus.ACTIVE, RekeningStatus.REJECTED, RekeningStatus.CLOSED)
        );
        List<Rekening> all = new ArrayList<>(pending.size() + history.size());
        all.addAll(pending);
        all.addAll(history);
        return all;
    }

    @Transactional
    public void approve(Long id, String supervisorName, String notes) {
        Rekening r = getByIdOrThrow(id);
        r.setStatus(RekeningStatus.ACTIVE);
        r.setStatusActive(true);
        r.setApprovedBy(supervisorName);
        r.setApprovedAt(LocalDateTime.now());
        r.setApprovalNotes(notes);
        r.setRejectionReason(null);
        rekeningRepository.save(r);
        if (userActivityService != null) {
            userActivityService.log(
                    supervisorName, supervisorName, "ROLE_SUPERVISOR",
                    ActivityType.APPROVAL_ACCOUNT, "APPROVAL",
                    "Menyetujui pembukaan rekening " + r.getNomorRekening() + " (" + r.getNamaRekening() + ")",
                    r.getNomorRekening(), r.getNominalSetoranAwal(), "APPROVED"
            );
        }
    }

    @Transactional
    public void reject(Long id, String supervisorName, String reason, String notes) {
        if (!StringUtils.hasText(reason)) {
            throw new IllegalArgumentException("Alasan penolakan wajib diisi.");
        }
        Rekening r = getByIdOrThrow(id);
        r.setStatus(RekeningStatus.REJECTED);
        r.setStatusActive(false);
        r.setApprovedBy(supervisorName);
        r.setApprovedAt(LocalDateTime.now());
        r.setApprovalNotes(notes);
        r.setRejectionReason(reason.trim());
        rekeningRepository.save(r);
        if (userActivityService != null) {
            userActivityService.log(
                    supervisorName, supervisorName, "ROLE_SUPERVISOR",
                    ActivityType.APPROVAL_ACCOUNT, "APPROVAL",
                    "Menolak pembukaan rekening " + r.getNomorRekening() + " - Alasan: " + reason,
                    r.getNomorRekening(), r.getNominalSetoranAwal(), "REJECTED"
            );
        }
    }
}

