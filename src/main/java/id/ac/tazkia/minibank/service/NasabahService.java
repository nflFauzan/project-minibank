package id.ac.tazkia.minibank.service;

import id.ac.tazkia.minibank.entity.Nasabah;
import id.ac.tazkia.minibank.entity.NasabahStatus;
import id.ac.tazkia.minibank.repository.NasabahRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NasabahService {

    private final NasabahRepository nasabahRepository;

    @Transactional
    public Nasabah createNasabah(Nasabah form) {
        // createdBy dari user login
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getName())) {
            if (form.getCreatedBy() == null || form.getCreatedBy().isBlank()) {
                form.setCreatedBy(auth.getName());
            }
        }

        // default status
        if (form.getStatus() == null) {
            form.setStatus(NasabahStatus.INACTIVE);
        }

        // CIF auto-generate kalau kosong
        if (form.getCif() == null || form.getCif().isBlank()) {
            form.setCif(generateNextCif());
        }

        return nasabahRepository.save(form);
    }

    private String generateNextCif() {
        // CIF format: C0000001 (1 huruf + 7 digit)
        String max = nasabahRepository.findMaxCif();
        int next = 1;

        if (max != null && max.startsWith("C") && max.length() == 8) {
            try {
                next = Integer.parseInt(max.substring(1)) + 1;
            } catch (NumberFormatException ignored) {
                // fallback tetap next=1
            }
        }
        return String.format("C%07d", next);
    }

    @Transactional(readOnly = true)
    public List<Nasabah> listAllCustomers() {
        return nasabahRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Nasabah getById(Long id) {
        return nasabahRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Nasabah not found: " + id));
    }

    @Transactional
    public void updateNasabah(Long id, Nasabah form) {
        Nasabah n = nasabahRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Nasabah not found: " + id));

        // Salin semua field (full editable)
        n.setNik(form.getNik());
        n.setNamaSesuaiIdentitas(form.getNamaSesuaiIdentitas());
        n.setNamaIbuKandung(form.getNamaIbuKandung());
        n.setJenisKelamin(form.getJenisKelamin());
        n.setTempatLahir(form.getTempatLahir());
        n.setTanggalLahir(form.getTanggalLahir());
        n.setAgama(form.getAgama());
        n.setPenduduk(form.getPenduduk());
        n.setStatusPernikahan(form.getStatusPernikahan());
        n.setNegara(form.getNegara());
        n.setEmail(form.getEmail());
        n.setNoHp(form.getNoHp());
        n.setPekerjaan(form.getPekerjaan());
        n.setNamaPerusahaan(form.getNamaPerusahaan());
        n.setJabatan(form.getJabatan());
        n.setPenghasilanPerBulan(form.getPenghasilanPerBulan());

        // Alamat Identitas
        n.setAlamatIdentitas(form.getAlamatIdentitas());
        n.setProvinsiIdentitas(form.getProvinsiIdentitas());
        n.setKotaIdentitas(form.getKotaIdentitas());
        n.setKecamatanIdentitas(form.getKecamatanIdentitas());
        n.setKelurahanIdentitas(form.getKelurahanIdentitas());
        n.setRtIdentitas(form.getRtIdentitas());
        n.setRwIdentitas(form.getRwIdentitas());
        n.setKodePosIdentitas(form.getKodePosIdentitas());

        // Alamat Domisili
        n.setAlamatDomisili(form.getAlamatDomisili());
        n.setProvinsiDomisili(form.getProvinsiDomisili());
        n.setKotaDomisili(form.getKotaDomisili());
        n.setKecamatanDomisili(form.getKecamatanDomisili());
        n.setKelurahanDomisili(form.getKelurahanDomisili());
        n.setRtDomisili(form.getRtDomisili());
        n.setRwDomisili(form.getRwDomisili());
        n.setKodePosDomisili(form.getKodePosDomisili());

        // Status biarkan (CS tidak ubah)
        nasabahRepository.save(n);
    }
}
