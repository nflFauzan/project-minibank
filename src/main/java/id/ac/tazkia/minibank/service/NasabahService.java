package id.ac.tazkia.minibank.service;

import id.ac.tazkia.minibank.entity.Nasabah;
import id.ac.tazkia.minibank.entity.NasabahStatus;
import id.ac.tazkia.minibank.repository.NasabahRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NasabahService {

    private final NasabahRepository nasabahRepository;


@Transactional(readOnly = true)
public List<Nasabah> listAllCustomers() {
    return nasabahRepository.findAll();
}

@Transactional(readOnly = true)
public Nasabah getById(Long id) {
    return nasabahRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Nasabah tidak ditemukan"));
}

@Transactional
public Nasabah updateNasabah(Long id, Nasabah form) {
    Nasabah n = getById(id);

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

    // Status tetap biarkan (jangan ubah manual di CS)
    return nasabahRepository.save(n);
}
}