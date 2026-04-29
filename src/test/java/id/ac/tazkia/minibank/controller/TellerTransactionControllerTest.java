package id.ac.tazkia.minibank.controller;

import id.ac.tazkia.minibank.entity.Rekening;
import id.ac.tazkia.minibank.entity.TipeTransaksi;
import id.ac.tazkia.minibank.entity.Transaksi;
import id.ac.tazkia.minibank.repository.RekeningRepository;
import id.ac.tazkia.minibank.repository.TransaksiRepository;
import id.ac.tazkia.minibank.repository.UserRepository;
import id.ac.tazkia.minibank.service.TellerDepositService;
import id.ac.tazkia.minibank.service.TellerTransferService;
import id.ac.tazkia.minibank.service.TellerWithdrawalService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = TellerTransactionController.class)
@AutoConfigureMockMvc(addFilters = false)
@DisplayName("TellerTransactionController Unit Tests")
class TellerTransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean private TransaksiRepository transaksiRepository;
    @MockBean private RekeningRepository rekeningRepository;
    @MockBean private TellerDepositService tellerDepositService;
    @MockBean private TellerWithdrawalService tellerWithdrawalService;
    @MockBean private TellerTransferService tellerTransferService;
    @MockBean private UserRepository userRepository;

    // ==================== TRANSACTION LIST ====================

    @Test
    @DisplayName("GET /teller/transaction/list - menampilkan daftar transaksi")
    void list_shouldReturnView() throws Exception {
        when(transaksiRepository.search(any(), any(), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of()));

        mockMvc.perform(get("/teller/transaction/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("teller/transaction/list"))
                .andExpect(model().attributeExists("page", "active"));
    }

    @Test
    @DisplayName("GET /teller/transaction/list - dengan parameter query dan type")
    void list_withQueryAndType() throws Exception {
        when(transaksiRepository.search(eq("budi"), eq(TipeTransaksi.DEPOSIT), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of()));

        mockMvc.perform(get("/teller/transaction/list")
                        .param("q", "budi")
                        .param("type", "DEPOSIT")
                        .param("page", "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("teller/transaction/list"));
    }

    @Test
    @DisplayName("GET /teller/transaction/{id} - view detail transaksi berhasil")
    void view_shouldReturnDetail() throws Exception {
        UUID id = UUID.randomUUID();
        Rekening r = new Rekening();
        r.setNomorRekening("54300000101");
        
        Transaksi t = new Transaksi();
        t.setId(id);
        t.setGroupId(UUID.randomUUID());
        t.setTipe(TipeTransaksi.DEPOSIT);
        t.setRekening(r);
        t.setJumlah(new BigDecimal("500000"));
        t.setSaldoSebelum(new BigDecimal("1000000"));
        t.setSaldoSesudah(new BigDecimal("1500000"));

        when(transaksiRepository.findById(id)).thenReturn(Optional.of(t));
        when(transaksiRepository.findByGroupIdOrderByProcessedAtAsc(any())).thenReturn(List.of(t));

        mockMvc.perform(get("/teller/transaction/" + id))
                .andExpect(status().isOk())
                .andExpect(view().name("teller/transaction/view"))
                .andExpect(model().attributeExists("tx", "groupTx"));
    }

    @Test
    @DisplayName("GET /teller/transaction/{id} - not found throws exception")
    void view_notFound() throws Exception {
        UUID id = UUID.randomUUID();
        when(transaksiRepository.findById(id)).thenReturn(Optional.empty());

        try {
            mockMvc.perform(get("/teller/transaction/" + id));
            fail("Should have thrown ServletException");
        } catch (jakarta.servlet.ServletException e) {
            assertTrue(e.getCause() instanceof EntityNotFoundException);
        }
    }

    // ==================== DEPOSIT ====================

    @Test
    @DisplayName("GET /teller/transaction/deposit - list rekening untuk deposit")
    void depositSelect_shouldReturnView() throws Exception {
        when(rekeningRepository.searchActiveForTeller(any(), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of()));

        mockMvc.perform(get("/teller/transaction/deposit"))
                .andExpect(status().isOk())
                .andExpect(view().name("teller/transaction/deposit_select"))
                .andExpect(model().attributeExists("page", "active"));
    }

    @Test
    @DisplayName("GET /teller/transaction/deposit - dengan query parameter")
    void depositSelect_withQuery() throws Exception {
        when(rekeningRepository.searchActiveForTeller(eq("budi"), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of()));

        mockMvc.perform(get("/teller/transaction/deposit").param("q", "budi"))
                .andExpect(status().isOk())
                .andExpect(view().name("teller/transaction/deposit_select"));
    }

    @Test
    @DisplayName("GET /teller/transaction/deposit/{no} - form deposit berhasil")
    void depositForm_success() throws Exception {
        Rekening r = new Rekening();
        r.setNomorRekening("54300000101");
        r.setStatusActive(true);
        when(tellerDepositService.getActiveRekening("54300000101")).thenReturn(r);

        mockMvc.perform(get("/teller/transaction/deposit/54300000101"))
                .andExpect(status().isOk())
                .andExpect(view().name("teller/transaction/deposit_form"))
                .andExpect(model().attributeExists("rekening", "form"));
    }

    @Test
    @DisplayName("GET /teller/transaction/deposit/{no} - rekening tidak aktif redirect")
    void depositForm_error_redirect() throws Exception {
        when(tellerDepositService.getActiveRekening("99999"))
                .thenThrow(new IllegalStateException("Rekening tidak aktif"));

        mockMvc.perform(get("/teller/transaction/deposit/99999"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/teller/transaction/deposit"))
                .andExpect(flash().attributeExists("error"));
    }

    @Test
    @DisplayName("POST /teller/transaction/deposit/{no} - deposit berhasil")
    void depositProcess_success() throws Exception {
        var result = new TellerDepositService.DepositResult("T1000001", new BigDecimal("1500000"));
        when(tellerDepositService.deposit(any(), any(), any(), any(), any())).thenReturn(result);

        mockMvc.perform(post("/teller/transaction/deposit/54300000101")
                        .param("jumlahSetoran", "500000")
                        .param("keterangan", "Setoran Tunai")
                        .param("noReferensi", "REF001")
                        .principal(new UsernamePasswordAuthenticationToken("teller1", "pass")))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/teller/transaction/list"))
                .andExpect(flash().attributeExists("success"));
    }

    @Test
    @DisplayName("POST /teller/transaction/deposit/{no} - deposit gagal redirect ke form")
    void depositProcess_fail() throws Exception {
        doThrow(new RuntimeException("Saldo error")).when(tellerDepositService)
                .deposit(any(), any(), any(), any(), any());

        mockMvc.perform(post("/teller/transaction/deposit/54300000101")
                        .param("jumlahSetoran", "500000")
                        .param("keterangan", "Setoran Tunai")
                        .principal(new UsernamePasswordAuthenticationToken("teller1", "pass")))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/teller/transaction/deposit/54300000101"))
                .andExpect(flash().attributeExists("error"));
    }

    @Test
    @DisplayName("POST /teller/transaction/deposit/{no} - auth null fallback ke '-'")
    void depositProcess_noAuth() throws Exception {
        var result = new TellerDepositService.DepositResult("T1000001", new BigDecimal("1500000"));
        when(tellerDepositService.deposit(any(), any(), any(), any(), eq("-"))).thenReturn(result);

        mockMvc.perform(post("/teller/transaction/deposit/54300000101")
                        .param("jumlahSetoran", "500000")
                        .param("keterangan", "Setoran Tunai"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/teller/transaction/list"));
    }

    // ==================== WITHDRAWAL ====================

    @Test
    @DisplayName("GET /teller/transaction/withdrawal - list rekening untuk withdrawal")
    void withdrawalSelect_shouldReturnView() throws Exception {
        when(rekeningRepository.searchActiveForTeller(any(), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of()));

        mockMvc.perform(get("/teller/transaction/withdrawal"))
                .andExpect(status().isOk())
                .andExpect(view().name("teller/transaction/withdrawal_select"));
    }

    @Test
    @DisplayName("GET /teller/transaction/withdrawal/{no} - form withdrawal berhasil")
    void withdrawalForm_success() throws Exception {
        Rekening r = new Rekening();
        r.setNomorRekening("54300000101");
        r.setStatusActive(true);
        when(tellerWithdrawalService.getActiveRekening("54300000101")).thenReturn(r);

        mockMvc.perform(get("/teller/transaction/withdrawal/54300000101"))
                .andExpect(status().isOk())
                .andExpect(view().name("teller/transaction/withdrawal_form"))
                .andExpect(model().attributeExists("rekening", "form"));
    }

    @Test
    @DisplayName("GET /teller/transaction/withdrawal/{no} - error redirect")
    void withdrawalForm_error() throws Exception {
        when(tellerWithdrawalService.getActiveRekening("99999"))
                .thenThrow(new IllegalStateException("Rekening tidak aktif"));

        mockMvc.perform(get("/teller/transaction/withdrawal/99999"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/teller/transaction/withdrawal"))
                .andExpect(flash().attributeExists("error"));
    }

    @Test
    @DisplayName("POST /teller/transaction/withdrawal/{no} - withdrawal berhasil")
    void withdrawalProcess_success() throws Exception {
        var result = new TellerWithdrawalService.WithdrawalResult("T2000001", new BigDecimal("500000"));
        when(tellerWithdrawalService.withdraw(any(), any(), any(), any(), any())).thenReturn(result);

        mockMvc.perform(post("/teller/transaction/withdrawal/54300000101")
                        .param("jumlahPenarikan", "500000")
                        .param("keterangan", "Penarikan Tunai")
                        .principal(new UsernamePasswordAuthenticationToken("teller1", "pass")))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/teller/transaction/list"))
                .andExpect(flash().attributeExists("success"));
    }

    @Test
    @DisplayName("POST /teller/transaction/withdrawal/{no} - withdrawal gagal")
    void withdrawalProcess_fail() throws Exception {
        doThrow(new RuntimeException("Saldo tidak cukup")).when(tellerWithdrawalService)
                .withdraw(any(), any(), any(), any(), any());

        mockMvc.perform(post("/teller/transaction/withdrawal/54300000101")
                        .param("jumlahPenarikan", "9999999")
                        .param("keterangan", "Penarikan Tunai")
                        .principal(new UsernamePasswordAuthenticationToken("teller1", "pass")))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/teller/transaction/withdrawal/54300000101"))
                .andExpect(flash().attributeExists("error"));
    }

    @Test
    @DisplayName("POST /teller/transaction/withdrawal/{no} - auth null fallback ke '-'")
    void withdrawalProcess_noAuth() throws Exception {
        var result = new TellerWithdrawalService.WithdrawalResult("T2000001", new BigDecimal("500000"));
        when(tellerWithdrawalService.withdraw(any(), any(), any(), any(), eq("-"))).thenReturn(result);

        mockMvc.perform(post("/teller/transaction/withdrawal/54300000101")
                        .param("jumlahPenarikan", "500000")
                        .param("keterangan", "Penarikan Tunai"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/teller/transaction/list"));
    }

    // ==================== TRANSFER ====================

    @Test
    @DisplayName("GET /teller/transaction/transfer - step 1 pilih rekening sumber")
    void transferSelectSource_shouldReturnView() throws Exception {
        when(rekeningRepository.searchActiveForTeller(any(), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of()));

        mockMvc.perform(get("/teller/transaction/transfer"))
                .andExpect(status().isOk())
                .andExpect(view().name("teller/transaction/transfer_select_source"));
    }

    @Test
    @DisplayName("GET /teller/transaction/transfer/{sourceNo} - step 2 pilih rekening tujuan")
    void transferSelectTarget_shouldReturnView() throws Exception {
        Rekening source = new Rekening();
        source.setNomorRekening("SRC001");
        when(rekeningRepository.findByNomorRekening("SRC001")).thenReturn(Optional.of(source));
        when(rekeningRepository.searchActiveForTellerExclude(eq("SRC001"), any(), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of()));

        mockMvc.perform(get("/teller/transaction/transfer/SRC001"))
                .andExpect(status().isOk())
                .andExpect(view().name("teller/transaction/transfer_select_target"))
                .andExpect(model().attributeExists("sourceRekening", "page"));
    }

    @Test
    @DisplayName("GET /teller/transaction/transfer/{sourceNo} - sumber tidak ditemukan throws exception")
    void transferSelectTarget_notFound() throws Exception {
        when(rekeningRepository.findByNomorRekening("NOTFOUND")).thenReturn(Optional.empty());

        try {
            mockMvc.perform(get("/teller/transaction/transfer/NOTFOUND"));
            fail("Should have thrown ServletException");
        } catch (jakarta.servlet.ServletException e) {
            assertTrue(e.getCause() instanceof EntityNotFoundException);
        }
    }

    @Test
    @DisplayName("GET /teller/transaction/transfer/{sourceNo}/{targetNo} - step 3 form transfer")
    void transferForm_shouldReturnView() throws Exception {
        Rekening source = new Rekening();
        source.setNomorRekening("SRC001");
        Rekening target = new Rekening();
        target.setNomorRekening("DST001");

        when(rekeningRepository.findByNomorRekening("SRC001")).thenReturn(Optional.of(source));
        when(rekeningRepository.findByNomorRekening("DST001")).thenReturn(Optional.of(target));

        mockMvc.perform(get("/teller/transaction/transfer/SRC001/DST001"))
                .andExpect(status().isOk())
                .andExpect(view().name("teller/transaction/transfer_form"))
                .andExpect(model().attributeExists("sourceRekening", "targetRekening", "form"));
    }

    @Test
    @DisplayName("GET /teller/transaction/transfer/{sourceNo}/{targetNo} - target tidak ditemukan throws exception")
    void transferForm_targetNotFound() throws Exception {
        Rekening source = new Rekening();
        source.setNomorRekening("SRC001");
        when(rekeningRepository.findByNomorRekening("SRC001")).thenReturn(Optional.of(source));
        when(rekeningRepository.findByNomorRekening("NOTFOUND")).thenReturn(Optional.empty());

        try {
            mockMvc.perform(get("/teller/transaction/transfer/SRC001/NOTFOUND"));
            fail("Should have thrown ServletException");
        } catch (jakarta.servlet.ServletException e) {
            assertTrue(e.getCause() instanceof EntityNotFoundException);
        }
    }

    @Test
    @DisplayName("POST /teller/transaction/transfer/{sourceNo}/{targetNo} - berhasil")
    void transferProcess_success() throws Exception {
        UUID groupId = UUID.randomUUID();
        when(tellerTransferService.transfer(any(), any(), any(), any(), any(), any())).thenReturn(groupId);

        mockMvc.perform(post("/teller/transaction/transfer/SRC001/DST001")
                        .param("jumlah", "500000")
                        .param("keteranganTambahan", "Transfer dana")
                        .param("noReferensi", "REF001")
                        .principal(new UsernamePasswordAuthenticationToken("teller1", "pass")))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/teller/transaction/list"))
                .andExpect(flash().attributeExists("success"));
    }

    @Test
    @DisplayName("POST /teller/transaction/transfer/{sourceNo}/{targetNo} - gagal redirect")
    void transferProcess_fail() throws Exception {
        doThrow(new RuntimeException("Transfer error")).when(tellerTransferService)
                .transfer(any(), any(), any(), any(), any(), any());

        mockMvc.perform(post("/teller/transaction/transfer/SRC001/DST001")
                        .param("jumlah", "500000")
                        .param("keteranganTambahan", "Transfer dana")
                        .principal(new UsernamePasswordAuthenticationToken("teller1", "pass")))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/teller/transaction/transfer/SRC001/DST001"))
                .andExpect(flash().attributeExists("error"));
    }

    @Test
    @DisplayName("POST /teller/transaction/transfer - auth null fallback ke '-'")
    void transferProcess_noAuth() throws Exception {
        UUID groupId = UUID.randomUUID();
        when(tellerTransferService.transfer(any(), any(), any(), any(), any(), eq("-"))).thenReturn(groupId);

        mockMvc.perform(post("/teller/transaction/transfer/SRC001/DST001")
                        .param("jumlah", "500000")
                        .param("keteranganTambahan", "Transfer dana"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/teller/transaction/list"));
    }
}
