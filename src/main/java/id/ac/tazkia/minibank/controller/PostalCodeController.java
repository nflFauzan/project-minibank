package id.ac.tazkia.minibank.controller;

import id.ac.tazkia.minibank.dto.PostalCodeDto;
import id.ac.tazkia.minibank.service.PostalCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/postal-code")
public class PostalCodeController {

    @Autowired
    private PostalCodeService postalCodeService;

    @GetMapping("/{kodePos}")
    public ResponseEntity<?> getByKodePos(@PathVariable("kodePos") String kodePos) {
        return postalCodeService.findByKodePos(kodePos)
                .map(dto -> ResponseEntity.ok(dto))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
