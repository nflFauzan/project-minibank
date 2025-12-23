package id.ac.tazkia.minibank.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class AccountOpenForm {
    private Long produkId;
    private BigDecimal nominalSetoranAwal;
    private String tujuanPembukaan;
}
