package id.ac.tazkia.minibank.controller.rest;

import id.ac.tazkia.minibank.entity.PostalCode;
import id.ac.tazkia.minibank.repository.PostalCodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/postal-code")
@RequiredArgsConstructor
public class LocationApiController {

    private final PostalCodeRepository postalCodeRepository;

    @GetMapping("/{kodePos}")
    public Map<String, Object> byKodePos(@PathVariable String kodePos) {
        PostalCode pc = postalCodeRepository.findFirstByKodePos(kodePos)
                .orElseThrow(() -> new IllegalArgumentException("Kode pos tidak ditemukan"));

        Map<String, Object> out = new HashMap<>();
        out.put("kodePos", pc.getKodePos());
        out.put("provinsi", pc.getProvinsi());
        out.put("kota", pc.getKota());
        out.put("kecamatan", pc.getKecamatan());
        out.put("kelurahan", pc.getKelurahan());
        return out;
    }

    @GetMapping("/provinces")
    public List<String> provinces() {
        return postalCodeRepository.findDistinctProvinsi();
    }

    @GetMapping("/cities")
    public List<String> cities(@RequestParam("prov") String prov) {
        return postalCodeRepository.findDistinctKotaByProvinsi(prov);
    }

    @GetMapping("/districts")
    public List<String> districts(@RequestParam("prov") String prov,
                                  @RequestParam("kota") String kota) {
        return postalCodeRepository.findDistinctKecamatan(prov, kota);
    }

    @GetMapping("/villages")
    public List<String> villages(@RequestParam("prov") String prov,
                                 @RequestParam("kota") String kota,
                                 @RequestParam("kec") String kec) {
        return postalCodeRepository.findDistinctKelurahan(prov, kota, kec);
    }

    @GetMapping("/codes")
    public List<String> codes(@RequestParam("prov") String prov,
                              @RequestParam("kota") String kota,
                              @RequestParam("kec") String kec,
                              @RequestParam("kel") String kel) {
        return postalCodeRepository.findKodePosByHierarchy(prov, kota, kec, kel);
    }
}
