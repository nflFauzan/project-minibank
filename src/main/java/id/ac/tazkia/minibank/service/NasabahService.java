package id.ac.tazkia.minibank.service;

import id.ac.tazkia.minibank.entity.Nasabah;
import id.ac.tazkia.minibank.entity.NasabahStatus;
import id.ac.tazkia.minibank.repository.NasabahRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NasabahService {

    private final NasabahRepository nasabahRepository;

    public List<Nasabah> listAllCustomers() {
        return nasabahRepository.findAllByOrderByCreatedAtDesc();
    }

    public List<Nasabah> listByStatus(NasabahStatus status) {
        return nasabahRepository.findByStatusOrderByCreatedAtDesc(status);
    }

    @Transactional
    public Nasabah createNasabah(Nasabah input, String createdByName) {

        // ===== CIF: FIXED LENGTH 8 (C + 7 digit) =====
        // contoh: C0000001 s/d C9999999
        String maxCif = nasabahRepository.findMaxCif(); // bisa null
        int next = 1;

        if (maxCif != null && !maxCif.isBlank()) {
            // maxCif contoh: C10000002
            String digits = maxCif.replaceAll("\\D+", ""); // ambil angka saja
            if (!digits.isBlank()) {
                try {
                    next = Integer.parseInt(digits) + 1;
                } catch (NumberFormatException ignored) {
                    next = 1;
                }
            }
        }

        // pastikan 7 digit saja
        // kalau sudah lebih dari 9999999, stop biar tidak nabrak panjang kolom
        if (next > 9_999_999) {
            throw new IllegalStateException("CIF sudah melebihi batas 7 digit (maks C9999999).");
        }

        String newCif = "C" + String.format("%07d", next); // selalu 8 karakter

        Nasabah n = new Nasabah();
        n.setCif(newCif);

        // ===== DATA NASABAH =====
        n.setNik(input.getNik());
        n.setNamaLengkap(input.getNamaLengkap());
        n.setTempatLahir(input.getTempatLahir());
        n.setTanggalLahir(input.getTanggalLahir());
        n.setNamaIbuKandung(input.getNamaIbuKandung());
        n.setJenisKelamin(input.getJenisKelamin());
        n.setPenduduk(input.getPenduduk());
        n.setStatusPernikahan(input.getStatusPernikahan());
        n.setAgama(input.getAgama());
        n.setNegara(input.getNegara());

        // kontak
        n.setNoHp(input.getNoHp());
        n.setEmail(input.getEmail());

        // pekerjaan
        n.setPekerjaan(input.getPekerjaan());
        n.setNamaPerusahaan(input.getNamaPerusahaan());
        n.setJabatan(input.getJabatan());
        n.setPenghasilanPerBulan(input.getPenghasilanPerBulan());

        // ===== ALAMAT IDENTITAS =====
        n.setAlamatIdentitas(input.getAlamatIdentitas());
        n.setProvinsiIdentitas(input.getProvinsiIdentitas());
        n.setKotaIdentitas(input.getKotaIdentitas());
        n.setKecamatanIdentitas(input.getKecamatanIdentitas());
        n.setKelurahanIdentitas(input.getKelurahanIdentitas());
        n.setRtIdentitas(input.getRtIdentitas());
        n.setRwIdentitas(input.getRwIdentitas());
        n.setKodePosIdentitas(input.getKodePosIdentitas());

        // ===== ALAMAT DOMISILI =====
        n.setAlamatDomisili(input.getAlamatDomisili());
        n.setProvinsiDomisili(input.getProvinsiDomisili());
        n.setKotaDomisili(input.getKotaDomisili());
        n.setKecamatanDomisili(input.getKecamatanDomisili());
        n.setKelurahanDomisili(input.getKelurahanDomisili());
        n.setRtDomisili(input.getRtDomisili());
        n.setRwDomisili(input.getRwDomisili());
        n.setKodePosDomisili(input.getKodePosDomisili());

        // status sebelum approval
        n.setStatus(NasabahStatus.INACTIVE);
        n.setCreatedBy(createdByName);

        return nasabahRepository.save(n);
    }
}
