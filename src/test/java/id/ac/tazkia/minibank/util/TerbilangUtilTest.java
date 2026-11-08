package id.ac.tazkia.minibank.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TerbilangUtilTest {

    @Test
    @DisplayName("Null and Zero should return Nol Rupiah")
    void testNullAndZero() {
        assertEquals("Nol Rupiah", TerbilangUtil.toTerbilang(null));
        assertEquals("Nol Rupiah", TerbilangUtil.toTerbilang(BigDecimal.ZERO));
    }

    @Test
    @DisplayName("Single digits and belasan")
    void testSingleDigitsAndBelasan() {
        assertEquals("Satu Rupiah", TerbilangUtil.toTerbilang(BigDecimal.valueOf(1)));
        assertEquals("Sepuluh Rupiah", TerbilangUtil.toTerbilang(BigDecimal.valueOf(10)));
        assertEquals("Sebelas Rupiah", TerbilangUtil.toTerbilang(BigDecimal.valueOf(11)));
        assertEquals("Lima Belas Rupiah", TerbilangUtil.toTerbilang(BigDecimal.valueOf(15)));
    }

    @Test
    @DisplayName("Hundreds and Thousands")
    void testHundredsAndThousands() {
        assertEquals("Seratus Rupiah", TerbilangUtil.toTerbilang(BigDecimal.valueOf(100)));
        assertEquals("Seratus Dua Puluh Lima Rupiah", TerbilangUtil.toTerbilang(BigDecimal.valueOf(125)));
        assertEquals("Seribu Rupiah", TerbilangUtil.toTerbilang(BigDecimal.valueOf(1000)));
        assertEquals("Dua Ribu Lima Ratus Rupiah", TerbilangUtil.toTerbilang(BigDecimal.valueOf(2500)));
    }

    @Test
    @DisplayName("Millions and Billions")
    void testMillionsAndBillions() {
        assertEquals("Satu Juta Rupiah", TerbilangUtil.toTerbilang(BigDecimal.valueOf(1000000)));
        assertEquals("Lima Puluh Juta Rupiah", TerbilangUtil.toTerbilang(BigDecimal.valueOf(50000000)));
        assertEquals("Satu Miliar Rupiah", TerbilangUtil.toTerbilang(BigDecimal.valueOf(1000000000L)));
    }

    @Test
    @DisplayName("Negative numbers")
    void testNegativeNumbers() {
        assertEquals("Minus Lima Ratus Ribu Rupiah", TerbilangUtil.toTerbilang(BigDecimal.valueOf(-500000)));
    }
}

