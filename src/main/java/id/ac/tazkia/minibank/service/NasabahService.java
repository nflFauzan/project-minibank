package id.ac.tazkia.minibank.service;

import id.ac.tazkia.minibank.entity.Nasabah;
import id.ac.tazkia.minibank.repository.NasabahRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NasabahService {

    @Autowired
    private NasabahRepository nasabahRepository;

    public Nasabah createNasabahBaru(Nasabah nasabah) {
        // validasi dasar NIK 16 digit (kalau mau lebih rumit, nanti)
        if (nasabah.getNik() != null && nasabah.getNik().length() != 16) {
            throw new IllegalArgumentException("NIK harus 16 digit");
        }

        String cifBaru = generateNextCif();
        nasabah.setCif(cifBaru);

        return nasabahRepository.save(nasabah);
    }

    private String generateNextCif() {
        // Format: C1xxxxxx (x = digit, mulai dari 000001)
        String lastCif = nasabahRepository.findMaxCif();
        int nextNumber = 1;

        if (lastCif != null && lastCif.startsWith("C1") && lastCif.length() >= 3) {
            String numericPart = lastCif.substring(2); // ambil setelah "C1"
            try {
                nextNumber = Integer.parseInt(numericPart) + 1;
            } catch (NumberFormatException ignored) {
                // kalau parsing gagal, biarkan nextNumber tetap 1
            }
        }

        return String.format("C1%06d", nextNumber);
    }
}
