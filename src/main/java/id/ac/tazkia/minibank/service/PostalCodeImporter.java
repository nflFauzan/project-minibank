package id.ac.tazkia.minibank.service;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import id.ac.tazkia.minibank.repository.PostalCodeRepository;
import id.ac.tazkia.minibank.entity.PostalCode;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;


@Service
public class PostalCodeImporter {

    @Autowired
    private PostalCodeRepository repo;

    @PostConstruct
    public void loadData() throws Exception {
        if (repo.count() > 0) return; // sudah pernah import

        InputStream is = getClass().getResourceAsStream("/data/kodepos.csv");
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        String line;
        br.readLine(); // skip header

        while ((line = br.readLine()) != null) {
            String[] parts = line.split(",");

            PostalCode pc = new PostalCode();
            pc.setKodePos(parts[0]);
            pc.setProvinsi(parts[1]);
            pc.setKota(parts[2]);
            pc.setKecamatan(parts[3]);
            pc.setKelurahan(parts[4]);

            repo.save(pc);
        }
    }
}
