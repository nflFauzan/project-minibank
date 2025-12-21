package id.ac.tazkia.minibank.dto;

import java.util.List;

public class DashboardSummaryDto {
    private long totalNasabah;
    private long totalRekening;
    private long totalProduk;
    private List<NasabahSummary> nasabahTerbaru;

    // getters & setters
    public long getTotalNasabah() { return totalNasabah; }
    public void setTotalNasabah(long totalNasabah) { this.totalNasabah = totalNasabah; }

    public long getTotalRekening() { return totalRekening; }
    public void setTotalRekening(long totalRekening) { this.totalRekening = totalRekening; }

    public long getTotalProduk() { return totalProduk; }
    public void setTotalProduk(long totalProduk) { this.totalProduk = totalProduk; }

    public List<NasabahSummary> getNasabahTerbaru() { return nasabahTerbaru; }
    public void setNasabahTerbaru(List<NasabahSummary> nasabahTerbaru) { this.nasabahTerbaru = nasabahTerbaru; }

    public static class NasabahSummary {
    private Long id;
    private String namaLengkap;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNamaLengkap() { return namaLengkap; }
    public void setNamaLengkap(String namaLengkap) { this.namaLengkap = namaLengkap; }
}

}
