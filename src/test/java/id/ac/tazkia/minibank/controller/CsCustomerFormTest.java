package id.ac.tazkia.minibank.controller;

import id.ac.tazkia.minibank.entity.Nasabah;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Customer Form Normalization & Validation Tests")
class CsCustomerFormTest {

    @Test
    @DisplayName("normalizeUppercase - should uppercase string fields and trim")
    void testNormalizeUppercase() {
        Nasabah n = new Nasabah();
        n.setNamaLengkap("  ahmad fauzan  ");
        n.setTempatLahir("bogor");
        n.setNamaIbuKandung("siti fatimah");
        n.setAlamatIdentitas("jl. sentul city no. 12");
        n.setPekerjaan("mahasiswa");
        n.setJabatan("staff");
        n.setStatusPernikahan("belum menikah");
        n.setAgama("islam");
        n.setPenduduk("wni");

        n.onPrePersist();

        assertEquals("AHMAD FAUZAN", n.getNamaLengkap());
        assertEquals("BOGOR", n.getTempatLahir());
        assertEquals("SITI FATIMAH", n.getNamaIbuKandung());
        assertEquals("JL. SENTUL CITY NO. 12", n.getAlamatIdentitas());
        assertEquals("MAHASISWA", n.getPekerjaan());
        assertEquals("STAFF", n.getJabatan());
        assertEquals("BELUM MENIKAH", n.getStatusPernikahan());
        assertEquals("ISLAM", n.getAgama());
        assertEquals("WNI", n.getPenduduk());
        assertEquals("INDONESIA", n.getNegara(), "WNI harus otomatis mengisi negara INDONESIA jika kosong");
    }
}

