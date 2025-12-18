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
    public Nasabah createCustomer(Nasabah input, String createdByName) {
        // generate CIF
        String maxCif = nasabahRepository.findMaxCif();
        int next = 1;
        if (maxCif != null && !maxCif.isBlank()) {
            try {
                next = Integer.parseInt(maxCif) + 1;
            } catch (NumberFormatException ignored) {
                next = 1;
            }
        }
        String newCif = String.format("%08d", next);

        Nasabah n = new Nasabah();
        n.setCif(newCif);

        // wajib
        n.setNik(input.getNik());
        n.setNamaLengkap(input.getNamaLengkap());

        // optional (isi sesuai form kamu)
        n.setEmail(input.getEmail());
        n.setNoHp(input.getNoHp());
        n.setTempatLahir(input.getTempatLahir());
        n.setTanggalLahir(input.getTanggalLahir());
        n.setNamaIbuKandung(input.getNamaIbuKandung());
        n.setJenisKelamin(input.getJenisKelamin());
        n.setAgama(input.getAgama());

        n.setKodePosIdentitas(input.getKodePosIdentitas());
        n.setProvinsiIdentitas(input.getProvinsiIdentitas());
        n.setKotaIdentitas(input.getKotaIdentitas());
        n.setKecamatanIdentitas(input.getKecamatanIdentitas());
        n.setKelurahanIdentitas(input.getKelurahanIdentitas());
        n.setRtIdentitas(input.getRtIdentitas());
        n.setRwIdentitas(input.getRwIdentitas());

        // status sebelum approve supervisor = INACTIVE (kita pakai PENDING)
        n.setStatus(NasabahStatus.INACTIVE);
        n.setCreatedBy(createdByName);

        return nasabahRepository.save(n);
    }
}
