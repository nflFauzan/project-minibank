package id.ac.tazkia.minibank.service;

import id.ac.tazkia.minibank.dto.PostalCodeDto;
import id.ac.tazkia.minibank.entity.PostalCode;
import id.ac.tazkia.minibank.repository.PostalCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PostalCodeService {

    @Autowired
    private PostalCodeRepository postalCodeRepository;

    public Optional<PostalCodeDto> findByKodePos(String kodePos) {
        if (kodePos == null) return Optional.empty();
        String trimmed = kodePos.trim();
        return postalCodeRepository.findFirstByKodePos(trimmed)
                .map(p -> new PostalCodeDto(p.getKodePos(), p.getProvinsi(), p.getKota(), p.getKecamatan(), p.getKelurahan()));
    }
}
