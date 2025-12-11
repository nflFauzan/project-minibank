package id.ac.tazkia.minibank.controller;

import id.ac.tazkia.minibank.service.PostalCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/locations")
public class LocationController {

    @Autowired
    private PostalCodeService postalCodeService;

    @GetMapping("/provinces")
    public List<String> getProvinces() {
        return postalCodeService.getAllProvinces();
    }

    @GetMapping("/cities")
    public List<String> getCities(@RequestParam("provinsi") String provinsi) {
        return postalCodeService.getCitiesByProvince(provinsi);
    }

    @GetMapping("/districts")
    public List<String> getDistricts(@RequestParam("provinsi") String provinsi,
                                     @RequestParam("kota") String kota) {
        return postalCodeService.getDistricts(provinsi, kota);
    }

    @GetMapping("/villages")
    public List<String> getVillages(@RequestParam("provinsi") String provinsi,
                                    @RequestParam("kota") String kota,
                                    @RequestParam("kecamatan") String kecamatan) {
        return postalCodeService.getVillages(provinsi, kota, kecamatan);
    }
}
