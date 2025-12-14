package id.ac.tazkia.minibank.service;

import id.ac.tazkia.minibank.dto.RekeningForm;
import id.ac.tazkia.minibank.entity.Rekening;
import id.ac.tazkia.minibank.repository.RekeningRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PembukaanRekeningService {

    private final RekeningRepository rekeningRepository;

    public PembukaanRekeningService(RekeningRepository rekeningRepository) {
        this.rekeningRepository = rekeningRepository;
    }

    @Transactional
    public Rekening bukaRekeningBaru(RekeningForm form, String namaPetugasCs) {
        Rekening r = new Rekening();

        // map form -> entity
        r.setCifNasabah(form.getCifNasabah());
        r.setNamaNasabah(form.getNamaNasabah());
        r.setNik(form.getNik());
        r.setAlamatDomisili(form.getAlamatDomisili());
        r.setNomorTelepon(form.getNomorTelepon());
        r.setEmail(form.getEmail());

        r.setJenisRekening(form.getJenisRekening());
        r.setProduk(form.getProduk());
        r.setNominalSetoranAwal(form.getNominalSetoranAwal());
        r.setSumberDanaAwal(form.getSumberDanaAwal());
        r.setTujuanPembukaan(form.getTujuanPembukaan());
        r.setJenisKartuAtm(form.getJenisKartuAtm());
        r.setFasilitasEchannel(form.getFasilitasEchannel());
        r.setMediaKomunikasi(form.getMediaKomunikasi());

        r.setCabangPembukaan(form.getCabangPembukaan());
        r.setPetugasCs(namaPetugasCs);   // << diisi dari user login, bukan dari form

        return rekeningRepository.save(r);
    }
}
