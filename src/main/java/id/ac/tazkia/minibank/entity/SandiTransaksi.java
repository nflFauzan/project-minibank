package id.ac.tazkia.minibank.entity;

import java.util.Arrays;
import java.util.Optional;

public enum SandiTransaksi {
    SETOR_TUNAI("52", "Setor Tunai"),
    SETORAN_KLIRING("244", "Setoran Kliring"),
    PENARIKAN_TUNAI("26", "Penarikan Tunai"),
    PENARIKAN_KLIRING("201", "Penarikan Kliring"),
    TRANSFER("213", "Transfer/Pemindahbukuan"),
    INCOMING_WU_SWIFT_SKN("61", "Incoming WU/SWIFT/SKN"),
    OUTGOING_WU_SWIFT_SKN("11", "Outgoing WU/SWIFT/SKN"),
    INCOMING_RTGS("836", "Incoming RTGS"),
    OUTGOING_RTGS("832", "Outgoing RTGS"),

    BIAYA_PEMINDAHBUKUAN_EBANKING("234", "Biaya Pemindahbukuan via e-Banking"),
    BIAYA_ADMINISTRASI("961", "Biaya Administrasi"),
    BIAYA_ATM("30", "Biaya ATM"),
    PENCAIRAN_PEMBIAYAAN("402", "Pencairan Pembiayaan"),
    ANGSURAN_PEMBIAYAAN("885", "Angsuran Pembiayaan"),
    PEMBAYARAN_EBANKING("977", "Pembayaran/Pembelian via e-Banking"),
    PAYROLL("68", "Payroll"),
    BAGI_HASIL_ATAU_BONUS("790", "Bagi Hasil atau Bonus"),
    PENCAIRAN_DEPOSITO("365", "Pencairan Deposito"),

    INSTALMENT("493", "Instalment"),
    STANDING_INSTRUCTION("214", "Standing Instruction"),
    ZAKAT("987", "Zakat"),
    PAJAK("41", "Pajak"),
    DENDA_DENDA("372", "Denda-denda"),
    KOREKSI("12", "Koreksi"),
    LAIN_LAIN("975", "Lain-lain");

    private final String code;
    private final String description;

    SandiTransaksi(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static Optional<SandiTransaksi> fromCode(String code) {
        if (code == null || code.isBlank()) {
            return Optional.empty();
        }
        return Arrays.stream(values())
                .filter(s -> s.code.equalsIgnoreCase(code.trim()))
                .findFirst();
    }

    public static String resolveDefault(TipeTransaksi tipe) {
        if (tipe == null) {
            return SETOR_TUNAI.getCode();
        }
        return switch (tipe) {
            case DEPOSIT -> SETOR_TUNAI.getCode();
            case WITHDRAWAL -> PENARIKAN_TUNAI.getCode();
            case TRANSFER -> TRANSFER.getCode();
        };
    }
}

