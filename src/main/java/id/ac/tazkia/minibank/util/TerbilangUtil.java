package id.ac.tazkia.minibank.util;

import java.math.BigDecimal;

public class TerbilangUtil {

    private static final String[] SATUAN = {
            "", "Satu", "Dua", "Tiga", "Empat", "Lima", "Enam", "Tujuh", "Delapan", "Sembilan", "Sepuluh", "Sebelas"
    };

    public static String toTerbilang(BigDecimal amount) {
        if (amount == null) {
            return "Nol Rupiah";
        }
        long nominal = amount.longValue();
        if (nominal == 0) {
            return "Nol Rupiah";
        }
        if (nominal < 0) {
            return ("Minus " + convert(Math.abs(nominal))).replaceAll("\\s+", " ").trim() + " Rupiah";
        }
        return convert(nominal).replaceAll("\\s+", " ").trim() + " Rupiah";
    }

    private static String convert(long n) {
        if (n < 12) {
            return SATUAN[(int) n];
        } else if (n < 20) {
            return convert(n - 10) + " Belas";
        } else if (n < 100) {
            return convert(n / 10) + " Puluh " + convert(n % 10);
        } else if (n < 200) {
            return "Seratus " + convert(n - 100);
        } else if (n < 1000) {
            return convert(n / 100) + " Ratus " + convert(n % 100);
        } else if (n < 2000) {
            return "Seribu " + convert(n - 1000);
        } else if (n < 1000000) {
            return convert(n / 1000) + " Ribu " + convert(n % 1000);
        } else if (n < 1000000000L) {
            return convert(n / 1000000) + " Juta " + convert(n % 1000000);
        } else if (n < 1000000000000L) {
            return convert(n / 1000000000L) + " Miliar " + convert(n % 1000000000L);
        } else {
            return convert(n / 1000000000000L) + " Triliun " + convert(n % 1000000000000L);
        }
    }
}

