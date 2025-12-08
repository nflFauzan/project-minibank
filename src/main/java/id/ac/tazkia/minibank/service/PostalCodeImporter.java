package id.ac.tazkia.minibank.service;

import id.ac.tazkia.minibank.entity.PostalCode;
import id.ac.tazkia.minibank.repository.PostalCodeRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Import data kode pos dari file CSV ke tabel postal_code
 * secara otomatis saat aplikasi pertama kali jalan.
 *
 * Syarat:
 * - File CSV ada di: src/main/resources/data/kodepos.csv
 * - Delimiter: titik koma ';'
 * - Header: kode_pos;provinsi;kota;kecamatan;kelurahan
 */
@Service
public class PostalCodeImporter {

    private static final Logger log = LoggerFactory.getLogger(PostalCodeImporter.class);

    private static final String RESOURCE_PATH = "/data/kodepos.csv";
    private static final String DELIMITER = ",";
    private static final int BATCH_SIZE = 1000;

    @Autowired
    private PostalCodeRepository postalCodeRepository;

    @PostConstruct
    public void importIfEmpty() {
        try {
            long existing = postalCodeRepository.count();
            if (existing > 0) {
                log.info("Postal code table already has {} rows. Skip import.", existing);
                return;
            }

            InputStream is = getClass().getResourceAsStream(RESOURCE_PATH);
            if (is == null) {
                log.warn("CSV kodepos TIDAK ditemukan di path: {}", RESOURCE_PATH);
                return;
            }

            log.info("Mulai import data kodepos dari {}", RESOURCE_PATH);

            try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
                String line;
                boolean headerSkipped = false;
                List<PostalCode> batch = new ArrayList<>();
                long total = 0;

                while ((line = br.readLine()) != null) {
                    // skip header
                    if (!headerSkipped) {
                        headerSkipped = true;
                        continue;
                    }

                    line = line.trim();
                    if (line.isEmpty()) {
                        continue;
                    }

                    // pakai delimiter ';' sesuai CSV kamu
                    String[] parts = line.split(DELIMITER, -1);
                    if (parts.length < 5) {
                        log.warn("Baris CSV tidak valid (kolom < 5), dilewati: {}", line);
                        continue;
                    }

                    PostalCode pc = new PostalCode();
                    pc.setKodePos(parts[0].trim());
                    pc.setProvinsi(parts[1].trim());
                    pc.setKota(parts[2].trim());
                    pc.setKecamatan(parts[3].trim());
                    pc.setKelurahan(parts[4].trim());

                    batch.add(pc);
                    total++;

                    if (batch.size() >= BATCH_SIZE) {
                        postalCodeRepository.saveAll(batch);
                        batch.clear();
                        log.info("Imported {} rows kodepos sejauh ini…", total);
                    }
                }

                if (!batch.isEmpty()) {
                    postalCodeRepository.saveAll(batch);
                }

                long finalCount = postalCodeRepository.count();
                log.info("Import data kodepos SELESAI. Total baris di DB: {}", finalCount);
            }
        } catch (Exception e) {
            log.error("Gagal import data kodepos dari CSV", e);
        }
    }
}
